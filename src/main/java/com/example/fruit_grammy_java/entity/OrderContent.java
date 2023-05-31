package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ordercontent")
public class OrderContent {
	
	// �q��s�X
	@Id
	@Column(name = "num_id")
	private String num_id;
	
	// ���~�W��
	@Column(name = "item_name")
	private String item_name;
	
	// �ƶq
	@Column(name = "item_number")
	private int item_number;
	
	// ���
	@Column(name = "item_price")
	private int item_price;
	
	// �`����
	@Column(name = "total_price")
	private int total_price;

	//��a
	@Column(name = "seller_account")
	private String seller_account;

	@Column(name = "item_condition")
	private String item_condition;
	
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

	public int getItem_number() {
		return item_number;
	}

	public void setItem_number(int item_number) {
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

	public String getSeller_account() {
		return seller_account;
	}

	public void setSeller_account(String seller_account) {
		this.seller_account = seller_account;
	}

	public String getItem_condition() {
		return item_condition;
	}

	public void setItem_condition(String item_condition) {
		this.item_condition = item_condition;
	}

	
}
