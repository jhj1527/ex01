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

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("/api/file/*")
@Slf4j
public class FileController {
	@PostMapping("/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile[] file, @RequestParam("folderPath") String folderPath) {
//		List.of(file).forEach(f -> 
//			log.info("uploadFolderPath ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + f)
//		);
		
		List<AttachDto> list = new ArrayList<>();
		String uploadFolder = "D:\\upload";
		
		File uploadPath = new File(uploadFolder, folderPath);
		
		log.info("uploadPath ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + uploadPath);
		
		// 폴더 없으면 생성
		if (!uploadPath.exists()) {
			uploadPath.mkdirs();
		}
		
		for (MultipartFile multipartFile : file) {
			log.info("====================================");
			log.info("upload File Name : " + multipartFile.getOriginalFilename());
			log.info("upload File Size : " + multipartFile.getSize());
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("uploadFileName ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				
				AttachDto dto = AttachDto.builder()
						.attchId(uuid.toString())
						.filePath(folderPath)
						.fileName(uploadFileName)
						.fileType(checkImageType(saveFile))
						.build();
				
				list.add(dto);
				
				multipartFile.transferTo(saveFile);
				
				// check Image type file
//				if (dto.isFileType()) {
//					
//					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
//					Thumbnailator.createThumbnail(multipartFile.getInputStream() , thumbnail , 100 , 100);
//					
//					thumbnail.close();
//					
//				} else {
//					multipartFile.transferTo(saveFile);
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
	
	@GetMapping("/getFile")
	public ResponseEntity<?> getFile(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {
		try {
			File file = new File("D:\\upload\\" + filePath + "\\" + fileName);
			
			ResponseEntity<byte[]> result = null;
			
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			
			return result;
			
		} catch (IOException e) {
			e.printStackTrace();
			
			Map<String, Object> map = new HashMap<>();
			map.put("message", e.getMessage());
			
			log.error(map.get("message").toString());
			
			return ResponseEntity.internalServerError().body(map);
		}
	}
	
//	@GetMapping("/display")
//	public ResponseEntity<?> display(@RequestParam("filePath") String filePath, @RequestParam("fileName") String fileName) {
//		try {
//			Path path = Paths.get("D:\\upload\\" + filePath + "\\" + fileName);
//			Resource resource = new UrlResource(path.toUri());
//			
//			if (resource.exists() || resource.isReadable()) {
//                return ResponseEntity.ok()
//                		.contentType(MediaType.IMAGE_JPEG)
//                		.body(resource);
//                
//            } else {
//                return ResponseEntity.notFound().build();
//            }
//			
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//			
//			return ResponseEntity.notFound().build();
//		}
//	}
	
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
	public ResponseEntity<?> delete(@RequestBody AttachDto dto) {
//	public ResponseEntity<?> delete(@RequestParam("fileName") String fileName, @RequestParam("fileType")String fileType) {
//		log.info("deleteFile ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ : " + fileName);
//		log.info("fileType : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + fileType);
		log.info("dto : ▶▶▶▶▶▶▶▶▶▶▶▶▶▶▶ " + dto);

		try {
			File file = new File("D:\\upload\\" + dto.getFilePath().trim() + "\\" + URLDecoder.decode(dto.getFileName(), "UTF-8"));
			log.info("file : " + file.toString());

			if (file.exists()) {
				file.delete();
			}

//            File[] directoryList = file.getParentFile().listFiles();
//
//            if (directoryList.length == 0) file.getParentFile().delete();
//
//            if (Filetype.equals("image")) {
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
