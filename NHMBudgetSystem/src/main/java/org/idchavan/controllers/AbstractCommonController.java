package org.idchavan.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.idchavan.common.MasterData;
import org.idchavan.entity.DocumentEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

abstract public class AbstractCommonController {
	
	protected final char COMPLETED_YES = 'Y';
	protected final char COMPLETED_NO = 'N';
	protected final String UPLOAD_DIRECTORY = "PDFFiles";
	protected final String CATEGORY_GEN = "GEN";
	protected final String CATEGORY_SCSP = "SCSP";
	protected final String CATEGORY_STSP = "STSP";
	protected final String SHARE_CENTRAL = "Central";
	protected final String SHARE_STATE = "State";
	
	@Value("${db.date.formatter}")
	protected String dbDateFormatter;
	
	@Value("${dis.date.formatter}")
	protected String dispDateFormatter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(dbDateFormatter);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	public void populateDefaultMasterData(Model model) {
		model.addAttribute("categories", MasterData.getInstance().getCategoryList());
		model.addAttribute("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addAttribute("programName", MasterData.getInstance().getProgramList());
		model.addAttribute("programGroups", MasterData.getInstance().getProgramGroupList());
		model.addAttribute("share", MasterData.getInstance().getShare());
		model.addAttribute("seasonTypes", MasterData.getInstance().getSeasonTypeList());
		model.addAttribute("programGroupsMap", MasterData.getInstance().getProgramGroupsMap());
	}
	
	public void prepareDocument(HttpSession session, DocumentEntity documentEntity) throws IOException {

		if (StringUtils.isEmpty(documentEntity.getFileName())) {
			documentEntity.setFileName("EMPTY");
			documentEntity.setDescription("Testing");
			documentEntity.setContent("".getBytes());
			return;
		} else {
			ServletContext context = session.getServletContext();
			StringBuilder path = new StringBuilder(context.getRealPath("") + UPLOAD_DIRECTORY);
			path.append(File.separator).append(documentEntity.getRid()).append("_")
					.append(documentEntity.getFileName());

			File file = new File(path.toString());
			if (file.exists()) {
				documentEntity.setContent(FileUtils.readFileToByteArray(file));
			} else {
				System.out.println("=====FILE NOT EXITS========[" + path + "");
				documentEntity.setFileName("EMPTY.pdf");
				documentEntity.setContent("".getBytes());
			}
		}
	}

}
