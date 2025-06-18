package com.example.ex01.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex01.dto.MemberDto;
import com.example.ex01.error.ErrorCode;
import com.example.ex01.error.ErrorDto;
import com.example.ex01.exception.ApiException;
import com.example.ex01.service.MemberService;
import com.example.ex01.validator.MemberValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/member")
public class MemberController {
	private final MemberService memberService;
	private final MemberValidator memberValidator;
	
	@Autowired
	public MemberController(MemberService memberService, MemberValidator memberValidator) {
		this.memberService = memberService;
		this.memberValidator = memberValidator;
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> get(@RequestParam("id") String id, HttpServletRequest request, Authentication auth) {
//		log.info(auth.toString());
		
//		JSONParser parser = new JSONParser();
//		JSONObject object = new JSONObject();
//		ObjectMapper objectMapper = new ObjectMapper();
		
//		try {
//			object = (JSONObject) parser.parse(id);
//			
//		} catch (ParseException e) {
//			e.printStackTrace();
//			return new ResponseEntity<>("error", HttpStatus.BAD_REQUEST);
//		}
//		
//		String memberid = (String) object.get("id");
		
		MemberDto dto = memberService.findById(id);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	
	@PostMapping("/insert")
	public ResponseEntity<?> insert(@Validated @RequestBody MemberDto dto, BindingResult bindingResult) {
		Map<String, Object> map = new HashMap<>();
		int insert = 0;
		
		try {
			memberValidator.validate(dto, bindingResult);
			
			if (bindingResult.hasErrors()) {
				String meaasge = bindingResult.getAllErrors()
						.stream()
						.map(ObjectError::getDefaultMessage)
						.collect(Collectors.joining());
				
//				map = bindingResult.getAllErrors()
//						.stream()
//						.collect(Collectors.toMap((e) -> "message", e1 -> e1.getDefaultMessage()));
				throw new ApiException(ErrorCode.MEMBER_REQUIRED, meaasge);
			}
			
			insert = memberService.insert(dto);
			return ResponseEntity.status(HttpStatus.OK).body(insert);
			
		} catch (ApiException e) {
//			ErrorDto resultDto = ErrorDto.builder()
//					.type(e.getError().getType())
//					.code(e.getError().getCode())
//					.message(e.getMessage())
//					.build();
			
			log.error("error!!!!!!!!!!!!!!!!!!!");
			map.put("message", e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
		}
	}
}
