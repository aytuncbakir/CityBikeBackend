package com.citybike.ws.user;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citybike.ws.error.NotFoundException;
import com.citybike.ws.file.FileService;
import com.citybike.ws.user.vm.UserUpdateVM;

@Service
public class UserService {
	
	UserRepository userRepository;
	PasswordEncoder passwordEncoder;
	FileService fileService;

	public UserService(UserRepository userService, PasswordEncoder passwordEncoder, FileService fileService) {
		this.userRepository = userService;
		this.passwordEncoder = passwordEncoder;
		this.fileService = fileService;
	}

	public void save(User user) {
		String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		userRepository.save(user);
	}

	public Page<User> getUsers(Pageable page, User user) {
		if(user != null)
			return userRepository.findByUsernameNot(user.getUsername(), page);
		else	
			return userRepository.findAll(page);
	}

	public User getByUsername(String username) {
		User  inDB =  userRepository.findByUsername(username);
		if(inDB == null) {
			 throw new NotFoundException();
		}
		return inDB;
	}

	public User updateUser(String username, UserUpdateVM updatedUser) {
		User inDB = getByUsername(username);
		inDB.setDisplayName(updatedUser.getDisplayName());
		if(updatedUser.getImage() != null) {
			String oldImage = inDB.getImage();
			try {
				String storedFileName = fileService.writeBase64EncodedStringToFile(updatedUser.getImage());
				inDB.setImage(storedFileName);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			fileService.deleteProfileImage(oldImage);
		}
		userRepository.save(inDB);
		return inDB;
	}

	public void deleteUser(String username) {
		User inDB = userRepository.findByUsername(username);
		fileService.deleteAllStoredFilesOfUser(inDB);
		userRepository.delete(inDB);
	}

	public long count() {
		return userRepository.count();
	}
}