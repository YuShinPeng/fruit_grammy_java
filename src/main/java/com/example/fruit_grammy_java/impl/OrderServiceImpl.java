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
		
		if(orderGet.getOrder_condition().equals("�w�X�f")) {
			orderGet.setOrder_condition("�w����");
			orderDao.save(orderGet);
			return new OrderResponse(orderGet,"�q��w����");
		}
		
		if(orderGet.getOrder_condition().equals("�w����")){
			return new OrderResponse("�q��w�g�����F");
		}
		// �ʪ����걵�����A�\�ೣ�S���D�A�n�ɻ������q��ɶ��P���
		return new OrderResponse("�q��|���X�f");
	}

	public OrderResponse shippedOrder(OrderRequest orderRequest){
		String id = orderRequest.getOrder_id();
		Optional<Order> orderOp = orderDao.findById(id);
		Order orderGet = orderOp.get();

		if(orderGet.getOrder_condition().equals("���X�f")){
			orderGet.setOrder_condition("�w�X�f");
			orderDao.save(orderGet);
			return new OrderResponse("���A���w�X�f");
		}
		return new OrderResponse("�q��w�X�f�Χ���");
	}

	

	public OrderResponse allOrder(OrderRequest orderRequest){
		// Ū��ϥΪ̱b��
		String userAcc = orderRequest.getSellerAccount();
		// ���X�Ҧ��q�椺�e
		List<OrderContent> allOrderContent = contentDao.findAll();

		// ��X�ӨϥΪ̱b���������P��q�椺�e(�q��s��-1���e,�q��s��-2���e)
		List<OrderContent> userOrderContent = new ArrayList<OrderContent>();
		for(OrderContent item:allOrderContent){
			if(item.getSeller_account().equals(userAcc)){
				userOrderContent.add(item);
			}
		}

		// �ϥΪ̱b���ҫ������U�P��q��s�����e(�q��s��1,�q��s��2...)
		List<Order> allOrder = orderDao.findAll();
		List<Order> orderList = new ArrayList<Order>();
		for(OrderContent item:userOrderContent){
			String orderId = item.getNum_id().substring(0, 5);
			
			// �קK�q�歫��
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
		// �q�Ҧ��q����ӶR�a���q��þ�z�� List
		for(Order item:orderList){
			if(item.getBuyer_account().equals(buyer) && !buyerOrderList.contains(item)){
				buyerOrderList.add(item);
			}
		}

		// �q�R�a���q�� List ���X����A���ӭq�檺���e��t�~��z�� List
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
