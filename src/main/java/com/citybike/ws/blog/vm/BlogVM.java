package com.citybike.ws.blog.vm;


import com.citybike.ws.blog.Blog;
import com.citybike.ws.file.vm.FileAttachmentVM;
import com.citybike.ws.user.vm.UserVM;

import lombok.Data;


@Data
public class BlogVM {
	

	private long id;
	private String content;
	private long timestamp;
	private UserVM user;
	private FileAttachmentVM fileAttachment;
	
	public BlogVM(Blog blog) {
		this.setId(blog.getId());
		this.setContent(blog.getContent());
		this.setTimestamp(blog.getTimestamp().getTime());
		this.setUser(new UserVM(blog.getUser()));
		if(blog.getFileAttachment() != null)
			this.fileAttachment = new FileAttachmentVM(blog.getFileAttachment());
	}
}
