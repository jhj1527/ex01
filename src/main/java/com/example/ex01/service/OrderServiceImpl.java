package com.example.ex01.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex01.dto.CartDto;
import com.example.ex01.dto.OrderDto;
import com.example.ex01.dto.OrderDto.OrderItemDto;
import com.example.ex01.mapper.CartMapper;
import com.example.ex01.mapper.OrderMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
	private final OrderMapper orderMapper;
	private final CartMapper cartMapper;
	
	@Autowired
	public OrderServiceImpl(OrderMapper orderMapper, CartMapper cartMapper) {
		this.orderMapper = orderMapper;
		this.cartMapper = cartMapper;
	}

	@Override
	public OrderDto get(String orderId) {
		return orderMapper.get(orderId);
	}

	@Transactional
	@Override
	public void insert(OrderDto dto) {
		dto.setOrderId(craeteNum(6));
		
		dto.getList().stream().forEach(order -> {
			order.setOrderId(dto.getOrderId());
			
			CartDto cartDto = CartDto
					.builder()
					.id(order.getId())
					.ino(order.getIno())
					.build();
			
			cartDto = cartMapper.get(cartDto);
			
			if (cartDto != null) {
				cartMapper.delete(cartDto.getCno());
			}
		});
		
		orderMapper.insert(dto);
		
		orderMapper.orderItemInsert(dto.getList());
	}
	
	@Override
	public void orderItemInsert(List<OrderItemDto> list) {
		
		
	}
	
	private String craeteNum(int range) {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String formatDate = today.format(formatter);
		
        Random ramdom = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append(formatDate);

        IntStream.range(0 , range).forEach(i -> {
            stringBuilder.append(ramdom.nextInt(10));
        });
        
        log.info("orderId : " + stringBuilder.toString());

        return stringBuilder.toString();
    }
}
