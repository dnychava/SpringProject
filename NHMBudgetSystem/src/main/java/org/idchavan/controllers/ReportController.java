package org.idchavan.controllers;

import java.util.Date;

import org.idchavan.common.MasterData;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportController extends AbstractCommonController {

	@Autowired
	public PendingOrderFilterDTO pendingOrdFilter;
	
	@RequestMapping("/viewDelayDaysReportFilter")
	public ModelAndView viewSanctionOrderFilter() {
		ModelAndView model = new ModelAndView("viewDelayDaysReportFilter");
		//need to be remove the below commented code after the testing done
		/*model.addObject("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addObject("programName", MasterData.getInstance().getProgramList());
		model.addObject("categories", MasterData.getInstance().getCategoryList());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("dispDateFormatter", dispDateFormatter);
		model.addObject("docFormatType", MasterData.getInstance().getDocFormatType());
		model.addObject("fromDate", new Date());
		model.addObject("toDate", new Date());*/
		addMasterFields(model);

		model.addObject("pendingOrdFilter", pendingOrdFilter);

		return model;
	}
	
	@RequestMapping("/pendingSummaryReportFilter")
	public ModelAndView pendingSummaryReportFilter() {
		ModelAndView model = new ModelAndView("pendingSummaryReportFilter");
		//need to be remove the below commented code after the testing done
		/*model.addObject("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addObject("programName", MasterData.getInstance().getProgramList());
		model.addObject("categories", MasterData.getInstance().getCategoryList());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("dispDateFormatter", dispDateFormatter);
		model.addObject("docFormatType", MasterData.getInstance().getDocFormatType());
		model.addObject("fromDate", new Date());
		model.addObject("toDate", new Date());*/
		addMasterFields(model);
		model.addObject("sinalcialYears", MasterData.getInstance().getFinalcialYearList(true));
		model.addObject("pendingOrdFilter", pendingOrdFilter);

		return model;
	}
	
	@RequestMapping("/viewBudgetReportFilter")
	public ModelAndView viewBudgetReportFilter() {
		ModelAndView model = new ModelAndView("viewBudgetReportFilter");
		addMasterFields(model);
		model.addObject("seasionType", MasterData.getInstance().getSeasonTypeList());
		model.addObject("pendingOrdFilter", pendingOrdFilter);
		return model;
	}
	
	private void addMasterFields(ModelAndView model){
		
		model.addObject("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addObject("programName", MasterData.getInstance().getProgramList());
		model.addObject("categories", MasterData.getInstance().getCategoryList());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("dispDateFormatter", dispDateFormatter);
		model.addObject("docFormatType", MasterData.getInstance().getDocFormatType());
		model.addObject("fromDate", new Date());
		model.addObject("toDate", new Date());
		
	}
}
