package com.example.ex01.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.CartDto;

@Mapper
public interface CartMapper {
	List<CartDto> getList(String id);
	
	int sumPrice(String id);
	
	int getCount(CartDto dto);
	
	void insert(CartDto dto);
	
	void update(List<CartDto> list);
	
	void delete(Long cno);
}
