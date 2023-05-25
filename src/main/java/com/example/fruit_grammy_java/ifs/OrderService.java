package com.example.fruit_grammy_java.ifs;

import com.example.fruit_grammy_java.vo.OrderRequest;
import com.example.fruit_grammy_java.vo.OrderResponse;

public interface OrderService {

	
	public OrderResponse addOrder(OrderRequest orderRequest);
	
	public OrderResponse doneOrder(OrderRequest orderRequest);

	public OrderResponse shippedOrder(OrderRequest orderRequest);

	public OrderResponse allOrder(OrderRequest orderRequest);

	public OrderResponse buyerOrder(OrderRequest orderRequest);
}
