package com.citybike.ws.auth;

import com.citybike.ws.user.vm.UserVM;

import lombok.Data;

@Data
public class AuthResponse {
	
	private String token;
	private UserVM user;

}
