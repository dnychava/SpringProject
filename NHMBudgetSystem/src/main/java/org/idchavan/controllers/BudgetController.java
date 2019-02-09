package org.idchavan.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.idchavan.bo.BudgetBO;
import org.idchavan.bo.MasterDataBO;
import org.idchavan.common.FormMode;
import org.idchavan.entity.BudgetDetailEntityImpl;
import org.idchavan.entity.BudgetEntityImpl;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.idchavan.validators.BankValidator;
import org.idchavan.validators.BudgetValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BudgetController extends AbstractCommonController {

	@Autowired
	private BudgetBO budgetBO;

	@Autowired
	private MasterDataBO mstrDataBO;

	@Autowired
	public BudgetValidator budgetValidator;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.setValidator(budgetValidator);
	}
	
	@RequestMapping("/viewBudget")
	public String viewBudget(Model model) {
		model.addAttribute("budgetList", budgetBO.getAllBudgets());
		return "viewBudget";
	}

	@RequestMapping(value = "/addBudget", method = RequestMethod.GET)
	public String showAddBudgetForm(Model model) {

		BudgetEntityImpl budget = new BudgetEntityImpl();
		budget.getRid();

		MasterProgramNameEntityImpl mstPrgrmNameEntity = new MasterProgramNameEntityImpl();

		BudgetDetailEntityImpl budgetDetail = new BudgetDetailEntityImpl();
		budgetDetail.setAmtInLakh(BigDecimal.ZERO);
		budgetDetail.setBudgetEntityImpl(budget);
		budgetDetail.setMstrPrgrNameEnitityImpl(mstPrgrmNameEntity);

		List<BudgetDetailEntityImpl> budgetDetailList = new ArrayList<BudgetDetailEntityImpl>();
		budgetDetailList.add(budgetDetail);

		budget.setBudgetDetails(budgetDetailList);

		// Populate Default MasterData
		populateDefaultMasterData(model);

		model.addAttribute("formMode", FormMode.NEW);
		model.addAttribute("budget", budget);

		return "addBudget";
	}

	@RequestMapping(value = "/editBudget", method = RequestMethod.POST)
	public String showEditBudgetForm(@RequestParam("budgetRid") String budgetRid, Model model) {

		BudgetEntityImpl budgetEntity = budgetBO.findByRid(budgetRid);
		// Populate Default MasterData
		populateDefaultMasterData(model);
		model.addAttribute("formMode", FormMode.EDIT);
		model.addAttribute("budget", budgetEntity);

		return "addBudget";
	}

	@RequestMapping({ "/cancelBudget", "/edit/cancelBudget" })
	public String cancelAddBudgetForm(Model model) {
		return "redirect:/viewBudget";
	}

	@RequestMapping(value = { "/getMstrBudgetData", "/edit/getMstrBudgetData" }, method = RequestMethod.POST)
	public String getBudgetMasterData(HttpServletRequest request, Model model) {
		String category = request.getParameter("category");
		String shareType = request.getParameter("shareType");
		String mstrMaingGroupRid = request.getParameter("mainGroupRid");

		model.addAttribute("msterBudgetViewList",
				mstrDataBO.getAllMstrPrgrmNamesByGivenParame(mstrMaingGroupRid, shareType, category));
		return "popupMasterBudget";
	}

	@RequestMapping(value = { "/saveBudget", "/edit/save" }, method = RequestMethod.POST)
	public String saveOrUpdateBudgetForm(@ModelAttribute("budget") @Validated BudgetEntityImpl budgetEntity, BindingResult result,
			final RedirectAttributes redirectAttributes, Model model, HttpSession session, HttpServletRequest request)
			throws IOException {
		FormMode currentFormMode;

		// Get the current form mode
		String formMode = request.getParameter("formMode");
		System.out.println("======formMode===[" + formMode + "]");

		/*
		 * Get the all deleted rids of sanction order detail in form edit mode.
		 * which user was deleted the rows When click on '-' button;
		 */
		String budgetDtlRidsDeleted = request.getParameter("budgetDtlRidsDeleted");
		// System.out.println("======sanOrdDtlRidsDeleted===[" +
		// sanOrdDtlRidsDeleted +
		// "]");

		if (StringUtils.equals("NEW", formMode)) {
			currentFormMode = FormMode.NEW;
			budgetEntity.setCreatedDate(new Date());
		} else {
			currentFormMode = FormMode.EDIT;
			budgetEntity.setModifiedDate(new Date());
		}

		if (result.hasErrors()) {
			populateDefaultMasterData(model);
			return "addBudget";
		} else {
			for (BudgetDetailEntityImpl budgetDetail : budgetEntity.getBudgetDetails()) {

				switch (currentFormMode) {
				case NEW:
					budgetDetail.setCreatedDate(new Date());
					break;
				case EDIT:
					budgetDetail.setModifiedDate(new Date());
					break;
				default:
					break;
				}
				if (budgetDetail.getBudgetEntityImpl() == null) {
					budgetDetail.setBudgetEntityImpl(budgetEntity);

					// Don't comment the below line because if rid null then
					// create new rid.
					budgetDetail.getRid();
				}
			}

			prepareDocument(session, budgetEntity.getDocumentEntity());

			MasterMainGroupEntityImpl findMstrMainGroupEntity = budgetBO
					.findMainGroupByRid(budgetEntity.getMstrMainGroupEnity().getRid());
			if (findMstrMainGroupEntity != null) {
				budgetEntity.setMstrMainGroupEnity(findMstrMainGroupEntity);
			} else {
				throw new RuntimeException(
						"Selected Main Group does not exist in Master Main group. Please co-ordinate with Admin.");
			}
			budgetBO.saveOrUpdate(budgetEntity);
			System.out.println("IN SAVE sanctionOrder[" + budgetEntity + "]");
			redirectAttributes.addAttribute("css", "success");
			if (currentFormMode == FormMode.EDIT) {
				redirectAttributes.addAttribute("msg", "Data Updated Successfully!");
				if (StringUtils.isNotEmpty(budgetDtlRidsDeleted)) {
					List<BudgetDetailEntityImpl> budgetDtdList = budgetBO
							.getAllBudgetDetails(Arrays.asList(budgetDtlRidsDeleted.split("~~")));
					for (BudgetDetailEntityImpl budgetDtEntity : budgetDtdList) {
						budgetBO.deleteBudgetDetail(budgetDtEntity);
					}
				}
			} else {
				redirectAttributes.addAttribute("msg", "Data Save Successfully!");
			}
			System.out.println("===========Data save==================");

			// will redirect to view budget request mapping
			return "redirect:/viewBudget";
		}
	}

	@RequestMapping(value = "/deleteBudget/{budgetRid}", method = RequestMethod.GET)
	public String deleteBudgetRecord(@PathVariable("budgetRid") String budgetRid, Model model,
			final RedirectAttributes redirectAttributes) {

		BudgetEntityImpl budgetEntity = budgetBO.findByRid(budgetRid);
		budgetBO.deleteBudget(budgetEntity);
		System.out.println("IN DELETED BUDGET DATA[" + budgetEntity + "]");
		redirectAttributes.addAttribute("css", "danger");
		redirectAttributes.addAttribute("msg", "Data deleted Successfully!");

		// will redirect to view budget request mapping
		return "redirect:/viewBudget";
	}

	@RequestMapping(value = { "editBudget/viewPDFFile/{fileName:.+}",
			"/viewBudgetPDFFile/{fileName:.+}" }, method = RequestMethod.GET)
	public void viewPDFFile(@PathVariable("fileName") String fileName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String fullFilePath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;

		// The below line for download the pdf file
		// response.addHeader("Content-Disposition",
		// "attachment;filename=report.pdf");
		response.setContentType("application/pdf");
		response.setDateHeader("Expires", -1);

		File downloadFile = new File(fullFilePath);
		if (downloadFile.exists()) {
			FileInputStream inputStream = new FileInputStream(downloadFile);
			response.setContentLengthLong(downloadFile.length());
			// get output stream of the response
			IOUtils.copy(inputStream, response.getOutputStream());
			inputStream.close();
		} else {
			String docRid = fileName.split("_")[0];
			DocumentEntity docEntity = budgetBO.findDocumentByRid(docRid);
			if (docEntity != null) {
				response.setContentLengthLong(docEntity.getContent().length);
				response.getOutputStream().write(docEntity.getContent());
			} else {
				response.setContentType("plain/text");
				response.getOutputStream().write("<h1>FILE NOT FOUND!</h1>".getBytes());
			}
		}

	}

}
