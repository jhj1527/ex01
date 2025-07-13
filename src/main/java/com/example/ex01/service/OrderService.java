package com.example.ex01.service;

import java.util.List;

import com.example.ex01.dto.OrderDto;

public interface OrderService {
	OrderDto get(String orderId);
	
	void insert(OrderDto dto);

	String craeteNum(int range);

	List<OrderDto> getList(String id);
}
