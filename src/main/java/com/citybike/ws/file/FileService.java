package com.citybike.ws.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.citybike.ws.config.AppConfiguration;
import com.citybike.ws.user.User;

@Service
public class FileService {
	
	AppConfiguration appConfiguration;
	Tika tika ;
	FileAttachmentRepository fileAttachmentRepository;
		
	public FileService(AppConfiguration appConfiguration, FileAttachmentRepository fileAttachmentRepository) {
		this.appConfiguration = appConfiguration;
		this.fileAttachmentRepository = fileAttachmentRepository;
		tika = new Tika();
	}


	public String writeBase64EncodedStringToFile(String image) throws IOException {
		
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getProfileStoragePath()+"/"+fileName);
		OutputStream outputStream = new FileOutputStream(target);
		
		byte[] base64encoded = Base64.getDecoder().decode(image);
		outputStream.write(base64encoded);
		outputStream.close();
		return fileName;
	}
	
	public String generateRandomName() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public void deleteProfileImage(String oldImageName){
		if(oldImageName == null ) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getProfileStoragePath(), oldImageName));
	}
	
	public void deleteAttachmentFile(String oldImageName){
		if(oldImageName == null ) {
			return;
		}
		deleteFile(Paths.get(appConfiguration.getAttachmentStoragePath(), oldImageName));
		
	}
	
	private void deleteFile( Path path) {	
		try {
			Files.deleteIfExists(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String detectType(String base64) {
		byte[] base64encoded = Base64.getDecoder().decode(base64);
		return tika.detect(base64encoded);
	}


	public String detectType(byte[] byteData) {
		return tika.detect(byteData);
	}


	public FileAttachment saveBlogAttachment(MultipartFile multipartFile) {
		String fileName = generateRandomName();
		File target = new File(appConfiguration.getAttachmentStoragePath()+"/"+fileName);
		OutputStream outputStream;
		String fileType = null;
		try {
			outputStream = new FileOutputStream(target);
			outputStream.write(multipartFile.getBytes());
			outputStream.close();
			fileType = detectType(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileAttachment fileAttachment = new FileAttachment();
		fileAttachment.setName(fileName);
		fileAttachment.setDate(new Date());
		fileAttachment.setFileType(fileType);
		return fileAttachmentRepository.save(fileAttachment);
		
	}


	public void deleteAllStoredFilesOfUser(User inDB) {
		deleteProfileImage(inDB.getImage());
		List<FileAttachment> filesToBeRemoved = fileAttachmentRepository.findByBlogUser(inDB);
		for(FileAttachment file : filesToBeRemoved) {
			deleteAttachmentFile(file.getName());
		}
	}
	
	
	

}
