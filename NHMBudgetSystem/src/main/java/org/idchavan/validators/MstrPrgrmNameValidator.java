package org.idchavan.validators;

import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.idchavan.entity.interfaces.IMasterProgramNameBO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MstrPrgrmNameValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return MasterProgramNameEntityImpl.class.equals(clazz);
	}

	@Override
	public void validate(Object targetObj, Errors errors) {
		IMasterProgramNameBO mstrPrgrmNameBO = (MasterProgramNameEntityImpl) targetObj;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inEng", "fieldBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inMarathi", "fieldBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "inShort", "fieldBlank");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "accountHead", "fieldBlank");
		
		if ("NONE".equalsIgnoreCase(mstrPrgrmNameBO.getCategory())) {
			errors.rejectValue("category", "bank.category");
		}
		if ("NONE".equalsIgnoreCase(mstrPrgrmNameBO.getShareType())) {
			errors.rejectValue("shareType", "bank.shareType");
		}
		if ("NONE".equalsIgnoreCase(mstrPrgrmNameBO.getMstrMainGroupRid())) {
			errors.rejectValue("mstrMainGroupRid", "bank.shareType");
		}
	}

}
