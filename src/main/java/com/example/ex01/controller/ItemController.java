package com.example.ex01.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex01.dto.ItemDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class ItemController {
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody ItemDto dto) {
//		log.info(dto.toString());
		return new ResponseEntity<>("aa", HttpStatus.OK);
	}
}
