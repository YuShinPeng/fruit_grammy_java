package com.example.fruit_grammy_java.vo;


import java.util.List;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.entity.Shopping;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShoppingRequest {
	
	// �^�ǳ浧Shopping�Ҧ����
	@JsonProperty("Shopping_info")
	private Shopping shopping; 

	// �^�Ǧh��Shopping�Ҧ����
	@JsonProperty("Shopping_List")
	private List<Shopping> shoppingList;
	
	// �^�ǶR�a�b��
	@JsonProperty("buyerAccount")
	private String buyerAccount;
	
	// �ӫ~�s�X
	@JsonProperty("product")
	private String product;
	
	//��s�ƶq
	@JsonProperty("number")
	private int number; 
	
	//�ʪ����N�X
	@JsonProperty("shoppingCode")
	private String shoppingCode;
	
	public ShoppingRequest(Shopping shopping, List<Shopping> shoppingList, String buyerAccount, String product) {
		super();
		this.shopping = shopping;
		this.shoppingList = shoppingList;
		this.buyerAccount = buyerAccount;
		this.product = product;
	}

	public ShoppingRequest(List<Shopping> shoppingList) {
		super();
		this.shoppingList = shoppingList;
	}

	public ShoppingRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShoppingRequest(Shopping shopping) {
		super();
		this.shopping = shopping;
	}

	public String getShoppingCode() {
		return shoppingCode;
	}

	public void setShoppingCode(String shoppingCode) {
		this.shoppingCode = shoppingCode;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Shopping getShopping() {
		return shopping;
	}

	public void setShopping(Shopping shopping) {
		this.shopping = shopping;
	}

	public List<Shopping> getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(List<Shopping> shoppingList) {
		this.shoppingList = shoppingList;
	}
	
	
	

}
