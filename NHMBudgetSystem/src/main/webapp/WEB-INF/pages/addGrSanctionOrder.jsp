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
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
 -->
<spring:url value="/resources/app/css/main.css" var="mainCss" />
<spring:url value="/resources/app/js/comman.js" var="comman" />
<spring:url value="/resources/app/js/appFileUpload.js" var="fileUpload" />

<link href="${mainCss}" rel="stylesheet"/>
<script src="${comman}" type="text/javascript"></script>
<script src="${fileUpload}" type="text/javascript"></script>


<script type="text/javascript">
	//This variable hold the current index when onblur event fired 
	//of selection share type combo box(Elements);
	var curObjIndexOfShareType;
	
	var isCorrectAmt = true;
	
	$(document).ready(function() {
		 //Adding new row when clicked on 'Add(+)' button.
		$("#addRow").click(function(event) {
			event.preventDefault();
			var proNameOption = createOption(programName);
			var categorieOption = createOption(categories);
			var shareTypeOption = createOption(shareType);
			lastIndex++;
			//console.log("lastIndex["+lastIndex+"]");
			var newRow = "<tr>"
				+"<td>"
					+"<input type='checkbox' name='record'/> <input type='hidden' name='grSanctionOrderDetails["+lastIndex+"].rid' id='grSanctionOrderDetails"+lastIndex+".rid' value='null'/>"
					+"<input type='hidden' id='grSanctionOrderDetails"+lastIndex+".sanOrdDtlRid' name='grSanctionOrderDetails["+lastIndex+"].sanOrdDtlRid' value='null'/>" 
				+"</td>"
				+"<td>"
					+"<select autofocus id='grSanctionOrderDetails"+lastIndex+".orderProgramName' name='grSanctionOrderDetails["+lastIndex+"].orderProgramName' class='form-control'>"+proNameOption+"</select>"
					+"<form:errors name='grSanctionOrderDetails["+lastIndex+"].orderProgramName' cssClass='error' element='div'/>"	
				+"</td>"
				+"<td>"
					+"<div class='input-group date'  data-provide='datepicker' data-date-format='dd/mm/yyyy'>"
						+"<input type='text' name='grSanctionOrderDetails["+lastIndex+"].orderDate' id='grSanctionOrderDetails"+lastIndex+".orderDate' value='' class='form-control' placeholder='dd/mm/yyyy' />"
						+"<div class='input-group-addon'>"
                 		+"<i class='fa fa-calendar' aria-hidden='true'></i>"
                 	+"</div>"
					+"<form:errors path='grSanctionOrderDetails["+lastIndex+"].orderDate' cssClass='error' element='div'/>"
				+"</td>"					
				+"<td>"
					+"<input name='grSanctionOrderDetails["+lastIndex+"].orderNumber' id='grSanctionOrderDetails"+lastIndex+".orderNumber' class='form-control'/>" 
					+"<form:errors path='grSanctionOrderDetails["+lastIndex+"].orderNumber' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<select name='grSanctionOrderDetails["+lastIndex+"].orderCategory' id='grSanctionOrderDetails"+lastIndex+".orderCategory' class='form-control' >"+categorieOption+"</select>"
					+"<form:errors path='grSanctionOrderDetails["+lastIndex+"].orderCategory' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<select name='grSanctionOrderDetails["+lastIndex+"].share' id='grSanctionOrderDetails"+lastIndex+".share' onchange='openSanctionOrd(this);' curObjIndex='"+lastIndex+"' class='form-control'>"+shareTypeOption+"</select>"
					+"<form:errors path='grSanctionOrderDetails["+lastIndex+"].share' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' name='grSanctionOrderDetails["+lastIndex+"].sanOrdDtlNumber' id='grSanctionOrderDetails"+lastIndex+".sanOrdDtlNumber' class='form-control' readonly='readonly'/>"
					+"<form:errors path='grSanctionOrderDetails["+lastIndex+"].sanOrdDtlNumber' cssClass='error' element='div'/>"
				+"</td>"					
				+"<td>"
					+"<input type='number' value='0.00' name='grSanctionOrderDetails["+lastIndex+"].orderAmt' id='grSanctionOrderDetails"+lastIndex+".orderAmt' onblur='amtConvertInLakh("+lastIndex+");' class='form-control' step='.01'/>"
					+"<form:errors path='grSanctionOrderDetails["+lastIndex+"].orderAmt' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' value='0.00' name='grSanctionOrderDetails["+lastIndex+"].orderAmtInLakh' id='grSanctionOrderDetails"+lastIndex+".orderAmtInLakh' class='form-control' readonly='readonly'/>"
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
                }
            });
            calTotAmt();
        });
		
        /* $("#depositeDate").datepicker({
            dateFormat:"dd/mm/yy"
         });
		
        $(function() {
        	$('body').on('click','.input_date', function() {
    	    	$(this).datepicker({
    	            dateFormat:"dd/mm/yy"
    	         });
    	    });
    	}); */
        
        /*
        *	This event file when click on selected button
        *	of modal a viewSantionOrderModal
        */
        $("#btnSelected").click(function(event) {
        	$("#viewSantionOrderModal .modal-body").find('input[name="selected_record"]').each(function(){
            	if($(this).is(":checked")){
            		var indexId = $(this).attr("id");
            		var rid = document.getElementById("sanOrdDtl_"+indexId+"_rid").value;
            		var amt = parseInt(document.getElementById("sanOrdDtl_"+indexId+"_amt").value);
            		var sanOrdNo=document.getElementById("sanOrdDtl_"+indexId+"_orderNumber").value;
            		var ordAmtElement = document.getElementById("grSanctionOrderDetails"+curObjIndexOfShareType+".orderAmt");
        		    ordAmtElement.value =amt;
        		    $("#selectedSanOrdAmt_"+curObjIndexOfShareType).val(amt);
        		    var sanOrdDtlRidElement = document.getElementById("grSanctionOrderDetails"+curObjIndexOfShareType+".sanOrdDtlRid");
        		    sanOrdDtlRidElement.value=rid;
        		    var sanOrdDtlRidElement = document.getElementById("grSanctionOrderDetails"+curObjIndexOfShareType+".sanOrdDtlNumber");
        		    sanOrdDtlRidElement.value=sanOrdNo;
                    $("#viewSantionOrderModal .close").click();
                    ordAmtElement.focus();
                    ordAmtElement.select();
                }
            });
		});//End btnSelected.
		
        /**
    	* This function calculate the amt in lakh for Bank field.
    	*/
    	$("#bankAmt").blur(function() {
    		var amt = parseInt($(this).val());
    		var totInLack = (amt / 100000);
    		$("#bankAmtInLakh").val(totInLack);
    	});//End bankAmt
    	
    	/*
         *	This event fire when click on selected button
         *	of modal a viewSantionOrderModal
         */
         $("#confirmOkBtn").click(function(event) {
        	$("#viewSantionOrderModal .close").click();
 		});//End btnSelected.
 		
 
 		/**
    	* This function upload the file
    	*/
    	$("#btnSubmit").click(function(event) {
    		fileUploadApp("viewGrSanctionOrderPDFFile");
    	});
    	//End upload file function
    	
	});
	
	function onFocusInOrderDate(curObj){
		
	}
	 
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
	
	/**
	* This function calculate the amt in lakh for Gr Sanction Order Amt field.
	*/	
	function amtConvertInLakh(indexOfObj) {
		console.log("amtConvertInLakh :: indexOf["+indexOfObj+"]");
		var amtInRsElement = document.getElementById("grSanctionOrderDetails"+ indexOfObj + ".orderAmt");
		var amtInLakh = document.getElementById("grSanctionOrderDetails"+ indexOfObj + ".orderAmtInLakh");
		var selectedSanOrdAmt = $("#selectedSanOrdAmt_"+indexOfObj).val();
		if(amtInRsElement.value > selectedSanOrdAmt ){
			//alert("Entered amount is greater than selected amount. <br> Selected amount is ["+selectedSanOrdAmt+"] and enter amount is ["+amtInRsElement.value+"]");
			var msg = "<strong>Oh snap!</strong> Entered amount is greater than selected amount. <br> Selected amount is ["+selectedSanOrdAmt+"] and enter amount is ["+amtInRsElement.value+"]";
			$("#ErrorMessage").empty();
			$('#ErrorMessage').append(msg);
			$('#ErrorMessage').show();
			amtInRsElement.focus();
			amtInRsElement.select();
		} else {
			$('#ErrorMessage').hide();
			var amtInRs = Number.parseFloat(amtInRsElement.value).toFixed(2);
			var totInLack = (amtInRs / 100000);
			if (amtInLakh != 'undefine' && amtInLakh != null) {
				amtInLakh.value = totInLack.toFixed(2);
			}
			amtInRsElement.value=amtInRs;
			calTotAmt();	
		}
		
	}
	
	function calTotAmt() {
		var totAmt = 0;
		for (var i = 0; i <=lastIndex; i++) {
			var orderAmt = document.getElementById("grSanctionOrderDetails"+ i + ".orderAmt");
			if(orderAmt!= null && orderAmt != 'undefine'){
				totAmt = totAmt + Number.parseFloat(orderAmt.value);
			}
		}
		document.getElementById("grSanctionOrder.totAmount").value=totAmt;
	}
	
	/**
	* This function fetch the Sanction Order using Ajax call
	* with provided param and show the popup dialog.
	* 1. year
	* 2. orderProgramName
	* 3. orderCategory
	* 4. orderShareType
	*/
	function openSanctionOrd(curObj){
		var selectedVal = $(curObj).find('option:selected').val();
		if( selectedVal != "NONE" ){
			curObjIndexOfShareType = $(curObj).attr('curObjIndex');
			var elmntProg = document.getElementById("grSanctionOrderDetails"+curObjIndexOfShareType+".orderProgramName");
			var progVal = elmntProg.options[elmntProg.selectedIndex].value; 
			var elmntCategory = document.getElementById("grSanctionOrderDetails"+curObjIndexOfShareType+".orderCategory");
			var categoryVal = elmntCategory.options[elmntCategory.selectedIndex].value;
			//var rid = document.getElementById("grSanctionOrderDetails["+curObjIndex+"].rid").value;
			var rid = "";
			var year = $("#year option:selected").val();
			requestDatas = "rid="+rid+"&programName="+encodeURIComponent(progVal)+"&category="+categoryVal+"&shareType="+selectedVal+"&year="+year+"&rand="+Math.random(); 
			try{
				
				$('#viewSantionOrderModal').modal({
		       		keyboard:true,
		         	show:true,
		         	backdrop:'static'
		        });
			}catch(e){
				alert(e.message);
			}
			var modal1 = $('#viewSantionOrderModal');
			// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  	$.ajax({
      		  type: "POST",
	          url: "getSanctionOrderDetails?"+requestDatas,
	          cache: false,
	          timeout: 600000,
	          success: function (data) {
	        	  modal1.find('.modal-body div').html("");
	        	  modal1.find('.modal-body div').html(data);
	          },
	          error: function (e) {
	          	console.log("ERROR : ", e);
	          }
        	});// end ajax event.
		}
	}
	
</script>
<!-- <style type="text/css">
.error {
	color: red;
	font-weight: bold;
}
.modal-lg {
    max-width: 90% !important;
}
</style> -->
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-header"><i class="fa fa-file-text-o"></i> GR Form</h3>
			</div>
		</div>
		<section class="panel" >
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Add GR Details</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div class="alert alert-danger" role="alert" id="ErrorMessage" style="display: none;">
				  	
				</div>
				<div class="form">
				<form:form action="saveGrSanctionOrder" method="post" modelAttribute="grSanctionOrder"> 
					<script>
						var requestData='';
						var programName = '<c:out value="${programName}"/>'; 
						programName = programName.substring(1, (programName.length) - 1);
						var categories = '<c:out value="${categories}"/>';
						categories = categories.substring(1, (categories.length) - 1);
						var shareType = '<c:out value="${share}"/>';
						shareType = shareType.substring(1, (shareType.length) - 1);
					</script>
					<input type="hidden" name="formMode" id="formMode" value="${formMode}" />
					<input type="hidden" name="sanOrdDtlRidsDeleted" id="sanOrdDtlRidsDeleted" >
					<div class="row form-group">	<!-- 1fst Row -->
					 	<div class="col-md-3">
					 		<div class="form-group">
								<label for="year" class="control-label">Year <span class="required">*</span></label>
								<form:hidden path="rid" value="${rid}" /> 
								<form:select path="year" id="year" class="form-control">
									<form:option value="NONE" label="Select" />
									<form:options items="${sinalcialYears}" />
								</form:select> 
								<form:errors path="year" cssClass="error" element="div" />
							</div>
						</div>
						<div class="col-md-3">
					 		<div class="form-group">
								<label for="programGroupName" class="control-label">Program Group Name <span class="required">*</span></label>
								<form:select path="programGroupName" class="form-control">
									<form:option value="NONE" label="Select" />
									<form:options items="${programGroups}" />
								</form:select> <form:errors path="programGroupName" cssClass="error"
									element="div" />
							</div>
						</div>
						<div class="col-md-3">
					 		<div class="form-group">
					 			<div class="row">
									<c:choose>
										<c:when test="${ not empty grSanctionOrder.documentEntity.fileName}">
											<spring:url value="viewGrSanctionOrderPDFFile/${grSanctionOrder.documentEntity.rid}_${grSanctionOrder.documentEntity.fileName}" 
														var="viewPDFUrl"></spring:url>
											<a id="fileName" href="javascript:viewFile('${viewPDFUrl}')"
												style="display: inline-block;margin-bottom: 5px;">
												<span>${grSanctionOrder.documentEntity.fileName}</span>
											</a>
										</c:when>
										<c:otherwise>
											<a id="fileName" href="#" style="display: inline-block;margin-bottom: 5px;">
											<span>No File Attached</span>
											</a>
										</c:otherwise>
									</c:choose>
									<form:hidden path="documentEntity.rid" id="documentEntityRid" value="${grSanctionOrder.documentEntity.rid}" /> 
									<form:hidden path="documentEntity.fileName" id="documentEntityFileName" value="${grSanctionOrder.documentEntity.fileName}" />
								</div>
								 <!-- Button to Open the Modal -->
								 <div class="row">
									<button type="button" class="btn btn-default" data-toggle="modal" data-target="#fileUploadModal" id="fileName1"> 
											<i class="fa fa-paperclip" aria-hidden="true"></i> File Attach
									</button>
								</div>
							</div>
						</div>
						<div class="col-md-3">
					 		<div class="form-group">
								<label for="sanctionOrder.totAmount" class="control-label">Total Amount</label>
								<input type ="text" class="form-control" name="totAmount" id="grSanctionOrder.totAmount" readonly="readonly" value="${grSanctionOrder.totAmount}"/>
							</div>
						</div>
					</div> <!-- 1fst Row end -->
					<div class="row form-group"> <!-- 2nd row start -->
						<table id="SanOrdDtlTbl" class="table table-sm">
							<thead>
							<tr>
								<th style="width:1%"><button id="addRow" class="btn btn-default"><i class="fa fa-plus-square-o" aria-hidden="true"></i></button></th>
								<th style="width:16%">Program Name <span class="required">*</span></th>
								<th style="width:14%">GR Order Date(dd/mm/yyyy) <span class="required">*</span></th>
								<th style="width:15%">Gr Order No. <span class="required">*</span></th>
								<th style="width:9%">Category <span class="required">*</span></th>
								<th style="width:10%">Share <span class="required">*</span></th>
								<th style="width:12%">Sanction Order No. <span class="required">*</span></th>
								<th style="width:14%">Amt In Rs. <span class="required">*</span></th>
								<th style="width:15%"><span>Amt In Lakh</span></th>
							</tr>
							</thead>
							<tfoot>
								<tr>
									<td colspan="9" >
										<button type="button" id="delete-row" class="delete-row btn btn-default">
											<i class="fa fa-trash-o" aria-hidden="true"></i>
										</button>
									</td>
								</tr>
							</tfoot>
							<tbody>
									<c:forEach items="${grSanctionOrder.grSanctionOrderDetails}"
										var="orderDetail" varStatus="status">
										<tr>
											<td><input type="hidden"
												name="grSanctionOrderDetails[${status.index}].rid"
												value="${orderDetail.rid}"
												id="grSanctionOrderDetails${status.index}.rid" />
												
												<input type="hidden"
												name="grSanctionOrderDetails[${status.index}].sanOrdDtlRid"
												value="${orderDetail.sanOrdDtlRid}" 
												id="grSanctionOrderDetails${status.index}.sanOrdDtlRid" />
												
												<input type="hidden" value="${orderDetail.orderAmt}" 
													id="selectedSanOrdAmt_${status.index}" />
												
												<!-- This lastIndex var use for add new row  -->
												<script>
													var lastIndex = '<c:out value="${status.index}"/>'; 
												</script>
												
												<c:if test="${ status.index > 0 }">
													<input type='checkbox' name='record' id="${orderDetail.rid}"/>
												</c:if></td>
												
											<td>
												<form:select path="grSanctionOrderDetails[${status.index}].orderProgramName" 
													id="grSanctionOrderDetails${status.index}.orderProgramName" class="form-control">
													<form:option value="NONE" label="Select" selected="selected" />
													<form:options items="${programName}" />
												</form:select>
												<form:errors path="grSanctionOrderDetails[${status.index}].orderProgramName" cssClass="error" element="div" />
											</td>
											<td>
												<div class="input-group date"  data-provide="datepicker" data-date-format="dd/mm/yyyy">
													<form:input	path="grSanctionOrderDetails[${status.index}].orderDate" value="" class="form-control" placeholder="dd/mm/yyyy"/> 
													<div class="input-group-addon">
								                 		<i class="fa fa-calendar" aria-hidden="true"></i>
								                 	</div>
												</div>
												<form:errors path="grSanctionOrderDetails[${status.index}].orderDate" cssClass="error" element="div" />
												<%-- id="grSanctionOrderDetails${status.index}.orderDate" --%> 
											</td>
											<td><form:input
													path="grSanctionOrderDetails[${status.index}].orderNumber"
													value="${orderDetail.orderNumber}"
													id="grSanctionOrderDetails${status.index}.orderNumber" class="form-control"/> <form:errors
													path="grSanctionOrderDetails[${status.index}].orderNumber"
													cssClass="error" element="div" /></td>
											<td><form:select
													path="grSanctionOrderDetails[${status.index}].orderCategory"
													id="grSanctionOrderDetails${status.index}.orderCategory" class="form-control">
													<form:option value="NONE" label="Select" />
													<form:options items="${categories}" />
												</form:select> <form:errors
													path="grSanctionOrderDetails[${status.index}].orderCategory"
													id="grSanctionOrderDetails${status.index}.orderCategory"
													cssClass="error" element="div" /></td>
											<td><form:select
													path="grSanctionOrderDetails[${status.index}].share" onchange="openSanctionOrd(this);"
													id="grSanctionOrderDetails${status.index}.share" 
													curObjIndex="${status.index}" class="form-control">
													<form:option value="NONE" label="Select" />
													<form:options items="${share}" />
												</form:select> <form:errors
													path="grSanctionOrderDetails[${status.index}].share"
													cssClass="error" element="div" /></td>
											<td>
												<input type="text"
													name="grSanctionOrderDetails[${status.index}].sanOrdDtlNumber"
													id="grSanctionOrderDetails${status.index}.sanOrdDtlNumber" 
													value="${orderDetail.sanOrdDtlNumber}" readonly="readonly" class="form-control"/>
												<form:errors
													path="grSanctionOrderDetails[${status.index}].sanOrdDtlNumber"
													cssClass="error" element="div" />
											</td>
											<td><form:input
													path="grSanctionOrderDetails[${status.index}].orderAmt"
													id="grSanctionOrderDetails${status.index}.orderAmt"
													type="number" value="${orderDetail.orderAmt}"
													onblur="amtConvertInLakh('${status.index}')" class="form-control" step=".01"/> 
													<form:errors
													path="grSanctionOrderDetails[${status.index}].orderAmt"
													cssClass="error" element="div" /></td>
											<td><input type="text" name="grSanctionOrderDetails[${status.index}].orderAmtInLakh" 
											     id="grSanctionOrderDetails${status.index}.orderAmtInLakh" value="${orderDetail.orderAmtInLakh}" class="form-control" readonly/>
											</td>
										</tr>
									</c:forEach>	
								</tbody>			
							</table>
						</div> <!-- 2nd row end -->
						<div class="row form-group"> <!-- 3rd row start -->
							<div class="col-md-9"></div>
						 	<div class="col-md-2">
								 <a href="cancelGrSanctionOrder" class="btn btn-link pull-right">Cancel</a>
							</div>
							<div class="col-md-1">
								<button type="submit" class="btn btn-primary pull-right" value="Save"><i class="fa fa-floppy-o" aria-hidden="true"></i>  Save </button>
							</div>
						</div> <!-- 3rd row start -->
				</form:form>
				</div>
			</div>
			<div>
				<!-- The Modal for upload file-->
				<div class="modal fade" id="fileUploadModal" >
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
				
				<!-- The viewSantionOrderModal -->
				<div class="modal fade" id="viewSantionOrderModal">
					<div class="modal-dialog modal-lg" >
						<div class="modal-content"  >
							<!-- Modal Header -->
							<div class="modal-header">
								<button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
								<h4 class="modal-title">Sanction Order selection</h4>
							</div>
	
							<!-- Modal body -->
							<div class="modal-body">
								<div id="content"> Loading... </div>
							</div>
	
							<!-- Modal footer -->
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
								<button id="btnSelected" class="btn btn-primary">Selected</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
	</div>
</body>
</html>