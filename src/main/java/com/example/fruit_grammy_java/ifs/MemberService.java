package com.example.fruit_grammy_java.ifs;

import com.example.fruit_grammy_java.vo.SignupResponse;
import com.example.fruit_grammy_java.vo.SignupRequest;

public interface MemberService {

	// �|�����U
	public SignupResponse addMember(SignupRequest request);
	
	// �|���ק�
	public SignupResponse setMember(SignupRequest request);
	
	// �|���n�J
	public SignupResponse login(SignupRequest request);
}
