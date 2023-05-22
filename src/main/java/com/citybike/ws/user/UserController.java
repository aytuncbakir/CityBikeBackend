package com.citybike.ws.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.citybike.ws.shared.CurrentUser;
import com.citybike.ws.shared.GenericResponse;
import com.citybike.ws.user.vm.UserUpdateVM;
import com.citybike.ws.user.vm.UserVM;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/1.0")
public class UserController {
	
	UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	Page<UserVM> getUsers(Pageable page, @CurrentUser User user){
		return userService.getUsers(page, user).map(UserVM::new);
	}
	
	@GetMapping("/users/{username}")
	UserVM getUser(@PathVariable String username){
		return new UserVM(userService.getByUsername(username));
	}
	
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.OK)
	public GenericResponse createUser(@Valid @RequestBody User user) {
		userService.save(user);
		return new  GenericResponse("Usercreated");
	}
	
	@PutMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	UserVM updateUser(@Valid @RequestBody UserUpdateVM updatedUser, @PathVariable String username ){
		User user = userService.updateUser(username, updatedUser);
		return new UserVM(user);
	}
	
	@DeleteMapping("/users/{username}")
	@PreAuthorize("#username == principal.username")
	GenericResponse deleteUser( @PathVariable String username ){
		userService.deleteUser(username); 
		return new GenericResponse("User deleted");
	}
	
	
	@GetMapping("/users/count")
	public long count() {
		return userService.count();
	}
	
	
}
