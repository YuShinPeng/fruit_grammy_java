package com.example.fruit_grammy_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fruit_grammy_java.ifs.ProductService;
import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ProductResponse;

@CrossOrigin
@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@PostMapping("add_product")
	public ProductResponse addProduct(@RequestBody ProductRequest productReq) {
		return productService.addProduct(productReq);
	}
	
	@PostMapping("update_product")
	public ProductResponse updateProduct(@RequestBody ProductRequest productReq) {
		return productService.updateProduct(productReq);
	}
	
	@PostMapping("search_sell_product")
	public ProductResponse searchAddProduct(@RequestBody ProductRequest productReq) {
		return productService.searchAddProduct(productReq.getName());
	}

	@PostMapping("search_product")
	public ProductResponse searchPlaceProduct(@RequestBody ProductRequest productReq) {
		return productService.searchPlaceProduct(productReq.getPlace());
	}

}
