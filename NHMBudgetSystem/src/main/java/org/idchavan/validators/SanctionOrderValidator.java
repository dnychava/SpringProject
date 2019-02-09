package org.idchavan.validators;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SanctionOrderValidator implements Validator {
	
	@Value("${dis.date.formatter}")
	protected String dispDateFormatter;

	@Override
	public boolean supports(Class<?> clazz) {
		return SanctionOrderEntity.class.equals(clazz);
	}

	@Override
	public void validate(Object targetObt, Errors error) {
		SanctionOrderEntity sanctionOrder = (SanctionOrderEntity) targetObt;
		
		Collection<SanctionOrderDetailEntity> removedObj = new ArrayList<SanctionOrderDetailEntity>();
		// Remove the deleted record from JS
		for (SanctionOrderDetailEntity sanOrdDtl : sanctionOrder.getSanctionOrderDetails()) {
			if (sanOrdDtl.getOrderProgramName()== null) {
				removedObj.add(sanOrdDtl);
			}
		}
		sanctionOrder.getSanctionOrderDetails().removeAll(removedObj);
		
		if ("NONE".equalsIgnoreCase(sanctionOrder.getYear())) {
			error.rejectValue("year", "sanOrd.year");
		}

		if ("NONE".equalsIgnoreCase(sanctionOrder.getProgramGroupName())) {
			error.rejectValue("programGroupName", "sanOrd.programGroupName");
		}
		int index = 0;
		for (SanctionOrderDetailEntity sanOrdDtl : sanctionOrder.getSanctionOrderDetails()) {
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getOrderProgramName())) {
				error.rejectValue("sanctionOrderDetails[" + index + "].orderProgramName", "sanOrd.orderProgramName");
			}
			if (sanOrdDtl.getOrderDate() == null) {
				error.rejectValue("sanctionOrderDetails[" + index + "].orderDate", "sanOrd.orderDate");
			}else if( !isValidDate(sanctionOrder.getYear(), sanOrdDtl.getOrderDate())){
				error.rejectValue("sanctionOrderDetails[" + index + "].orderDate", "sanOrd.orderDateBetween");
			}
			
			if (sanOrdDtl.getOrderDate() == null && "".equalsIgnoreCase(sanOrdDtl.getOrderNumber())) {
				error.rejectValue("sanctionOrderDetails[" + index + "].orderNumber", "sanOrd.orderNumber");
			}
			if ("NONE".equalsIgnoreCase(sanOrdDtl.getOrderCategory())) {
				error.rejectValue("sanctionOrderDetails[" + index + "].orderCategory", "sanOrd.orderCategory");
			}
			if (sanOrdDtl.getOrderAmt().compareTo(BigDecimal.ZERO) == 0) {
				error.rejectValue("sanctionOrderDetails[" + index + "].orderAmt", "sanOrd.amt.zero");
			} else if (sanOrdDtl.getOrderAmt().compareTo(BigDecimal.ZERO) < 0) {
				error.rejectValue("sanctionOrderDetails[" + index + "].orderAmt", "sanOrd.amt.negative");
			}
			index++;
		}
	}
	
	/**
	 * This method validate the given date 
	 * is correct or not, between the selected <code>Financial Year.</code>
	 * 
	 * @param year the selected 
	 * @param checkDate the selected date.
	 * @return false if not date between the Financial Year. 
	 * Otherwise return true
	 */
	private boolean isValidDate(String year, Date checkDate){
		String[] yesrs = year.split("-");
		boolean returnVal = true;
		if(yesrs.length > 1 ){
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 01);
			cal.set(Calendar.MONTH, 03);// Jan = 0, dec = 11
			cal.set(Calendar.YEAR, Integer.parseInt(yesrs[0]));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			
			Date fromDate = cal.getTime();
			
			cal.set(Calendar.DAY_OF_MONTH, 31);
			cal.set(Calendar.MONTH, 02);// Jan = 0, dec = 11
			cal.set(Calendar.YEAR, Integer.parseInt("20" + yesrs[1]));
			
			Date toDate = cal.getTime();
			
			if (checkDate.getTime() >= fromDate.getTime() && checkDate.getTime() <= toDate.getTime()) {
				returnVal = true;
			} else {
				returnVal = false;
			}
		}
		return returnVal;
	}
}
