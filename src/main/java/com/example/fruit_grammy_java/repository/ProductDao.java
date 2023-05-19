package com.example.fruit_grammy_java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fruit_grammy_java.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {
	// 透過產地搜尋
	public List<Product> findByPlace(String place);
	
	// 透過品名搜尋
		public List<Product> findByName(String name);

}
