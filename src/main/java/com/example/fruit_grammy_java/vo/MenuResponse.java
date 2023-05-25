package com.example.fruit_grammy_java.vo;

import java.awt.Menu;
import java.util.List;

import com.example.fruit_grammy_java.entity.DishList;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuResponse {

	private Menu menu;

	private DishList dishList;

	private List<DishList> cookList;
	
	private String message;

	public MenuResponse() {

	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public DishList getDishList() {
		return dishList;
	}

	public void setDishList(DishList dishList) {
		this.dishList = dishList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MenuResponse(String message) {
		super();
		this.message = message;
	}

	public MenuResponse(DishList dishList, String message) {
		super();
		this.dishList = dishList;
		this.message = message;
	}

	public List<DishList> getCookList() {
		return cookList;
	}

	public void setCookList(List<DishList> cookList) {
		this.cookList = cookList;
	}

	public MenuResponse(List<DishList> cookList, String message) {
		this.cookList = cookList;
		this.message = message;
	}

}
