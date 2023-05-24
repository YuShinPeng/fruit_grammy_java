package com.example.fruit_grammy_java.ifs;


import com.example.fruit_grammy_java.vo.ProductRequest;
import com.example.fruit_grammy_java.vo.ShoppingRequest;
import com.example.fruit_grammy_java.vo.ShoppingResponse;

public interface ShoppingService {
	
	
	
	public ShoppingResponse addData(ShoppingRequest req); //顧客將資料建進購物車
	public ShoppingResponse modiData(ShoppingRequest req); //已進入購物車資料修改
	public ShoppingResponse deleteData(ShoppingRequest req); //已進入購物車資料刪除
	public ShoppingResponse getShoppingData(ShoppingRequest req); //撈出購物車資料
}
