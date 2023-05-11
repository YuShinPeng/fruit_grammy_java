package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordercontent")
public class OrderContent {
	
	// 編碼
	@Id
	@Column(name = "num_id")
	private String num_id;
	
	// 產品名稱
	@Column(name = "item_name")
	private String item_name;
	
	// 數量
	@Column(name = "item_number")
	private String item_number;
	
	// 單價
	@Column(name = "item_price")
	private int item_price;
	
	// 總價格
	@Column(name = "total_price")
	private int total_price;

	public OrderContent() {

	}

	public String getNum_id() {
		return num_id;
	}

	public void setNum_id(String num_id) {
		this.num_id = num_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}

	public String getItem_number() {
		return item_number;
	}

	public void setItem_number(String item_number) {
		this.item_number = item_number;
	}

	public int getItem_price() {
		return item_price;
	}

	public void setItem_price(int item_price) {
		this.item_price = item_price;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}

}
