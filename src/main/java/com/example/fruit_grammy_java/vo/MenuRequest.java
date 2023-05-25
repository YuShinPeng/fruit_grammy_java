package com.example.fruit_grammy_java.vo;

import com.example.fruit_grammy_java.entity.DishList;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuRequest {
	
//	@JsonProperty("dishlist")
	private DishList dishList;
	
	private String name;

	public MenuRequest() {
		
	}

	public DishList getDishList() {
		return dishList;
	}

	public void setDishList(DishList dishList) {
		this.dishList = dishList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	

}
