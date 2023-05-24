package com.example.fruit_grammy_java.vo;

import java.util.List;

import com.example.fruit_grammy_java.entity.Order;
import com.example.fruit_grammy_java.entity.OrderContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderRequest {

	//訂單編號
	@JsonProperty("order_id")
	private String order_id;
	
	@JsonProperty("sellerAccount")
	private String sellerAccount;
	
	@JsonProperty("buyerAccount")
	private String buyerAccount;
	
	@JsonProperty("order")
	private Order order;
	
	// 購物車內容品項
	@JsonProperty("contentList")
	private List<OrderContent> contentList;

	
	
	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSellerAccount() {
		return sellerAccount;
	}

	public void setSellerAccount(String sellerAccount) {
		this.sellerAccount = sellerAccount;
	}

	public String getBuyerAccount() {
		return buyerAccount;
	}

	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}

	public List<OrderContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<OrderContent> contentList) {
		this.contentList = contentList;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	
	
	
	
}
