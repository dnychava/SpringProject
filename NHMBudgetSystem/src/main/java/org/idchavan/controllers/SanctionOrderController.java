package org.idchavan.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.idchavan.entity.MasterSharePercentageEntity;
import org.idchavan.entity.SanctionOrderDetailEntity;
import org.idchavan.entity.SanctionOrderEntity;
import org.idchavan.validators.SanctionOrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpRequest;
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
public class SanctionOrderController extends AbstractCommonController {

	@Autowired
	public SanctionOrderBO sancOrdBO;
	
	@Autowired
	public GrSanctionOrderBO grSanOrdBO;
	
	@Autowired
	public BankBO bankBO;

	@Autowired
	public SanctionOrderValidator sanOrdValidator;

	@Autowired
	public ApplicationContext context;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		super.initBinder( binder );
		binder.setValidator(sanOrdValidator);
	}

	@RequestMapping("/viewSanctionOrder")
	public String viewSanctionOrder(Model model) {
		model.addAttribute("sanctionOrderList", sancOrdBO.getAllSanctionOrders());
		return "viewSanctionOrder";
	}

	@RequestMapping(value = "/addSanctionOrder", method = RequestMethod.GET)
	public String showform(Model model) {

		SanctionOrderEntity sanctionOrder = new SanctionOrderEntity();
		sanctionOrder.getRid();

		sanctionOrder.setCreatedDate(new Date());

		SanctionOrderDetailEntity sanctionOrderDetail = new SanctionOrderDetailEntity();
		sanctionOrderDetail.setOrderAmtInLakh(BigDecimal.ZERO);
		sanctionOrderDetail.setSanctionOrder(sanctionOrder);

		List<SanctionOrderDetailEntity> listOrdDetails = new ArrayList<SanctionOrderDetailEntity>();
		listOrdDetails.add(sanctionOrderDetail);

		sanctionOrder.setSanctionOrderDetails(listOrdDetails);

		// Populate Default MasterData
		populateDefaultMasterData(model);

		model.addAttribute("formMode", FormMode.NEW);
		model.addAttribute("sanctionOrder", sanctionOrder);

		return "addSanctionOrder";

	}

	@RequestMapping(value = { "/save", "edit/save" }, method = RequestMethod.POST)
	public String saveOrUpdate(@ModelAttribute("sanctionOrder") @Validated SanctionOrderEntity sanctionOrder,
			BindingResult result, final RedirectAttributes redirectAttributes, Model model, HttpSession session,
			HttpServletRequest request) throws IOException {
		FormMode currentFormMode;
		request.setCharacterEncoding( "UTF-8" );
		// Get the current form mode
		String formMode = request.getParameter("formMode");
		System.out.println("======formMode===[" + formMode + "]");

		/*
		 * Get the all deleted rids of sanction order detail in form edit mode. which
		 * user was deleted the rows When click on '-' button;
		 */
		String sanOrdDtlRidsDeleted = request.getParameter("sanOrdDtlRidsDeleted");
		// System.out.println("======sanOrdDtlRidsDeleted===[" + sanOrdDtlRidsDeleted +
		// "]");

		if (StringUtils.equals("NEW", formMode)) {
			currentFormMode = FormMode.NEW;
			sanctionOrder.setCreatedDate(new Date());
		} else {
			currentFormMode = FormMode.EDIT;
			sanctionOrder.setModifiedDate(new Date());
		}
		System.out.println(
				"======MasterSharePerEntityList===[" + MasterData.getInstance().getMasterSharePerEntityList() + "]");

		if (result.hasErrors()) {
			populateDefaultMasterData(model);
			return "addSanctionOrder";
		} else {
			for (SanctionOrderDetailEntity sanOrderDetails : sanctionOrder.getSanctionOrderDetails()) {

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
				//calculating state share Amt And Lakh
				calculateStateShareAmtAndInLakh(sanOrderDetails);
				
				if (sanOrderDetails.getSanctionOrder() == null) {
					sanOrderDetails.setSanctionOrder(sanctionOrder);

					// Don't comment the below line because if rid null then create new rid.
					sanOrderDetails.getRid();
				}
			}
			
			prepareDocument(session, sanctionOrder.getDocumentEntity());
			try {
				sancOrdBO.saveOrUpdate(sanctionOrder);
			} catch (DataIntegrityViolationException e1) {
				e1.printStackTrace();
				System.out.println(e1.getMessage());
				result.rejectValue("duplicateError", "record.duplicate");
				populateDefaultMasterData(model);
				return "addSanctionOrder";
			}
			
			System.out.println("IN SAVE sanctionOrder[" + sanctionOrder + "]");
			redirectAttributes.addAttribute("css", "success");
			if (currentFormMode == FormMode.EDIT) {
				redirectAttributes.addAttribute("msg", "Data Updated Successfully!");
				if (StringUtils.isNotEmpty(sanOrdDtlRidsDeleted)) {
					List<SanctionOrderDetailEntity> sanOrdDtdList = sancOrdBO
							.getAllSanctionOrderDetails(Arrays.asList(sanOrdDtlRidsDeleted.split("~~")));
					for (SanctionOrderDetailEntity sanOrdDtsEntity : sanOrdDtdList) {
						sancOrdBO.deleteSanctionOrderDetails(sanOrdDtsEntity);
					}
				}
			} else {
				redirectAttributes.addAttribute("msg", "Data Save Successfully!");
			}
			System.out.println("===========Data save==================");

			// will redirect to view sanction order request mapping
			return "redirect:/viewSanctionOrder";
		}

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String edit(@RequestParam("sanOrdRid") String sanOrdRid, Model model) {
		SanctionOrderEntity sanctionOrder = sancOrdBO.findByRid(sanOrdRid);
		// Populate Default MasterData
		populateDefaultMasterData(model);
		model.addAttribute("formMode", FormMode.EDIT);
		model.addAttribute("sanctionOrder", sanctionOrder);

		return "addSanctionOrder";
	}

	@RequestMapping(value = "/delete/{sanOrdRid}", method = RequestMethod.GET)
	public String delete(@PathVariable("sanOrdRid") String sanOrdRid, Model model,
			final RedirectAttributes redirectAttributes) {

		SanctionOrderEntity sanctionOrder = sancOrdBO.findByRid(sanOrdRid);

		sancOrdBO.deleteSanctionOrder(sanctionOrder);
		
		for (SanctionOrderDetailEntity sanOrdDtlEntity : sanctionOrder.getSanctionOrderDetails()) {
			deleteGrSanctionOrder(sanOrdDtlEntity);
		}
		
		System.out.println("IN SAVE sanctionOrder[" + sanctionOrder + "]");
		redirectAttributes.addAttribute("css", "danger");
		redirectAttributes.addAttribute("msg", "Data deleted Successfully!");

		// will redirect to view sanction order request mapping
		return "redirect:/viewSanctionOrder";
	}
	
	private void deleteGrSanctionOrder( SanctionOrderDetailEntity sanOrdDtlEntity ) {
		List<GrSanctionOrderDetailEntity> grSanOrdDtlList = grSanOrdBO.getAllGrSanOrdDtlBySanOrdDtlRid( sanOrdDtlEntity.getRid() );
		for (GrSanctionOrderDetailEntity grSanOrdDtlEntity : grSanOrdDtlList) {
			if( grSanOrdDtlEntity != null ) {
				grSanOrdBO.deleteGrSanctionOrder(grSanOrdDtlEntity.getGrSanctionOrder());
				deleteBankDetail(grSanOrdDtlEntity);
			}
		}
	}
	
	private void deleteBankDetail( GrSanctionOrderDetailEntity grSanOrdDtlEntity ) {
		List<BankDetailEntity> bankDtlList = bankBO.findBankDtlByGrSanOrdDtlRid( grSanOrdDtlEntity.getRid() );
		for (BankDetailEntity bankDetailEntity : bankDtlList) {
			if (bankDetailEntity != null ) {
				bankBO.deleteBank(bankDetailEntity.getBank());
			}
		}
	}
	
	

	@RequestMapping({ "/cancelSanctionOrder", "/edit/cancelSanctionOrder" })
	public String cancelSanctionOrder(Model model) {
		return "redirect:/viewSanctionOrder";
	}

	@RequestMapping("/homePageFromSanctionOrder")
	public String homePageFromSanctionOrder(Model model) {
		// This should be redirect at home page(index.jsp)
		return "redirect:/index.jsp";
	}

	@RequestMapping(value = { "/edit/viewSanctionOrdPDFFile/{fileName:.+}",
			"/viewSanctionOrdPDFFile/{fileName:.+}",
			"/addSanctionOrder/viewSanctionOrdPDFFile/{fileName:.+}" }, method = RequestMethod.GET)
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
			DocumentEntity docEntity = sancOrdBO.findDocumentByRid(docRid);
			if (docEntity != null) {
				response.setContentLengthLong(docEntity.getContent().length);
				response.getOutputStream().write(docEntity.getContent());
			} else {
				response.setContentType("plain/text");
				response.getOutputStream().write("<h1>FILE NOT FOUND!</h1>".getBytes());
			}
		}

	}

	@RequestMapping(value = { "savefile", "edit/savefile" }, method = RequestMethod.POST, produces = "application/json")
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

	/***
	 * This method calculating the state share amount and state share amount in lakh
	 * @param sanOrdDetail
	 */
	private void calculateStateShareAmtAndInLakh(SanctionOrderDetailEntity sanOrdDetail) {
		
		for (MasterSharePercentageEntity mstShrPerEnty : MasterData.getInstance().getMasterSharePerEntityList()) {

			if (StringUtils.equals(ShareTypeEnum.STATE.toString(), mstShrPerEnty.getShareType())) {
				Date fromDate = mstShrPerEnty.getFromDate();
				Date toDate = mstShrPerEnty.getToDate();

				// Input
				Date enterDate = sanOrdDetail.getOrderDate();
				if ((enterDate.after(fromDate) || enterDate.equals(fromDate))
						&& (enterDate.before(toDate) || enterDate.equals(toDate))) {

					BigDecimal divided = new BigDecimal("100");
					BigDecimal percentage = new BigDecimal(mstShrPerEnty.getPercentage());
					divided = divided.subtract( percentage );
					sanOrdDetail.setOrderSharePer(Integer.parseInt(divided.toString()));
					sanOrdDetail.setOrderStateSharePer( mstShrPerEnty.getPercentage() );
					
					// calculating for state share amount
					sanOrdDetail.setOrderStateShareAmt(sanOrdDetail.getOrderAmt()
							.multiply(percentage).divide(divided, 2,RoundingMode.HALF_UP));
					
					// calculating for state share amount in lakha
					sanOrdDetail.setOrderStateShareAmtInLakh(
							sanOrdDetail.getOrderStateShareAmt().divide(new BigDecimal(100000),4,RoundingMode.HALF_UP));
					System.out.println( " StateShareAmt["+sanOrdDetail.getOrderStateShareAmt()+"] StateShareAmtInLakh["+sanOrdDetail.getOrderStateShareAmtInLakh()+"]");
					break;

				}
			}
		}
	}

}
