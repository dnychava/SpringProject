package org.idchavan.bo.impl;

import java.util.List;

import org.idchavan.bo.BudgetBO;
import org.idchavan.dao.BudgetDAO;
import org.idchavan.entity.BudgetDetailEntityImpl;
import org.idchavan.entity.BudgetEntityImpl;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Dnyaneshwar
 * @since 19-Feb-2018
 */
@Service("budgetBO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BudgetBOImpl implements BudgetBO {

	@Autowired
	private BudgetDAO budgetDAO;
	
	public BudgetBOImpl() {
		System.out.println("Constructor Called BankBOImpl");
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveOrUpdate(BudgetEntityImpl budgetEntity) {
		budgetDAO.saveOrUpdate(budgetEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteBudget(BudgetEntityImpl budgetEntity) {
		budgetDAO.deleteBudget(budgetEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BudgetEntityImpl> getAllBudgets() {
		return budgetDAO.getAllBudgets();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public BudgetEntityImpl findByRid(String rid) {
		return budgetDAO.findByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<BudgetDetailEntityImpl> getAllBudgetDetails(List<String> rids) {
		return budgetDAO.getAllBudgetDetails(rids);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteBudgetDetail(BudgetDetailEntityImpl budgetDetail) {
		budgetDAO.deleteBudgetDetail(budgetDetail);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public DocumentEntity findDocumentByRid(String rid) {
		return budgetDAO.findDocumentByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public MasterProgramNameEntityImpl findMstrPrgrmNameByRid(String rid) {
		return budgetDAO.findMstrPrgrmNameByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public MasterMainGroupEntityImpl findMainGroupByRid(String rid) {
		return budgetDAO.findMainGroupByRid(rid);
	}

}
