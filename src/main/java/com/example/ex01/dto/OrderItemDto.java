package com.example.ex01.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
	private Long oino;
	private String orderId;
	private Long ino;
	private String id;
	private int amount;
	private int price;
	private int discount;
	private AttachDto dto;
}
