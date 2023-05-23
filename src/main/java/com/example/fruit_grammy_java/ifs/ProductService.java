package com.example.fruit_grammy_java.ifs;

import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ProductResponse;

public interface ProductService {

	// 賣家上架新商品
	public ProductResponse addProduct(ProductRequest productReq);

	// 賣家修改上架商品
	public ProductResponse updateProduct(ProductRequest productReq);
	
	// 賣家搜尋上架商品 -- 品名搜尋
	public ProductResponse searchAddProduct(String name);
	
	// 賣家搜尋上架商品 --特定搜尋
	public ProductResponse searchSpecificProduct(String name);
	
	// 買家搜尋 生產履歷 -- 透過產地搜尋
	public ProductResponse searchPlaceProduct(String place);
 	
}
