package com.example.ex01.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex01.dto.CriteriaDto;
import com.example.ex01.dto.ItemDto;
import com.example.ex01.exception.ApiException;
import com.example.ex01.service.ItemService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/item")
@Slf4j
public class ItemController {
	private final ItemService itemService;
	
	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getList(@RequestParam(name = "pageNum") int pageNum, @RequestParam(name = "amount") int amount, 
			@RequestParam(name = "type") String type, @RequestParam(name = "keyword") String keyword) {
		
		CriteriaDto dto = CriteriaDto.builder()
				.pageNum(pageNum)
				.amount(amount)
				.type(type)
				.keyword(keyword)
				.build();
		
//		log.info(dto.toString());
		
		return ResponseEntity.ok().body(itemService.getList(dto));
	}

	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody ItemDto dto) throws Exception {
//		log.info(dto.toString());
		
		try {
			itemService.insert(dto);
			
		} catch (ApiException e) {
			log.error("ApiException ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶");
			Map<String, Object> map = new HashMap<>();
			
			map.put("message", e.getMessage());
			
			return ResponseEntity.status(e.getError().getType()).body(map);
			
		} 
		
		return new ResponseEntity<>("insert", HttpStatus.CREATED);
	}
	
	@GetMapping("/{ino}")
	public ResponseEntity<?> get(@PathVariable("ino") Long ino) {
		ItemDto dto =  itemService.get(ino);
		
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PatchMapping("/update")
	public ResponseEntity<?> update(@RequestBody ItemDto dto) {
		itemService.update(dto);
		return new ResponseEntity<>("ok", HttpStatus.OK);
	}
}
