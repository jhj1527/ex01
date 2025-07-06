package com.example.ex01.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.CartDto;

@Mapper
public interface CartMapper {
	List<CartDto> getList(String id);
	
	CartDto get(CartDto dto);
	
	int getCount(CartDto dto);
	
	int[] totalPrice(String id);
	
	void insert(CartDto dto);
	
	void update(CartDto dto);
	
	void updateList(List<CartDto> list);
	
	void delete(Long cno);
}
