package com.citybike.ws.shared;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.citybike.ws.file.FileService;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileTypeValidator implements ConstraintValidator<FileType, String>{
	
	@Autowired
	FileService fileService;
	
	String[] types;
	
	@Override
	public void initialize(FileType constraintAnnotation) {
		
		this.types = constraintAnnotation.types();
	}

	@Override
	public boolean isValid(String image, ConstraintValidatorContext context) {
		
		if(image == null || image.isEmpty())
			return true;
		
		String fileType = fileService.detectType(image);
		for(String supportedType : this.types) {
			if(fileType.contains(supportedType) ) {
				return true;
			}
		}
		String supportedTypes = Arrays.stream(this.types).collect(Collectors.joining(", "));
		context.disableDefaultConstraintViolation();
		HibernateConstraintValidatorContext hibernateConstraintValidatorContext = context.unwrap(HibernateConstraintValidatorContext.class);
		hibernateConstraintValidatorContext.addMessageParameter("types", supportedTypes);
		hibernateConstraintValidatorContext.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).addConstraintViolation();
		return false;
	}

}
