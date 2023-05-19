package com.example.fruit_grammy_java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fruit_grammy_java.entity.Product;

@Repository
public interface ProductDao extends JpaRepository<Product, String> {
	// �z�L���a�j�M
	public List<Product> findByPlace(String place);
	
	// �z�L�~�W�j�M
		public List<Product> findByName(String name);

}
