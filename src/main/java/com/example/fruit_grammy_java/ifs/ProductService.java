package com.example.fruit_grammy_java.ifs;

import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ProductResponse;

public interface ProductService {

	// 賣家上架新商品
	public ProductResponse addProduct(ProductRequest productReq);

	// 買家搜尋

}
