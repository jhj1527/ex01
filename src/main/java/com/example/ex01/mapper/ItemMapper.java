package com.example.ex01.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.CriteriaDto;
import com.example.ex01.dto.ItemDto;

@Mapper
public interface ItemMapper {
	List<ItemDto> getList(CriteriaDto dto);
	
	ItemDto get(Long ino);

	int getCount(CriteriaDto dto);
	
	void insert(ItemDto dto);

	void update(ItemDto dto);
	
	void delete(Long ino);
}
