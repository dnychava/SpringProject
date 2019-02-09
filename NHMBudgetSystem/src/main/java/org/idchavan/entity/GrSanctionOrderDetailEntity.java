package org.idchavan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * @author Dnyaneshwar chavan
 * @since 14-cot-17
 * @version 1.0
 * */

@Entity
@Table(name ="GR_SAN_ORD_DTL" )
public class GrSanctionOrderDetailEntity implements Serializable{
	
	private static final long serialVersionUID = -7988799579036225137L;
	
	@Id
	@Column(name="GR_SAN_ORD_DTL_RID", unique = true, nullable=false, length=36)
	private String rid;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "GR_SAN_ORD_DTL__GR_SAN_ORD_RID", nullable = false, referencedColumnName = "GR_SAN_ORD_RID")
	private GrSanctionOrderEntity grSanctionOrder;
	
	@Column(name = "GR_SAN_ORD_DTL__SAN_ORD_DTL_RID", nullable = true, length=36)
	private String sanOrdDtlRid;
	
	@Column(name = "GR_SAN_ORD_DTL__SAN_ORD_DTL_NUMBER", nullable = true, length=500)
	private String sanOrdDtlNumber;
	
	@Column(name="GR_SAN_ORD_DTL_PROGRAM_NAME", nullable=false, length=255)
	private String orderProgramName;
	
	@Column(name="GR_SAN_ORD_DTL_DATE", nullable=false)
	private Date orderDate;
	
	@Column(name="GR_SAN_ORD_DTL_NUMBER", nullable=false, length=500)
	private String orderNumber;
	
	@Column(name="GR_SAN_ORD_DTL_SHARE", nullable=false, length=20)
	private String share;
	
	@Column(name="GR_SAN_ORD_DTL_CATEGORY", nullable=false, length=10)
	private String orderCategory;
	
	@Column(name="GR_SAN_ORD_DTL_AMOUNT", nullable=false, precision=19, scale=2)
	private BigDecimal orderAmt = new BigDecimal("0.00");
	
	@Column(name="GR_SAN_ORD_DTL_AMOUNT_IN_LACK", nullable=false, precision=19, scale=2)
	private BigDecimal orderAmtInLakh = new BigDecimal("0.00");
	
	@Column(name="GR_SAN_ORD_DTL_COMPLETED", nullable=false, length=1)
	private char completed='N';
	
	@Column(name="GR_SAN_ORD_DTL_CREATED_BY", nullable=true, length=15)
	private String createdBy;
	
	@Column(name="GR_SAN_ORD_DTL_CREATED_TIMESTAMP", nullable=true)
	private Date createdDate;
	
	@Column(name="GR_SAN_ORD_DTL_MODIFIED_BY", nullable=true, length=15)
	private String modifiedBy;
	
	@Column(name="GR_SAN_ORD_DTL_MODIFIED_TIMESTAMP", nullable=true)
	private Date modifiedDate;
	
	public GrSanctionOrderDetailEntity() {
		// TODO Auto-generated constructor stub
	}

	public String getRid() {
		if(this.rid==null || "null".equalsIgnoreCase(rid)) {
			this.rid = UUID.randomUUID().toString();
		}
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
	
	public GrSanctionOrderEntity getGrSanctionOrder() {
		return grSanctionOrder;
	}

	public void setGrSanctionOrder(GrSanctionOrderEntity grSanctionOrder) {
		this.grSanctionOrder = grSanctionOrder;
	}
	
	public String getSanOrdDtlRid() {
		return sanOrdDtlRid;
	}

	public void setSanOrdDtlRid(String sanOrdDtlRid) {
		this.sanOrdDtlRid = sanOrdDtlRid;
	}
	
	public String getSanOrdDtlNumber() {
		return sanOrdDtlNumber;
	}

	public void setSanOrdDtlNumber(String sanOrdDtlNumber) {
		this.sanOrdDtlNumber = sanOrdDtlNumber;
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

	public String getShare() {
		return share;
	}

	public void setShare(String grShare) {
		this.share = grShare;
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
	
	public char getCompleted() {
		return completed;
	}

	public void setCompleted(char completed) {
		this.completed = completed;
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
		return "GrSanctionOrderDetailEntity [rid=" + rid + ", orderProgramName=" + orderProgramName + ", orderDate="
				+ orderDate + ", orderNumber=" + orderNumber + ", grShare=" + share + ", orderCategory="
				+ orderCategory + ", orderAmt=" + orderAmt + ", orderAmtInLakh=" + orderAmtInLakh + ", createdBy="
				+ createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate="
				+ modifiedDate + "]";
	}
	
}
