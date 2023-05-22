package com.citybike.ws.user.vm;

import com.citybike.ws.shared.FileType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateVM {
	
	@NotNull(message= "{citybike.validation.constraints.displayName.NotNull.message}")
	@Size(min=4, max=255)
	private String displayName;
	
	@FileType(types = {"jpeg", "png"})
	private String image;

}
