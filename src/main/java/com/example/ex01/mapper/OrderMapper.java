package com.example.ex01.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.OrderDto;

@Mapper
public interface OrderMapper {
	OrderDto get(String orderId);
	
	void insert(OrderDto dto);
}
