package com.example.ex01.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.ex01.dto.AttachDto;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/file/*")
@Slf4j
public class FileController {
	@PostMapping("/upload")
	public ResponseEntity<?> upload(MultipartFile[] uploadFile , @RequestParam("uploadFolderPath") String uploadFolderPath) {
		log.info("uploadFolderPath ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + uploadFolderPath);
		
		List<AttachDto> list = new ArrayList<>();
		String uploadFolder = "D:\\upload";
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		
		log.info("uploadPath ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + uploadPath);
		
		// 폴더 없으면 생성
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for (MultipartFile file : uploadFile) {
			log.info("====================================");
			log.info("upload File Name : " + file.getOriginalFilename());
			log.info("upload File Size : " + file.getSize());
			
			String uploadFileName = file.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("uploadFileName ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				
				AttachDto dto = AttachDto.builder()
						.attchId(uuid.toString())
						.filePath(uploadFolderPath)
						.fileName(uploadFileName)
						.fileType(checkImageType(saveFile))
						.build();
				
				list.add(dto);
				
				file.transferTo(saveFile);
				
				// check Image type file
//				if (checkImageType(saveFile)) {
//					dto.setFileType(checkImageType(saveFile));
//					
//					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
//					Thumbnailator.createThumbnail(file.getInputStream() , thumbnail , 100 , 100);
//					
//					thumbnail.close();
//					
//				} else {
//					file.transferTo(saveFile);
//				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
				
				Map<String, Object> map = new HashMap<>();
				map.put("message", e.getMessage());
				
				log.error(map.get("message").toString());
				
				return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR); 
			}
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}
	
	@GetMapping("/display")
	public ResponseEntity<?> display(@RequestParam("fileName") String fileName) {
		try {
			File file = new File("D:\\upload\\" + fileName);
			
			ResponseEntity<byte[]> result = null;
			
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file) , header , HttpStatus.OK);
			
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			log.error(map.get("message").toString());
			
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
	}
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download(@RequestHeader("User-Agent") String userAgent, String fileName) {
		Resource resource = new FileSystemResource("D:\\upload\\" + fileName);

		log.info("resource ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ : " + resource);

		if (!resource.exists()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		String resourceName = resource.getFilename();
		// remove uuid
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		HttpHeaders headers = new HttpHeaders();

		try {
			String downloadName = null;

			if (userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8").replaceAll("\\+", " ");

			} else if (userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");

			} else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}

			log.info("downloadName : " + downloadName);

			headers.add("content-Disposition", "attachment; filename=" + downloadName);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<?> delete(String fileName, String type) {
		log.info("deleteFile ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ : " + fileName);
		log.info("type : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + type);

		try {
			File file = new File("D:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			log.info("file : " + file);

			if (file.exists()) {
				file.delete();
			}

//            File[] directoryList = file.getParentFile().listFiles();
//
//            if (directoryList.length == 0) file.getParentFile().delete();
//
//            if (type.equals("image")) {
//                String largeFileName = file.getAbsolutePath().replace("s_" , "");
//                log.info("largeFileName : " + largeFileName);
//
//                file = new File(largeFileName);
//                file.delete();
//            }

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>("deleted", HttpStatus.OK);
	}

	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

        return false;
	}
}
