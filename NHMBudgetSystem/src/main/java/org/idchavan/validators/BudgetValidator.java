package org.idchavan.validators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.BankEntity;
import org.idchavan.entity.BudgetDetailEntityImpl;
import org.idchavan.entity.BudgetEntityImpl;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class BudgetValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return BudgetEntityImpl.class.equals(clazz);
	}

	@Override
	public void validate(Object targetObt, Errors error) {
		BudgetEntityImpl budget = (BudgetEntityImpl) targetObt;

		Collection<BudgetDetailEntityImpl> removedObj = new ArrayList<BudgetDetailEntityImpl>();
		// Remove the deleted record from JS
		for (BudgetDetailEntityImpl budgetDtl : budget.getBudgetDetails()) {
			if (budgetDtl.getMstrPrgrNameEnitityImpl().getAccountHead()== null) {
				removedObj.add(budgetDtl);
			}
		}
		budget.getBudgetDetails().removeAll(removedObj);
		
		if ("NONE".equalsIgnoreCase(budget.getYear())) {
			error.rejectValue("year", "budget.year");
		}
		
		if ("NONE".equalsIgnoreCase(budget.getMstrMainGroupEnity().getRid())) {
			error.rejectValue("mstrMainGroupEnity.rid", "budget.mainGroup");
		}
		
		int index = 0;
		for (BudgetDetailEntityImpl budgetDtl : budget.getBudgetDetails()) {
			if ("NONE".equalsIgnoreCase(budgetDtl.getMstrPrgrNameEnitityImpl().getShareType())) {
				error.rejectValue("budgetDetails[" + index + "].mstrPrgrNameEnitityImpl.shareType", "budget.shareType");
			}
			if ("NONE".equalsIgnoreCase(budgetDtl.getMstrPrgrNameEnitityImpl().getCategory())) {
				error.rejectValue("budgetDetails[" + index + "].mstrPrgrNameEnitityImpl.category", "budget.category");
			}
			if (StringUtils.isEmpty(budgetDtl.getMstrPrgrNameEnitityImpl().getAccountHead())
					|| StringUtils.isBlank(budgetDtl.getMstrPrgrNameEnitityImpl().getAccountHead())) {
				error.rejectValue("budgetDetails[" + index + "].mstrPrgrNameEnitityImpl.accountHead", "budget.accountHead");
			}
			if ("NONE".equalsIgnoreCase(budgetDtl.getSeasionType())) {
				error.rejectValue("budgetDetails[" + index + "].seasionType", "budget.seasonType");
			}
			if (budgetDtl.getAmtInLakh().compareTo(BigDecimal.ZERO) == 0) {
				error.rejectValue("budgetDetails[" + index + "].amtInLakh", "budget.amtInLack.zero");
			} else if (budgetDtl.getAmtInLakh().compareTo(BigDecimal.ZERO) < 0) {
				error.rejectValue("budgetDetails[" + index + "].amtInLakh", "budget.amtInLack.negative");
			}
			index++;
		}
	}
}
