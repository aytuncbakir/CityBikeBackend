package com.citybike.ws.blog.vm;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BlogSubmitVM {

	@Size(min= 20 , max=400)
	private String content;
	
	private long attachmentId;
}
