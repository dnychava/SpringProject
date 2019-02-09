package org.idchavan.entity.interfaces;

import java.math.BigDecimal;
import java.util.List;

import org.idchavan.entity.BudgetDetailEntityImpl;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.MasterMainGroupEntityImpl;

public interface IBudgetBO extends IMainBO {

	/**
	 * @return the budgetDetails
	 */
	public List<BudgetDetailEntityImpl> getBudgetDetails();

	/**
	 * @param BudgetDetails the budgetDtlEntityImpls to set
	 */
	public void setBudgetDetails(List<BudgetDetailEntityImpl> BudgetDetails);
	
	/**
	 * @return the documentEntity
	 */
	public DocumentEntity getDocumentEntity();

	/**
	 * @param documentEntity the documentEntity to set
	 */
	public void setDocumentEntity(DocumentEntity documentEntity);

	/**
	 * @return the mstrMainGroupEnity
	 */
	public MasterMainGroupEntityImpl getMstrMainGroupEnity();

	/**
	 * @param mstrMainGroupEnity the mstrMainGroupEnity to set
	 */
	public void setMstrMainGroupEnity(MasterMainGroupEntityImpl mstrMainGroupEnity);
	
	/**
	 * @return the year
	 */
	public String getYear();

	/**
	 * @param year the year to set
	 */
	public void setYear(String year);

	/**
	 * @return the totAmount
	 */
	public BigDecimal getTotAmount();

	/**
	 * @param totAmount the totAmount to set
	 */
	public void setTotAmount(BigDecimal totAmount);
}
