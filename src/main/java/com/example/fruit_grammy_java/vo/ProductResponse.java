package com.example.fruit_grammy_java.vo;

import java.util.List;

import com.example.fruit_grammy_java.entity.Product;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {

	private Product product;

	@JsonProperty("product_list")
	private List<Product> productList;

	private String name;

	private String type;

	private String place;

	private String date;

	private String description;

	private String message;

	private Integer price;

	private Integer number;

	private List<ProductResponse> searchAllRes;

	private Product getProduct;

	public ProductResponse() {

	}

	

	



	public ProductResponse(List<ProductResponse> searchAllRes) {
		this.searchAllRes = searchAllRes;
	}

	public ProductResponse(Product getProduct) {
		super();
		this.getProduct = getProduct;
	}

	public Product getGetProduct() {
		return getProduct;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setGetProduct(Product getProduct) {
		this.getProduct = getProduct;
	}

	public List<ProductResponse> getSearchAllRes() {
		return searchAllRes;
	}

	public void setSearchAllRes(List<ProductResponse> searchAllRes) {
		this.searchAllRes = searchAllRes;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public ProductResponse(String message) {
		super();
		this.message = message;
	}

	public ProductResponse(List<Product> productList, String message) {
		super();
		this.productList = productList;
		this.message = message;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
