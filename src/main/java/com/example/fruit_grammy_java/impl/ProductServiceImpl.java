package com.example.fruit_grammy_java.impl;

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

	// ��a�W�[�s�ӫ~
	// �W�[�ݶ��T
	@Override
	public ProductResponse addProduct(ProductRequest productReq) {
		// ���ˬd��ƪ�List
		List<Product> productList = productReq.getProductList();

		for (Product item : productList) {
			if (!StringUtils.hasText(item.getHs_code())) {
				return new ProductResponse("�ӫ~�s�X���o����");
			}

			if (productDao.existsById(item.getHs_code())) {
				return new ProductResponse("���ӫ~�w�W�[");
			}

			if (!StringUtils.hasText(item.getSeller_account())) {
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

			if (!StringUtils.hasText(item.getDate())) {
				return new ProductResponse("�Ħ�������o����");
			}

			if (item.getPrice() <= 0) {
				return new ProductResponse("���椣�o�p��ε���0");
			}

			if (!StringUtils.hasText(item.getDescription())) {
				return new ProductResponse("�ӫ~�Ƶ��������o����");
			}
		}

		// �s�W���\�^�Ǹ�T�M���\�T��
		productDao.saveAll(productList);
		return new ProductResponse(productList, "�s�W���\");
	}

	// ��a�ק�W�[�ӫ~
	@Override
	public ProductResponse updateProduct(ProductRequest productReq) {
		Optional<Product> findProduct = productDao.findById(productReq.getHs_code());
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

		return new ProductResponse(getProduct);
	}

	// ��a�j�M�W�[�ӫ~
	@Override
	public ProductResponse searchAddProduct(String name) {
		List<Product> searchReq = productDao.findByName(name);

		List<ProductResponse> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(name)) {
			return new ProductResponse("�j�M���e���o����");
		}

		for (Product item : searchReq) {
			ProductResponse searchRes = new ProductResponse();
			searchRes.setPlace(name);
			searchRes.setDate(item.getDate());
			searchRes.setType(item.getType());
			searchRes.setNumber(item.getNumber());
			searchRes.setPrice(item.getPrice());
			searchRes.setDescription(item.getDescription());
			searchAllRes.add(searchRes);
		}
		return new ProductResponse(searchAllRes);

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
	public ProductResponse searchSpecificProduct(String name) { //�W�[���j�M
		
		
		List<Product> searchAllRes = new ArrayList<>();

		if (!StringUtils.hasText(name)) {//�p�G��null�A�������ѥ������
			
			List<Product> allSearchReq = productDao.findAll();
			return new ProductResponse(allSearchReq,"all info");
		}
		
		List<Product> searchReq = productDao.findByNameContaining(name);


		for (Product item : searchReq) {

			searchAllRes.add(item);
		}
		return new ProductResponse(searchAllRes,"specific info");
	}

}
