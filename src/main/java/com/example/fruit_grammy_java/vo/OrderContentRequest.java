package com.example.fruit_grammy_java.vo;

import java.util.List;

import com.example.fruit_grammy_java.entity.OrderContent;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderContentRequest {

	@JsonProperty("contentList")
	private List<OrderContent> contentList;
	
	@JsonProperty("num_id")
	private String num_id;

	public List<OrderContent> getContentList() {
		return contentList;
	}

	public void setContentList(List<OrderContent> contentList) {
		this.contentList = contentList;
	}

	public String getNum_id() {
		return num_id;
	}

	public void setNum_id(String num_id) {
		this.num_id = num_id;
	}


	
	
	
}
