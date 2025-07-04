package com.example.ex01.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex01.dto.CartDto;
import com.example.ex01.error.ErrorCode;
import com.example.ex01.exception.ApiException;
import com.example.ex01.mapper.CartMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CartServiceImpl implements CartService {
	private final CartMapper cartMapper;
	
	@Autowired
	public CartServiceImpl(CartMapper cartMapper) {
		this.cartMapper = cartMapper;
	}
	
	@Override
	public List<CartDto> getList(String id) {
		List<CartDto> list = cartMapper.getList(id); 
		return list;
	}
	
	@Override
	public int sumPrice(String id) {
		return cartMapper.sumPrice(id);
	}
	
	@Override
	public int getCount(CartDto dto) {
		return cartMapper.getCount(dto);
	}
	
	@Transactional
	@Override
	public void insert(CartDto dto) throws Exception{
		int cnt = cartMapper.getCount(dto);
		
		if (cnt == 1) {
			throw new ApiException(ErrorCode.DUPLICATION, "카트 중복");
		}
		
		cartMapper.insert(dto);
	}

	@Transactional
	@Override
	public void update(List<CartDto> list) {
		try {
			cartMapper.update(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR , "시스템 오류");
		}
	}

}
