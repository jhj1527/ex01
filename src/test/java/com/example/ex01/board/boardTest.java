package com.example.ex01.board;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import com.example.ex01.config.DatabaseConfig;
import com.example.ex01.dto.BoardDto;
import com.example.ex01.mapper.BoardMapper;
import com.example.ex01.service.BoardService;
import com.example.ex01.service.BoardServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Import({DatabaseConfig.class})
//@ContextConfiguration(classes = {DatabaseConfig.class , BoardServiceImpl.class})
@SpringBootTest
//@MybatisTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class boardTest {
	@Autowired
	private BoardService boardService;
	@Autowired
	private BoardMapper boardMapper;

	@Test
	void contextLoads() {
//		List<Map<String , Object>> list = boardService.getList();
		
//		log.info("list : " + list.toString());
		
		BoardDto dto = new BoardDto();
		
		LongStream.rangeClosed(16, 150).forEach(i -> {
			dto.setBno(i);
			dto.setTitle("title" + i);
			dto.setContent("content" + i);
			dto.setId("member1");
			dto.setRegDate(LocalDate.now());
			
			boardService.insert(dto);
		});
		
		
	}

}
