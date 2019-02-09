package org.idchavan.validators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.BankEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BankValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BankEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object targetObt, Errors error) {
		BankEntity bank = (BankEntity) targetObt;

		Collection<BankDetailEntity> removedObj = new ArrayList<BankDetailEntity>();
		// Remove the deleted record from JS
		for (BankDetailEntity sanOrdDtl : bank.getBankDetails()) {
			if (sanOrdDtl.getGrProgramName()== null) {
				removedObj.add(sanOrdDtl);
			}
		}
		bank.getBankDetails().removeAll(removedObj);
		
		if ("NONE".equalsIgnoreCase(bank.getYear())) {
			error.rejectValue("year", "bank.year");
		}
		ValidationUtils.rejectIfEmptyOrWhitespace(error, "name", "bank.name");
		
		int index = 0;
		for (BankDetailEntity sanOrdDtl : bank.getBankDetails()) {
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getGrProgramName())) {
				error.rejectValue("bankDetails[" + index + "].grProgramName", "bank.grProgramName");
			}
			if (sanOrdDtl.getDepositDate() == null) {
				error.rejectValue("bankDetails[" + index + "].depositDate", "bank.depositDate");
			}
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getCategory())) {
				error.rejectValue("bankDetails[" + index + "].category", "bank.category");
			}
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getShare())) {
				error.rejectValue("bankDetails[" + index + "].share", "bank.shareType");
			}
			if (StringUtils.isEmpty(sanOrdDtl.getGrSanOrdDtlNumber())
					|| StringUtils.isBlank(sanOrdDtl.getGrSanOrdDtlNumber())) {
				error.rejectValue("bankDetails[" + index + "].grSanOrdDtlNumber", "bank.grSanOrdDtlNumber");
			}
			if (sanOrdDtl.getAmt().compareTo(BigDecimal.ZERO) == 0) {
				error.rejectValue("bankDetails[" + index + "].amt", "bank.amt.zero");
			} else if (sanOrdDtl.getAmt().compareTo(BigDecimal.ZERO) < 0) {
				error.rejectValue("bankDetails[" + index + "].amt", "bank.amt.negative");
			}
			index++;
		}
	}
}
