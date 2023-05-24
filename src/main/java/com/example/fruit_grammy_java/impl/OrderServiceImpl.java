package com.example.fruit_grammy_java.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Order;
import com.example.fruit_grammy_java.entity.OrderContent;
import com.example.fruit_grammy_java.ifs.OrderService;
import com.example.fruit_grammy_java.repository.OrderContentDao;
import com.example.fruit_grammy_java.repository.OrderDao;
import com.example.fruit_grammy_java.vo.OrderRequest;
import com.example.fruit_grammy_java.vo.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderContentDao contentDao;
	@Autowired
	private OrderDao orderDao;
	
	public OrderResponse addOrder(OrderRequest orderRequest) {
		
		Order order = orderRequest.getOrder();
		
		List<OrderContent> contentList = orderRequest.getContentList();
		
		List <String> idList = new ArrayList<>();  
		for(OrderContent item : contentList) {	 
			if(!StringUtils.hasText(item.getItem_name()) || item.getItem_number() <= 0 ) {
				return new OrderResponse("格式錯誤");
			}
			// 等待商品資料庫入駐，要補上若無商品或庫存不夠的防呆
			idList.add(item.getNum_id());
			
		}
		order.setContent(String.join(",", idList));
		
		orderDao.save(order);
		contentDao.saveAll(contentList);
		
		return new OrderResponse(order,"訂單新增成功");
	}
	
	
	public OrderResponse doneOrder(OrderRequest orderRequest) {
		String id = orderRequest.getOrder_id();
		Optional<Order> orderOp = orderDao.findById(id);
		Order orderGet = orderOp.get();
		
		if(orderGet.getOrder_condition().equals("已完成")) {
			orderDao.deleteById(id);
			return new OrderResponse("訂單已刪除");
		}
		
		// 購物車串接完成，功能都沒問題，要補齊完成訂單時間與欄位
		return new OrderResponse("訂單尚未完成");
	}
}
