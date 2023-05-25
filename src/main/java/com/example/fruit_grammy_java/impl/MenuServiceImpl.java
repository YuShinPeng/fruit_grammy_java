package com.example.fruit_grammy_java.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.fruit_grammy_java.entity.DishList;
import com.example.fruit_grammy_java.ifs.MenuService;
import com.example.fruit_grammy_java.repository.DishListDao;
import com.example.fruit_grammy_java.repository.MenuDao;
import com.example.fruit_grammy_java.vo.MenuRequest;
import com.example.fruit_grammy_java.vo.MenuResponse;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private DishListDao dishListDao;

	// 菜譜搜尋
	@Override
	public MenuResponse chooseMenu(MenuRequest request) {
		
		String name = request.getName();
		DishList dishDao = dishListDao.findByName(name);
		
		if(!StringUtils.hasText(name)) {
			return new MenuResponse("無此菜譜");
		}
		
		return new MenuResponse(dishDao, "做法如下");
	}

}
