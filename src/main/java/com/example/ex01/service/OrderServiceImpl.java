package com.example.ex01.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex01.dto.OrderDto;
import com.example.ex01.mapper.OrderMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final OrderMapper orderMapper;
	
	@Autowired
	public OrderServiceImpl(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	@Override
	public OrderDto get(String orderId) {
		return orderMapper.get(orderId);
	}

	@Transactional
	@Override
	public void insert(OrderDto dto) {
		orderMapper.insert(dto);
	}
}
