package com.citybike.ws.auth;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import com.citybike.ws.error.AuthException;
import com.citybike.ws.user.User;
import com.citybike.ws.user.UserRepository;
import com.citybike.ws.user.vm.UserVM;

import jakarta.transaction.Transactional;

@Service
public class AuthService {

	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	TokenRepository tokenRepository;
	
	public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenRepository tokenRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenRepository = tokenRepository;
	}

	public AuthResponse authenticate(Credentials credentilas) {
		User inDB = userRepository.findByUsername(credentilas.getUsername());;
		if(inDB == null) {
			throw new AuthException();
		}
		
		boolean isMatched = passwordEncoder.matches(credentilas.getPassword(), inDB.getPassword());
		if(!isMatched) {
			throw new AuthException();
		}
		
		UserVM userVm = new UserVM(inDB);
		
//		Date expiresAt = new Date(System.currentTimeMillis() + 10 * 1000);
		String token = generateRandomToken();
		Token tokenEntity = new Token();
		tokenEntity.setToken(token);
		tokenEntity.setUser(inDB);
		tokenRepository.save(tokenEntity);
		
		AuthResponse response = new AuthResponse();
		response.setToken(token);
		response.setUser(userVm);
		return response;
	}

	@Transactional
	public UserDetails getUserDetails(String token) {
		
		Optional<Token> optionalToken = tokenRepository.findById(token);
		if(!optionalToken.isPresent()) {
			return null;
		}
		return  optionalToken.get().getUser();
	}
	
	public String generateRandomToken() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}

	public void clearToken(String token) {
		tokenRepository.deleteById(token);
	}

}
