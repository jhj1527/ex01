package com.example.ex01.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
	private String orderId;
	private String id;
	private String address1;
	private String address2;
	private int orderPrice;
	private int charge;
	private int state;
	private LocalDate regDate;
	private List<OrderItemDto> list;
}
