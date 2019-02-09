package org.idchavan.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.idchavan.DTO.SanctionOrderDetailViewDTO;
import org.idchavan.bo.BankBO;
import org.idchavan.bo.GrSanctionOrderBO;
import org.idchavan.bo.SanctionOrderBO;
import org.idchavan.common.FileInfo;
import org.idchavan.common.FormMode;
import org.idchavan.common.MasterData;
import org.idchavan.common.ShareTypeEnum;
import org.idchavan.entity.BankDetailEntity;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.GrSanctionOrderEntity;
import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.filterDTO.SanctionOrderFilterDTO;
import org.idchavan.validators.GrSanctionOrderValidator;
import org.idchavan.validators.SanctionOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class GRSanctionOrderController extends AbstractCommonController {

	@Autowired
	public GrSanctionOrderBO grSanctionOrderBO;

	@Autowired
	private SanctionOrderBO sanOrdBO;

	@Autowired
	public BankBO bankBO;

	@Autowired
	public GrSanctionOrderValidator grSanOrdValidator;

	@Autowired
	private OrderDetailFilterDTO sanOrdDtlFilter;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		/*
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		 * dateFormat.setLenient(false); binder.registerCustomEditor(Date.class,
		 * new CustomDateEditor(dateFormat, true));
		 */
		super.initBinder(binder);
		binder.setValidator(grSanOrdValidator);
	}

	@RequestMapping("/viewGrSanctionOrder")
	public String viewSanctionOrder(Model model) {
		model.addAttribute("sanctionOrderList", grSanctionOrderBO.getAllGrSanctionOrders());
		return "viewGrSanctionOrder";
	}

	@RequestMapping(value = { "/getSanctionOrderDetails",
			"/edit/getSanctionOrderDetails" }, method = RequestMethod.POST)
	public String getSanctionOrdView(HttpServletRequest request, Model model) {
		String rid = request.getParameter("rid");
		String programName = request.getParameter("programName");
		String category = request.getParameter("category");
		String shareType = request.getParameter("shareType");
		String year = request.getParameter("year");

		sanOrdDtlFilter.setRid(rid);
		sanOrdDtlFilter.setProgramName(programName);
		sanOrdDtlFilter.setCategory(category);
		sanOrdDtlFilter.setShareType(shareType);
		sanOrdDtlFilter.setCompleted(COMPLETED_NO);

		ShareTypeEnum shareTypeEnum = null;

		if (StringUtils.equalsIgnoreCase(ShareTypeEnum.STATE.getName(), shareType)) {
			shareTypeEnum = ShareTypeEnum.STATE;
		} else if (StringUtils.equalsIgnoreCase(ShareTypeEnum.CENTRAL.getName(), shareType)) {
			shareTypeEnum = ShareTypeEnum.CENTRAL;
		}
		System.out.println("=========testingForSanctionView====shareTypeEnum[" + shareTypeEnum.getName() + "]");
		ArrayList<String> sanOrdRids = new ArrayList<String>();
		List<SanctionOrderEntity> sanOrdList = sanOrdBO.findSanOrdByYear(year);
		for (SanctionOrderEntity sanOrdEntity : sanOrdList) {
			sanOrdRids.add(sanOrdEntity.getRid());
		}
		if (sanOrdRids.isEmpty()) {
			sanOrdRids.add("");
		}
		sanOrdDtlFilter.setRidListForDTLTbl(sanOrdRids);
		// System.out.println("========[" +
		// sanOrdBO.getAllSanctionOrderDetails(sanOrdDtlFilter) + "]");
		List<SanctionOrderDetailEntity> sanOrdDtlList = sanOrdBO.getAllSanctionOrderDetails(sanOrdDtlFilter);
		List<SanctionOrderDetailViewDTO> sanOrdDtlViewList = new ArrayList<SanctionOrderDetailViewDTO>();
		for (SanctionOrderDetailEntity sanOrdDtl : sanOrdDtlList) {

			GrSanctionOrderDetailEntity grSanOrdDtl = grSanctionOrderBO.getGrDetailsUsingGroupBy(sanOrdDtl.getRid(),
					shareTypeEnum.getName());
			String grSanOrdDtlRid = grSanOrdDtl == null ? "" : grSanOrdDtl.getRid();
			BankDetailEntity bankDtlEntity = bankBO.getBankDetailsUsingGroupByOfGrsanOrdDtlRid(grSanOrdDtlRid,
					shareTypeEnum.getName());

			SanctionOrderDetailViewDTO sanOrdDtlViewDTO = new SanctionOrderDetailViewDTO();
			sanOrdDtlViewDTO.setRid(sanOrdDtl.getRid());
			sanOrdDtlViewDTO.setOrderProgramName(sanOrdDtl.getOrderProgramName());
			sanOrdDtlViewDTO.setOrderDate(sanOrdDtl.getOrderDate());
			sanOrdDtlViewDTO.setOrderNumber(sanOrdDtl.getOrderNumber());
			sanOrdDtlViewDTO.setOrderCategory(sanOrdDtl.getOrderCategory());
			sanOrdDtlViewDTO.setSharType(shareTypeEnum.getName());
			compareSanOrdDtlToGrSanOrdDtl(sanOrdDtl, grSanOrdDtl, bankDtlEntity, sanOrdDtlViewDTO, shareTypeEnum);
			sanOrdDtlViewList.add(sanOrdDtlViewDTO);
		}
		model.addAttribute("sanOrdDtlViewList", sanOrdDtlViewList);
		return "popupSanctionOrderDetails";
	}

	public void compareSanOrdDtlToGrSanOrdDtl(SanctionOrderDetailEntity sanOrdDtl,
			GrSanctionOrderDetailEntity grSanOrdDtl, BankDetailEntity bankDtlEntity,
			SanctionOrderDetailViewDTO sanOrdDtlViewDTO, ShareTypeEnum shareTypeEnum) {
		switch (shareTypeEnum) {
		case STATE:
			if (bankDtlEntity != null && sanOrdDtl.getOrderStateShareAmt().compareTo(bankDtlEntity.getAmt()) == 1) {
				sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderStateShareAmt().subtract(bankDtlEntity.getAmt()));
				sanOrdDtlViewDTO.setOrderAmtInLakh(
						sanOrdDtl.getOrderStateShareAmtInLakh().subtract(bankDtlEntity.getAmtInLakh()));

			} else if (grSanOrdDtl != null) {
				if (sanOrdDtl.getOrderStateShareAmt().compareTo(grSanOrdDtl.getOrderAmt()) == 0) {
					sanOrdDtlViewDTO.setRadioBtn(false);
					sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderStateShareAmt());
					sanOrdDtlViewDTO.setOrderAmtInLakh(sanOrdDtl.getOrderStateShareAmtInLakh());
				} else if (sanOrdDtl.getOrderStateShareAmt().compareTo(grSanOrdDtl.getOrderAmt()) == 1) {
					sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderStateShareAmt().subtract(grSanOrdDtl.getOrderAmt()));
					sanOrdDtlViewDTO.setOrderAmtInLakh(
							sanOrdDtl.getOrderStateShareAmtInLakh().subtract(grSanOrdDtl.getOrderAmtInLakh()));
				}
			} else {
				sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderStateShareAmt());
				sanOrdDtlViewDTO.setOrderAmtInLakh(sanOrdDtl.getOrderStateShareAmtInLakh());
			}
			break;
		case CENTRAL:
			if (bankDtlEntity != null && sanOrdDtl.getOrderAmt().compareTo(bankDtlEntity.getAmt()) == 1 ){
				sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderAmt().subtract(bankDtlEntity.getAmt()));
				sanOrdDtlViewDTO
						.setOrderAmtInLakh(sanOrdDtl.getOrderAmtInLakh().subtract(bankDtlEntity.getAmtInLakh()));
			}else if (grSanOrdDtl != null) {
				if (sanOrdDtl.getOrderAmt().compareTo(grSanOrdDtl.getOrderAmt()) == 0) {
					sanOrdDtlViewDTO.setRadioBtn(false);
					sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderAmt());
					sanOrdDtlViewDTO.setOrderAmtInLakh(sanOrdDtl.getOrderAmtInLakh());
				} else if (sanOrdDtl.getOrderAmt().compareTo(grSanOrdDtl.getOrderAmt()) == 1) {
					sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderAmt().subtract(grSanOrdDtl.getOrderAmt()));
					sanOrdDtlViewDTO
							.setOrderAmtInLakh(sanOrdDtl.getOrderAmtInLakh().subtract(grSanOrdDtl.getOrderAmtInLakh()));
				}
			} else {
				sanOrdDtlViewDTO.setOrderAmt(sanOrdDtl.getOrderAmt());
				sanOrdDtlViewDTO.setOrderAmtInLakh(sanOrdDtl.getOrderAmtInLakh());
			}
			break;
		default:
			System.out.println("=======NO SHARE TYPE FOUND==========");
			break;
		}
	}

	@RequestMapping(value = "/addGrSanctionOrder", method = RequestMethod.GET)
	public String showform(Model model) {

		GrSanctionOrderEntity sanctionOrder = new GrSanctionOrderEntity();
		sanctionOrder.getRid();

		sanctionOrder.setCreatedDate(new Date());

		GrSanctionOrderDetailEntity sanctionOrderDetail = new GrSanctionOrderDetailEntity();
		sanctionOrderDetail.setOrderAmtInLakh(BigDecimal.ZERO);
		sanctionOrderDetail.setGrSanctionOrder(sanctionOrder);

		List<GrSanctionOrderDetailEntity> listOrdDetails = new ArrayList<GrSanctionOrderDetailEntity>();
		listOrdDetails.add(sanctionOrderDetail);

		sanctionOrder.setGrSanctionOrderDetails(listOrdDetails);

		// Populate Default MasterData
		populateDefaultMasterData(model);

		model.addAttribute("formMode", FormMode.NEW);
		model.addAttribute("grSanctionOrder", sanctionOrder);

		return "addGrSanctionOrder";

	}

	@RequestMapping(value = { "/saveGrSanctionOrder", "/edit1/save1" }, method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("grSanctionOrder") @Validated GrSanctionOrderEntity sanctionOrder,
			BindingResult result, final RedirectAttributes redirectAttributes, Model model, HttpSession session,
			HttpServletRequest request) throws IOException {
		FormMode currentFormMode;

		// Get the current form mode
		String formMode = request.getParameter("formMode");
		System.out.println("======formMode===[" + formMode + "]");

		/*
		 * Get the all deleted rids of sanction order detail in form edit mode.
		 * which user was deleted the rows When click on '-' button;
		 */
		String sanOrdDtlRidsDeleted = request.getParameter("sanOrdDtlRidsDeleted");
		// System.out.println("======sanOrdDtlRidsDeleted===[" +
		// sanOrdDtlRidsDeleted +
		// "]");

		if (StringUtils.equals("NEW", formMode)) {
			currentFormMode = FormMode.NEW;
			sanctionOrder.setCreatedDate(new Date());
		} else {
			currentFormMode = FormMode.EDIT;
			sanctionOrder.setModifiedDate(new Date());
		}

		if (result.hasErrors()) {
			populateDefaultMasterData(model);
			return "addGrSanctionOrder";
		} else {
			for (GrSanctionOrderDetailEntity sanOrderDetails : sanctionOrder.getGrSanctionOrderDetails()) {

				switch (currentFormMode) {
				case NEW:
					sanOrderDetails.setCreatedDate(new Date());
					break;
				case EDIT:
					sanOrderDetails.setModifiedDate(new Date());
					break;
				default:
					break;
				}
				// calculating state share Amt And Lakh
				// calculateStateShareAmtAndInLakh(sanOrderDetails);

				if (sanOrderDetails.getGrSanctionOrder() == null) {
					sanOrderDetails.setGrSanctionOrder(sanctionOrder);

					// Don't comment the below line because if rid null then
					// create new rid.
					sanOrderDetails.getRid();
				}
			}

			prepareDocument(session, sanctionOrder.getDocumentEntity());

			grSanctionOrderBO.saveOrUpdate(sanctionOrder);
			System.out.println("IN SAVE sanctionOrder[" + sanctionOrder + "]");
			redirectAttributes.addAttribute("css", "success");
			if (currentFormMode == FormMode.EDIT) {
				redirectAttributes.addAttribute("msg", "Data Updated Successfully!");
				if (StringUtils.isNotEmpty(sanOrdDtlRidsDeleted)) {
					List<GrSanctionOrderDetailEntity> sanOrdDtdList = grSanctionOrderBO
							.getAllGrSanctionOrderDetails(Arrays.asList(sanOrdDtlRidsDeleted.split("~~")));
					for (GrSanctionOrderDetailEntity sanOrdDtsEntity : sanOrdDtdList) {
						grSanctionOrderBO.deleteGrSanctionOrderDetails(sanOrdDtsEntity);
					}
				}
			} else {
				redirectAttributes.addAttribute("msg", "Data Save Successfully!");
			}
			System.out.println("===========Data save==================");

			// will redirect to view sanction order request mapping
			return "redirect:/viewGrSanctionOrder";
		}

	}

	@RequestMapping(value = "/editGrSanctionOrder", method = RequestMethod.POST)
	public String edit(@RequestParam("grSanOrdRid") String grGanOrdRid, Model model) {

		GrSanctionOrderEntity sanctionOrder = grSanctionOrderBO.findByRid(grGanOrdRid);
		// Populate Default MasterData
		populateDefaultMasterData(model);
		model.addAttribute("formMode", FormMode.EDIT);
		model.addAttribute("grSanctionOrder", sanctionOrder);

		return "addGrSanctionOrder";
	}

	@RequestMapping(value = "/deleteGrSanctionOrder/{sanOrdRid}", method = RequestMethod.GET)
	public String delete(@PathVariable("sanOrdRid") String sanOrdRid, Model model,
			final RedirectAttributes redirectAttributes) {

		GrSanctionOrderEntity grSanOrdEntity = grSanctionOrderBO.findByRid(sanOrdRid);

		grSanctionOrderBO.deleteGrSanctionOrder(grSanOrdEntity);
		deletedBankDetails(grSanOrdEntity);
		System.out.println("IN SAVE sanctionOrder[" + grSanOrdEntity + "]");
		redirectAttributes.addAttribute("css", "danger");
		redirectAttributes.addAttribute("msg", "Data deleted Successfully!");

		// will redirect to view sanction order request mapping
		return "redirect:/viewGrSanctionOrder";
	}

	/**
	 * This method deleted the of <code>BankDetails<code> with given value.
	 * 
	 * @param sanctionOrder
	 */
	private void deletedBankDetails(GrSanctionOrderEntity sanctionOrder) {
		if (sanctionOrder != null) {
			List<GrSanctionOrderDetailEntity> grSanOrdDtlList = sanctionOrder.getGrSanctionOrderDetails();
			for (GrSanctionOrderDetailEntity grSanOrdDtlEntity : grSanOrdDtlList) {
				BankDetailEntity bankDtlEntity = bankBO.findByBankDtlRid(grSanOrdDtlEntity.getRid());
				if (bankDtlEntity != null) {
					bankBO.deleteBank(bankDtlEntity.getBank());
				}
				updateSanOrderDtl(grSanOrdDtlEntity);
			}
		}

	}

	/**
	 * This method updating the <code>COMPLETED </code> <br>
	 * column with <code>N</code> value of <code>SAN_DTL</code> table.<br>
	 * If current column value 'Y' otherwise nothing.
	 * 
	 * @param grSanOrdDtlEntity
	 *            of current update.
	 */
	private void updateSanOrderDtl(GrSanctionOrderDetailEntity grSanOrdDtlEntity) {
		SanctionOrderDetailEntity sanOrdDtlEntity = sanOrdBO
				.findSanctionOrderDetailByRid(grSanOrdDtlEntity.getSanOrdDtlRid());
		if (sanOrdDtlEntity != null) {
			if (StringUtils.equals(Character.toString(COMPLETED_YES),
					Character.toString(sanOrdDtlEntity.getCompleted()))) {
				sanOrdDtlEntity.setCompleted(COMPLETED_NO);
				sanOrdBO.saveOrUpdate(sanOrdDtlEntity.getSanctionOrder());
			}
		}
	}

	@RequestMapping({ "/cancelGrSanctionOrder", "/edit/cancelGrSanctionOrder" })
	public String cancelSanctionOrder(Model model) {
		return "redirect:/viewGrSanctionOrder";
	}

	@RequestMapping("/homePageFromGrSanctionOrder")
	public String homePageFromSanctionOrder(Model model) {
		// This should be redirect at home page(index.jsp)
		return "redirect:/index.jsp";
	}

	@RequestMapping(value = { "editGrSanctionOrder/viewPDFFile/{fileName:.+}",
			"/viewGrSanctionOrderPDFFile/{fileName:.+}" }, method = RequestMethod.GET)
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
			DocumentEntity docEntity = grSanctionOrderBO.findDocumentByRid(docRid);
			if (docEntity != null) {
				response.setContentLengthLong(docEntity.getContent().length);
				response.getOutputStream().write(docEntity.getContent());
			} else {
				response.setContentType("plain/text");
				response.getOutputStream().write("<h1>FILE NOT FOUND!</h1>".getBytes());
			}
		}

	}

	@RequestMapping(value = { "savefileGrSanctionOrder",
			"edit/savefileGrSanctionOrder" }, method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<FileInfo> saveimage(@RequestParam CommonsMultipartFile file,
			@RequestParam("documentRid") String documentRid, HttpSession session, HttpServletResponse response)
			throws Exception {

		String fileUploadMsg = "";
		FileInfo fileInfo = new FileInfo();
		try {
			ServletContext context = session.getServletContext();
			String path = context.getRealPath("") + UPLOAD_DIRECTORY;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = documentRid + "_" + file.getOriginalFilename();

			System.out.println(path + " " + documentRid);

			byte[] bytes = file.getBytes();

			File storeFilePath = new File(path + File.separator + fileName);

			fileInfo.setOrignalFileName(file.getOriginalFilename());
			fileInfo.setRenameFileName(fileName);
			fileInfo.setFilePath(storeFilePath.getAbsolutePath());

			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storeFilePath));
			stream.write(bytes);
			stream.flush();
			stream.close();
			fileUploadMsg = "File uploaded successfully !";
		} catch (Exception e) {
			e.printStackTrace();
			fileUploadMsg = "EXCEPTION : " + e.getMessage();
			fileInfo.setFileUploadMsg(fileUploadMsg);
			new ResponseEntity<FileInfo>(fileInfo, HttpStatus.EXPECTATION_FAILED);
		}
		fileInfo.setFileUploadMsg(fileUploadMsg);
		System.out.println(fileInfo);

		return new ResponseEntity<FileInfo>(fileInfo, HttpStatus.OK);
	}

	public void populateDefaultMasterData(Model model) {

		model.addAttribute("categories", MasterData.getInstance().getCategoryList());
		model.addAttribute("sinalcialYears", MasterData.getInstance().getFinalcialYearList());
		model.addAttribute("programName", MasterData.getInstance().getProgramList());
		model.addAttribute("programGroups", MasterData.getInstance().getProgramGroupList());
		model.addAttribute("share", MasterData.getInstance().getShare());

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
