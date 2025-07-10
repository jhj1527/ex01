package com.example.ex01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex01.dto.OrderDto;
import com.example.ex01.mapper.OrderMapper;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/order")
@Slf4j
public class OrderController {
	private final OrderMapper orderMapper;
	@Autowired
	public OrderController(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}


	@PostMapping("/insert")
	public ResponseEntity<?> OrderIsert(@RequestBody OrderDto dto) {
//		log.info(dto.toString());
		orderMapper.insert(dto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body("insert");
	}
	
	
}
