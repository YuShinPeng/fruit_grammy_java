package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "product")
public class Product {

	
	@Id
	@Column(name = "hs_code")
	private String hs_code;


	// ��a�b��

	// 嚙踝蕭a嚙箭嚙踝蕭


	@Column(name = "seller_account")
	private String seller_account;

	// 嚙踝蕭嚙踝蕭
	@Column(name = "type")
	private String type;

	// 嚙踝蕭嚙羯嚙磕嚙踝蕭
	@Column(name = "name")
	private String name;

	// 嚙踝蕭嚙窮
	@Column(name = "place")
	private String place;

	// 嚙複量
	@Column(name = "number")
	private int number;

	// 嚙衝佗蕭嚙踝蕭嚙�
	@Column(name = "date")
	private String date;

	// 嚙踝蕭嚙踝蕭
	@Column(name = "price")
	private int price;

	// 嚙複蛛蕭嚙踝蕭嚙踝蕭
	@Column(name = "description")
	private String description;

	public Product() {

	}

	public String getHs_code() {
		return hs_code;
	}

	public void setHs_code(String hs_code) {
		this.hs_code = hs_code;
	}

	public String getSeller_account() {
		return seller_account;
	}

	public void setSeller_account(String seller_account) {
		this.seller_account = seller_account;
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
