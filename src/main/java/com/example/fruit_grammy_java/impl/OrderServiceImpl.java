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
				return new OrderResponse("�榡���~");
			}
			// ���ݰӫ~��Ʈw�J�n�A�n�ɤW�Y�L�ӫ~�ήw�s���������b
			idList.add(item.getNum_id());
			
		}
		order.setContent(String.join(",", idList));
		
		orderDao.save(order);
		contentDao.saveAll(contentList);
		
		return new OrderResponse(order,"�q��s�W���\");
	}
	
	
	public OrderResponse doneOrder(OrderRequest orderRequest) {
		String id = orderRequest.getOrder_id();
		Optional<Order> orderOp = orderDao.findById(id);
		Order orderGet = orderOp.get();
		
		if(orderGet.getOrder_condition().equals("�w����")) {
			orderDao.deleteById(id);
			return new OrderResponse("�q��w�R��");
		}
		
		// �ʪ����걵�����A�\�ೣ�S���D�A�n�ɻ������q��ɶ��P���
		return new OrderResponse("�q��|������");
	}
}
