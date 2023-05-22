package com.citybike.ws.file;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

	FileService fileService;

	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping("/api/1.0/blog-attachments")
	FileAttachment saveBlogAttachment(MultipartFile file) {
		FileAttachment fileAttachment = fileService.saveBlogAttachment(file);
		return fileAttachment;
	}
}
