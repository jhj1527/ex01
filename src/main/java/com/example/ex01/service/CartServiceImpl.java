package com.example.ex01.service;

import java.util.ArrayList;
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
	public List<CartDto> getCheckList(List<Integer> list) {
		List<CartDto> cartlist = new ArrayList<>();
		
		list.forEach(num -> {
			
		});
		
		return null;
	}
	
	@Override
	public CartDto get(CartDto dto) {
		return cartMapper.getByino(dto);
	}
	
	@Override
	public int[] totalPrice(String id) {
		return cartMapper.totalPrice(id);
	}
	
	@Override
	public int getCount(CartDto dto) {
		return cartMapper.getCount(dto);
	}
	
	@Transactional
	@Override
	public void insert(CartDto dto) throws Exception {
		int cnt = cartMapper.getCount(dto);
		
		if (cnt != 0) {
			throw new ApiException(ErrorCode.DUPLICATION, "카트 중복");
		}
		
		cartMapper.insert(dto);
	}
	
	@Transactional
	@Override
	public void update(CartDto dto) throws Exception {
		CartDto cartDto = get(dto);
		
		if (cartDto != null) {
			if (cartDto.getAmount() + dto.getAmount() > 10) {
				throw new ApiException(ErrorCode.EXCEED_CART);
			}
			
			dto.setCno(cartDto.getCno());
			cartMapper.update(dto);
		}
	}

	@Transactional
	@Override
	public void updateList(List<CartDto> list) throws Exception {
		try {
			cartMapper.updateList(list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	@Override
	public void delete(Long cno) throws Exception {
		try {
			cartMapper.delete(cno);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
