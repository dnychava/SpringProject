package org.idchavan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.idchavan.entity.interfaces.IBudgetBO;

/**
 * 
 * @author Dnyaneshwar Chavan
 * @since 18-Feb-2018
 */
@Entity
@Table(name="BUDGET")
public class BudgetEntityImpl extends AbstractBaseEntity implements IBudgetBO, Serializable{

	private static final long serialVersionUID = 3570737682830873833L;
	
	
	private List<BudgetDetailEntityImpl> budgetDetails;
	
	private DocumentEntity documentEntity;
	
	private MasterMainGroupEntityImpl mstrMainGroupEnity;
	
	private String year;
	
	private BigDecimal totAmount = new BigDecimal("0.00");

	
	/**
	 * @return the budgetDtlEntityImpls
	 */
	@OneToMany( fetch=FetchType.EAGER,  mappedBy="budgetEntityImpl", cascade=CascadeType.ALL  )
	public List<BudgetDetailEntityImpl> getBudgetDetails() {
		return budgetDetails;
	}

	/**
	 * @param budgetDtlEntityImpls the budgetDtlEntityImpls to set
	 */
	public void setBudgetDetails(List<BudgetDetailEntityImpl> budgetDtlEntityImpls) {
		this.budgetDetails = budgetDtlEntityImpls;
	}

	/**
	 * @return the documentEntity
	 */
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "BUDGET__DOC_TBL_RID", nullable = false, referencedColumnName = "DOC_TBL_RID")
	public DocumentEntity getDocumentEntity() {
		return documentEntity;
	}

	/**
	 * @param documentEntity the documentEntity to set
	 */
	public void setDocumentEntity(DocumentEntity documentEntity) {
		this.documentEntity = documentEntity;
	}

	/**
	 * @return the mstrMainGroupEnity
	 */
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "BUDGET__MSTR_MAIN_GROUP_RID", nullable = false, referencedColumnName = "MSTR_MAIN_GROUP_RID")
	public MasterMainGroupEntityImpl getMstrMainGroupEnity() {
		return mstrMainGroupEnity;
	}

	/**
	 * @param mstrMainGroupEnity the mstrMainGroupEnity to set
	 */
	public void setMstrMainGroupEnity(MasterMainGroupEntityImpl mstrMainGroupEnity) {
		this.mstrMainGroupEnity = mstrMainGroupEnity;
	}

	/**
	 * @return the year
	 */
	@Column(name="BUDGET_YEAR",  nullable=true, length=10)
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the totAmount
	 */
	@Column(name = "BUDGET_TOT_AMOUNT", nullable = false, precision=19, scale=2)
	public BigDecimal getTotAmount() {
		return totAmount;
	}

	/**
	 * @param totAmount the totAmount to set
	 */
	public void setTotAmount(BigDecimal totAmount) {
		this.totAmount = totAmount;
	}

	@Id
	@Column(name="BUDGET_RID", unique = true, nullable=false, length=36)
	@Override
	public String getRid() {
		return super.getRid();
	}

	
	@Override
	public void setRid(String rid) {
		super.setRid(rid);
	}

	@Column(name="BUDGET_CREATED_BY", nullable=true, length=15)
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public void setCreatedBy(String createdBy) {
		super.setCreatedBy(createdBy);
	}

	@Column(name="BUDGET_CREATED_TIMESTAMP", nullable=true)
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		super.setCreatedDate(createdDate);
	}

	@Column(name="BUDGET_MODIFIED_BY", nullable=true, length=15)
	@Override
	public String getModifiedBy() {
		return super.getModifiedBy();
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		super.setModifiedBy(modifiedBy);
	}

	@Column(name="BUDGET_MODIFIED_TIMESTAMP", nullable=true)
	@Override
	public Date getModifiedDate() {
		return super.getModifiedDate();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		super.setModifiedDate(modifiedDate);
	}
}
