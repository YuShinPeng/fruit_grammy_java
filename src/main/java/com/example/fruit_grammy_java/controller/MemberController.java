package com.example.fruit_grammy_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fruit_grammy_java.ifs.MemberService;
import com.example.fruit_grammy_java.vo.SignupRequest;
import com.example.fruit_grammy_java.vo.SignupResponse;

@CrossOrigin
@RestController
public class MemberController {
	
	@Autowired
	private MemberService memberService;
	
	// �|�����U
	@PostMapping("sign_up")
	public SignupResponse addMember(@RequestBody SignupRequest request) {
		return memberService.addMember(request);
	}
	
	// �|���ק�
	@PostMapping("set_info")
	public SignupResponse setMember(@RequestBody SignupRequest request) {
		return memberService.setMember(request);
	}
	
	// �|���n�J
	@PostMapping("login")
	public SignupResponse login(@RequestBody SignupRequest request) {
		return memberService.login(request);
	}

}
