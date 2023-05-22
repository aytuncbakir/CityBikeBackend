package com.citybike.ws.blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citybike.ws.blog.vm.BlogSubmitVM;
import com.citybike.ws.blog.vm.BlogVM;
import com.citybike.ws.shared.CurrentUser;
import com.citybike.ws.shared.GenericResponse;
import com.citybike.ws.user.User;


import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/1.0")
public class BlogController {
	
	@Autowired
	BlogService blogService;
	

	@PostMapping("/blogs")
	@ResponseStatus(HttpStatus.OK)
    GenericResponse saveBlog(@Valid @RequestBody BlogSubmitVM blog, @CurrentUser User user) {
		blogService.save(blog, user);
		return new  GenericResponse("Blog added");
	}
	
	@GetMapping("/blogs")
	Page<BlogVM> getBlogs(@PageableDefault(sort="id", direction=Direction.DESC) Pageable page){
		return blogService.getBlogs(page).map(BlogVM::new);
	}
	
	@GetMapping({"/blogs/{id:[0-9]+}", "/users/{username}/blogs/{id:[0-9]+}" })
	ResponseEntity<?> getBlogsRelative(
			@PathVariable long id, @PathVariable(required=false) String username,
			@PageableDefault(sort="id", direction=Direction.DESC) Pageable page,
			@RequestParam(name="count", required=false, defaultValue = "false") boolean count,
			@RequestParam(name="direction", defaultValue = "before") String direction){
		if(count) {
			long newBlogsCount = blogService.getNewBlogsCount(id, username);
			Map<String, Long> response = new HashMap<String, Long>();
			response.put("count", newBlogsCount);
			return ResponseEntity.ok(response);
		}
		if(direction.equals("after")) {
			List<Blog> newBlogs = blogService.getNewBlogs(id, username,  page.getSort());
			List<BlogVM> newBlogsVM = newBlogs.stream().map(BlogVM::new).collect(Collectors.toList());
			return ResponseEntity.ok(newBlogsVM);
		}
		return ResponseEntity.ok( blogService.getOldBlogs(id,username,  page).map(BlogVM::new));
	}
	
	@GetMapping("/users/{username}/blogs")
	Page<BlogVM> getUserBlogs(@PathVariable String username, @PageableDefault(sort="id", direction=Direction.DESC) Pageable page, @CurrentUser User user){
		return blogService.getUserBlogs(page, username).map(BlogVM::new);
	}
	
	@DeleteMapping("/blogs/{id:[0-9]+}")
	@PreAuthorize("@blogSecurityService.isAllowedToDelete(#id, principal)")
	GenericResponse deleteBlog(@PathVariable long id) {
		blogService.delete(id);
		return new GenericResponse("Blog deleted.");
	}
	
	@GetMapping("/users/blogs/count")
	public long count() {
		return blogService.count();
	}	
}
