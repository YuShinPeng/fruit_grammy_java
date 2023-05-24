package com.example.fruit_grammy_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import com.example.fruit_grammy_java.ifs.ShoppingService;
import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ShoppingRequest;
import com.example.fruit_grammy_java.vo.ShoppingResponse;



@CrossOrigin
@RestController
public class ShoppingController {
	@Autowired
	private ShoppingService shoppingService;

	@PostMapping("add_shopping_car")
	public ShoppingResponse addData(@RequestBody ShoppingRequest req) {
	return  shoppingService.addData(req);
	}
	
	
	@PostMapping("modi_data")
	public ShoppingResponse modiData(@RequestBody ShoppingRequest req) {
	return  shoppingService.modiData(req);
	}
	
	
	@PostMapping("delete_data")
	public ShoppingResponse deleteData(@RequestBody ShoppingRequest req) {
	return  shoppingService.deleteData(req);
	}
	
	
	@PostMapping("get_shopping_data")
	public ShoppingResponse getShoppingData(@RequestBody ShoppingRequest req) {
	return  shoppingService.getShoppingData(req);
	}
}
