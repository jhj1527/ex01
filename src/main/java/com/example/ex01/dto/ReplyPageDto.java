package com.example.ex01.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReplyPageDto {
	private PageDto pageDto;
	private List<ReplyDto> list;
	private int replyCnt;
}
