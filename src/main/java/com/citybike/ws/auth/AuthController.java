package com.citybike.ws.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citybike.ws.shared.GenericResponse;

@RestController
@RequestMapping("/api/1.0")
public class AuthController {
	
	@Autowired
	AuthService authService;
	
	@PostMapping("/auth")
	AuthResponse handleAuthentication(@RequestBody Credentials credentilas) {
		return authService.authenticate(credentilas);
	}
	
	@PostMapping("/logout")
	GenericResponse handleLogout(@RequestHeader(name="Authorization") String authorization) {
		String token = authorization.substring(7);
		authService.clearToken(token);
		return new GenericResponse("Logout success");
		
	}
}
