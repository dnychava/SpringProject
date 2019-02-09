package org.idchavan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Dnyaneshwar chavan
 * @since 14-cot-17
 * @version 1.0
 */
@Entity
@Table(name="GR_BANK_DTL")
public class BankDetailEntity implements Serializable{
	
	private static final long serialVersionUID = -7988799579036225137L;
	
	@Id
	@Column(name="GR_BANK_DTL_RID", unique = true, nullable=false, length=36)
	private String rid;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "GR_BANK_DTL__GR_BANK_RID", nullable = false, referencedColumnName = "GR_BANK_RID")
	private BankEntity bank;
	
	@Column(name = "GR_BANK_DTL__GR_SAN_ORD_DTL_RID", nullable = false, length=36)
	private String grSanOrdDtlRid;
	
	@Column(name="GR_BANK_DTL_PROGRAM_NAME", nullable=false, length=255)
	private String grProgramName;
	
	@Column(name="GR_BANK_DTL_DEPOSIT_DATE",  nullable=true)
	private Date depositDate;
	
	@Column(name = "GR_BANK_DTL__GR_SAN_ORD_DTL_NUMBER", nullable = true, length=500)
	private String grSanOrdDtlNumber;
	
	@Column(name="GR_BANK_DTL_SHARE", nullable=false, length=20)
	private String share;
	
	@Column(name="GR_BANK_DTL_CATEGORY", nullable=false, length=10)
	private String category;
	
	@Column(name="GR_BANK_DTL_AMOUNT", nullable=false, precision=19, scale=2)
	private BigDecimal amt = new BigDecimal("0.00");
	
	@Column(name="GR_BANK_DTL_AMOUNT_IN_LACK", nullable=false, precision=19, scale=2)
	private BigDecimal amtInLakh = new BigDecimal("0.00");
	
	@Column(name="GR_BANK_DTL_COMPLETED", nullable=false, length=1)
	private char completed='N';
	
	@Column(name="GR_BANK_DTL_CREATED_BY", nullable=true, length=15)
	private String createdBy;
	
	@Column(name="GR_BANK_DTL_CREATED_TIMESTAMP", nullable=true)
	private Date createdDate;
	
	@Column(name="GR_BANK_DTL_MODIFIED_BY", nullable=true, length=15)
	private String modifiedBy;
	
	@Column(name="GR_BANK_DTL_MODIFIED_TIMESTAMP", nullable=true)
	private Date modifiedDate;
	
	public BankDetailEntity() {
		
	}

	public String getRid() {
		if(this.rid==null || "null".equalsIgnoreCase(this.rid)) {
			this.rid = UUID.randomUUID().toString();
		}
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public BankEntity getBank() {
		return bank;
	}

	public void setBank(BankEntity bank) {
		this.bank = bank;
	}

	public String getGrSanOrdDtlRid() {
		return grSanOrdDtlRid;
	}

	public void setGrSanOrdDtlRid(String grSanOrdDtlRid) {
		this.grSanOrdDtlRid = grSanOrdDtlRid;
	}

	public String getGrProgramName() {
		return grProgramName;
	}

	public void setGrProgramName(String grProgramName) {
		this.grProgramName = grProgramName;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public String getGrSanOrdDtlNumber() {
		return grSanOrdDtlNumber;
	}

	public void setGrSanOrdDtlNumber(String grSanOrdDtlNumber) {
		this.grSanOrdDtlNumber = grSanOrdDtlNumber;
	}

	public String getShare() {
		return share;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getAmtInLakh() {
		return amtInLakh;
	}

	public void setAmtInLakh(BigDecimal amtInLakh) {
		this.amtInLakh = amtInLakh;
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
		return "BankDetailEntity [rid=" + rid + ", grSanOrdDtlRid=" + grSanOrdDtlRid + ", grProgramName="
				+ grProgramName + ", depositDate=" + depositDate + ", grSanOrdDtlNumber=" + grSanOrdDtlNumber
				+ ", share=" + share + ", category=" + category + ", amt=" + amt + ", amtInLakh=" + amtInLakh
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
}
