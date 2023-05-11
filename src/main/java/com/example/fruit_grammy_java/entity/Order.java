package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order")
public class Order {
	
	// �q��s�X
	@Id
	@Column(name = "order_id")
	private String order_id;
	
	// ��a�b��
	@Column(name = "seller_account")
	private String seller_account;
	
	// �R�a�b��
	@Column(name = "buyer_account")
	private String buyller_account;
	
	// �q�椺�e
	@Column(name = "content")
	private String content;

	public Order() {

	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getSeller_account() {
		return seller_account;
	}

	public void setSeller_account(String seller_account) {
		this.seller_account = seller_account;
	}

	public String getBuyller_account() {
		return buyller_account;
	}

	public void setBuyller_account(String buyller_account) {
		this.buyller_account = buyller_account;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
