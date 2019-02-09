package org.idchavan.validators;

import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MainGroupValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MasterMainGroupEntityImpl.class.equals(clazz);
	}

	@Override
	public void validate(Object targetObj, Errors errors) {
		IMasterMainGroupBO mainGroupBO = (MasterMainGroupEntityImpl) targetObj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inEng", "fieldBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inMarathi", "fieldBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inShort", "fieldBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sequence", "fieldBlank");
		
		if(mainGroupBO.getSequence() == 0 ){
			errors.rejectValue("sequence", "field.number.zero");
		}
	}

}
