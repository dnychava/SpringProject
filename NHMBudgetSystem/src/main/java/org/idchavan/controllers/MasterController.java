package org.idchavan.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.idchavan.bo.MasterDataBO;
import org.idchavan.common.FormMode;
import org.idchavan.common.MasterData;
import org.idchavan.common.StartupApplicationListener;
import org.idchavan.entity.MasterMainGroupEntityImpl;
import org.idchavan.entity.MasterProgramNameEntityImpl;
import org.idchavan.validators.MainGroupValidator;
import org.idchavan.validators.MstrPrgrmNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
public class MasterController extends AbstractCommonController {

	@Autowired
	private MasterDataBO masterDataBO;
	
	@Autowired
	private MainGroupValidator mainGroupValidator;
	
	@Autowired
	private MstrPrgrmNameValidator mstrPrgrmNameValidator;
	
	/*@InitBinder()
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		//binder.setValidator(mainGroupValidator);
		binder.addValidators(mainGroupValidator,mstrPrgrmNameValidator);
	}*/
	
	//************Master Main Group********************Start*******************************************/
	
	@RequestMapping("/viewMainGroup")
	public String viewMainGroup(Model model) {
		model.addAttribute("mainGroupkList", masterDataBO.getAllMasterMainGroup());
		return "viewMainGroup";
	}
	
	@RequestMapping(value = "/addMainGroup", method = RequestMethod.GET)
	public String addMainGroupForm(Model model) {

		MasterMainGroupEntityImpl mainGroupBO = new MasterMainGroupEntityImpl(); 
		mainGroupBO.getRid();
		
		model.addAttribute("formMode", FormMode.NEW);
		model.addAttribute("mainGroupBO", mainGroupBO);

		return "addMainGroup";

	}
	
	@RequestMapping(value = { "/saveMainGroup", "/edit/save" }, method = RequestMethod.POST)
	public String saveOrUpdateMainGroup(@ModelAttribute("mainGroupBO") MasterMainGroupEntityImpl mainGroupBO, BindingResult result,
			final RedirectAttributes redirectAttributes, Model model, HttpSession session, HttpServletRequest request)
			throws IOException {
		mainGroupValidator.validate(mainGroupBO, result);
		if( result.hasErrors() ){
			return "addMainGroup";
		}
		// Get the current form mode
		FormMode currentFormMode;
		
		String formMode = request.getParameter("formMode");
		
		if (StringUtils.equals("NEW", formMode)) {
			currentFormMode = FormMode.NEW;
			mainGroupBO.setCreatedDate(new Date());
		} else {
			currentFormMode = FormMode.EDIT;
			mainGroupBO.setModifiedDate(new Date());
		}
		try {
			masterDataBO.saveMasterMainGroup(mainGroupBO);
		} catch (DataIntegrityViolationException e) {
			result.rejectValue("sequence", "mainGroup.sequence.duplicate");
			return "addMainGroup";
		}
		
		redirectAttributes.addAttribute("css", "success");
		if (currentFormMode == FormMode.EDIT) {
			redirectAttributes.addAttribute("msg", "Data Updated Successfully!");
		} else {
			redirectAttributes.addAttribute("msg", "Data Save Successfully!");
		}
		MasterData.getInstance().updateMasterMainGroup( masterDataBO.getAllMasterMainGroup());
		System.out.println("===========Data save==================");

		// will redirect to view sanction order request mapping
		return "redirect:/viewMainGroup";
	}
	
	@RequestMapping({ "/cancelMainGroup", "/edit/cancelMainGroup" })
	public String cancelMainGroup(Model model) {
		return "redirect:/viewMainGroup";
	}
	
	@RequestMapping(value = "/editMainGroup", method = RequestMethod.POST)
	public String editMainGroup(@RequestParam("mainGroupRid") String mainGroupRid, Model model) {
		
		MasterMainGroupEntityImpl mainGroupBO = masterDataBO.findMainGroupByRid(mainGroupRid);
		model.addAttribute("formMode", FormMode.EDIT);
		model.addAttribute("mainGroupBO", mainGroupBO);

		return "addMainGroup";
	}
	
	@RequestMapping(value = "/deleteMainGroup/{mainGroupRid}", method = RequestMethod.GET)
	public String deleteMainGroup(@PathVariable("mainGroupRid") String mainGroupRid, Model model,
			final RedirectAttributes redirectAttributes) {

		MasterMainGroupEntityImpl mainGroupBO = masterDataBO.findMainGroupByRid(mainGroupRid);

		masterDataBO.deleteMstrMainGroup(mainGroupBO);
		
		MasterData.getInstance().updateMasterMainGroup( masterDataBO.getAllMasterMainGroup());
		
		redirectAttributes.addAttribute("css", "danger");
		redirectAttributes.addAttribute("msg", "Data deleted Successfully!");

		// will redirect to view sanction order request mapping
		return "redirect:/viewMainGroup";
	}
	//************Master Main Group********************End*******************************************/
	
	//************Master Program Name********************Start*******************************************/
	@RequestMapping("/viewMstrPrgrmName")
	public String viewMstrPrgmName(Model model) {
		model.addAttribute("mstrPrgrmNameList", masterDataBO.getAllMstrPrgrmNames());
		populateDefaultMasterData(model);
		return "viewMstrPrgrmName";
	}
	
	@RequestMapping(value = "/addMstrPrgrmName", method = RequestMethod.GET)
	public String addMstrPrgrmNameForm(Model model) {

		MasterProgramNameEntityImpl mstrPrgrmNameBO = new MasterProgramNameEntityImpl(); 
		mstrPrgrmNameBO.getRid();
		
		populateDefaultMasterData(model);
		model.addAttribute("formMode", FormMode.NEW);
		model.addAttribute("mstrPrgrmNameBO", mstrPrgrmNameBO);

		return "addMstrPrgrmName";

	}
	
	@RequestMapping(value = { "/saveMstrPrgrmName", "/edit/save" }, method = RequestMethod.POST)
	public String saveOrUpdateMstrPrgrmName(@ModelAttribute("mstrPrgrmNameBO") MasterProgramNameEntityImpl mstrPrgrmNameBO, BindingResult result,
			final RedirectAttributes redirectAttributes, Model model, HttpSession session, HttpServletRequest request)
			throws IOException {
		mstrPrgrmNameValidator.validate(mstrPrgrmNameBO, result);
		if( result.hasErrors() ){
			populateDefaultMasterData(model);
			return "addMstrPrgrmName";
		}
		// Get the current form mode
		FormMode currentFormMode;
		
		String formMode = request.getParameter("formMode");
		
		if (StringUtils.equals("NEW", formMode)) {
			currentFormMode = FormMode.NEW;
			mstrPrgrmNameBO.setCreatedDate(new Date());
		} else {
			currentFormMode = FormMode.EDIT;
			mstrPrgrmNameBO.setModifiedDate(new Date());
		}
		try {
			masterDataBO.saveMasterProgramName(mstrPrgrmNameBO);
		} catch (DataIntegrityViolationException e) {
			result.rejectValue("accountHead", "mstrPrgrmName.accountHeadCategoryShareType.duplicate");
			result.rejectValue("category", "mstrPrgrmName.accountHeadCategoryShareType.duplicate");
			result.rejectValue("shareType", "mstrPrgrmName.accountHeadCategoryShareType.duplicate");
			populateDefaultMasterData(model);
			return "addMstrPrgrmName";
		}
		
		redirectAttributes.addAttribute("css", "success");
		if (currentFormMode == FormMode.EDIT) {
			redirectAttributes.addAttribute("msg", "Data Updated Successfully!");
		} else {
			redirectAttributes.addAttribute("msg", "Data Save Successfully!");
		}
		System.out.println("===========Data save==================");

		// will redirect to view sanction order request mapping
		return "redirect:/viewMstrPrgrmName";
	}
	
	@RequestMapping({ "/cancelMstrPrgrmName", "/edit/cancelMstrPrgrmName" })
	public String cancelMstrPrgrmName(Model model) {
		return "redirect:/viewMstrPrgrmName";
	}
	
	@RequestMapping(value = "/editMstrPrgrmName", method = RequestMethod.POST)
	public String editMstrPrgrmName(@RequestParam("mstrPrgrmNameRid") String mstrPrgrmNameRid, Model model) {
		
		MasterProgramNameEntityImpl mstrPrgrmNameBO = masterDataBO.findMstrPrgrmNameByRid(mstrPrgrmNameRid);
		populateDefaultMasterData(model);
		model.addAttribute("formMode", FormMode.EDIT);
		model.addAttribute("mstrPrgrmNameBO", mstrPrgrmNameBO);

		return "addMstrPrgrmName";
	}
	
	@RequestMapping(value = "/deleteMstrPrgrmName/{mstrPrgrmNameRid}", method = RequestMethod.GET)
	public String deleteMstrPrgrmName(@PathVariable("mstrPrgrmNameRid") String mstrPrgrmNameRid, Model model,
			final RedirectAttributes redirectAttributes) {

		MasterProgramNameEntityImpl mstrPrgrmNameBO = masterDataBO.findMstrPrgrmNameByRid(mstrPrgrmNameRid);

		masterDataBO.deleteMstrPrgrmName(mstrPrgrmNameBO);
		
		redirectAttributes.addAttribute("css", "danger");
		redirectAttributes.addAttribute("msg", "Data deleted Successfully!");

		// will redirect to view sanction order request mapping
		return "redirect:/viewMstrPrgrmName";
	}
	//************Master Program Name********************End*******************************************/
	
	public void populateDefaultMasterData(Model model) {
		model.addAttribute("programGroups", MasterData.getInstance().getProgramGroupsMap());
		model.addAttribute("categories", MasterData.getInstance().getCategoryList());
		model.addAttribute("share", MasterData.getInstance().getShare());
	}
}
