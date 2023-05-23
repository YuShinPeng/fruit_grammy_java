package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shopping")
public class Shopping {
	
	
	@Id
	@Column(name = "buyer_account")
	private String buyerAccount;
	@Column(name = "buyer_content")
	private String buyerContent;
	
	
	
	
	
	public Shopping() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Shopping(String buyerAccount, String buyerContent) {
		super();
		this.buyerAccount = buyerAccount;
		this.buyerContent = buyerContent;
	}
	public String getBuyerAccount() {
		return buyerAccount;
	}
	public void setBuyerAccount(String buyerAccount) {
		this.buyerAccount = buyerAccount;
	}
	public String getBuyerContent() {
		return buyerContent;
	}
	public void setBuyerContent(String buyerContent) {
		this.buyerContent = buyerContent;
	}
	
	
	
	

}
