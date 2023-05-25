package com.example.fruit_grammy_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fruit_grammy_java.ifs.MenuService;
import com.example.fruit_grammy_java.vo.MenuRequest;
import com.example.fruit_grammy_java.vo.MenuResponse;

@CrossOrigin
@RestController
public class GetMenuController {
	
	@Autowired
	private MenuService menuService;
	
	// µÊ√–∑j¥M
	@PostMapping("show-info")
	public MenuResponse chooseMenu(@RequestBody MenuRequest request) {
		return menuService.chooseMenu(request);
	}

}
