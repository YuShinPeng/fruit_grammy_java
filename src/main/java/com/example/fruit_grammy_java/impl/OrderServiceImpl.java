package com.example.fruit_grammy_java.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Order;
import com.example.fruit_grammy_java.entity.OrderContent;
import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.entity.Shopping;
import com.example.fruit_grammy_java.ifs.OrderService;
import com.example.fruit_grammy_java.repository.OrderContentDao;
import com.example.fruit_grammy_java.repository.OrderDao;
import com.example.fruit_grammy_java.repository.ProductDao;
import com.example.fruit_grammy_java.repository.ShoppingContentDao;
import com.example.fruit_grammy_java.repository.ShoppingDao;
import com.example.fruit_grammy_java.vo.OrderContentRequest;
import com.example.fruit_grammy_java.vo.OrderRequest;
import com.example.fruit_grammy_java.vo.OrderResponse;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderContentDao contentDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ShoppingContentDao shoppingContentDao;
	@Autowired
	private ShoppingDao shoppingDao;
//===========================新增訂單=================================
	public OrderResponse addOrder(OrderRequest orderRequest) {
		// 從前端取得訂單資訊
		Order order = orderRequest.getOrder();
		// 從前端取得訂單內容
		List<OrderContent> contentList = orderRequest.getContentList();
		
		// 建立新的 List 存放訂單內容的 num_id
		List <String> idList = new ArrayList<>();  
		for(OrderContent item : contentList) {
			// 防呆，不能沒有商品名稱或是購買數量
			if(!StringUtils.hasText(item.getItem_name()) || item.getItem_number() <= 0 ) {
				return new OrderResponse("格式錯誤");
			}
			// 等待商品資料庫入駐，要補上若無商品或庫存不夠的防呆
			Optional<Product> product = productDao.findById(item.getItem_id());			
			Product productGet = product.get();
			
			// 扣除庫存(應移到訂單改成已出貨時)
			productGet.setNumber(productGet.getNumber()-item.getItem_number());
			
			// 將 num_id 集結成 List
			idList.add(item.getNum_id());
			
		}
		String phoneCheck = "[0][9]\\d{8}";
		if(!Pattern.matches(phoneCheck, order.getBuyer_phone())) {
			return new OrderResponse("請輸入正確手機號碼格式");
		}
//		if(order.getSent_address())
		// 使用 Join 的方式把訂單內容的 num_id 加入訂單資訊方便管理
		order.setContent(String.join(",", idList));
		
		orderDao.save(order);
		contentDao.saveAll(contentList);
		
		// 轉換成訂單成功時同時刪除使用者購物車
		String buyerAccount = order.getBuyer_account();
		Optional<Shopping> buyerShoppingCar = shoppingDao.findById(buyerAccount);
		Shopping shoppingCar = buyerShoppingCar.get();
		// 將購物車內容逐一清除
		for(String item:shoppingCar.getBuyerContent().split(",")) {
			if(!shoppingContentDao.existsById(item)) {
				return new OrderResponse("找不到資料");
			}
			shoppingContentDao.deleteById(item);
			
		}
		shoppingDao.deleteById(buyerAccount);
		return new OrderResponse(order,"訂單新增成功");
	}
	
//===========================訂單更改成已完成=================================
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

//===========================訂單更改成已出貨=================================
	public OrderResponse shippedOrder(OrderRequest orderRequest){
		String id = orderRequest.getOrder_id();
		Optional<Order> orderOp = orderDao.findById(id);
		Order orderGet = orderOp.get();
		// 訂單狀態為"未出貨"
		if(orderGet.getOrder_condition().equals("未出貨")){
		// 取得訂單的訂單內容並做 forEach 迴圈
		String content = orderGet.getContent();		
		for(String item: content.split(",")) {
			//取的訂單內容中每筆的詳細資料
			Optional<OrderContent> detail = contentDao.findById(item);
			OrderContent detailGet = detail.get();
			if(detailGet.getItem_condition().equals("未出貨")) {
				return new OrderResponse("尚有產品未出貨");
			}
		}
		
			orderGet.setOrder_condition("已出貨");
			orderDao.save(orderGet);
			return new OrderResponse("狀態更改已出貨");
		}
		return new OrderResponse("訂單已出貨或完成");
	}

//===========================訂單品項更改成已出貨=================================
	public OrderResponse shippedItem(OrderContentRequest orderContentRequest) {
		String num_id = orderContentRequest.getNum_id();
		if(!StringUtils.hasText(num_id)) {
			return new OrderResponse("不能為空");
		}
		Optional<OrderContent> item = contentDao.findById(num_id);
		OrderContent itemGet = item.get();
		if(!itemGet.getItem_condition().equals("未出貨")) {
			return new OrderResponse("已完成出貨");
		}
		itemGet.setItem_condition("已出貨");
		contentDao.save(itemGet);
		return new OrderResponse("已更改狀態");
	}
	
//===========================訂單品項更改成未出貨=================================	
	public OrderResponse callBackItem(OrderContentRequest orderContentRequest) {
		String num_id = orderContentRequest.getNum_id();
		if(!StringUtils.hasText(num_id)){
			return new OrderResponse("要有欄位");
		}
		Optional<OrderContent> item = contentDao.findById(num_id);
		OrderContent itemGet = item.get();
		if(!itemGet.getItem_condition().equals("已出貨")){
			return new OrderResponse("本來就還沒出貨");
		}
		itemGet.setItem_condition("未出貨");
		contentDao.save(itemGet);
		return new OrderResponse("已更改回未出貨");
	}
//===========================顯示賣家訂單=================================	
	public OrderResponse sellerOrder(OrderRequest orderRequest){
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
//===========================顯示買家訂單=================================	
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
