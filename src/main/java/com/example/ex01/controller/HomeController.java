package com.example.ex01.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ex01.dto.MemberDto;

import lombok.extern.slf4j.Slf4j;

@Controller
public class HomeController {
	@GetMapping("")
	public String home() {
		return "index.html";
	}
}
