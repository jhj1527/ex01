package com.example.ex01.service;

import com.example.ex01.dto.MemberDto;


public interface MemberService {
	MemberDto findById(String id);
	
	MemberDto findByPassword(String password);
	
	int insert(MemberDto dto);
}
