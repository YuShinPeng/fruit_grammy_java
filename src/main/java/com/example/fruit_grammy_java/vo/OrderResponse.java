package com.example.fruit_grammy_java.vo;

import java.util.List;
import java.util.Map;

import com.example.fruit_grammy_java.entity.Order;
import com.example.fruit_grammy_java.entity.OrderContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
	
	@JsonProperty("order")
	private Order order;
	
	private String orderId;
	
	private String message;

	private List<OrderContent> contentList;

	private List<String> orderIdList;

	private List<Order> orderList;
	
	public OrderResponse(){

	}
	
	public OrderResponse(List<OrderContent> contentList){
		this.contentList = contentList;
	}

	public OrderResponse(String orderId, List<OrderContent> contentList){
		this.orderId = orderId;
		this.contentList = contentList;
	}

	// public OrderResponse(List<String> orderIdList, List<OrderContent> contentList){
	// 	this.orderIdList = orderIdList;
	// 	this.contentList = contentList;
	// }


	public OrderResponse(List<OrderContent> contentList, List<Order> orderList) {
		this.contentList = contentList;
		this.orderList = orderList;
	}

	public OrderResponse(Order order, String message) {
		this.order = order;
		this.message = message;
	}
	
	public OrderResponse(String message) {
		this.message = message;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<OrderContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<OrderContent> contentList) {
		this.contentList = contentList;
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<String> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}


	

	
	
	
	
}
