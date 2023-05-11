package com.example.fruit_grammy_java.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "member")
public class Member {
	
	// �b��
	@Id
	@Column(name = "account")
	private String account;
	
	// �K�X
	@Column(name = "password")
	private String password;
	
	// �H�c
	@Column(name = "email")
	private String email;
	
	// ���
	@Column(name = "phone")
	private String phone;
	
	// �a�}
	@Column(name = "address")
	private String address;

	public Member() {

	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
