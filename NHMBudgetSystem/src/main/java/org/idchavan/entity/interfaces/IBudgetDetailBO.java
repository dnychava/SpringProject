package org.idchavan.entity.interfaces;

import java.math.BigDecimal;

import org.idchavan.entity.BudgetEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;

public interface IBudgetDetailBO extends IMainBO {

	/**
	 * @return the budgetEntityImpl
	 */
	public BudgetEntityImpl getBudgetEntityImpl();

	/**
	 * @param budgetEntityImpl the budgetEntityImpl to set
	 */
	public void setBudgetEntityImpl(BudgetEntityImpl budgetEntityImpl);

	/**
	 * @return the mstrPrgrNameEnitityImpl
	 */
	public MasterProgramNameEntityImpl getMstrPrgrNameEnitityImpl();

	/**
	 * @param mstrPrgrNameEnitityImpl the mstrPrgrNameEnitityImpl to set
	 */
	public void setMstrPrgrNameEnitityImpl(MasterProgramNameEntityImpl mstrPrgrNameEnitityImpl);

	/**
	 * @return the seasionType
	 */
	public String getSeasionType();

	/**
	 * @param seasionType the seasionType to set
	 */
	public void setSeasionType(String seasionType);

	/**
	 * @return the amtInLakh
	 */
	public BigDecimal getAmtInLakh();

	/**
	 * @param amtInLakh the amtInLakh to set
	 */
	public void setAmtInLakh(BigDecimal amtInLakh);

}
