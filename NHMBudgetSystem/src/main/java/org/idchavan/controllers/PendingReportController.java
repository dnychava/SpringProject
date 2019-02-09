package org.idchavan.controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.idchavan.DTO.PendingReportViewDTO;
import org.idchavan.bo.BankBO;
import org.idchavan.bo.GrSanctionOrderBO;
import org.idchavan.bo.SanctionOrderBO;
import org.idchavan.common.MasterData;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.filterDTO.PendingOrderFilterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PendingReportController extends AbstractCommonController {

	@Autowired
	public SanctionOrderBO sanctionOrderBO;

	@Autowired
	public GrSanctionOrderBO grSanctionOrderBO;

	@Autowired
	public BankBO bankBO;

	@Autowired
	public PendingOrderFilterDTO pendingOrdFilter;

	@RequestMapping("/pendingReportFilter")
	public ModelAndView viewSanctionOrderFilter() {
		ModelAndView model = new ModelAndView("pendingReportFilter");
		model.addObject("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addObject("programName", MasterData.getInstance().getProgramList());
		model.addObject("categories", MasterData.getInstance().getCategoryList());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("dispDateFormatter", dispDateFormatter);
		model.addObject("docFormatType", MasterData.getInstance().getDocFormatType());
		model.addObject("fromDate", new Date());
		model.addObject("toDate", new Date());
		

		model.addObject("pendingOrdFilter", pendingOrdFilter);

		return model;
	}

	@RequestMapping("/viewPendingReportFilter")
	public ModelAndView viewPendingSanctionOrderFilter() {
		ModelAndView model = new ModelAndView("viewPendingReportFilter");
		model.addObject("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addObject("programName", MasterData.getInstance().getProgramList());
		model.addObject("categories", MasterData.getInstance().getCategoryList());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("shareType", MasterData.getInstance().getShare());
		model.addObject("dispDateFormatter", dispDateFormatter);

		model.addObject("pendingOrdFilter", pendingOrdFilter);

		return model;
	}
	@RequestMapping(value = "/viewPendingReport", method = RequestMethod.POST)
	public ModelAndView viewSanctionOrderReport(
			@ModelAttribute("pendingOrdFilter") PendingOrderFilterDTO pendingOrdFilter, HttpServletRequest request) {
		System.out.println(" ==Before==== sanOrdFilter[" + pendingOrdFilter + "]");
		List<SanctionOrderEntity> sanOrdList = sanctionOrderBO.getSactionOrdGroubyProgGroupName(pendingOrdFilter);
		// This <code>programGroupHM</code> hold all sanction order depend upon <Code>
		// Program Group Name</code>
		LinkedHashMap<String, LinkedHashMap<String, PendingReportViewDTO>> programGroupHM = new LinkedHashMap<String, LinkedHashMap<String, PendingReportViewDTO>>();

		// Sanction Order <code>for</code>
		for (SanctionOrderEntity sanOrdEntity : sanOrdList) {//

			LinkedHashMap<String, PendingReportViewDTO> allPendingSanOrdHM = programGroupHM
					.get(sanOrdEntity.getProgramGroupName());

			if (allPendingSanOrdHM == null) {
				allPendingSanOrdHM = new LinkedHashMap<String, PendingReportViewDTO>();
				programGroupHM.put(sanOrdEntity.getProgramGroupName(), allPendingSanOrdHM);
			}
			List<SanctionOrderDetailEntity> sanOrdDtlList = sanctionOrderBO
					.getSanOrdDtlOfGroupByProgramName(sanOrdEntity.getRid(), pendingOrdFilter);
			// Sanction Order Detail <code>for</code>
			for (SanctionOrderDetailEntity sanOrdDtlEntity : sanOrdDtlList) {
				PendingReportViewDTO pendingRprtDTO = allPendingSanOrdHM.get(sanOrdDtlEntity.getOrderProgramName());
				if (pendingRprtDTO == null) {

					pendingRprtDTO = new PendingReportViewDTO();
					allPendingSanOrdHM.put(sanOrdDtlEntity.getOrderProgramName(), pendingRprtDTO);
				}
				pendingRprtDTO.setProgramName(sanOrdDtlEntity.getOrderProgramName());
				if (StringUtils.equals(CATEGORY_GEN, sanOrdDtlEntity.getOrderCategory())) {
					pendingRprtDTO.getSanctionOrder().setGeneral(sanOrdDtlEntity.getOrderAmtInLakh());
					pendingRprtDTO.getStateShare().setGeneral(sanOrdDtlEntity.getOrderStateShareAmtInLakh());
				} else if (StringUtils.equals(CATEGORY_SCSP, sanOrdDtlEntity.getOrderCategory())) {
					pendingRprtDTO.getSanctionOrder().setScp(sanOrdDtlEntity.getOrderAmtInLakh());
					pendingRprtDTO.getStateShare().setScp(sanOrdDtlEntity.getOrderStateShareAmtInLakh());
				} else if (StringUtils.equals(CATEGORY_STSP, sanOrdDtlEntity.getOrderCategory())) {
					pendingRprtDTO.getSanctionOrder().setTsp(sanOrdDtlEntity.getOrderAmtInLakh());
					pendingRprtDTO.getStateShare().setTsp(sanOrdDtlEntity.getOrderStateShareAmtInLakh());
				}
				List<GrSanctionOrderDetailEntity> grSanOrdDtlList = grSanctionOrderBO
						.getAllGrSanOrdDtlBySanOrdDtlRid(sanOrdDtlEntity.getRid());
				System.out.println(" sanOrdDtlEntity.getRid() [" + sanOrdDtlEntity.getRid() + "]");
				// GR Sanction Order Detail <code>for</code>
				for (GrSanctionOrderDetailEntity grSanOrdDtlEntity : grSanOrdDtlList) {
					List<BankDetailEntity> bnkDtlEntityList = bankBO
							.getBankDetailsOfGrsanOrdDtlRidUsingGroupBy(grSanOrdDtlEntity.getRid(), pendingOrdFilter);
					for (BankDetailEntity bankDetailEntity : bnkDtlEntityList) {
						if (bankDetailEntity != null) {
							System.out.println("bnkDtlEntityList[" + bnkDtlEntityList.size() + "] band_rid["
									+ bankDetailEntity.getGrSanOrdDtlRid() + "]");
							if (StringUtils.equals(SHARE_STATE, bankDetailEntity.getShare())) {
								if (StringUtils.equals(CATEGORY_GEN, bankDetailEntity.getCategory())) {
									pendingRprtDTO.getStateShareRecevied().setGeneral(bankDetailEntity.getAmtInLakh());
								} else if (StringUtils.equals(CATEGORY_SCSP, bankDetailEntity.getCategory())) {
									pendingRprtDTO.getStateShareRecevied().setScp(bankDetailEntity.getAmtInLakh());
								} else if (StringUtils.equals(CATEGORY_STSP, bankDetailEntity.getCategory())) {
									pendingRprtDTO.getStateShareRecevied().setTsp(bankDetailEntity.getAmtInLakh());
								}
							} else if (StringUtils.equals(SHARE_CENTRAL, bankDetailEntity.getShare())) {
								if (StringUtils.equals(CATEGORY_GEN, bankDetailEntity.getCategory())) {
									pendingRprtDTO.getCentralShareRecevied()
											.setGeneral(bankDetailEntity.getAmtInLakh());
								} else if (StringUtils.equals(CATEGORY_SCSP, bankDetailEntity.getCategory())) {
									pendingRprtDTO.getCentralShareRecevied().setScp(bankDetailEntity.getAmtInLakh());
								} else if (StringUtils.equals(CATEGORY_STSP, bankDetailEntity.getCategory())) {
									pendingRprtDTO.getCentralShareRecevied().setTsp(bankDetailEntity.getAmtInLakh());
								}
							}
						}
					}
				} // End : GR Sanction Order Detail <code>for</code>
			} // End : Sanction Order Detail <code>for</code>
		} // End : Sanction Order <code>for</code>

		// Calculating total and pending amount depend on share type
		calculatePendingAndTotAmt(programGroupHM, pendingOrdFilter);
		System.out.println(" ====== programGroupHM[" + programGroupHM + "] pendingOrdFilter[" + pendingOrdFilter + "]");
		System.out.println("reportResult[" + sanOrdList + "]");
		ModelAndView view = new ModelAndView("viewPendingReport");
		view.addObject("shareType", pendingOrdFilter.getShareType());
		view.addObject("programGroupHM", programGroupHM);
		
		return view;
	}

	/**
	 * Calculating total and pending amount depend on share type
	 * 
	 * @param programGroupHM
	 * @param pendingOrdFilter
	 */
	public void calculatePendingAndTotAmt(
			LinkedHashMap<String, LinkedHashMap<String, PendingReportViewDTO>> programGroupHM,
			PendingOrderFilterDTO pendingOrdFilter) {
		for (Iterator<Entry<String, LinkedHashMap<String, PendingReportViewDTO>>> mainGroupIt = programGroupHM.entrySet()
				.iterator(); mainGroupIt.hasNext();) {// MainGroup Iterator
			Entry<String, LinkedHashMap<String, PendingReportViewDTO>> entry = mainGroupIt.next();
			String key = entry.getKey();
			LinkedHashMap<String, PendingReportViewDTO> value = entry.getValue();
			for (Iterator<Entry<String, PendingReportViewDTO>> promNameIt = entry.getValue().entrySet().iterator(); promNameIt
					.hasNext();) {
				Entry<String, PendingReportViewDTO> programEntry = promNameIt.next();
				PendingReportViewDTO valPendingRprtDTO = programEntry.getValue();
				valPendingRprtDTO.getSanctionOrder().calculateTotal();
				valPendingRprtDTO.getStateShare().calculateTotal();
				valPendingRprtDTO.getStateShareRecevied().calculateTotal();
				if (StringUtils.equals(SHARE_STATE, pendingOrdFilter.getShareType())) {
					BigDecimal general = valPendingRprtDTO.getStateShare().getGeneral()
							.subtract(valPendingRprtDTO.getStateShareRecevied().getGeneral());
					BigDecimal scp = valPendingRprtDTO.getStateShare().getScp()
							.subtract(valPendingRprtDTO.getStateShareRecevied().getScp());
					BigDecimal tsp = valPendingRprtDTO.getStateShare().getTsp()
							.subtract(valPendingRprtDTO.getStateShareRecevied().getTsp());
					
					valPendingRprtDTO.getPending().setGeneral(general);
					valPendingRprtDTO.getPending().setScp(scp);
					valPendingRprtDTO.getPending().setTsp(tsp);
					valPendingRprtDTO.getPending().calculateTotal();
				} else if (StringUtils.equals(SHARE_CENTRAL, pendingOrdFilter.getShareType())) {
					BigDecimal general = valPendingRprtDTO.getSanctionOrder().getGeneral()
							.subtract(valPendingRprtDTO.getCentralShareRecevied().getGeneral());
					BigDecimal scp = valPendingRprtDTO.getSanctionOrder().getScp()
							.subtract(valPendingRprtDTO.getCentralShareRecevied().getScp());
					BigDecimal tsp = valPendingRprtDTO.getSanctionOrder().getTsp()
							.subtract(valPendingRprtDTO.getCentralShareRecevied().getTsp());
					valPendingRprtDTO.getPending().setGeneral(general);
					valPendingRprtDTO.getPending().setScp(scp);
					valPendingRprtDTO.getPending().setTsp(tsp);
					valPendingRprtDTO.getPending().calculateTotal();
				}
			}//end promNameIt
		}//end mainGroupIt
		System.out.println("After calculated the calculated the pending amt mainGroupIt["+programGroupHM+"]");
	}

}
