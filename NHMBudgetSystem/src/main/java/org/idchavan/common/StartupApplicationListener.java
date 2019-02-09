package org.idchavan.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.idchavan.bo.MasterDataBO;
import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/***
 * This class will be called automatic after all beans initialize and server
 * started.
 * 
 * @author Dnyaneshwar Chavan
 * @since 05-11-2017
 * @version 1.0
 *
 */
@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private MasterDataBO masterDataBO;


	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {

		if (MasterData.getInstance().getMasterSharePerEntityList() == null) {
			MasterData.getInstance().setMasterSharePerEntityList(masterDataBO.getAllMasterSharePercentages());
			if (MasterData.getInstance().getMasterSharePerEntityList().size() == 0) {
				try {
					priperSharePeercentageData(MasterData.getInstance());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		priperMstrMainGroup(); 
		
	}

	
	private void priperMstrMainGroup() {
		List<IMasterMainGroupBO> mstrMainGroupBOList = masterDataBO.getAllMasterMainGroup();
		MasterData.getInstance().updateMasterMainGroup(mstrMainGroupBOList);
	}

	/**
	 * This method only call when the 'MSTR_SHARE_PER' table is empty.
	 * 
	 * @throws ParseException
	 */
	private void priperSharePeercentageData( MasterData masterData) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fromDateInStr = "01/04/2004";
		String toDateInStr = "31/03/2008";
		int percentage = 100;
		Date fromDate = sdf.parse(fromDateInStr);
		Date toDate = sdf.parse(toDateInStr);

		MasterSharePercentageEntity entity = new MasterSharePercentageEntity();
		entity.getRid();
		entity.setFromDate(fromDate);
		entity.setToDate(toDate);
		entity.setPercentage(percentage);
		entity.setShareType(ShareTypeEnum.STATE.toString());
		masterDataBO.saveMasterSharePercentage(entity);

		fromDateInStr = "01/04/2008";
		toDateInStr = "31/03/2012";
		percentage = 20;
		fromDate = sdf.parse(fromDateInStr);
		toDate = sdf.parse(toDateInStr);

		MasterSharePercentageEntity entity1 = new MasterSharePercentageEntity();
		entity1.getRid();
		entity1.setFromDate(fromDate);
		entity1.setToDate(toDate);
		entity1.setPercentage(percentage);
		entity1.setShareType(ShareTypeEnum.STATE.toString());
		masterDataBO.saveMasterSharePercentage(entity1);

		fromDateInStr = "01/04/2012";
		toDateInStr = "31/03/2015";
		percentage = 25;
		fromDate = sdf.parse(fromDateInStr);
		toDate = sdf.parse(toDateInStr);

		MasterSharePercentageEntity entity2 = new MasterSharePercentageEntity();
		entity2.getRid();
		entity2.setFromDate(fromDate);
		entity2.setToDate(toDate);
		entity2.setPercentage(percentage);
		entity2.setShareType(ShareTypeEnum.STATE.toString());
		masterDataBO.saveMasterSharePercentage(entity2);

		fromDateInStr = "01/10/2015";
		toDateInStr = sdf.format( new Date() );
		percentage = 40;
		fromDate = sdf.parse(fromDateInStr);
		toDate = sdf.parse(toDateInStr);

		MasterSharePercentageEntity entity3 = new MasterSharePercentageEntity();
		entity3.getRid();
		entity3.setFromDate(fromDate);
		entity3.setToDate(toDate);
		entity3.setPercentage(percentage);
		entity3.setShareType(ShareTypeEnum.STATE.toString());
		masterDataBO.saveMasterSharePercentage(entity3);

		List<MasterSharePercentageEntity> masterSharePerEntityList = new ArrayList<MasterSharePercentageEntity>();
		masterSharePerEntityList.add(entity);
		masterSharePerEntityList.add(entity1);
		masterSharePerEntityList.add(entity2);
		masterSharePerEntityList.add(entity3);

		masterData.setMasterSharePerEntityList(masterSharePerEntityList);

	}
}
