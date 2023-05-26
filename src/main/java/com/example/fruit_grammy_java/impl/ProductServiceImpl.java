package com.example.fruit_grammy_java.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.ifs.ProductService;
import com.example.fruit_grammy_java.repository.ProductDao;
import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ProductResponse;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	// �ˬd����X�k�ʨp����k
	private boolean isValidDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(date);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// ��a�W�[�s�ӫ~
	// �W�[�ݶ��T
	@Override
	public ProductResponse addProduct(ProductRequest productReq) {
		// ���ˬd��ƪ�List
		List<Product> productList = productReq.getProductList();

		for (Product item : productList) {

			if (!StringUtils.hasText(item.getHsCode())) {
				return new ProductResponse("�ӫ~�s�X���o����");
			}

			if (productDao.existsById(item.getHsCode())) {
				return new ProductResponse("���ӫ~�w�W�[");
			}

			if (!StringUtils.hasText(item.getSellerAccount())) {
				return new ProductResponse("��a�b�����o����");
			}

			if (!StringUtils.hasText(item.getName())) {
				return new ProductResponse("�ӫ~�W�٤��o����");
			}

			if (!StringUtils.hasText(item.getType())) {
				return new ProductResponse("�������o����");
			}

			if (!StringUtils.hasText(item.getPlace())) {
				return new ProductResponse("���a���o����");
			}

			if (item.getNumber() <= 0) {
				return new ProductResponse("�ƶq���o�p��ε���0");
			}

			if (!StringUtils.hasText(item.getDescription())) {
				return new ProductResponse("�ӫ~�Ƶ��������o����");
			}

			if (!StringUtils.hasText(item.getDate())) {
				return new ProductResponse("�Ħ�������o����");
			}

			// �ˬd����O�_�ŦX�榡�B�㦳�X�k��
			String date = item.getDate();
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			if (!date.matches(regex)) {
				return new ProductResponse("����榡���~");
			}

			// �ˬd������O�_�X�k�� ex. 4044-04-04 ���i��
			if (!isValidDate(item.getDate())) {
                return new ProductResponse("������s�b");
            }
			
		}
		// �s�W���\�^�Ǹ�T�M���\�T��
		productDao.saveAll(productList);
		return new ProductResponse(productList, "�s�W���\");
	}

	// ��a�ק�W�[�ӫ~
	@Override
	public ProductResponse updateProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHsCode());
		if (!findProduct.isPresent()) {
			return new ProductResponse("���ӫ~���s�b");
		}

		// �s�b���o�����~��T
		Product getProduct = findProduct.get();

		// �קﲣ�~�W��
		if (StringUtils.hasText(productReq.getName())) {
			getProduct.setName(productReq.getName());
			productDao.save(getProduct);
		}

		// �ק����
		if (StringUtils.hasText(productReq.getType())) {
			getProduct.setType(productReq.getType());
			productDao.save(getProduct);
		}

		// �קﲣ�a
		if (StringUtils.hasText(productReq.getPlace())) {
			getProduct.setPlace(productReq.getPlace());
			productDao.save(getProduct);
		}

		// �ק�ƶq
		if (productReq.getNumber() > 0) {
			getProduct.setNumber(productReq.getNumber());
			productDao.save(getProduct);
		}

		// �ק���
		if (StringUtils.hasText(productReq.getDate())) {
			getProduct.setDate(productReq.getDate());
			// �ˬd����O�_�ŦX�榡
			String date = productReq.getDate();
			String regex = "\\d{4}-\\d{2}-\\d{2}";
			if (!date.matches(regex)) {
				return new ProductResponse("����榡���~");
			}
			productDao.save(getProduct);
		}

		// �ק����
		if (productReq.getPrice() > 0) {
			getProduct.setPrice(productReq.getPrice());
			productDao.save(getProduct);
		}

		// �ק�ԭz
		if (StringUtils.hasText(productReq.getDescription())) {
			getProduct.setDescription(productReq.getDescription());
			productDao.save(getProduct);
		}

		return new ProductResponse("�ק令�\", getProduct);
	}

	// �R�a�j�M �Ͳ��i�� -- �z�L���a�j�M
	@Override
	public ProductResponse searchPlaceProduct(String place) {

		List<Product> searchReq = productDao.findByPlace(place);

		List<ProductResponse> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(place)) {
			return new ProductResponse("�j�M���e���o����");
		}

		for (Product item : searchReq) {
			ProductResponse searchRes = new ProductResponse();
			searchRes.setPlace(place);
			searchRes.setName(item.getName());
			searchRes.setDate(item.getDate());
			searchRes.setType(item.getType());
			searchRes.setNumber(item.getNumber());
			searchRes.setPrice(item.getPrice());
			searchRes.setDescription(item.getDescription());
			searchAllRes.add(searchRes);
		}
		return new ProductResponse(searchAllRes);

	}

	@Override
	public ProductResponse searchSpecificProduct(String name) { // �W�[���j�M

		List<Product> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(name)) {// �p�G��null�A�������ѥ������

			List<Product> allSearchReq = productDao.findAll();
			return new ProductResponse(allSearchReq, "all info");
		}

		List<Product> searchReq = productDao.findByNameContaining(name);

		for (Product item : searchReq) {
			searchAllRes.add(item);
		}
		return new ProductResponse(searchAllRes, "specific info");
	}

	// ��a�w�W�[�ӫ~
	@Override
	public ProductResponse searchSellerProduct(String sellerAccount) {
		List<Product> searchReq = productDao.findBySellerAccount(sellerAccount);

		List<ProductResponse> searchAllRes = new ArrayList<>();

		for (Product item : searchReq) {
			ProductResponse searchRes = new ProductResponse();
			searchRes.setHsCode(item.getHsCode());
			searchRes.setPlace(item.getPlace());
			searchRes.setName(item.getName());
			searchRes.setDate(item.getDate());
			searchRes.setType(item.getType());
			searchRes.setNumber(item.getNumber());
			searchRes.setPrice(item.getPrice());
			searchRes.setDescription(item.getDescription());
			searchAllRes.add(searchRes);
		}

		return new ProductResponse("�j�M���G�p�U", searchAllRes);
	}

	// �R���w�W�[�ӫ~
	@Override
	public ProductResponse removeProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHsCode());
		if (!findProduct.isPresent()) {
			return new ProductResponse("���ӫ~���s�b");
		}

		try {
			Product product = findProduct.get();
			productDao.delete(product);
			return new ProductResponse("�ӫ~�w���\�R��");
		} catch (Exception e) {
			e.printStackTrace();
			return new ProductResponse("�R���ӫ~����");
		}

	}

}
