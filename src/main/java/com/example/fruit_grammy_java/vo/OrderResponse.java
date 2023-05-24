package com.example.fruit_grammy_java.vo;

import com.example.fruit_grammy_java.entity.Order;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
	
	@JsonProperty("order")
	private Order order;
	
	private String orderId;
	
	private String message;
	
	
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
	
	
	
}
