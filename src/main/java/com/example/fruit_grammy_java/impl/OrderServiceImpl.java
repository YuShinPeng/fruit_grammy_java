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
		
		if(orderGet.getOrder_condition().equals("已出貨")) {
			orderGet.setOrder_condition("已完成");
			orderDao.save(orderGet);
			return new OrderResponse(orderGet,"訂單已完成");
		}
		
		if(orderGet.getOrder_condition().equals("已完成")){
			return new OrderResponse("訂單已經完成了");
		}
		// 購物車串接完成，功能都沒問題，要補齊完成訂單時間與欄位
		return new OrderResponse("訂單尚未出貨");
	}

	public OrderResponse shippedOrder(OrderRequest orderRequest){
		String id = orderRequest.getOrder_id();
		Optional<Order> orderOp = orderDao.findById(id);
		Order orderGet = orderOp.get();

		if(orderGet.getOrder_condition().equals("未出貨")){
			orderGet.setOrder_condition("已出貨");
			orderDao.save(orderGet);
			return new OrderResponse("狀態更改已出貨");
		}
		return new OrderResponse("訂單已出貨或完成");
	}

	

	public OrderResponse allOrder(OrderRequest orderRequest){
		// 讀到使用者帳號
		String userAcc = orderRequest.getSellerAccount();
		// 取出所有訂單內容
		List<OrderContent> allOrderContent = contentDao.findAll();

		// 找出該使用者帳號對應的銷售訂單內容(訂單編號-1內容,訂單編號-2內容)
		List<OrderContent> userOrderContent = new ArrayList<OrderContent>();
		for(OrderContent item:allOrderContent){
			if(item.getSeller_account().equals(userAcc)){
				userOrderContent.add(item);
			}
		}

		// 使用者帳號所持有的各銷售訂單編號內容(訂單編號1,訂單編號2...)
		List<Order> allOrder = orderDao.findAll();
		List<Order> orderList = new ArrayList<Order>();
		for(OrderContent item:userOrderContent){
			String orderId = item.getNum_id().substring(0, 5);
			
			// 避免訂單重複
			for(Order elements: allOrder){
				if(!orderList.contains(elements) && elements.getOrder_id().equals(orderId)){
					orderList.add(elements);
				}
			}
		}
	
		return new OrderResponse(userOrderContent,orderList);
	}

	public OrderResponse buyerOrder(OrderRequest orderRequest){
		String buyer = orderRequest.getBuyerAccount();

		
		List<OrderContent> contentList = contentDao.findAll();
		List<Order> orderList = orderDao.findAll();

		List<Order> buyerOrderList = new ArrayList<Order>();
		List<OrderContent> buyerContentList = new ArrayList<OrderContent>();
		// 從所有訂單找到該買家的訂單並整理成 List
		for(Order item:orderList){
			if(item.getBuyer_account().equals(buyer) && !buyerOrderList.contains(item)){
				buyerOrderList.add(item);
			}
		}

		// 從買家的訂單 List 取出條件，找到該訂單的內容後另外整理成 List
		for(Order form:buyerOrderList){
			String form_id = form.getOrder_id();
			for(OrderContent elements:contentList){
				String order_id = elements.getNum_id().substring(0, 5);
				if(order_id.equals(form_id)){
					buyerContentList.add(elements);
				}
			}
		}

		return new OrderResponse(buyerContentList, buyerOrderList);
	}
}
