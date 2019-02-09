package org.idchavan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Nationalized;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * @author Dnyaneshwar chavan
 * @since 14-cot-17
 * @version 1.0
 */

@Entity
@Table(name = "SAN_ORD_DTL",
		uniqueConstraints={
		@UniqueConstraint(
				columnNames={"SAN_ORD_DTL_PROGRAM_NAME", "SAN_ORD_DTL_DATE", "SAN_ORD_DTL_CATEGORY", "SAN_ORD_DTL_AMOUNT"  },
				name="UK__SAN_ORD_DTL__ALL_DETAIL_FIELDS"
			)
	})
public class SanctionOrderDetailEntity implements Serializable {

	private static final long serialVersionUID = -7988799579036225137L;

	@Id
	@Column(name = "SAN_ORD_DTL_RID", unique = true, nullable = false, length=36)
	private String rid;

	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.EAGER)
	@JoinColumn(name = "SAN_ORD_DTL__SAN_ORD_RID", nullable = false, referencedColumnName = "SAN_ORD_RID")
	private SanctionOrderEntity sanctionOrder;
	
	@Nationalized
	@Column(name="SAN_ORD_DTL_PROGRAM_NAME", nullable=false, length=255)
	private String orderProgramName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SAN_ORD_DTL_DATE", nullable = true)
	private Date orderDate;

	@Column(name = "SAN_ORD_DTL_NUMBER", nullable = false, length=500)
	private String orderNumber;

	@Column(name = "SAN_ORD_DTL_CATEGORY", nullable = false, length=10)
	private String orderCategory;

	@Column(name = "SAN_ORD_DTL_AMOUNT", nullable = false, precision=19, scale=2)
	BigDecimal orderAmt = new BigDecimal("0.00");

	@Column(name = "SAN_ORD_DTL_AMOUNT_IN_LAKH", nullable = false, precision=19, scale=2)
	BigDecimal orderAmtInLakh = new BigDecimal("0.00");
	
	@Column(name = "SAN_ORD_DTL_STATE_SHARE_AMOUNT", nullable = false, precision=19, scale=2)
	BigDecimal orderStateShareAmt = new BigDecimal("0.00");
	
	@Column(name = "SAN_ORD_DTL_STATE_SHARE_AMOUNT_IN_LAKH", nullable = false, precision=19, scale=2)
	BigDecimal orderStateShareAmtInLakh = new BigDecimal("0.00");
	
	@Column(name = "SAN_ORD_DTL_COMPLETED", nullable = false, length=1)
	char completed='N';
	
	@Column(name = "SAN_ORD_DTL_STATE_SHARE_COMPLETED", nullable = false, length=1)
	char stateShareCompleted='N';

	@Column(name = "SAN_ORD_DTL_SHARE_PER", nullable = false, length=1)
	private int orderSharePer = 0;
	
	@Column(name = "SAN_ORD_DTL_STATE_SHARE_PER", nullable = false, length=1)
	private int orderStateSharePer = 0;
	
	
	@Column(name = "SAN_ORD_DTL_CREATED_BY", nullable = true, length=15)
	String createdBy;

	@Column(name = "SAN_ORD_DTL_CREATED_TIMESTAMP", nullable = true)
	Date createdDate;

	@Column(name = "SAN_ORD_DTL_MODIFIED_BY", nullable = true, length=15)
	String modifiedBy;

	@Column(name = "SAN_ORD_DTL_MODIFIED_TIMESTAMP", nullable = true, length=15)
	Date modifiedDate;

	public SanctionOrderDetailEntity() {
		System.out.println("SanctionOrderDetail");
	}

	public String getRid() {

		if (this.rid == null || "null".equalsIgnoreCase(rid)) {
			this.rid = UUID.randomUUID().toString();
		}
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
	
	public SanctionOrderEntity getSanctionOrder() {
		return sanctionOrder;
	}

	public void setSanctionOrder(SanctionOrderEntity sanctionOrder) {
		this.sanctionOrder = sanctionOrder;
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
	
	public BigDecimal getOrderStateShareAmt() {
		return orderStateShareAmt;
	}

	public void setOrderStateShareAmt(BigDecimal orderStateShareAmt) {
		this.orderStateShareAmt = orderStateShareAmt;
	}

	public BigDecimal getOrderStateShareAmtInLakh() {
		return orderStateShareAmtInLakh;
	}

	public void setOrderStateShareAmtInLakh(BigDecimal orderStateShareAmtInLakh) {
		this.orderStateShareAmtInLakh = orderStateShareAmtInLakh;
	}
	
	public char getCompleted() {
		return completed;
	}

	public void setCompleted(char completed) {
		this.completed = completed;
	}
	
	public char getStateShareCompleted() {
		return stateShareCompleted;
	}

	public void setStateShareCompleted(char stateShareCompleted) {
		this.stateShareCompleted = stateShareCompleted;
	}
	
	/**
	 * @return the orderSharePer
	 */
	public int getOrderSharePer() {
		return orderSharePer;
	}

	/**
	 * @param orderSharePer the orderSharePer to set
	 */
	public void setOrderSharePer(int orderSharePer) {
		this.orderSharePer = orderSharePer;
	}
	
	/**
	 * @return the orderStateSharePer
	 */
	public int getOrderStateSharePer() {
		return orderStateSharePer;
	}

	/**
	 * @param orderStateSharePer the orderStateSharePer to set
	 */
	public void setOrderStateSharePer(int orderStateSharePer) {
		this.orderStateSharePer = orderStateSharePer;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Override
	public String toString() {
		return "SanctionOrderDetailEntity [rid=" + rid + ", orderProgramName=" + orderProgramName + ", orderDate="
				+ orderDate + ", orderNumber=" + orderNumber + ", orderCategory=" + orderCategory + ", orderAmt="
				+ orderAmt + ", orderAmtInLakh=" + orderAmtInLakh + ", orderStateShareAmt=" + orderStateShareAmt
				+ ", orderStateShareAmtInLakh=" + orderStateShareAmtInLakh + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate
				+ "]";
	}
}
