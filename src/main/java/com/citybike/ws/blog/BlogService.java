package com.citybike.ws.blog;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.citybike.ws.blog.vm.BlogSubmitVM;
import com.citybike.ws.file.FileAttachment;
import com.citybike.ws.file.FileAttachmentRepository;
import com.citybike.ws.file.FileService;
import com.citybike.ws.user.User;
import com.citybike.ws.user.UserService;

@Service
public class BlogService {

	BlogRepository blogRepository;
	FileAttachmentRepository fileAttachmentRepository;
	FileService fileService;
	BlogSecurityService blogSecurityService;
	UserService userService;
	
	public BlogService(BlogRepository blogRepository, FileService fileService, UserService userService,
			FileAttachmentRepository fileAttachmentRepository,BlogSecurityService blogSecurityService) {
		this.blogRepository = blogRepository;
		this.fileAttachmentRepository = fileAttachmentRepository;
		this.blogSecurityService = blogSecurityService;
		this.fileService = fileService;
		this.userService = userService;
	}

	public void save(BlogSubmitVM blogSubmitVM, User user) {
		Blog blog = new Blog();
		blog.setContent(blogSubmitVM.getContent());
		blog.setTimestamp(new Date());
		blog.setUser(user);
		blogRepository.save(blog);
		Optional<FileAttachment> optionalFileAttachment = fileAttachmentRepository.findById(blogSubmitVM.getAttachmentId());
		if(optionalFileAttachment.isPresent()) {
			FileAttachment fileAttachment = optionalFileAttachment.get();
			fileAttachment.setBlog(blog);
			fileAttachmentRepository.save(fileAttachment);
		}
	}

	public Page<Blog> getBlogs(Pageable page) {
		return blogRepository.findAll(page);
	}
	
	public Page<Blog> getUserBlogs(Pageable page, String username) {
		User inDB = userService.getByUsername(username);
		return blogRepository.findByUser(inDB, page);
	}

	public Page<Blog> getOldBlogs(long id, String username, Pageable page) {
		
		if(username == null)
			return blogRepository.findAll(idLessThan(id), page);
		User inDB = userService.getByUsername(username);
		return blogRepository.findAll(idLessThan(id).and( userIs(inDB)), page);
	}


	public long getNewBlogsCount(long id, String username) {
		
		if(username == null) 
			return blogRepository.count(idGreaterThan(id));
			
		User inDB = userService.getByUsername(username);
		return blogRepository.count(idGreaterThan(id).and(userIs(inDB)));
	}


	public List<Blog> getNewBlogs(long id, String username, Sort sort) {
	 
		if(username == null) 
			return blogRepository.findAll(idGreaterThan(id), sort);
		
		User inDB = userService.getByUsername(username);
		return blogRepository.findAll(idGreaterThan(id).and(userIs(inDB)), sort);
	}
	
	public void delete(long id) {
		Optional<Blog> optionalBlog = blogRepository.findById(id);
		Blog blog = null;
		if(optionalBlog.isPresent()) {
			blog = optionalBlog.get();
		}
		
		if(blog.getFileAttachment() != null) {
			String fileName = blog.getFileAttachment().getName();
			fileService.deleteAttachmentFile(fileName);
		}
		blogRepository.deleteById(id);
	}
	
	public long count() {
		return blogRepository.count();
	}
	
	Specification<Blog> idLessThan(long id){
		return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("id"), id);
	}
	
	Specification<Blog> userIs(User user){
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user"), user);
	}
	
	Specification<Blog> idGreaterThan(long id){
		return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("id"), id);
	}

	
	

}
