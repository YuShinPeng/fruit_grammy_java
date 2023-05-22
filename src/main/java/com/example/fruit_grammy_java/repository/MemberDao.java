package com.example.fruit_grammy_java.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.fruit_grammy_java.entity.Member;

@Repository
public interface MemberDao extends JpaRepository<Member, String>{

	// ·j´Memail
	Member findByEmail(String email);
}
