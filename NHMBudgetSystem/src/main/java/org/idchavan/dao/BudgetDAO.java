package org.idchavan.dao;

import java.util.List;

import org.idchavan.entity.BudgetDetailEntityImpl;
import org.idchavan.entity.BudgetEntityImpl;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;

public interface BudgetDAO {
	
	public void saveOrUpdate(BudgetEntityImpl budgetEntity);

	public void deleteBudget(BudgetEntityImpl budgetEntity);

	public List<BudgetEntityImpl> getAllBudgets();

	public BudgetEntityImpl findByRid(String rid);

	public List<BudgetDetailEntityImpl> getAllBudgetDetails(List<String> rids);

	public void deleteBudgetDetail(BudgetDetailEntityImpl budgetDetail);

	public DocumentEntity findDocumentByRid(String rid);

	public MasterProgramNameEntityImpl findMstrPrgrmNameByRid(String rid);

	public MasterMainGroupEntityImpl findMainGroupByRid(String rid);

}
