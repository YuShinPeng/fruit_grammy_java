package com.example.fruit_grammy_java.vo;

import java.util.List;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.entity.Shopping;
import com.example.fruit_grammy_java.entity.ShoppingContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShoppingResponse {
	@JsonProperty("Shopping_info")
	private Shopping shopping; // 回傳單筆Shopping所有欄位
	@JsonProperty("Shopping_List")
	private List<Shopping> shoppingList;// 回傳多筆Shopping所有欄位
	private String msg;
	
	private Product product;
	
	@JsonProperty("Shopping_Detail_List")
	private List<ShoppingContent> shoppingContentList;
	
	
	
	
	
	public ShoppingResponse(List<ShoppingContent> shoppingContentList) {
		super();
		this.shoppingContentList = shoppingContentList;
	}

	public ShoppingResponse(String msg, List<ShoppingContent> shoppingContentList) {
		super();
		this.msg = msg;
		this.shoppingContentList = shoppingContentList;
	}

	public ShoppingResponse(String msg) {
		super();
		this.msg = msg;
	}

	public ShoppingResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ShoppingResponse(Shopping shopping, String msg) {
		super();
		this.shopping = shopping;
		this.msg = msg;
	}
	
	
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shopping getShopping() {
		return shopping;
	}
	public void setShopping(Shopping shopping) {
		this.shopping = shopping;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ShoppingResponse(List<Shopping> shoppingList, String msg) {
		super();
		this.shoppingList = shoppingList;
		this.msg = msg;
	}

	public List<Shopping> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(List<Shopping> shoppingList) {
		this.shoppingList = shoppingList;
	}

	public List<ShoppingContent> getShoppingContentList() {
		return shoppingContentList;
	}

	public void setShoppingContentList(List<ShoppingContent> shoppingContentList) {
		this.shoppingContentList = shoppingContentList;
	}
	
	
}
