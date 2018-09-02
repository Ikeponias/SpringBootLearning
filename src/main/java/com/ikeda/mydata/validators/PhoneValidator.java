package com.ikeda.mydata.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ikeda.mydata.annotations.Phone;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
	private boolean onlyNumber = false;
	
	@Override
	public void initialize(Phone phone) {
		onlyNumber = phone.onlyNumber();
	}
	
	@Override
	public boolean isValid(String input, ConstraintValidatorContext cxt) {
		if (input == null) {
			return false;
		}
		
		if (onlyNumber) {
			return input.matches("[0-9]*");
		} else {
			return input.matches("[0-9()-]*");
		}
	}
}
