package com.example.fruit_grammy_java.ifs;

import com.example.fruit_grammy_java.vo.SignupResponse;
import com.example.fruit_grammy_java.vo.SignupRequest;

public interface MemberService {

	// 會員註冊
	public SignupResponse addMember(SignupRequest request);
	
	// 會員修改
	public SignupResponse setMember(SignupRequest request);
	
	// 會員登入
	public SignupResponse login(SignupRequest request);
}
