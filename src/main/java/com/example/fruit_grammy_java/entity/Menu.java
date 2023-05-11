package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu")
public class Menu {
	
	// ²£«~¦WºÙ
	@Id
	@Column(name = "name")
	private String name;
	
	// ­¹ÃÐ1
	@Column(name = "dish1")
	private String dish1;
	
	// ­¹ÃÐ2
	@Column(name = "dish2")
	private String dish2;
	
	// ­¹ÃÐ3
	@Column(name = "dish3")
	private String dish3;

	public Menu() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDish1() {
		return dish1;
	}

	public void setDish1(String dish1) {
		this.dish1 = dish1;
	}

	public String getDish2() {
		return dish2;
	}

	public void setDish2(String dish2) {
		this.dish2 = dish2;
	}

	public String getDish3() {
		return dish3;
	}

	public void setDish3(String dish3) {
		this.dish3 = dish3;
	}

}
