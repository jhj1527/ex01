package com.example.ex01.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.ex01.dto.AttachDto;

@Mapper
public interface AttachMapper {
	List<AttachDto> getList(Long ino);
	
	void insert(AttachDto dto);
	
	void delete(String attachId);
	
	void deleteAll(Long ino);
}
