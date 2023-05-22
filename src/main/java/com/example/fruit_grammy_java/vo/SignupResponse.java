package com.example.fruit_grammy_java.vo;

import java.util.List;

import com.example.fruit_grammy_java.entity.Member;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupResponse {
	
	private Member member;
	
	private List<Member> memberList;
	
	private String message;

	public SignupResponse() {
		
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Member> getMemberList() {
		return memberList;
	}

	public void setMemberList(List<Member> memberList) {
		this.memberList = memberList;
	}

	public SignupResponse(List<Member> memberList, String message) {
		super();
		this.memberList = memberList;
		this.message = message;
	}

	public SignupResponse(String message) {
		super();
		this.message = message;
	}

	public SignupResponse(Member member, String message) {
		super();
		this.member = member;
		this.message = message;
	}
	
	
	
	
	

}
