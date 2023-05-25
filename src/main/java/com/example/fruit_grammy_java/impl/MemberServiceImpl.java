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

	// 會員註冊
	@Override
	public SignupResponse addMember(SignupRequest request) {
		
		List<Member> memberList = request.getMemberList();
		List<Member> err = new ArrayList<>();
		
		// 正規表達式
		String patternPhone = "[0][9]\\d{8}";
		String petternPwd = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
		
		for(Member item : memberList) {
			
			// 防呆
			if(!StringUtils.hasText(item.getAccount()) || !StringUtils.hasText(item.getPassword())
					|| !StringUtils.hasText(item.getConfirm_password()) || !StringUtils.hasText(item.getEmail())
					|| !StringUtils.hasText(item.getPhone()) || !StringUtils.hasText(item.getAddress())) {
				return new SignupResponse("輸入資料不得為空");
			}
			if(!Pattern.matches(petternPwd, item.getPassword())) {
				return new SignupResponse("密碼錯誤（英數字相加需要大於6個字元）");
			}
			if(!item.getPassword().equals(item.getConfirm_password())) {
				return new SignupResponse("請輸入相同的密碼");
			}
			if(!Pattern.matches(patternPhone, item.getPhone())) {
				return new SignupResponse("無效的手機格式");
			}
			if(memberDao.existsById(item.getEmail())) {
				err.add(item);
			}
		}
		if(!err.isEmpty()) {
			return new SignupResponse("此信箱已被使用");
		}
		memberDao.saveAll(memberList);
		
		
		return new SignupResponse("註冊成功");
	}

	// 會員修改
	@Override
	public SignupResponse setMember(SignupRequest request) {
		
		String email = request.getEmail();
		Member originalMember = memberDao.findByEmail(email);
		
		// 防呆
		if (originalMember == null) {
			return new SignupResponse("該會員不存在");
		}
		// 正規表達式
		String patternPhone = "[0][9]\\d{8}";
		String petternPwd = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$";
		
		if(StringUtils.hasText(request.getPassword()) || StringUtils.hasText(request.getConfirm_password())) {
			
			if(!Pattern.matches(petternPwd, request.getPassword())) {
				return new SignupResponse("密碼錯誤（英數字相加需要大於6個字元）");
			}
			if(!request.getPassword().equals(request.getConfirm_password())) {
				return new SignupResponse("請輸入相同的密碼");
			}
			
		}
		
		if(StringUtils.hasText(request.getPhone())) {
			
			if(!Pattern.matches(patternPhone, request.getPhone())) {
				return new SignupResponse("無效的手機格式");
			}
		}
		
		
		// 將新資料加入
		Member updateMember = new Member();
		updateMember.setEmail(email);
		updateMember.setPassword(request.getPassword());
		updateMember.setConfirm_password(request.getConfirm_password());
		updateMember.setAccount(request.getAccount());
		updateMember.setPhone(request.getPhone());
		updateMember.setAddress(request.getAddress());
		
		// 如果欄位為空，則使用原有的資料
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
		
		// 執行更新操作
		memberDao.save(updateMember);
	
		return new SignupResponse("修改成功");
	}

	// 會員登入
	@Override
	public SignupResponse login(SignupRequest request) {
		
		String email = request.getEmail();
		String pwd = request.getPassword();
		Member daoInfo = memberDao.findByEmail(email);
		
		// 防呆
		if (daoInfo == null) {
			return new SignupResponse("該會員不存在");
		}
		if (!pwd.equals(daoInfo.getPassword())) {
			return new SignupResponse("密碼錯誤");
		}
		
		return new SignupResponse(daoInfo, "登入成功! HELLO!");
	}
	
	

}
