package com.example.ex01.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ex01.dto.AttachDto;
import com.example.ex01.service.FileService;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("/api/file/*")
@Slf4j
public class FileController {
//	private final String uploadFolder = "D:\\upload";
	
	private final FileService fileService;
	
	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}

	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile[] file, @RequestParam("folderPath") String folderPath) {
		try {
//			List.of(file).forEach(f -> {
//				log.info("file ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + f);
//			});
//			
//			log.info("folderPath : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + folderPath);
			
			List<AttachDto> list = fileService.upload(file, folderPath);
			
			return ResponseEntity.status(HttpStatus.OK).body(list);
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			log.error(map.get("message").toString());
			
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@GetMapping("/getFile")
	public ResponseEntity<?> getFile(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {
		try {
//			log.info("filePath : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + filePath);
//			log.info("fileName : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + fileName);
			
			ResponseEntity<byte[]> result = fileService.getFlle(filePath, fileName);
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			log.error(map.get("message").toString());
			
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@GetMapping("/download")
	public ResponseEntity<?> download(@RequestHeader("User-Agent") String userAgent, String fileName) {
		try {
//			log.info("userAgent : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + userAgent);
//			log.info("fileName : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + fileName);
			
			ResponseEntity<Resource> result = fileService.download(userAgent, fileName);
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			log.error(map.get("message").toString());
			
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody AttachDto dto) {
		try {
//			log.info("dto : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + dto);
			
			fileService.delete(dto);

		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			log.error(map.get("message").toString());
			
			return ResponseEntity.internalServerError().body(map);
		}

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}
}
