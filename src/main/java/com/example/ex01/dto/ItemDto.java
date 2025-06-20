package com.example.ex01.dto;

import java.util.List;

import lombok.Data;

@Data
public class ItemDto {
	private Long ino;
    private String category;
    private String name;
    private Long price;
    private Long amount;
    private Long discount;
    private String content;
    private List<AttachDto> attachList;
}
