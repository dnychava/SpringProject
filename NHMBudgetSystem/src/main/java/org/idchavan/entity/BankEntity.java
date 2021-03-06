package org.idchavan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Dnyaneshwar chavan
 * @since 14-cot-17
 * @version 1.0
 */
@Entity
@Table(name="GR_BANK")
public class BankEntity implements Serializable{
	
	private static final long serialVersionUID = -7988799579036225137L;
	
	@Id
	@Column(name="GR_BANK_RID", unique = true, nullable=false, length=36)
	private String rid;
	
	@Column(name="GR_BANK_YEAR",  nullable=true, length=10)
	private String year;
	
	@Column(name="GR_BANK_NAME",  nullable=true, length=255)
	private String name;
	
	@OneToMany( fetch=FetchType.EAGER,  mappedBy="bank", cascade=CascadeType.ALL  )
	private List<BankDetailEntity> bankDetails = new ArrayList<BankDetailEntity>();
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "GR_BANK__DOC_TBL_RID", nullable = false, referencedColumnName = "DOC_TBL_RID")
	private DocumentEntity documentEntity;

	@Column(name = "GR_BANK_TOT_AMOUNT", nullable = false, precision=19, scale=2)
	private BigDecimal totAmount = new BigDecimal("0.00");
	
	@Column(name="GR_BANK_CREATED_BY", nullable=true, length=15)
	private String createdBy;
	
	@Column(name="GR_BANK_CREATED_TIMESTAMP", nullable=true)
	private Date createdDate;
	
	@Column(name="GR_BANK_MODIFIED_BY", nullable=true, length=15)
	private String modifiedBy;
	
	@Column(name="GR_BANK_MODIFIED_TIMESTAMP", nullable=true)
	private Date modifiedDate;
	
	public BankEntity() {
		
	}

	public String getRid() {
		if(this.rid==null ) {
			this.rid = UUID.randomUUID().toString();
		}
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<BankDetailEntity> getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(List<BankDetailEntity> bankDetails) {
		this.bankDetails = bankDetails;
	}

	public DocumentEntity getDocumentEntity() {
		return documentEntity;
	}

	public void setDocumentEntity(DocumentEntity documentEntity) {
		this.documentEntity = documentEntity;
	}

	public BigDecimal getTotAmount() {
		return totAmount;
	}
	
	public void setTotAmount(BigDecimal totAmount) {
		this.totAmount = totAmount;
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
		return "BankEntity [rid=" + rid + ", name=" + name + ", totAmount=" + totAmount + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate
				+ "]";
	}
}
