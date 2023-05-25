package com.example.fruit_grammy_java.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.Member;
import com.example.fruit_grammy_java.ifs.MemberService;
import com.example.fruit_grammy_java.repository.MemberDao;
import com.example.fruit_grammy_java.vo.SignupResponse;
import com.example.fruit_grammy_java.vo.SignupRequest;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberDao memberDao;

	// �|�����U
	@Override
	public SignupResponse addMember(SignupRequest request) {
		
		List<Member> memberList = request.getMemberList();
		List<Member> err = new ArrayList<>();
		
		// ���W��F��
		String patternPhone = "[0][9]\\d{8}";
		String petternPwd = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
		
		for(Member item : memberList) {
			
			// ���b
			if(!StringUtils.hasText(item.getAccount()) || !StringUtils.hasText(item.getPassword())
					|| !StringUtils.hasText(item.getConfirm_password()) || !StringUtils.hasText(item.getEmail())
					|| !StringUtils.hasText(item.getPhone()) || !StringUtils.hasText(item.getAddress())) {
				return new SignupResponse("��J��Ƥ��o����");
			}
			if(!Pattern.matches(petternPwd, item.getPassword())) {
				return new SignupResponse("�K�X���~�]�^�Ʀr�ۥ[�ݭn�j��6�Ӧr���^");
			}
			if(!item.getPassword().equals(item.getConfirm_password())) {
				return new SignupResponse("�п�J�ۦP���K�X");
			}
			if(!Pattern.matches(patternPhone, item.getPhone())) {
				return new SignupResponse("�L�Ī�����榡");
			}
			if(memberDao.existsById(item.getEmail())) {
				err.add(item);
			}
		}
		if(!err.isEmpty()) {
			return new SignupResponse("���H�c�w�Q�ϥ�");
		}
		memberDao.saveAll(memberList);
		
		
		return new SignupResponse("���U���\");
	}

	// �|���ק�
	@Override
	public SignupResponse setMember(SignupRequest request) {
		
		String email = request.getEmail();
		Member originalMember = memberDao.findByEmail(email);
		
		// ���b
		if (originalMember == null) {
			return new SignupResponse("�ӷ|�����s�b");
		}
		// ���W��F��
		String patternPhone = "[0][9]\\d{8}";
		String petternPwd = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
		
		if(StringUtils.hasText(request.getPassword()) || StringUtils.hasText(request.getConfirm_password())) {
			
			if(!Pattern.matches(petternPwd, request.getPassword())) {
				return new SignupResponse("�K�X���~�]�^�Ʀr�ۥ[�ݭn�j��6�Ӧr���^");
			}
			if(!request.getPassword().equals(request.getConfirm_password())) {
				return new SignupResponse("�п�J�ۦP���K�X");
			}
			
		}
		
		if(StringUtils.hasText(request.getPhone())) {
			
			if(!Pattern.matches(patternPhone, request.getPhone())) {
				return new SignupResponse("�L�Ī�����榡");
			}
		}
		
		
		// �N�s��ƥ[�J
		Member updateMember = new Member();
		updateMember.setEmail(email);
		updateMember.setPassword(request.getPassword());
		updateMember.setConfirm_password(request.getConfirm_password());
		updateMember.setAccount(request.getAccount());
		updateMember.setPhone(request.getPhone());
		updateMember.setAddress(request.getAddress());
		
		// �p�G��쬰�šA�h�ϥέ즳�����
		if(!StringUtils.hasText(updateMember.getPassword())) {
			updateMember.setPassword(originalMember.getPassword());
		}
		if(!StringUtils.hasText(updateMember.getConfirm_password())) {
			updateMember.setConfirm_password(originalMember.getConfirm_password());
		}
		if(!StringUtils.hasText(updateMember.getAccount())) {
			updateMember.setAccount(originalMember.getAccount());
		}
		if(!StringUtils.hasText(updateMember.getPhone())) {
			updateMember.setPhone(originalMember.getPhone());
		}
		if(!StringUtils.hasText(updateMember.getAddress())) {
			updateMember.setAddress(originalMember.getAddress());
		}
		
		// �����s�ާ@
		memberDao.save(updateMember);
	
		return new SignupResponse("�ק令�\");
	}

	// �|���n�J
	@Override
	public SignupResponse login(SignupRequest request) {
		
		String email = request.getEmail();
		String pwd = request.getPassword();
		Member daoInfo = memberDao.findByEmail(email);
		
		// ���b
		if (daoInfo == null) {
			return new SignupResponse("�ӷ|�����s�b");
		}
		if (!pwd.equals(daoInfo.getPassword())) {
			return new SignupResponse("�K�X���~");
		}
		
		return new SignupResponse(daoInfo, "�n�J���\! HELLO!");
	}
	
	

}
