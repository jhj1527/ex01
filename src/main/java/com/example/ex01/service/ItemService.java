package com.example.ex01.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.example.ex01.dto.CriteriaDto;
import com.example.ex01.dto.ItemDto;

public interface ItemService {
	Map<String, Object> getList(CriteriaDto dto);
	
	ItemDto get(Long ino);
	
	int getCount(CriteriaDto dto);
	
	void insert(ItemDto dto) throws Exception;

	void update(ItemDto dto);
	
	void delete(Long ino);
}
