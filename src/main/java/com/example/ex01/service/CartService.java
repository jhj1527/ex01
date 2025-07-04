package com.example.ex01.service;

import java.util.List;

import com.example.ex01.dto.CartDto;

public interface CartService {
	List<CartDto> getList(String id);
	
	int sumPrice(String id);
	
	int getCount(CartDto dto);
	
	void insert(CartDto dto) throws Exception;

	void update(List<CartDto> list);
}
