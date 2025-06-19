package com.example.ex01.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttachDto {
	private String attchId;
	private Long ino;
	private String filePath;
	private String fileName;
	private boolean fileType;
}
