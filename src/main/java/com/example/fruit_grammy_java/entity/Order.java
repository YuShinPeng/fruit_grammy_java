	package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "orderform")
@JsonInclude(JsonInclude.Include.NON_NULL)
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
	private String buyer_account;
	
	// �R�a�q��
	@Column(name = "buyer_phone")
	private String buyer_phone;
	
	// �H�e�a�}
	@Column(name = "sent_address")
	private String sent_address;
	
	// �q�椺�e
	@Column(name = "content")
	private String content;

	// �q�檬�A
	@Column(name = "order_condition")
	private String order_condition;	
	
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

	public String getBuyer_account() {
		return buyer_account;
	}

	public void setBuyer_account(String buyer_account) {
		this.buyer_account = buyer_account;
	}

	public String getBuyer_phone() {
		return buyer_phone;
	}

	public void setBuyer_phone(String buyer_phone) {
		this.buyer_phone = buyer_phone;
	}

	public String getSent_address() {
		return sent_address;
	}

	public void setSent_address(String sent_address) {
		this.sent_address = sent_address;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrder_condition() {
		return order_condition;
	}

	public void setOrder_condition(String order_condition) {
		this.order_condition = order_condition;
	}

	
	
	
}
