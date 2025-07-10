package com.example.ex01.service;

import com.example.ex01.dto.OrderDto;

public interface OrderService {
	OrderDto get(String orderId);
	
	void insert(OrderDto dto);
}
