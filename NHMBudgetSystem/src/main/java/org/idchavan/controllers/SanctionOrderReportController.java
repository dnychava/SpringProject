package org.idchavan.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.idchavan.bo.SanctionOrderBO;
import org.idchavan.common.MasterData;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SanctionOrderReportController extends AbstractCommonController {

	@Autowired
	private MasterData masterData;

	@Autowired
	public SanctionOrderBO sanctionOrderBO;

	@Autowired
	public SanctionOrderFilterDTO sanOrdFilter;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
	}

	@RequestMapping("/viewSanctionOrderFilter")
	public ModelAndView viewSanctionOrderFilter() {
		ModelAndView model = new ModelAndView("viewSanctionOrderFilter");
		model.addObject("sinalcialYears", masterData.getFinalcialYearList());
		model.addObject("programName", masterData.getProgramList());
		model.addObject("categories", masterData.getCategoryList());
		model.addObject("docFormatType", masterData.getDocFormatType());
		
		model.addObject("sanOrdFilter", sanOrdFilter);

		return model;
	}

	@RequestMapping(value = "/viewSanctionOrderReport", method = RequestMethod.POST)
	public ModelAndView viewSanctionOrderReport(@ModelAttribute("sanOrdFilter") SanctionOrderFilterDTO sanOrdFilter,
			HttpServletRequest request) {
		System.out.println(" ==Before==== sanOrdFilter[" + sanOrdFilter + "]");
		if (sanOrdFilter.getPrograms() == null) {
			sanOrdFilter.setPrograms("All");
		}
		if (sanOrdFilter.getCategories() == null) {
			sanOrdFilter.setCategories("All");
		}
		List<SanctionOrderEntity> reportResult;
		if ("All".equalsIgnoreCase(sanOrdFilter.getPrograms()) && "All".equalsIgnoreCase(sanOrdFilter.getCategories())
				&& "All".equalsIgnoreCase(sanOrdFilter.getYear())) {
			reportResult = sanctionOrderBO.getAllSanctionOrders();
		} else {
			reportResult = sanctionOrderBO.getSanctionOrdersReport(sanOrdFilter);
		}

		System.out.println(" ==After==== sanOrdFilter[" + sanOrdFilter + "]");
		System.out.println("reportResult[" + reportResult + "]");
		ModelAndView view = new ModelAndView("viewSanctionReport");
		view.addObject("sanctionOrderList", reportResult);
		return view;
	}
}
