package com.example.fruit_grammy_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fruit_grammy_java.entity.Shopping;

public interface ShoppingDao extends JpaRepository<Shopping,String> {

}
