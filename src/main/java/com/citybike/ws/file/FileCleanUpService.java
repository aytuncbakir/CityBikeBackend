package com.citybike.ws.file;

import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.citybike.ws.config.AppConfiguration;

@Service
@EnableScheduling
public class FileCleanUpService {
	
	FileAttachmentRepository fileAttachmentRepository;
	FileService fileService;
	AppConfiguration appConfiguration;
	
	public FileCleanUpService(FileAttachmentRepository fileAttachmentRepository, FileService fileService, AppConfiguration appConfiguration) {
		this.fileAttachmentRepository = fileAttachmentRepository;
		this.fileService = fileService;
		this.appConfiguration = appConfiguration;
	}

	@Scheduled(fixedRateString = "${citybike.rate}")
	public void cleanUpStorage() {
		Date twentyFourHoursAgo = new Date(System.currentTimeMillis() - (appConfiguration.getRate()));
		List<FileAttachment> filesToBeDeleted = fileAttachmentRepository.findByDateBeforeAndBlogIsNull(twentyFourHoursAgo);
		for(FileAttachment file : filesToBeDeleted) {
			fileService.deleteAttachmentFile(file.getName());
			fileAttachmentRepository.deleteById(file.getId());
		}
	}
}
