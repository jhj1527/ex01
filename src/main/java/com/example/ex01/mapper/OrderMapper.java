package com.example.ex01.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.OrderDto;
import com.example.ex01.dto.OrderDto.OrderItemDto;

@Mapper
public interface OrderMapper {
	OrderDto get(String orderId);
	
	void insert(OrderDto dto);

	void orderItemInsert(List<OrderItemDto> list);
}
