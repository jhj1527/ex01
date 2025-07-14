package com.example.ex01.service;

import java.util.List;

import com.example.ex01.dto.OrderDto;

public interface OrderService {
	List<OrderDto> getList(String id);
	
	OrderDto getDetailList(String orderId);
	
	OrderDto get(String orderId);
	
	void insert(OrderDto dto);

	String craeteNum(int range);

	void delete(String orderId);
}
