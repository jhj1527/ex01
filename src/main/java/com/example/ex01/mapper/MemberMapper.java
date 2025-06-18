package com.example.ex01.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.MemberDto;

@Mapper
public interface MemberMapper {
	MemberDto findById(String id);
	
	MemberDto findByPassword(String password);
	
	int insert(MemberDto dto);
}
