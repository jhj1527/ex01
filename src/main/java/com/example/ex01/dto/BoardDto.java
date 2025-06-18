package com.example.ex01.dto;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	@Id
	private Long bno;
	@Column
	private String title;
	@Column
	private String content;
	@Column
	private LocalDate regDate;
	@Column
	private LocalDate updateDate;
	@Column
	private String id;
	@Column
	private int viewCount;
	@Column
	private int replyCount;
}
