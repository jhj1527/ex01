package com.example.ex01.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
	private Long oino;
	private String orderId;
	private Long ino;
	private String id;
	private int amount;
	private int price;
//	private int discount;
	private AttachDto dto;
}
