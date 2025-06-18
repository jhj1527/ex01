package com.example.ex01.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ex01.dto.BoardDto;
import com.example.ex01.dto.CriteriaDto;
import com.example.ex01.dto.PageDto;
import com.example.ex01.dto.ReplyDto;
import com.example.ex01.error.ErrorCode;
import com.example.ex01.exception.ApiException;
import com.example.ex01.mapper.BoardMapper;
import com.example.ex01.mapper.ReplyMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
	@Autowired
	private final BoardMapper boardMapper;
	private final ReplyMapper replyMapper;
	
	public BoardServiceImpl(BoardMapper boardMapper, ReplyMapper replyMapper) {
		this.boardMapper = boardMapper;
		this.replyMapper = replyMapper;
	}
	
	@Override
	public List<Map<String, Object>> getList(CriteriaDto dto) {
		return boardMapper.getList(dto);
	}

	@Override
	@Transactional
	public BoardDto get(Long bno) {
		int result = viewCountupdate(bno);
		
		if (result != 1) {
			throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR , "시스템 오류");
		}
		
		return boardMapper.get(bno);
	}

	@Override
	@Transactional
	public int insert(BoardDto dto) {
		return boardMapper.insert(dto);
	}

	@Override
	@Transactional
	public int update(BoardDto dto, Long bno) {
		BoardDto boardDto = boardMapper.get(bno);

		if (boardDto == null || boardDto.getBno() != bno) {
			return 0;
		}
		
		return boardMapper.update(dto);
	}

	@Override
	@Transactional
	public int delete(Long bno) {
		List<ReplyDto> list = replyMapper.getListByBno(bno);
		
		if (!list.isEmpty()) {
			list.forEach(dto -> {
				replyMapper.delete(dto.getRno());
			});
		}
		
		return boardMapper.delete(bno);
	}

	@Override
	public int getTotalCount(CriteriaDto dto) {
		return boardMapper.getTotalCount(dto);
	}

	@Override
	public int viewCountupdate(Long bno) {
		return boardMapper.viewCountupdate(bno);
	}
	
}
