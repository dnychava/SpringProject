<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script> -->

<spring:url value="/resources/app/css/main.css" var="mainCss" />
<spring:url value="/resources/app/js/comman.js" var="comman" />
<spring:url value="/resources/app/js/appFileUpload.js" var="fileUpload" />

<link href="${mainCss}" rel="stylesheet">
<script src="${comman}"></script>
<script src="${fileUpload}"></script>

<script type="text/javascript">
var $ = jQuery;
	$(document).ready(function() {
		 //Adding new row when clicked on 'Add(+)' button.
		$("#addRow").click(function(event) {
			event.preventDefault();
			var proNameOption = createOption(programName);
			var categorieOption = createOption(categories);
			lastIndex++;
			//console.log("lastIndex["+lastIndex+"]");
			var newRow = "<tr>"
				+"<td> <input type='checkbox' name='record'/> <input type='hidden' name='sanctionOrderDetails["+lastIndex+"].rid' value='null'/> </td>"
				+"<td>"
					+"<select autofocus id='sanctionOrderDetails["+lastIndex+"].orderProgramName' name='sanctionOrderDetails["+lastIndex+"].orderProgramName' class='form-control'>"+proNameOption+"</select>"
					+"<form:errors name='sanctionOrderDetails["+lastIndex+"].orderProgramName' cssClass='error' element='div'/>"	
				+"</td>"
				+"<td>"
					+"<div class='input-group date'  data-provide='datepicker' data-date-format='dd/mm/yyyy'>"
						+"<input type='text' name='sanctionOrderDetails["+lastIndex+"].orderDate' class='form-control' placeholder='dd/mm/yyyy'/>"
						+"<div class='input-group-addon'>"
                 		+"<i class='fa fa-calendar' aria-hidden='true'></i>"
                 		+"</div>"
					+"</div>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderDate' cssClass='error' element='div'/>"
				+"</td>"					
				+"<td>"
					+"<input name='sanctionOrderDetails["+lastIndex+"].orderNumber' class='form-control'/>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderNumber' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<select name='sanctionOrderDetails["+lastIndex+"].orderCategory' class='form-control'>"+categorieOption+"</select>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderCategory' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='number' value='0.00' name='sanctionOrderDetails["+lastIndex+"].orderAmt' id='sanctionOrderDetails["+lastIndex+"].orderAmt' onblur='amtConvertInLakh("+lastIndex+");' class='form-control' step='.01'/>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderAmt' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' value='0.00' name='sanctionOrderDetails["+lastIndex+"].orderAmtInLakh' id='sanctionOrderDetails["+lastIndex+"].orderAmtInLakh' readonly class='form-control'/>"
				+"</td>"
				+"</tr>";
			$("#SanOrdDtlTbl tbody").append(newRow);
		});		
		
		 
		// Find and remove selected table rows
        $("#delete-row").click(function(){
            $("table tbody").find('input[name="record"]').each(function(){
            	if($(this).is(":checked")){
            		var sanOrdDtlRid = $("#sanOrdDtlRidsDeleted").val();
            		//console.log("==B4==sanOrdDtlRid["+sanOrdDtlRid+"]");
            		$("#sanOrdDtlRidsDeleted").val(sanOrdDtlRid+"~~"+$(this).attr("id"));
            		//console.log("==after==sanOrdDtlRid["+$("#sanOrdDtlRidsDeleted").val()+"]");
                    $(this).parents("tr").remove();
                    lastIndex--;
                    
                }
            });
            calTotAmt();
        });
		
        /**
    	* This function upload the file
    	*/
    	$("#btnSubmit").click(function(event) {
    		fileUploadApp("viewSanctionOrdPDFFile");
    	});
    	//End upload file function
		
	}); // End document
	 
	function createOption(optionData){
		if(optionData != null 
			&& optionData != "undefine" ){
			var arr = optionData.split(",");
			var optionStr = "<option value='NONE' selected='selected'>Select</option>";
			for( var i = 0; i < arr.length; i++){
				optionStr = optionStr + "<option value='"+arr[i].trim()+"'>"+arr[i].trim()+"</option>"
			}
			return optionStr;
		}
	}
	function amtConvertInLakh(indexOfObj) {
		console.log("amtConvertInLakh :: indexOf["+indexOfObj+"]");
		var amtInRsElement = document.getElementById("sanctionOrderDetails["+ indexOfObj + "].orderAmt");
		var amtInLakh = document.getElementById("sanctionOrderDetails["+ indexOfObj + "].orderAmtInLakh");
		var amtInRs = amtInRsElement.value;
		amtInRs = Number.parseFloat(amtInRs).toFixed(2);
		var totInLack = (amtInRs / 100000);
		if (amtInLakh != 'undefine' && amtInLakh != null) {
			amtInLakh.value = totInLack.toFixed(2);
		}
		amtInRsElement.value=amtInRs;
		calTotAmt();
	}
	
	function calTotAmt() {
		var totAmt = 0;
		for (var i = 0; i <=lastIndex; i++) {
			var orderAmt = document.getElementById("sanctionOrderDetails["+ i + "].orderAmt");
			if(orderAmt!= null && orderAmt != 'undefine'){
				totAmt = totAmt + Number.parseFloat(orderAmt.value);
			}
		}
		document.getElementById("sanctionOrder.totAmount").value=totAmt;
	}
</script>
<style type="text/css">
/* .error {
	color: red;
	font-weight: bold;
} */
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-header"><i class="fa fa-file-text-o"></i> Sanction Order Form</h3>
			</div>
		</div>
	<section class="panel" >
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Add New Sanction Order</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
			<div class="form">
			<form:form action="save" method="post" modelAttribute="sanctionOrder">
				<script>
					var programName = '<c:out value="${programName}"/>'; 
					programName = programName.substring(1, (programName.length) - 1);
					var categories = '<c:out value="${categories}"/>';
					categories = categories.substring(1, (categories.length) - 1);
				</script>
				<input type="hidden" name="formMode" id="formMode" value="${formMode}" />
				<input type="hidden" name="sanOrdDtlRidsDeleted" id="sanOrdDtlRidsDeleted" >
				<form:errors path="duplicateError" cssClass="error" element="div" />
				<div class="row form-group">	<!-- 1fst Row -->
				 	<div class="col-md-3">
				 		<div class="form-group">
				 			<label for="year" class="control-label">Year <span class="required">*</span></label>
							<form:hidden path="rid" value="${rid}" /> 
							<form:select class="form-control" path="year" id="year">
								<form:option value="NONE" label="Select"  />
								<form:options items="${sinalcialYears}" />
							</form:select> 
							<form:errors path="year" cssClass="error" element="div" />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<label for="programGroupName" class="control-label">Program Group Name <span class="required">*</span></label>
								<form:select path="programGroupName" class="form-control" id="programGroupName">
									<form:option value="NONE" label="Select" />
									<form:options items="${programGroups}" />
								</form:select> 
								<form:errors path="programGroupName" cssClass="error" element="div" />
						</div>
					</div>
					<div class="col-md-3">
						<div class="form-group">
							<div class="row">
								<c:choose>
										<c:when test="${ not empty sanctionOrder.documentEntity.fileName}">
											<spring:url value="viewSanctionOrdPDFFile/${sanctionOrder.documentEntity.rid}_${sanctionOrder.documentEntity.fileName}" 
														var="viewPDFUrl"></spring:url>
											<a id="fileName" class="" href="javascript:viewFile('${viewPDFUrl}')"
												style="display: inline-block;margin-bottom: 5px;">
												<span>${sanctionOrder.documentEntity.fileName}</span>
											</a>
										</c:when>
										<c:otherwise>
											<a id="fileName" href="#" style="display: inline-block;margin-bottom: 5px;">
												<span>No File Attached</span>
											</a>
										</c:otherwise>
								</c:choose>
								<form:hidden path="documentEntity.rid" id="documentEntityRid" value="${sanctionOrder.documentEntity.rid}" /> 
								<form:hidden path="documentEntity.fileName" id="documentEntityFileName"	value="${sanctionOrder.documentEntity.fileName}" />
							</div>
							<div class="row">
								 <!-- Button to Open the Modal -->
								<button type="button" class="btn btn-default" data-toggle="modal" data-target="#fileUploadModal" id="fileName1"> 
									<i class="fa fa-paperclip" aria-hidden="true"></i> File Attach
								</button>
							</div>
						</div>
					</div>
					<div class="col-md-3">
							<div class="form-group">
								<label for="sanctionOrder.totAmount" class="control-label">Total Amount</label>
								<form:input path="totAmount" class="form-control" id="sanctionOrder.totAmount" 
											readonly="readonly" value="${sanctionOrder.totAmount}"/>
							</div>
					</div>
					
				</div> <!-- 1fst Row end -->
				<div class="row">
					<table id="SanOrdDtlTbl" class="table">
					
					<!-- The Modal	<tr>
						<th colspan="7">Add New Sanction Order</th>	
					</tr>-->
					<thead>
					<tr>
						<th style="width:3%"><button id="addRow" class="btn btn-default"><i class="fa fa-plus-square-o" aria-hidden="true"></i></button></th>
						<th style="width:15%">Program Name <span class="required">*</span></th>
						<th style="width:14%">Sanction Order Date(dd/mm/yyyy) <span class="required">*</span></th>
						<th>Sanction Order No. <span class="required">*</span></th>
						<th style="width:9%">Category <span class="required">*</span></th>
						<th style="width:15%">Amt In Rs. <span class="required">*</span></th>
						<th style="width:10%"><span>Amt In Lakh </span></th>
					</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="7" ><button type="button" id="delete-row" class="delete-row btn btn-default"><i class="fa fa-trash-o" aria-hidden="true"></i></button></td>
						</tr>
					</tfoot>
					<tbody>
					<c:forEach items="${sanctionOrder.sanctionOrderDetails}"
						var="orderDetail" varStatus="status">
						<tr>
							<td><input type="hidden"
								name="sanctionOrderDetails[${status.index}].rid"
								value="${orderDetail.rid}"
								id="sanctionOrderDetails[${status.index}].rid" />
								
								<!-- This lastIndex var user for add new row  -->
								<script>
									var lastIndex = '<c:out value="${status.index}"/>'; 
								</script>
								
								<c:if test="${ status.index > 0 }">
									<input type='checkbox' name='record' id="${orderDetail.rid}"/>
								</c:if></td>
								
							<td><form:select 
									path="sanctionOrderDetails[${status.index}].orderProgramName"
									id="sanctionOrderDetails[${status.index}].orderProgramName" class="form-control">
									<form:option value="NONE" label="Select" selected="selected"  />
									<form:options items="${programName}" />
								</form:select> <form:errors
									path="sanctionOrderDetails[${status.index}].orderProgramName"
									cssClass="error" element="div"/></td>
							<td>
								<div class="input-group date"  data-provide="datepicker" data-date-format="dd/mm/yyyy">
									<form:input path="sanctionOrderDetails[${status.index}].orderDate" value="${dateString}" class="form-control" placeholder="dd/mm/yyyy"/>
									<div class="input-group-addon">
				                 		<i class="fa fa-calendar" aria-hidden="true"></i>
				                 	</div>
								</div>
								<form:errors path="sanctionOrderDetails[${status.index}].orderDate" cssClass="error" element="div" />
							</td>
							<td><form:input
									path="sanctionOrderDetails[${status.index}].orderNumber"
									value="${orderDetail.orderNumber}"
									id="sanctionOrderDetails[${status.index}].orderNumber" class="form-control"/> <form:errors
									path="sanctionOrderDetails[${status.index}].orderNumber"
									cssClass="error" element="div" /></td>
							<td><form:select
									path="sanctionOrderDetails[${status.index}].orderCategory" class="form-control">
									<form:option value="NONE" label="Select" />
									<form:options items="${categories}" />
								</form:select> <form:errors
									path="sanctionOrderDetails[${status.index}].orderCategory"
									cssClass="error" element="div" /></td>
							<td><form:input
									path="sanctionOrderDetails[${status.index}].orderAmt"
									id="sanctionOrderDetails[${status.index}].orderAmt"
									type="number" value="${orderDetail.orderAmt}"
									onblur="amtConvertInLakh('${status.index}')" class="form-control"
									step=".01"/> <form:errors
									path="sanctionOrderDetails[${status.index}].orderAmt"
									cssClass="error" element="div" /></td>
							<td><input type="text"
								name="sanctionOrderDetails[${status.index}].orderAmtInLakh"
								id="sanctionOrderDetails[${status.index}].orderAmtInLakh"
								value="${orderDetail.orderAmtInLakh}" class="form-control" readonly/></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				</div>
				<div class="row form-group">	
					<div class="col-md-9"></div>
				 	<div class="col-md-2">
							<a href="cancelSanctionOrder" class="btn btn-link pull-right">Cancel</a>
					</div>
					<div class="col-md-1">
						<button type="submit" class="btn btn-primary pull-right" value="Save"><i class="fa fa-floppy-o" aria-hidden="true"></i>  Save </button>
					</div>
				</div>
			</form:form>
			</div>
		</div>
		<div>

			<!-- The Modal -->
			<div class="modal fade" id="fileUploadModal">
				<div class="modal-dialog">
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<button aria-hidden="true" type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">File Upload Dialog</h4>
						</div>

						<!-- Modal body -->
						<div class="modal-body">
							<div data-alerts="alerts" id="alerts"></div>
							<form method="post" action="savefile"
								enctype="multipart/form-data" id="fileUploadForm">
								<div class="form-group">
									<label for="fileToUpload">Please select file</label> <input
										name="file" id="file" type="file" class="form-control" />
								</div>
							</form>
						</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">Close</button>
							<button type="submit" id="btnSubmit" class="btn btn-primary">Upload</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>
	</div>
</body>
</html>