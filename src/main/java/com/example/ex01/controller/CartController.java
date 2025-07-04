package com.example.ex01.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex01.dto.CartDto;
import com.example.ex01.exception.ApiException;
import com.example.ex01.service.CartService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/cart")
@Slf4j
public class CartController {
	private final CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@GetMapping("/list")
	public ResponseEntity<?> getList(@RequestParam("id") String id) {
		return ResponseEntity.ok(cartService.getList(id));
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@RequestBody CartDto dto, HttpServletResponse response) {
		try {
			cartService.insert(dto);
			
			return new ResponseEntity<>("insert", HttpStatus.CREATED);
			
		} catch (ApiException e) {
			log.error(e.getMessage());
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			return new ResponseEntity<>(map, e.getError().getType());
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@PatchMapping("/update")
	public ResponseEntity<?> update(@RequestBody List<CartDto> list) {
		try {
			list.stream().forEach(c -> {
				log.info(c.toString());
			});
			
			cartService.update(list);
			
			return ResponseEntity.ok("insert");
			
		} catch (Exception e) {
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam("cno") Long cno) {
		return ResponseEntity.ok("a");
	}
}
