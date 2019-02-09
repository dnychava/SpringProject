package org.idchavan.bo.impl;

import java.util.List;

import org.idchavan.bo.MasterDataBO;
import org.idchavan.dao.MasterDataDAO;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.idchavan.entity.interfaces.IMasterProgramNameBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("masterDataBO")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MasterDataBOImpl implements MasterDataBO {
	
	@Autowired
	MasterDataDAO masterDataDAO;
	
	public MasterDataBOImpl() {
		System.out.println("Constructor Called SanctionOrderBOImpl");
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<MasterSharePercentageEntity> getAllMasterSharePercentages() {
		return masterDataDAO.getAllMasterSharePercentages();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveMasterSharePercentage(MasterSharePercentageEntity percentageEntity) {
		masterDataDAO.saveMasterSharePercentage(percentageEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<IMasterMainGroupBO> getAllMasterMainGroup() {
		return masterDataDAO.getAllMasterMainGroup();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveMasterMainGroup(MasterMainGroupEntityImpl mstrMainGroup) {
		masterDataDAO.saveMasterMainGroup(mstrMainGroup);
		
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public MasterMainGroupEntityImpl findMainGroupByRid(String rid) {
		return masterDataDAO.findMainGroupByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteMstrMainGroup(MasterMainGroupEntityImpl mstrMainGroupEntityImpl) {
		masterDataDAO.deleteMstrMainGroup(mstrMainGroupEntityImpl);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<IMasterProgramNameBO> getAllMstrPrgrmNames() {
		return masterDataDAO.getAllMstrPrgrmNames();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void saveMasterProgramName(MasterProgramNameEntityImpl mstrPrgrmNameEntity) {
		masterDataDAO.saveMasterProgramName(mstrPrgrmNameEntity);
		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public MasterProgramNameEntityImpl findMstrPrgrmNameByRid(String rid) {
		return masterDataDAO.findMstrPrgrmNameByRid(rid);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	@Override
	public void deleteMstrPrgrmName(MasterProgramNameEntityImpl mstrPrgrmNameEntity) {
		masterDataDAO.deleteMstrPrgrmName(mstrPrgrmNameEntity);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public List<IMasterProgramNameBO> getAllMstrPrgrmNamesByGivenParame(String mstrMaingGroupRid, String shareType,
			String category) {
		return masterDataDAO.getAllMstrPrgrmNamesByGivenParame(mstrMaingGroupRid, shareType, category);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
	public void clear() {
		masterDataDAO.clear();
	}
}
