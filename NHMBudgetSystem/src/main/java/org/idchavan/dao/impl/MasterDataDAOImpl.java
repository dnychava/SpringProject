package org.idchavan.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.idchavan.dao.AbstractDAO;
import org.idchavan.dao.MasterDataDAO;
import org.idchavan.entity.BankEntity;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.idchavan.entity.interfaces.IMasterProgramNameBO;
import org.springframework.stereotype.Repository;

/**
 * This class use for access the master data from database
 * 
 * @author Dnyaneshwar Chavan
 * @since 05-11-2017
 * @version 1.0
 *
 */
@Repository("masterDataDAO")
public class MasterDataDAOImpl extends AbstractDAO implements MasterDataDAO {

	public MasterDataDAOImpl() {
		System.out.println("Constructor Called MasterDataDAOImpl");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MasterSharePercentageEntity> getAllMasterSharePercentages() {
		Criteria criteria = getSession().createCriteria(MasterSharePercentageEntity.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public void saveMasterSharePercentage(MasterSharePercentageEntity percentageEntity) {
		saveOrUpdate(percentageEntity);

	}

	// Master Main Group Start
	@SuppressWarnings("unchecked")
	@Override
	public List<IMasterMainGroupBO> getAllMasterMainGroup() {
		Criteria criteria = getSession().createCriteria(MasterMainGroupEntityImpl.class);
		criteria.addOrder(Order.asc("sequence")).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}

	@Override
	public void saveMasterMainGroup(MasterMainGroupEntityImpl mstrMainGroup) {
		saveOrUpdate(mstrMainGroup);

	}

	@Override
	public MasterMainGroupEntityImpl findMainGroupByRid(String rid) {
		Criteria criteria = getSession().createCriteria(MasterMainGroupEntityImpl.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (MasterMainGroupEntityImpl) criteria.uniqueResult();
	}

	@Override
	public void deleteMstrMainGroup(MasterMainGroupEntityImpl mainGroupEntityImpl) {
		delete(mainGroupEntityImpl);
	}
	// Master Main Group End

	// Master Program Name Start
	@SuppressWarnings("unchecked")
	@Override
	public List<IMasterProgramNameBO> getAllMstrPrgrmNames() {
		Criteria criteria = getSession().createCriteria(MasterProgramNameEntityImpl.class)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return criteria.list();
	}

	@Override
	public void saveMasterProgramName(MasterProgramNameEntityImpl mstrPrgrmNameEntity) {
		saveOrUpdate(mstrPrgrmNameEntity);

	}

	@Override
	public MasterProgramNameEntityImpl findMstrPrgrmNameByRid(String rid) {
		Criteria criteria = getSession().createCriteria(MasterProgramNameEntityImpl.class);
		criteria.add(Restrictions.eq("rid", rid)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (MasterProgramNameEntityImpl) criteria.uniqueResult();
	}

	@Override
	public void deleteMstrPrgrmName(MasterProgramNameEntityImpl mstrPrgrmNameEntity) {
		delete(mstrPrgrmNameEntity);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<IMasterProgramNameBO> getAllMstrPrgrmNamesByGivenParame(String mstrMaingGroupRid, String shareType,
			String category) {
		Criteria criteria = getSession().createCriteria(MasterProgramNameEntityImpl.class);
		criteria.add(Restrictions.eq("mstrMainGroupRid", mstrMaingGroupRid));
		criteria.add(Restrictions.eq("shareType", shareType));
		criteria.add(Restrictions.eq("category", category)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return criteria.list();
	}
	// Master Program Name End
	
	@Override
	public void clear(){
		super.clear();
	}
}
