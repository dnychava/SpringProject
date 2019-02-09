package org.idchavan.validators;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.GrSanctionOrderEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class GrSanctionOrderValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return GrSanctionOrderEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object targetObt, Errors error) {
		GrSanctionOrderEntity sanctionOrder = (GrSanctionOrderEntity) targetObt;

		Collection<GrSanctionOrderDetailEntity> removedObj = new ArrayList<GrSanctionOrderDetailEntity>();
		// Remove the deleted record from JS
		for (GrSanctionOrderDetailEntity sanOrdDtl : sanctionOrder.getGrSanctionOrderDetails()) {
			if (sanOrdDtl.getOrderProgramName() == null) {
				removedObj.add(sanOrdDtl);
			}
		}
		sanctionOrder.getGrSanctionOrderDetails().removeAll(removedObj);

		if ("NONE".equalsIgnoreCase(sanctionOrder.getYear())) {
			error.rejectValue("year", "sanOrd.year");
		}

		if ("NONE".equalsIgnoreCase(sanctionOrder.getProgramGroupName())) {
			error.rejectValue("programGroupName", "sanOrd.programGroupName");
		}
		int index = 0;
		for (GrSanctionOrderDetailEntity sanOrdDtl : sanctionOrder.getGrSanctionOrderDetails()) {
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getOrderProgramName())) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].orderProgramName", "sanOrd.orderProgramName");
			}
			if (sanOrdDtl.getOrderDate() == null) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].orderDate", "sanOrd.orderDate");
			}
			if (StringUtils.isEmpty(sanOrdDtl.getOrderNumber()) || StringUtils.isBlank(sanOrdDtl.getOrderNumber())) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].orderNumber", "sanOrd.orderNumber");
			}
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getOrderCategory())) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].orderCategory", "sanOrd.orderCategory");
			}
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getShare())) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].share", "sanOrd.shareType");
			}
			if (StringUtils.isEmpty(sanOrdDtl.getSanOrdDtlNumber())
					|| StringUtils.isBlank(sanOrdDtl.getSanOrdDtlNumber())) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].sanOrdDtlNumber", "sanOrd.orderNumberInGR");
			}

			if (sanOrdDtl.getOrderAmt().compareTo(BigDecimal.ZERO) == 0) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].orderAmt", "sanOrd.amt.zero");
			} else if (sanOrdDtl.getOrderAmt().compareTo(BigDecimal.ZERO) < 0) {
				error.rejectValue("grSanctionOrderDetails[" + index + "].orderAmt", "sanOrd.amt.negative");
			}
			index++;
		}
	}
}
