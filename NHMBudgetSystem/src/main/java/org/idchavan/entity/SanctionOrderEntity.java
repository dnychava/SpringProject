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

@Entity
@Table(name ="SAN_ORD" )
public class SanctionOrderEntity implements Serializable{
	
	private static final long serialVersionUID = -7988799579036225137L;
	
	@Id
	@Column(name="SAN_ORD_RID", unique = true, nullable=false, length=36)
	String rid;
	
	@OneToMany( fetch=FetchType.EAGER,  mappedBy="sanctionOrder", cascade=CascadeType.ALL  )
	private List<SanctionOrderDetailEntity> sanctionOrderDetails = new ArrayList<SanctionOrderDetailEntity>();
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "SAN_ORD__DOC_TBL_RID", nullable = false, referencedColumnName = "DOC_TBL_RID")
	private DocumentEntity documentEntity;
	
	@Column(name="SAN_ORD_YEAR", nullable=false, length=10)
	private String year;
	
	@Column(name="SAN_ORD_PROGRAM_GROUP_NAME", nullable=false, length=255)
	private String programGroupName;
	
	@Column(name = "SAN_ORD_TOT_AMOUNT", nullable = false, precision=19, scale=2)
	private BigDecimal totAmount = new BigDecimal("0.00");
		
	@Column(name="SAN_ORD_CREATED_BY", nullable=true, length=15)
	private String createdBy;
	
	@Column(name="SAN_ORD_CREATED_TIMESTAMP", nullable=true)
	private Date createdDate;
	
	@Column(name="SAN_ORD_MODIFIED_BY", nullable=true, length=15 )
	private String modifiedBy;
	
	@Column(name="SAN_ORD_MODIFIED_TIMESTAMP", nullable=true)
	private Date modifiedDate;
	
	/**This field only use for handle the duplicate record exception.*/
	private String duplicateError;
	
	public SanctionOrderEntity() {

	}
		
	public String getRid() {
		if( this.rid == null ) {
			this.rid = UUID.randomUUID().toString();
		}
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}
	
	public List<SanctionOrderDetailEntity> getSanctionOrderDetails() {
		return sanctionOrderDetails;
	}

	public void setSanctionOrderDetails(List<SanctionOrderDetailEntity> sanctionOrderDetails) {
		this.sanctionOrderDetails = sanctionOrderDetails;
	}
	
	public DocumentEntity getDocumentEntity() {
		return documentEntity;
	}

	public void setDocumentEntity(DocumentEntity documentEntity) {
		this.documentEntity = documentEntity;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public String getProgramGroupName() {
		return programGroupName;
	}

	public void setProgramGroupName(String programGroupName) {
		this.programGroupName = programGroupName;
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
	
	/**
	 * @return the duplicateError
	 */
	public String getDuplicateError() {
		return duplicateError;
	}

	/**
	 * @param duplicateError the duplicateError to set
	 */
	public void setDuplicateError(String duplicateError) {
		this.duplicateError = duplicateError;
	}

	@Override
	public String toString() {
		return "SanctionOrder [rid=" + rid + ", year=" + year + ", programGroupName=" + programGroupName
				+ ", createdBy=" + createdBy + ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy
				+ ", modifiedDate=" + modifiedDate + "]";
	}
}
