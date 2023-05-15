package com.example.fruit_grammy_java.impl;

import java.util.List;

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
	// �W�[�ݶ��T --> �~�W ���� ���a �ƶq �Ħ���� ���� �Ƶ�����
	@Override
	public ProductResponse addProduct(ProductRequest productReq) {
		// ���ˬd��ƪ�List
		List<Product> productList = productReq.getProductList();

		for (Product item : productList) {
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
		return new ProductResponse(productList,"�s�W���\");
	}

}
