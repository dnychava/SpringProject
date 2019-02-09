package org.idchavan.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


/**
 * Handles requests for the application file upload requests
 */
@Controller
public class FileUploadController {

	private static final String UPLOAD_DIRECTORY = "PDFFiles";

	
	@RequestMapping("/fileUpload")
	public String uploadForm() {

		return "fileUpload";
	}

	@RequestMapping(value = "/uploadExcelData", method = RequestMethod.POST, produces = "application/json")
	public String saveimage(@RequestParam MultipartFile file, HttpSession session, HttpServletResponse response)
			throws Exception {

		String fileUploadMsg = "";
		try {
			
			
			ServletContext context = session.getServletContext();
			String path = context.getRealPath("") + UPLOAD_DIRECTORY;
			File dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			String fileName = file.getOriginalFilename();


			byte[] bytes = file.getBytes();

			File storeFilePath = new File(path + File.separator + fileName);


			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(storeFilePath));
			stream.write(bytes);
			stream.flush();
			stream.close();		

			readExcl(file,storeFilePath);
			
	            			
		} catch (Exception e) {
			e.printStackTrace();
			fileUploadMsg = "EXCEPTION : " + e.getMessage();
		}

		return "";
	}

	@RequestMapping(value = {"/Test/viewPDFFile/{fileName:.+}"}, method = RequestMethod.GET)
	public void viewPDFFile(@PathVariable("fileName") String fileName, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String fullFilePath = context.getRealPath("") + File.separator + UPLOAD_DIRECTORY + File.separator
				+ fileName;

		File downloadFile = new File(fullFilePath);
		FileInputStream inputStream = new FileInputStream(downloadFile);

		// get MIME type of the file
		String mimeType = context.getMimeType(fullFilePath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/pdf";
		}
		System.out.println("MIME type: " + mimeType);

		// The below line for download the pdf file
		// response.addHeader("Content-Disposition", "attachment;filename=report.pdf");

		response.setContentType("application/pdf");
		response.setDateHeader("Expires", -1);
		response.setContentLengthLong(downloadFile.length());
		// get output stream of the response
		IOUtils.copy(inputStream, response.getOutputStream());

		inputStream.close();
	}
	
	@RequestMapping("/viewDataUploadPage")
	public String viewDataUploadPage(Model model) {
		System.out.println("=========viewDataUploadPage==========");
		//model.addAttribute("sanctionOrderList", sancOrdBO.getAllSanctionOrders());
		return "viewDataUploadPage";
	}
	
	public void readExcl(MultipartFile file, File storeFilePath) {
		
		 Workbook workbook = null;
	        try {

	            workbook = Workbook.getWorkbook(file.getInputStream());

	            Sheet sheet = workbook.getSheet(0);
	            Cell cell1 = sheet.getCell(0, 0);
	            System.out.print(cell1.getContents() + ":");    // Test Count + :
	            Cell cell2 = sheet.getCell(0, 1);
	            System.out.println(cell2.getContents());        // 1

	            Cell cell3 = sheet.getCell(1, 0);
	            System.out.print(cell3.getContents() + ":");    // Result + :
	            Cell cell4 = sheet.getCell(1, 1);
	            System.out.println(cell4.getContents());        // Passed

	            System.out.print(cell1.getContents() + ":");    // Test Count + :
	            cell2 = sheet.getCell(0, 2);
	            System.out.println(cell2.getContents());        // 2

	            System.out.print(cell3.getContents() + ":");    // Result + :
	            cell4 = sheet.getCell(1, 2);
	            System.out.println(cell4.getContents());        // Passed 2

	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (BiffException e) {
	            e.printStackTrace();
	        } finally {

	            if (workbook != null) {
	                workbook.close();
	            }

	        }
		
		/*
		
		

		try {

			String fullPath = path + File.separator + "text.txt";
			final String EXCEL_FILE_LOCATION = "F:\\Kapil\\Index_utf-8.xlsx";
			FileInputStream excelFile = new FileInputStream(new File(EXCEL_FILE_LOCATION));
			System.out.println("=========Contendt=========="+file.getContentType());
			OPCPackage opc = OPCPackage.open(file.getInputStream());
			Workbook workbook = new XSSFWorkbook(opc);
			Sheet sheet = workbook.getSheetAt(0);
			System.out.println("Sheet Name [" + sheet.getSheetName() + "]");
			Iterator<Row> rowItr = sheet.iterator();
			DataFormatter df = new DataFormatter();
			OutputStreamWriter stream = new OutputStreamWriter(new FileOutputStream(fullPath), "UTF-8");
			
			
			while (rowItr.hasNext()) {
				Row row = (Row) rowItr.next();
				Iterator<Cell> cellItr = row.iterator();
				while (cellItr.hasNext()) {
					Cell cell1 = (Cell) cellItr.next();
					switch (cell1.getCellType()) {

					case Cell.CELL_TYPE_STRING:
						String org = new String (cell1.getRichStringCellValue().getString().getBytes (StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
						stream.write( (org + "-- "));
						System.out.print( org + "-- ");
						break;

					case Cell.CELL_TYPE_NUMERIC:
						if (DateUtil.isCellDateFormatted(cell1)) {
							System.out.print(cell1.getDateCellValue() + "-- ");
						} else {
							System.out.print(cell1.getNumericCellValue() + "-- ");
						}
						break;

					case Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell1.getBooleanCellValue() + "-- ");
						break;

					case Cell.CELL_TYPE_FORMULA:
						switch (cell1.getCachedFormulaResultType()) {
						case Cell.CELL_TYPE_NUMERIC:
							System.out.print(cell1.getNumericCellValue() + "-- ");
							break;
						case Cell.CELL_TYPE_STRING:
							String cellStr = cell1.getStringCellValue().replaceAll("'", "");
							System.out.print(cellStr + "-- ");
							break;
						}
						break;
					}

				}
				System.out.println();
				stream.write( ( "\n" ));

			}
			stream.flush();
			stream.close();	
			System.out.println("fullPath ["+fullPath+"]");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		 * Workbook workbook = null; try {
		 * 
		 * workbook = Workbook.getWorkbook(storeFilePath);
		 * 
		 * Sheet sheet = workbook.getSheet(0); Cell cell1 = sheet.getCell(0, 0);
		 * System.out.print(cell1.getContents() + ":"); // Test Count + : Cell
		 * cell2 = sheet.getCell(0, 1); System.out.println(cell2.getContents());
		 * // 1
		 * 
		 * Cell cell3 = sheet.getCell(1, 0);
		 * System.out.print(cell3.getContents() + ":"); // Result + : Cell cell4
		 * = sheet.getCell(1, 1); System.out.println(cell4.getContents()); //
		 * Passed
		 * 
		 * System.out.print(cell1.getContents() + ":"); // Test Count + : cell2
		 * = sheet.getCell(0, 2); System.out.println(cell2.getContents()); // 2
		 * 
		 * System.out.print(cell3.getContents() + ":"); // Result + : cell4 =
		 * sheet.getCell(1, 2); System.out.println(cell4.getContents()); //
		 * Passed 2
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } catch (BiffException
		 * e) { e.printStackTrace(); } finally {
		 * 
		 * if (workbook != null) { workbook.close(); }
		 * 
		 * }
		 
	*/}

}
