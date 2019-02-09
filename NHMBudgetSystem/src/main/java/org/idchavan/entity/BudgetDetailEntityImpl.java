package org.idchavan.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.idchavan.entity.interfaces.IBudgetDetailBO;

/**
 * 
 * @author Dnyaneshwar
 * @since 18-Feb-2018
 */
@Entity
@Table(name="BUDGET_DTL")
public class BudgetDetailEntityImpl extends AbstractBaseEntity implements IBudgetDetailBO, Serializable{

	private static final long serialVersionUID = 4003880614021721829L;
	
	private BudgetEntityImpl budgetEntityImpl;
	
	private MasterProgramNameEntityImpl mstrPrgrNameEnitityImpl;
	
	private String seasionType;
	
	private BigDecimal amtInLakh = new BigDecimal("0.00");

	/**
	 * @return the budgetEntityImpl
	 */
	@ManyToOne(fetch = FetchType.EAGER, cascade={CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumn(name = "BUDGET_DTL__BUDGET_RID", nullable= false, referencedColumnName = "BUDGET_RID")
	public BudgetEntityImpl getBudgetEntityImpl() {
		return budgetEntityImpl;
	}

	/**
	 * @param budgetEntityImpl the budgetEntityImpl to set
	 */
	public void setBudgetEntityImpl(BudgetEntityImpl budgetEntityImpl) {
		this.budgetEntityImpl = budgetEntityImpl;
	}

	/**
	 * @return the mstrPrgrNameEnitityImpl
	 */
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "BUDGET_DTL__MSTR_PROGRAM_NAME_RID", nullable = false, referencedColumnName = "MSTR_PROGRAM_NAME_RID")
	public MasterProgramNameEntityImpl getMstrPrgrNameEnitityImpl() {
		return mstrPrgrNameEnitityImpl;
	}

	/**
	 * @param mstrPrgrNameEnitityImpl the mstrPrgrNameEnitityImpl to set
	 */
	public void setMstrPrgrNameEnitityImpl(MasterProgramNameEntityImpl mstrPrgrNameEnitityImpl) {
		this.mstrPrgrNameEnitityImpl = mstrPrgrNameEnitityImpl;
	}

	/**
	 * @return the seasionType
	 */
	@Column(name="BUDGET_DTL_SEASION_TYPE",  nullable=true, length=10)
	public String getSeasionType() {
		return seasionType;
	}

	/**
	 * @param seasionType the seasionType to set
	 */
	public void setSeasionType(String seasionType) {
		this.seasionType = seasionType;
	}

	/**
	 * @return the amtInLakh
	 */
	@Column(name="BUDGET_DTL_AMOUNT_IN_LAKH", nullable=false, precision=19, scale=2)
	public BigDecimal getAmtInLakh() {
		return amtInLakh;
	}

	/**
	 * @param amtInLakh the amtInLakh to set
	 */
	public void setAmtInLakh(BigDecimal amtInLakh) {
		this.amtInLakh = amtInLakh;
	}

	@Id
	@Column(name="BUDGET_DTL_RID", unique = true, nullable=false, length=36)
	@Override
	public String getRid() {
		return super.getRid();
	}
	
	@Override
	public void setRid(String rid) {
		super.setRid(rid);
	}

	@Column(name="BUDGET_DTL_CREATED_BY", nullable=true, length=15)
	@Override
	public String getCreatedBy() {
		return super.getCreatedBy();
	}

	@Override
	public void setCreatedBy(String createdBy) {
		super.setCreatedBy(createdBy);
	}

	@Column(name="BUDGET_DTL_CREATED_TIMESTAMP", nullable=true)
	@Override
	public Date getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public void setCreatedDate(Date createdDate) {
		super.setCreatedDate(createdDate);
	}

	@Column(name="BUDGET_DTL_MODIFIED_BY", nullable=true, length=15)
	@Override
	public String getModifiedBy() {
		return super.getModifiedBy();
	}

	@Override
	public void setModifiedBy(String modifiedBy) {
		super.setModifiedBy(modifiedBy);
	}

	@Column(name="BUDGET_DTL_MODIFIED_TIMESTAMP", nullable=true)
	@Override
	public Date getModifiedDate() {
		return super.getModifiedDate();
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		super.setModifiedDate(modifiedDate);
	}
}
