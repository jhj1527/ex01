package com.example.ex01.service;

import java.util.List;

import com.example.ex01.dto.CartDto;

public interface CartService {
	List<CartDto> getList(String id);
	
	List<CartDto> getCheckList(int[] checkArr);
	
	CartDto get(CartDto dto);
	
	int getCount(CartDto dto);
	
	int[] totalPrice(String id);
	
	void insert(CartDto dto) throws Exception;

	void update(CartDto dto) throws Exception;
	
	void updateList(List<CartDto> list) throws Exception;
	
	void delete(Long cno) throws Exception;

}
