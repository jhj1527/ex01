package com.example.ex01.service;

import java.util.List;
import java.util.Map;

import com.example.ex01.dto.BoardDto;
import com.example.ex01.dto.CriteriaDto;
import com.example.ex01.dto.PageDto;

public interface BoardService {
	BoardDto get(Long bno);
	
	int insert(BoardDto dto);
	
	int update(BoardDto dto, Long bno);
	
	int delete(Long bno);

	int getTotalCount(CriteriaDto dto);

	List<Map<String, Object>> getList(CriteriaDto dto);

	int viewCountupdate(Long bno);
}
