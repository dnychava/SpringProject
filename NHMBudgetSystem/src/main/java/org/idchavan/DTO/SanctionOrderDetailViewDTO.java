package org.idchavan.DTO;

import java.math.BigDecimal;
import java.util.Date;


public class SanctionOrderDetailViewDTO {

	private String rid;

	private String orderProgramName;
	
	private Date orderDate;
	
	private String orderNumber;
	
	private String orderCategory;
	
	private BigDecimal orderAmt = new BigDecimal("0.00");
	
	private BigDecimal orderAmtInLakh = new BigDecimal("0.00");
	
	private boolean radioBtn = true;
	
	private String sharType;
	
	public SanctionOrderDetailViewDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getOrderProgramName() {
		return orderProgramName;
	}

	public void setOrderProgramName(String orderProgramName) {
		this.orderProgramName = orderProgramName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderCategory() {
		return orderCategory;
	}

	public void setOrderCategory(String orderCategory) {
		this.orderCategory = orderCategory;
	}

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public BigDecimal getOrderAmtInLakh() {
		return orderAmtInLakh;
	}

	public void setOrderAmtInLakh(BigDecimal orderAmtInLakh) {
		this.orderAmtInLakh = orderAmtInLakh;
	}

	public boolean isRadioBtn() {
		return radioBtn;
	}

	public void setRadioBtn(boolean radioBtn) {
		this.radioBtn = radioBtn;
	}
	
	public String getSharType() {
		return sharType;
	}

	public void setSharType(String sharType) {
		this.sharType = sharType;
	}

	@Override
	public String toString() {
		return "SanctionOrderDetailViewDTO [rid=" + rid + ", orderProgramName=" + orderProgramName + ", orderDate="
				+ orderDate + ", orderNumber=" + orderNumber + ", orderCategory=" + orderCategory + ", orderAmt="
				+ orderAmt + ", orderAmtInLakh=" + orderAmtInLakh + ", radioBtn=" + radioBtn + "]";
	}
	
}
