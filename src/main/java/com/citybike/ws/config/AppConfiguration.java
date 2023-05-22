package com.citybike.ws.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix="citybike")
public class AppConfiguration {
	
	private String uploadPath;
	
	private long rate;
	
	public String profileStorage = "profiles";
	
	public String attachmentStorage = "attachments";
	
	public String getProfileStoragePath() {
		return uploadPath + "/" + profileStorage;
	}
	
	public String getAttachmentStoragePath() {
		return uploadPath + "/" + attachmentStorage;
	}

}
