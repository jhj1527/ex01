package com.example.ex01.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.ex01.dto.AttachDto;

public interface FileService {

	List<AttachDto> upload(MultipartFile[] file, String folderPath);

	ResponseEntity<Resource> download(String userAgent, String fileName);

	ResponseEntity<byte[]> getFlle(String filePath, String fileName);

	void delete(AttachDto dto);
	
}
