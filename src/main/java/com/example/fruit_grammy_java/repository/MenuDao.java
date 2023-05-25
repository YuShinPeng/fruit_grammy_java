package com.example.fruit_grammy_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fruit_grammy_java.entity.Menu;

@Repository
public interface MenuDao extends JpaRepository<Menu, String>{

}
