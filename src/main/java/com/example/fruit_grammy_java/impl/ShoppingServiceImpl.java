package com.example.fruit_grammy_java.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.entity.Shopping;
import com.example.fruit_grammy_java.entity.ShoppingContent;
import com.example.fruit_grammy_java.ifs.ShoppingService;

import com.example.fruit_grammy_java.repository.ProductDao;
import com.example.fruit_grammy_java.repository.ShoppingContentDao;
import com.example.fruit_grammy_java.repository.ShoppingDao;

import com.example.fruit_grammy_java.vo.ShoppingRequest;
import com.example.fruit_grammy_java.vo.ShoppingResponse;

@Service
public class ShoppingServiceImpl implements ShoppingService {

	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShoppingDao shoppingDao;
	@Autowired
	private ShoppingContentDao shoppingContentDao;

	@Override
	public ShoppingResponse addData(ShoppingRequest req) {
		// �ʪ������e
		ShoppingContent shoppingContentList = new ShoppingContent();
		// �ʪ���
		Shopping shoppingBasic = new Shopping();

		// �~���ʶR�ƶq
		int shoppingAddNum = req.getNumber();

		// �ШD�R�a�b��
		String buyerAccount = req.getBuyerAccount();

// �T�{���O�_�w�g�[�J�L �s�X�������
		// �إߩҦ��ʪ������e�� List
		List<ShoppingContent> allList = shoppingContentDao.findAll();

		// ���~�s��
		String productId = req.getProduct();
		if(!StringUtils.hasText(productId)) {
			return new ShoppingResponse("�S�����");
		}
		// �ʪ������U�~�����ԲӸ��
		Optional<Product> ProductResult = productDao.findById(productId);
		Product productContext = ProductResult.get();
		// �ӫ~�s��, ItemID = productId
		String ItemID = productContext.getHsCode();

		// �Y���U��A�|��ܶR�a�b��
		if (StringUtils.hasText(buyerAccount)) {
			// allList = �ʪ������e��Ʈw�Ҧ����
			for (ShoppingContent item : allList) {
				// �Y�ӶR�a���ʪ������w���ӫ~���h�^��"����"
				if (item.getItemId().contains(ItemID) && item.getShoppingNumber().contains(buyerAccount)) {
					return new ShoppingResponse("����");
				}
			}
		}

		// ����R�a�ݨD�ƶq�j��w�s,�P�_���e�̡G�R�a�ݨD�ƶq�A�P�_����̡G�ӫ~�w�s�q
		if (shoppingAddNum > productContext.getNumber()) {
			return new ShoppingResponse("�W�L��a�ƶq�A�Э��U");
		}

		// ���b�A�Y�S�e�J�򥻸�ƫh����
		if (!StringUtils.hasText(buyerAccount) && !StringUtils.hasText(productId)) {
			return new ShoppingResponse("���~!�L�򥻸�T");
		}

		// �����n�J�L
		String code1 = "";
		// ���o�ӶR�a���ʪ�����T
		Optional<Shopping> ShoppingResult = shoppingDao.findById(buyerAccount);
		// �Y�S���ӶR�a���ʪ����A�h�s�W�@���ӶR�a���ʪ�����T
		if (!ShoppingResult.isPresent()) {
			shoppingBasic.setBuyerAccount(buyerAccount);
			code1 = buyerAccount + "_001";
			shoppingBasic.setBuyerContent(code1);
			shoppingDao.save(shoppingBasic);

			// �ʪ������e��Ʈw�]�n��۷s�W�A�]���O�s�ʪ�
			shoppingContentList.setShoppingNumber(code1);
			shoppingContentList.setItemId(productId);
			shoppingContentDao.save(shoppingContentList);

		}

		// �p�G�w�g�s�b��ơA�ԥX�J����ư����W�[
		if (ShoppingResult.isPresent()) {

			Shopping alreadyChart = ShoppingResult.get();

			// �N�h�ƪ��ʪ������e��T�v�@�[�J�}�C
			String[] ChartArr = alreadyChart.getBuyerContent().split(",");
			if (ChartArr.length >= 5) {
				return new ShoppingResponse("�s�W�W�L�����A�Х����b�A�ΧR���ʪ���");
			}

			// collection
			List<Integer> intArr = new ArrayList<>();

			for (int i = 0; i < ChartArr.length; i++) {
				// ex:as555@gmail.com_001 => as555@gmail.com & 001
				// numStr[0] = as555@gmail.com, numStr[1] = 001
				String[] numStr = ChartArr[i].split("_");
				int orderNum = Integer.valueOf(numStr[1]);
				intArr.add(orderNum);
			}

			// ���g�n�J�L
			// DSEC
			Collections.sort(intArr, Collections.reverseOrder());
			// �]���i��˧ǡA�ҥH [0] �|�O�̤j�Ʀr�A���X�� +1 �N�|�O�̷s�@��
			String orderStr = String.valueOf(intArr.get(0) + 1);
			// ex as555@gmail.com + _00 + �̷s�Ʀr
			String nextInfo = (buyerAccount + "_00" + orderStr);
			// �̷s���ʪ������e��ƥ[�W�w�s�b��
			String updateContent = nextInfo + "," + alreadyChart.getBuyerContent();
			// �s���@�����
			alreadyChart.setBuyerContent(updateContent);
			shoppingDao.save(alreadyChart);

			shoppingContentList.setShoppingNumber(nextInfo);
			shoppingContentList.setItemId(productId);
			shoppingContentDao.save(shoppingContentList);

		}

		shoppingContentList.setItemNum(shoppingAddNum);

		// productContext = �~�����ԲӸ��
		shoppingContentList.setItemName(productContext.getName());
		shoppingContentList.setDiscription(productContext.getDescription());
		shoppingContentList.setItemPrice(productContext.getPrice());
		shoppingContentList.setSellAccount(productContext.getSellerAccount());
		// �w�s�q
		shoppingContentList.setStock(productContext.getNumber());
		shoppingContentList.setShop_condition(false);
		//
		shoppingContentDao.save(shoppingContentList);

		return new ShoppingResponse("���\�[�J");
	}

	// ===================================================================
	private ShoppingResponse updateOrDelete(ShoppingRequest req, boolean isUpdate) {
		// �ϥΪ̬���
		String userAccount = req.getBuyerAccount();
		// �R��w�ʧǸ�
		String shoppingCode = req.getShoppingCode(); 

		if (!StringUtils.hasText(userAccount)) {
			return new ShoppingResponse("�L�ϥΤ��v��");
		}

		// �ھڴ��Ѫ�shoppingCode���n��s�R�������

		Optional<ShoppingContent> ShoppingContentResult = shoppingContentDao.findById(shoppingCode);

		ShoppingContent getShoppingContent = ShoppingContentResult.get();
		
		// ��s���p
		if (isUpdate) { 
			// ���~�ƶq
			int reqNum = req.getNumber(); 
			// ���~ID
			String productId = req.getProduct(); 
			Optional<Product> ProductList = productDao.findById(productId);
			Product produtInfo = ProductList.get();

			if (reqNum <= 0 && StringUtils.hasText(shoppingCode)) {
				return new ShoppingResponse("�Шϥβ����\��A�νЧ󥿬��j��s���Ʀr");
			} else if (reqNum > produtInfo.getNumber()) {
				return new ShoppingResponse("�w�W�L��a���Ѽƶq�W���A�нվ�A�̤j�Ȭ��w�s�ƶq");
			} else {
				getShoppingContent.setItemNum(reqNum);
				shoppingContentDao.save(getShoppingContent);
				return new ShoppingResponse("��s����");
			}

		}

		// �R�����p

		if (StringUtils.hasText(shoppingCode)) {

			shoppingContentDao.deleteById(shoppingCode);

			Optional<Shopping> ShoppingResult = shoppingDao.findById(userAccount);
			Shopping getShoppingResult = ShoppingResult.get();
			String[] shoppingOredrArr = getShoppingResult.getBuyerContent().split(",");

			if (shoppingOredrArr.length == 1) {
				shoppingDao.deleteById(userAccount);
			}
			List<String> resultArr = new ArrayList<>();// collection

			String textSet = "";
			for (int i = 0; i < shoppingOredrArr.length; i++) {

				String item = shoppingOredrArr[i];
				if (shoppingOredrArr[i].equals(shoppingCode)) {
					continue;
				}
				resultArr.add(item);
			}

			for (int i = 0; i < resultArr.size(); i++) {
				String item = resultArr.get(i);

				if (i == resultArr.size() - 1) {
					textSet = item + textSet;
				} else {
					textSet = textSet + "," + item;
				}

				getShoppingResult.setBuyerContent(textSet);
				shoppingDao.save(getShoppingResult);
			}
		}

		return new ShoppingResponse("�R������");

	}

	@Override
	public ShoppingResponse modiData(ShoppingRequest req) {

		return updateOrDelete(req, true);
	}

	@Override
	public ShoppingResponse deleteData(ShoppingRequest req) {

		return updateOrDelete(req, false);
	}

	// ======================================================
	@Override
	public ShoppingResponse getShoppingData(ShoppingRequest req) {
		List<ShoppingContent> userAllList = new ArrayList<>();

		String userAccount = req.getBuyerAccount();
		if (!StringUtils.hasText(userAccount)) {
			return new ShoppingResponse("�L�ϥΪ̤��v��");
		}

		userAllList = shoppingContentDao.findByShoppingNumberContaining(userAccount);
		if (userAllList.size() <= 0) {

			return new ShoppingResponse("�w����s���j�M����~");
		}
		return new ShoppingResponse(userAllList);

	}

}