package com.example.ex01.dto;

import lombok.Data;

@Data
public class CartDto {
	private Long cno;
	private String id;
	private Long ino;
	private int amount;
	private int totalPrice;
	private ItemDto itemDto;
}
