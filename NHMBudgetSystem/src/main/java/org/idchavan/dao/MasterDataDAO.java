package org.idchavan.dao;

import java.util.List;

import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.idchavan.entity.interfaces.IMasterProgramNameBO;

public interface MasterDataDAO {

	public List<MasterSharePercentageEntity> getAllMasterSharePercentages();

	public void saveMasterSharePercentage(MasterSharePercentageEntity percentageEntity);

	// Master Main Group Start
	public List<IMasterMainGroupBO> getAllMasterMainGroup();

	public void saveMasterMainGroup(MasterMainGroupEntityImpl mstrMainGroup);

	public MasterMainGroupEntityImpl findMainGroupByRid(String rid);

	public void deleteMstrMainGroup(MasterMainGroupEntityImpl mstrMainGroupEntityImpl);
	// Master Main Group End

	// Master Program Name Start
	public List<IMasterProgramNameBO> getAllMstrPrgrmNames();

	public void saveMasterProgramName(MasterProgramNameEntityImpl mstrPrgrmNameEntity);

	public MasterProgramNameEntityImpl findMstrPrgrmNameByRid(String rid);

	public void deleteMstrPrgrmName(MasterProgramNameEntityImpl mstrPrgrmNameEntity);

	public List<IMasterProgramNameBO> getAllMstrPrgrmNamesByGivenParame(String mstrMaingGroupRid, String shareType,
			String category);
	// Master Program Name End
	
	public void clear();

}
