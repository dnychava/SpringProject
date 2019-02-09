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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
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
import org.idchavan.entity.BankEntity;
import org.idchavan.entity.DocumentEntity;
import org.idchavan.entity.GrSanctionOrderDetailEntity;
import org.idchavan.entity.GrSanctionOrderEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.filterDTO.OrderDetailFilterDTO;
import org.idchavan.validators.BankValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class BankController extends AbstractCommonController {

	@Autowired
	public BankBO bankBO;

	@Autowired
	private SanctionOrderBO sanOrdBO;

	@Autowired
	private GrSanctionOrderBO grSanOrdBO;

	@Autowired
	public BankValidator bankValidator;

	@Autowired
	private OrderDetailFilterDTO sanOrdDtlFilter;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder(binder);
		binder.setValidator(bankValidator);
	}

	@RequestMapping("/viewBank")
	public String viewSanctionOrder(Model model) {
		model.addAttribute("bankList", bankBO.getAllBanks());
		return "viewBank";
	}

	@RequestMapping(value = { "/getGRSanctionOrderDetails",
			"/edit/getGRSanctionOrderDetails" }, method = RequestMethod.POST)
	public String grSanctionView(HttpServletRequest request, Model model) {
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
		ArrayList<String> grSanOrdRids = new ArrayList<String>();
		List<GrSanctionOrderEntity> grSanOrdList = grSanOrdBO.findGrSanOrdByYear(year);
		for (GrSanctionOrderEntity grSanOrdEntity : grSanOrdList) {
			grSanOrdRids.add(grSanOrdEntity.getRid());
		}
		if(grSanOrdRids.isEmpty()){
			grSanOrdRids.add("");
		}
		sanOrdDtlFilter.setRidListForDTLTbl(grSanOrdRids);
		System.out.println("=========testingForSanctionView====shareTypeEnum[" + shareTypeEnum.getName() + "]");
		List<GrSanctionOrderDetailEntity> grSanOrdDtlList = grSanOrdBO.getAllGrSanctionOrderDetails(sanOrdDtlFilter);
		List<SanctionOrderDetailViewDTO> sanOrdDtlViewList = new ArrayList<SanctionOrderDetailViewDTO>();
		for (GrSanctionOrderDetailEntity grSanOrdDtl : grSanOrdDtlList) {

			BankDetailEntity bankDtl = bankBO.getAllBankDetails(grSanOrdDtl.getRid(), shareTypeEnum.getName());

			SanctionOrderDetailViewDTO sanOrdDtlViewDTO = new SanctionOrderDetailViewDTO();
			sanOrdDtlViewDTO.setRid(grSanOrdDtl.getRid());
			sanOrdDtlViewDTO.setOrderProgramName(grSanOrdDtl.getOrderProgramName());
			sanOrdDtlViewDTO.setOrderDate(grSanOrdDtl.getOrderDate());
			sanOrdDtlViewDTO.setOrderNumber(grSanOrdDtl.getOrderNumber());
			sanOrdDtlViewDTO.setOrderCategory(grSanOrdDtl.getOrderCategory());
			compareGrSanOrdDtlToBankDtl(grSanOrdDtl, bankDtl, sanOrdDtlViewDTO);
			sanOrdDtlViewList.add(sanOrdDtlViewDTO);
		}
		model.addAttribute("sanOrdDtlViewList", sanOrdDtlViewList);
		return "popupGrSanctionOrderDetails";
	}

	public void compareGrSanOrdDtlToBankDtl(GrSanctionOrderDetailEntity grSanOrdDtl, BankDetailEntity bankDtl,
			SanctionOrderDetailViewDTO sanOrdDtlViewDTO) {

		if (bankDtl != null) {
			if (grSanOrdDtl.getOrderAmt().compareTo(bankDtl.getAmt()) == 0) {
				sanOrdDtlViewDTO.setRadioBtn(false);
				sanOrdDtlViewDTO.setOrderAmt(grSanOrdDtl.getOrderAmt());
				sanOrdDtlViewDTO.setOrderAmtInLakh(grSanOrdDtl.getOrderAmtInLakh());
			} else if (grSanOrdDtl.getOrderAmt().compareTo(bankDtl.getAmt()) == 1) {
				sanOrdDtlViewDTO.setOrderAmt(grSanOrdDtl.getOrderAmt().subtract(bankDtl.getAmt()));
				sanOrdDtlViewDTO.setOrderAmtInLakh(grSanOrdDtl.getOrderAmtInLakh().subtract(bankDtl.getAmtInLakh()));
			}
		} else {
			sanOrdDtlViewDTO.setOrderAmt(grSanOrdDtl.getOrderAmt());
			sanOrdDtlViewDTO.setOrderAmtInLakh(grSanOrdDtl.getOrderAmtInLakh());
		}
	}

	@RequestMapping(value = "/addBank", method = RequestMethod.GET)
	public String showform(Model model) {

		BankEntity bank = new BankEntity();
		bank.getRid();

		BankDetailEntity bankDetail = new BankDetailEntity();
		bankDetail.setAmt(BigDecimal.ZERO);
		bankDetail.setBank(bank);

		List<BankDetailEntity> listOrdDetails = new ArrayList<BankDetailEntity>();
		listOrdDetails.add(bankDetail);

		bank.setBankDetails(listOrdDetails);

		// Populate Default MasterData
		populateDefaultMasterData(model);

		model.addAttribute("formMode", FormMode.NEW);
		model.addAttribute("bank", bank);

		return "addBank";

	}

	@RequestMapping(value = { "/saveBank", "/edit/save" }, method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("bank") @Validated BankEntity bank, BindingResult result,
			final RedirectAttributes redirectAttributes, Model model, HttpSession session, HttpServletRequest request)
			throws IOException {
		FormMode currentFormMode;

		Set<String> sanOrdDtlRidSet = new HashSet<String>();
		// Get the current form mode
		String formMode = request.getParameter("formMode");
		System.out.println("======formMode===[" + formMode + "]");

		/*
		 * Get the all deleted rids of sanction order detail in form edit mode. which
		 * user was deleted the rows When click on '-' button;
		 */
		String bankDtlRidsDeleted = request.getParameter("grSanOrdDtlRidsDeleted");
		// System.out.println("======sanOrdDtlRidsDeleted===[" + sanOrdDtlRidsDeleted +
		// "]");

		if (StringUtils.equals("NEW", formMode)) {
			currentFormMode = FormMode.NEW;
			bank.setCreatedDate(new Date());
		} else {
			currentFormMode = FormMode.EDIT;
			bank.setModifiedDate(new Date());
		}

		if (result.hasErrors()) {
			populateDefaultMasterData(model);
			return "addBank";
		} else {
			for (BankDetailEntity bankDetail : bank.getBankDetails()) {

				switch (currentFormMode) {
				case NEW:
					bankDetail.setCreatedDate(new Date());
					break;
				case EDIT:
					bankDetail.setModifiedDate(new Date());
					break;
				default:
					break;
				}
				if (bankDetail.getBank() == null) {
					bankDetail.setBank(bank);

					// Don't comment the below line because if rid null then create new rid.
					bankDetail.getRid();
				}
				ShareTypeEnum curShareTypeEnum = null;
				if (StringUtils.equalsIgnoreCase("State", bankDetail.getShare())) {
					curShareTypeEnum = ShareTypeEnum.STATE;
				} else if (StringUtils.equalsIgnoreCase("Central", bankDetail.getShare())) {
					curShareTypeEnum = ShareTypeEnum.CENTRAL;
				}
				checkGrCompleted(bankDetail, curShareTypeEnum, sanOrdDtlRidSet);
			}

			prepareDocument(session, bank.getDocumentEntity());

			bankBO.saveOrUpdate(bank);
			checkSanctionOrderCompleted(bank.getBankDetails());
			System.out.println("IN SAVE sanctionOrder[" + bank + "]");
			redirectAttributes.addAttribute("css", "success");
			if (currentFormMode == FormMode.EDIT) {
				redirectAttributes.addAttribute("msg", "Data Updated Successfully!");
				if (StringUtils.isNotEmpty(bankDtlRidsDeleted)) {
					List<BankDetailEntity> bankDtdList = bankBO
							.getAllBankDetails(Arrays.asList(bankDtlRidsDeleted.split("~~")));
					for (BankDetailEntity bankDtEntity : bankDtdList) {
						bankBO.deleteBankDetail(bankDtEntity);
					}
				}
			} else {
				redirectAttributes.addAttribute("msg", "Data Save Successfully!");
			}
			System.out.println("===========Data save==================");

			// will redirect to view sanction order request mapping
			return "redirect:/viewBank";
		}

	}

	/**
	 * This method check the amount of current entered in the BankDetails. If this
	 * amount is equal of GRSanctionDetail amount. Then mark as completed and update
	 * column <code>'COMPLETED'</code> with value 'Y' of both. Otherwise no action.
	 * 
	 * @param bankDetail the Bank Detail entity 
	 * @param curShareTypeEnum the given share type
	 * @param sanOrdDtlRidSet
	 *            for the SanctionOrderDetail Rids
	 */
	private void checkGrCompleted(BankDetailEntity bankDetail, ShareTypeEnum curShareTypeEnum,
			Set<String> sanOrdDtlRidSet) {
		GrSanctionOrderDetailEntity grSanOrdDtlEntity = grSanOrdBO
				.findGrSanctionOrderDetailByRid(bankDetail.getGrSanOrdDtlRid());
		if (grSanOrdDtlEntity != null) {
			boolean isCompleted = false;
			switch (curShareTypeEnum) {
			case STATE:
				if (bankDetail.getAmt().compareTo(grSanOrdDtlEntity.getOrderAmt()) == 0) {
					bankDetail.setCompleted(COMPLETED_YES);
					grSanOrdDtlEntity.setCompleted(COMPLETED_YES);
					isCompleted = true;
				}
				break;
			case CENTRAL:
				if (bankDetail.getAmt().compareTo(grSanOrdDtlEntity.getOrderAmt()) == 0) {
					bankDetail.setCompleted(COMPLETED_YES);
					grSanOrdDtlEntity.setCompleted(COMPLETED_YES);
					isCompleted = true;
				}
				break;
			default:
				break;
			}
			if (isCompleted) {
				grSanOrdBO.saveOrUpdate(grSanOrdDtlEntity.getGrSanctionOrder());
			}
		}
	}

	private void checkSanctionOrderCompleted(List<BankDetailEntity> bankDtlList) {

		for (BankDetailEntity bankDetail : bankDtlList) {
			ShareTypeEnum curShareTypeEnum = null;
			if (StringUtils.equalsIgnoreCase("State", bankDetail.getShare())) {
				curShareTypeEnum = ShareTypeEnum.STATE;
			} else if (StringUtils.equalsIgnoreCase("Central", bankDetail.getShare())) {
				curShareTypeEnum = ShareTypeEnum.CENTRAL;
			}

			GrSanctionOrderDetailEntity grSanOrdDtlEntity1 = grSanOrdBO
					.findGrSanctionOrderDetailByRid(bankDetail.getGrSanOrdDtlRid());
			if (grSanOrdDtlEntity1 != null) {
				GrSanctionOrderDetailEntity grSanOrdDtlEntity = grSanOrdBO
						.getGrDetailsUsingGroupBy(grSanOrdDtlEntity1.getSanOrdDtlRid(), curShareTypeEnum.getName());
				SanctionOrderDetailEntity sanOrdDtlEntity = sanOrdBO
						.findSanctionOrderDetailByRid(grSanOrdDtlEntity.getSanOrdDtlRid());
				if (sanOrdDtlEntity != null) {
					boolean isCompleted = false;
					String completed = "" + grSanOrdDtlEntity.getCompleted();
					switch (curShareTypeEnum) {
					case STATE:
						if (grSanOrdDtlEntity.getOrderAmt().compareTo(sanOrdDtlEntity.getOrderStateShareAmt()) == 0
								&& StringUtils.equals(completed, Character.toString(COMPLETED_YES))) {
							sanOrdDtlEntity.setStateShareCompleted(COMPLETED_YES);
							isCompleted = true;
						}
						break;
					case CENTRAL:
						if (grSanOrdDtlEntity.getOrderAmt().compareTo(sanOrdDtlEntity.getOrderAmt()) == 0
								&& StringUtils.equals(completed, Character.toString(COMPLETED_YES))) {
							sanOrdDtlEntity.setCompleted(COMPLETED_YES);
							isCompleted = true;
						}
						break;
					default:
						break;
					}
					if (isCompleted) {
						sanOrdBO.saveOrUpdate(sanOrdDtlEntity.getSanctionOrder());
					}
				}
			}
		}
	}

	@RequestMapping(value = "/editBank", method = RequestMethod.POST)
	public String edit(@RequestParam("bankRid") String bankRid, Model model) {

		BankEntity bank = bankBO.findByRid(bankRid);
		// Populate Default MasterData
		populateDefaultMasterData(model);
		model.addAttribute("formMode", FormMode.EDIT);
		model.addAttribute("bank", bank);

		return "addBank";
	}

	@RequestMapping(value = "/deleteBank/{bankRid}", method = RequestMethod.GET)
	public String delete(@PathVariable("bankRid") String bankRid, Model model,
			final RedirectAttributes redirectAttributes) {

		BankEntity bank = bankBO.findByRid(bankRid);

		bankBO.deleteBank(bank);
		updateGROrder( bank );
		System.out.println("IN SAVE sanctionOrder[" + bank + "]");
		redirectAttributes.addAttribute("css", "danger");
		redirectAttributes.addAttribute("msg", "Data deleted Successfully!");

		// will redirect to view sanction order request mapping
		return "redirect:/viewBank";
	}

	/**
	 * This method updating the <code>COMPLETED </code> <br>
	 * column with <code>N</code> value of <code>GR_SAN_DTL</code> table.<br>
	 * If current column value 'Y' otherwise nothing.
	 * 
	 * @param bank of current deleted.
	 */
	public void updateGROrder(BankEntity bank) {
		List<BankDetailEntity> bnkDtlList = bank.getBankDetails();
		for (BankDetailEntity bankDetailEntity : bnkDtlList) {
			GrSanctionOrderDetailEntity grSanOrdDtlEntity = grSanOrdBO
					.findGrSanctionOrderDetailByRid(bankDetailEntity.getGrSanOrdDtlRid());
			if( grSanOrdDtlEntity != null ) {
				if (StringUtils.equals(Character.toString(COMPLETED_YES),
						Character.toString(grSanOrdDtlEntity.getCompleted()))) {
					grSanOrdDtlEntity.setCompleted(COMPLETED_NO);
					grSanOrdBO.saveOrUpdate( grSanOrdDtlEntity.getGrSanctionOrder() );
					updateSanOrderDtl(grSanOrdDtlEntity);
				}
			}	
		}
	}
	
	/**
	 * This method updating the <code>COMPLETED </code> <br>
	 * column with <code>N</code> value of <code>SAN_DTL</code> table.<br>
	 * If current column value 'Y' otherwise nothing.
	 * 
	 * @param grSanOrdDtlEntity of current update.
	 */
	public void updateSanOrderDtl(GrSanctionOrderDetailEntity grSanOrdDtlEntity) {
		SanctionOrderDetailEntity sanOrdDtlEntity = sanOrdBO.findSanctionOrderDetailByRid( grSanOrdDtlEntity.getSanOrdDtlRid() );
		if( sanOrdDtlEntity != null ) {
			if (StringUtils.equals(Character.toString(COMPLETED_YES),
					Character.toString(sanOrdDtlEntity.getCompleted()))) {
				sanOrdDtlEntity.setCompleted(COMPLETED_NO);
				sanOrdBO.saveOrUpdate( sanOrdDtlEntity.getSanctionOrder() );
			}
		}
	}

	@RequestMapping({ "/cancelBank", "/edit/cancelBank" })
	public String cancelBank(Model model) {
		return "redirect:/viewBank";
	}

	@RequestMapping("/homePageFromBank")
	public String homePageFromSanctionOrder(Model model) {
		// This should be redirect at home page(index.jsp)
		return "redirect:/index.jsp";
	}

	@RequestMapping(value = { "editBank/viewPDFFile/{fileName:.+}",
			"/viewBankPDFFile/{fileName:.+}" }, method = RequestMethod.GET)
	public void viewPDFFile(@PathVariable("fileName") String fileName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String fullFilePath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator + fileName;

		// The below line for download the pdf file
		// response.addHeader("Content-Disposition", "attachment;filename=report.pdf");
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
			DocumentEntity docEntity = bankBO.findDocumentByRid(docRid);
			if (docEntity != null) {
				response.setContentLengthLong(docEntity.getContent().length);
				response.getOutputStream().write(docEntity.getContent());
			} else {
				response.setContentType("plain/text");
				response.getOutputStream().write("<h1>FILE NOT FOUND!</h1>".getBytes());
			}
		}

	}

	@RequestMapping(value = { "saveBankfile",
			"edit/savefile" }, method = RequestMethod.POST, produces = "application/json")
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
