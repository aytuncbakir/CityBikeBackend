package com.citybike.ws.file;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citybike.ws.user.User;

public interface FileAttachmentRepository extends JpaRepository<FileAttachment, Long>{
	
	List<FileAttachment> findByDateBeforeAndBlogIsNull(Date date);
	
	List<FileAttachment> findByBlogUser(User user);
}
