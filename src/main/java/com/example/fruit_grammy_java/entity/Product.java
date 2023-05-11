package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	
	// ���~�s�X
	@Id
	@Column(name = "seller_account")
	private String seller_account;
	
	// ����
	@Column(name = "type")
	private String type;
	
	// ���~�W��
	@Column(name = "name")
	private String name;
	
	// ���a
	@Column(name = "place")
	private String place;
	
	// �ƶq
	@Column(name = "number")
	private int number;
	
	// �Ħ����
	@Column(name = "date")
	private String date;
	
	// ����
	@Column(name = "price")
	private int price;
	
	// �Ƶ�����
	@Column(name = "description")
	private String description;

	public Product() {

	}

	public String getProduct_id() {
		return seller_account;
	}

	public void setProduct_id(String product_id) {
		this.seller_account = product_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
