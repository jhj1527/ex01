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
public class ReplyDto {
	@Id
	private Long rno;
	@Column
	private Long bno;
	@Column
	private String reply;
	@Column
	private String id;
	@Column
	private LocalDate regDate;
	@Column
	private LocalDate updateDate;
}
