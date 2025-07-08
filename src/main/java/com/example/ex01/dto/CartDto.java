package com.example.ex01.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
	private Long cno;
	private String id;
	private Long ino;
	private String name;
	private int price;
	private int amount;
	private List<AttachDto> attachList;
}
