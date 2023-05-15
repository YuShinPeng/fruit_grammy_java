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

	// 賣家上架新商品
	// 上架需填資訊 --> 品名 種類 產地 數量 採收日期 價格 備註說明
	@Override
	public ProductResponse addProduct(ProductRequest productReq) {
		// 裝檢查資料的List
		List<Product> productList = productReq.getProductList();

		for (Product item : productList) {
			if (!StringUtils.hasText(item.getName())) {
				return new ProductResponse("商品名稱不得為空");
			}

			if (!StringUtils.hasText(item.getType())) {
				return new ProductResponse("種類不得為空");
			}

			if (!StringUtils.hasText(item.getPlace())) {
				return new ProductResponse("產地不得為空");
			}

			if (item.getNumber() <= 0) {
				return new ProductResponse("數量不得小於或等於0");
			}

			if (!StringUtils.hasText(item.getDate())) {
				return new ProductResponse("採收日期不得為空");
			}

			if (item.getPrice() <= 0) {
				return new ProductResponse("價格不得小於或等於0");
			}

			if (!StringUtils.hasText(item.getDescription())) {
				return new ProductResponse("商品備註說明不得為空");
			}
		}

		// 新增成功回傳資訊和成功訊息
		productDao.saveAll(productList);
		return new ProductResponse(productList,"新增成功");
	}

}
