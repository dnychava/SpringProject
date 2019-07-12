package org.idchavan.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.interfaces.IMasterMainGroupBO;
import org.idchavan.util.DateUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope(value="singleton")
@Component("masterData")
public class MasterData {

	private String[] categories = { "GEN", "SCSP", "STSP" };

	private String[] programGroups = { "NRHM", "NCD", "NUHM", "OTHER" };

	private String[] programs = { "RCH Flexipool", "Mission  Flexipool", "RI", "PPI", "NIDDCP", "Infra & Maint", "NUHM", "IDSP", "NVBDCP",
			"NVBDCP KIND", "NLEP", "RNTCP", "RNTCP KIND", "NPCB", "NPCDCS", "NTCP", "NPPCD", "NPHCE", "NMHP", "NOHP",
			"NPPCF", "Sickale Cell", "13th Finance Commission (IMR)"};
	
	private String[] share = { "State", "Central"};
	
	private String[] seasonTypes = { "Main", "Mansoon", "Winter", "Rewise Estimate", "Resource Envelope"};
	
	private Map<String,String> docFormatTye = new LinkedHashMap<String, String>();
	
	private Map<String,String> programGroupsMap;
	
	private List<MasterSharePercentageEntity> masterSharePerEntityList;
	
	private static MasterData instance;
	
	private MasterData() {
		
	}
	
	public static MasterData getInstance() {
		if( instance == null ) {
			instance = new MasterData();
		}
		return instance;
	}

	public List<String> getCategoryList() {
		return Arrays.asList(categories);
	}

	public List<String> getFinalcialYearList() {
		return getFinalcialYearList(false);
	}
	
	public List<String> getFinalcialYearList(boolean isAddAll) {
		return new DateUtil().getFinalcialYear(AppConstants.FROM_YEAR, AppConstants.TO_YEAR, isAddAll);
	}

	public List<String> getProgramList() {
		return Arrays.asList(programs);
	}
	
	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(String[] programs) {
		this.programs = programs;
		
	}

	public List<String> getProgramGroupList() {
		return Arrays.asList(programGroups);
	}
	
	/**
	 * @param programGroups the programGroups to set
	 */
	public void setProgramGroups(String[] programGroups) {
		//this.programGroups = programGroups;
	}
	
	/**
	 * @return the programGroupsMap
	 */
	public Map<String, String> getProgramGroupsMap() {
		return programGroupsMap;
	}

	/**
	 * @param programGroupsMap the programGroupsMap to set
	 */
	public void setProgramGroupsMap(Map<String, String> programGroupsMap) {
		this.programGroupsMap = programGroupsMap;
	}

	public List<MasterSharePercentageEntity> getMasterSharePerEntityList() {
		return masterSharePerEntityList;
	}

	public void setMasterSharePerEntityList(List<MasterSharePercentageEntity> masterSharePerEntityList) {
		this.masterSharePerEntityList = masterSharePerEntityList;
	}

	public List<String> getShare() {
		return Arrays.asList(share);
	}
	
	public Map<String, String> getDocFormatType(){
		
		docFormatTye.put("html", "HTML");
		docFormatTye.put("pdf", "PDF");
		docFormatTye.put("xlsx", "Excel");
		docFormatTye.put("docx", "Word");
		docFormatTye.put("pptx", "PowerPoint");
		return docFormatTye;
		//formatTye.put(Arrays.asList( formatTypeKey), Arrays.asList( formatTypeValue) );
	}
	
	public void updateMasterMainGroup(List<IMasterMainGroupBO> mstrMainGroupBOList){
		List<String> programGroups = new ArrayList<String>();
		Map<String,String> programGroupsMap = new LinkedHashMap<String, String>();
		for (IMasterMainGroupBO iMasterMainGroupBO : mstrMainGroupBOList) {
			programGroupsMap.put(iMasterMainGroupBO.getRid(),iMasterMainGroupBO.getInShort());
			programGroups.add(iMasterMainGroupBO.getInShort());
		}
		MasterData.getInstance().setProgramGroupsMap(programGroupsMap);
		MasterData.getInstance().setProgramGroups(programGroups.toArray( new String[programGroups.size()] ));
	}
	
	public List<String> getSeasonTypeList() {
		return Arrays.asList(seasonTypes);
	}
}
