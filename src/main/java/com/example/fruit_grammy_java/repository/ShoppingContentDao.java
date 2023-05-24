package com.example.fruit_grammy_java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fruit_grammy_java.entity.Product;
import com.example.fruit_grammy_java.entity.ShoppingContent;

public interface ShoppingContentDao extends JpaRepository<ShoppingContent,String> {
	
	
	public List<ShoppingContent> findByShoppingNumberContaining(String shoppingNumber);

}
