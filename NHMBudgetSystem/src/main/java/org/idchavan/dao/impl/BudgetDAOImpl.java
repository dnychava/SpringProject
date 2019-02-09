package org.idchavan.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.idchavan.dao.AbstractDAO;
import org.idchavan.dao.BudgetDAO;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.BudgetDetailEntityImpl;
import org.idchavan.entity.BudgetEntityImpl;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Dnyaneshwar
 * @since 19-Feb-2018
 */
@Repository("budgetDAO")
public class BudgetDAOImpl extends AbstractDAO implements BudgetDAO {

	public BudgetDAOImpl() {
		System.out.println(" Constructor Called BankDAOImpl");
	}
	
	@Override
	public void saveOrUpdate(BudgetEntityImpl budgetEntity) {
		super.saveOrUpdate(budgetEntity);
	}

	@Override
	public void deleteBudget(BudgetEntityImpl budgetEntity) {
		delete(budgetEntity);
	}
	
	@Override
	public void deleteBudgetDetail(BudgetDetailEntityImpl budgetDetail) {
		delete(budgetDetail);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetEntityImpl> getAllBudgets() {
		Criteria criteria = getSession().createCriteria(BudgetEntityImpl.class);
		criteria.addOrder(Order.desc("createdDate")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public BudgetEntityImpl findByRid(String rid) {
		Criteria criteria = getSession().createCriteria(BudgetEntityImpl.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (BudgetEntityImpl) criteria.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BudgetDetailEntityImpl> getAllBudgetDetails(List<String> rids) {
		Criteria criteria = getSession().createCriteria(BudgetDetailEntityImpl.class);
		criteria.add(Restrictions.in("rid", rids)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	
	@Override
	public DocumentEntity findDocumentByRid(String rid) {
		Criteria criteria = getSession().createCriteria(DocumentEntity.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (DocumentEntity) criteria.uniqueResult();
	}
	
	@Override
	public MasterProgramNameEntityImpl findMstrPrgrmNameByRid(String rid) {
		Criteria criteria = getSession().createCriteria(MasterProgramNameEntityImpl.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (MasterProgramNameEntityImpl) criteria.uniqueResult();
	}
	
	@Override
	public MasterMainGroupEntityImpl findMainGroupByRid(String rid) {
		Criteria criteria = getSession().createCriteria(MasterMainGroupEntityImpl.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (MasterMainGroupEntityImpl) criteria.uniqueResult();
	}
}
