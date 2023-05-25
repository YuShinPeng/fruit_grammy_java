package com.example.fruit_grammy_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.fruit_grammy_java.ifs.OrderService;
import com.example.fruit_grammy_java.vo.OrderRequest;
import com.example.fruit_grammy_java.vo.OrderResponse;

@CrossOrigin
@RestController
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = "addOrder" , method = RequestMethod.POST)
	public OrderResponse addOrder(@RequestBody OrderRequest orderRequest) {
		return orderService.addOrder(orderRequest);
	}

	// µ¹seller_account
	@RequestMapping(value = "seller_Order", method = RequestMethod.POST)
	public OrderResponse allOrder(@RequestBody OrderRequest orderRequest){
		return orderService.allOrder(orderRequest);
	}

	@PostMapping("buyer_Order")
	public OrderResponse buyerOrder(@RequestBody OrderRequest orderRequest){
		return orderService.buyerOrder(orderRequest);
	}

	// µ¹order_id
	@PostMapping("shippedOrder")
	public OrderResponse shippedOrder(@RequestBody OrderRequest orderRequest){
		return orderService.shippedOrder(orderRequest);
	}
	
	// µ¹order_id
	@PostMapping("doneOrder")
	public OrderResponse doneOrder(@RequestBody OrderRequest orderRequest){
		return orderService.doneOrder(orderRequest);
	}
}
