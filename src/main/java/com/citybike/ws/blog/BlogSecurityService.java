package com.citybike.ws.blog;

import java.util.Optional;

import org.springframework.stereotype.Service;


import com.citybike.ws.user.User;

@Service
public class BlogSecurityService {
	
	BlogRepository blogRepository;
	
	public BlogSecurityService(BlogRepository blogRepository) {
		this.blogRepository = blogRepository;
	}

	public boolean isAllowedToDelete(long id, User loggedInUser) {
		
		Optional<Blog> optionalBlog = blogRepository.findById(id);
		if(!optionalBlog.isPresent()) {
			return false;
		}
		
		Blog blog = optionalBlog.get();
		if(blog.getUser().getId() != loggedInUser.getId()) {
			return false;
		}
		return true;
		
	}
}
