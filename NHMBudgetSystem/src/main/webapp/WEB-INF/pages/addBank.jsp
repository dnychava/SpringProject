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
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script> -->

<spring:url value="/resources/app/css/main.css" var="mainCss" />
<spring:url value="/resources/app/js/comman.js" var="comman" />
<spring:url value="/resources/app/js/appFileUpload.js" var="fileUpload" />

<link href="${mainCss}" rel="stylesheet">
<script src="${comman}"></script>
<script src="${fileUpload}"></script>
<style type="text/css">
	
</style>


<script type="text/javascript">
	//This variable hold the current index when onblur event fired 
	//of selection share type combo box(Elements);
	var curObjIndexOfShareType;
	
	$(document).ready(function(event) {
		 //Adding new row when clicked on 'Add(+)' button.
		$("#addRow").click(function(event) {
			console.log("addRow");
			event.preventDefault();
			var proNameOption = createOption(programName);
			var categorieOption = createOption(categories);
			var shareTypeOption = createOption(shareType);
			lastIndex++;
			//console.log("lastIndex["+lastIndex+"]");
			var newRow = "<tr>"
				+"<td>"
					+"<input type='checkbox' name='record'/> <input type='hidden' name='bankDetails["+lastIndex+"].rid' id='bankDetails"+lastIndex+".rid' value='null'/>"
					+"<input type='hidden' id='bankDetails"+lastIndex+".grSanOrdDtlRid' name='bankDetails["+lastIndex+"].grSanOrdDtlRid' value='null'/>" 
				+"</td>"
				+"<td>"
					+"<select autofocus id='bankDetails"+lastIndex+".grProgramName' name='bankDetails["+lastIndex+"].grProgramName' class='form-control'>"+proNameOption+"</select>"
					+"<form:errors name='bankDetails["+lastIndex+"].grProgramName' cssClass='error' element='div'/>"	
				+"</td>"
				+"<td>"
					+"<div class='input-group date'  data-provide='datepicker' data-date-format='dd/mm/yyyy'>"
						+"<input type='text' name='bankDetails["+lastIndex+"].depositDate' id='bankDetails"+lastIndex+".depositDate' value='' class='form-control' placeholder='dd/mm/yyyy'/>"
						+"<div class='input-group-addon'>"
                 			+"<i class='fa fa-calendar' aria-hidden='true'></i>"
                 		+"</div>"
					+"</div>"
					+"<form:errors path='bankDetails["+lastIndex+"].depositDate' cssClass='error' element='div'/>"
				+"</td>"					
				+"<td>"
					+"<select name='bankDetails["+lastIndex+"].category' id='bankDetails"+lastIndex+".category' class='form-control' >"+categorieOption+"</select>"
					+"<form:errors path='bankDetails["+lastIndex+"].category' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<select name='bankDetails["+lastIndex+"].share' id='bankDetails"+lastIndex+".share' onchange='openSanctionOrd(this);' curObjIndex='"+lastIndex+"' class='form-control'>"+shareTypeOption+"</select>"
					+"<form:errors path='bankDetails["+lastIndex+"].share' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' name='bankDetails["+lastIndex+"].grSanOrdDtlNumber' id='bankDetails"+lastIndex+".grSanOrdDtlNumber' class='form-control' readonly />"
					+"<form:errors path='bankDetails["+lastIndex+"].grSanOrdDtlNumber' cssClass='error' element='div'/>"
				+"</td>"					
				+"<td>"
					+"<input type='number' value='0.00' name='bankDetails["+lastIndex+"].amt' id='bankDetails"+lastIndex+".amt' onblur='amtConvertInLakh("+lastIndex+");' class='form-control' step='.01' />"
					+"<form:errors path='bankDetails["+lastIndex+"].amt' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' value='0.00' name='bankDetails["+lastIndex+"].amtInLakh' id='bankDetails"+lastIndex+".amtInLakh' class='form-control' readonly/>"
				+"</td>"
				+"</tr>";
			$("#SanOrdDtlTbl tbody").append(newRow);
		});		
		 
		// Find and remove selected table rows
        $("#delete-row").click(function(){
            $("table tbody").find('input[name="record"]').each(function(){
            	if($(this).is(":checked")){
            		var grSanOrdDtlRid = $("#grSanOrdDtlRidsDeleted").val();
            		//console.log("==B4==grSanOrdDtlRid["+grSanOrdDtlRid+"]");
            		$("#grSanOrdDtlRidsDeleted").val(grSanOrdDtlRid+"~~"+$(this).attr("id"));
            		//console.log("==after==grSanOrdDtlRid["+$("#sanOrdDtlRidsDeleted").val()+"]");
                    $(this).parents("tr").remove();
                }
            });
            calTotAmt();
        });
		
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
            		var ordAmtElement = document.getElementById("bankDetails"+curObjIndexOfShareType+".amt");
        		    ordAmtElement.value =amt;
        		    
        		    var grSanOrdDtlRidElement = document.getElementById("bankDetails"+curObjIndexOfShareType+".grSanOrdDtlRid");
        		    grSanOrdDtlRidElement.value=rid;
        		    var sanOrdDtlRidElement = document.getElementById("bankDetails"+curObjIndexOfShareType+".grSanOrdDtlNumber");
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
    	
    	/**
    	* This function upload the file
    	*/
    	$("#btnSubmit").click(function(event) {
    		fileUploadApp("viewBankPDFFile");
    	});
    	//End upload file function
    	
	});
	
	function onFocusInDepositDate(curObj){
		
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
		var amtInRsElement = document.getElementById("bankDetails"+ indexOfObj + ".amt");
		var amtInLakh = document.getElementById("bankDetails"+ indexOfObj + ".amtInLakh");
		var amtInRs = Number.parseFloat(amtInRsElement.value).toFixed(2);
		var totInLack = ( amtInRs / 100000);
		if (amtInLakh != 'undefine' && amtInLakh != null) {
			amtInLakh.value = totInLack.toFixed(2);
		}
		amtInRsElement.value = amtInRs;
		calTotAmt();
	}
	
	function calTotAmt() {
		var totAmt = 0;
		for (var i = 0; i <=lastIndex; i++) {
			var amt = document.getElementById("bankDetails"+ i + ".amt");
			if(amt!= null && amt != 'undefine'){
				totAmt = totAmt + Number.parseFloat(amt.value);
			}
		}
		document.getElementById("bank.totAmount").value=totAmt;
	}
	
	/**
	* This function fetch the Sanction Order using Ajax call
	* with provided param and show the popup dialog.
	* 1. grProgramName
	* 2. category
	* 1. orderShareType
	*/
	function openSanctionOrd(curObj){
		var selectedVal = $(curObj).find('option:selected').val();
		if( selectedVal != "NONE" ){
			curObjIndexOfShareType = $(curObj).attr('curObjIndex');
			var elmntProg = document.getElementById("bankDetails"+curObjIndexOfShareType+".grProgramName");
			var progVal = elmntProg.options[elmntProg.selectedIndex].value; 
			var elmntCategory = document.getElementById("bankDetails"+curObjIndexOfShareType+".category");
			var categoryVal = elmntCategory.options[elmntCategory.selectedIndex].value;
			//var rid = document.getElementById("bankDetails["+curObjIndex+"].rid").value;
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
				alert("exception while setting the modal properies in addBank.jsp ["+e.message+"]");
			}
			var modal1 = $('#viewSantionOrderModal');
			// If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
		  	$.ajax({
      		  type: "POST",
	          url: "getGRSanctionOrderDetails?"+requestDatas,
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
<style type="text/css">
.error {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body onclick="false">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-12">
				<h3 class="page-header"><i class="fa fa-file-text-o"></i> Bank Form</h3>
			</div>
		</div>
	<section class="panel" >
				<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Add Bank Details</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div class="form">
					<form:form action="saveBank" method="post" modelAttribute="bank">
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
					<input type="hidden" name="grSanOrdDtlRidsDeleted" id="grSanOrdDtlRidsDeleted" >
					<div class="row form-group">	<!-- 1fst row start -->
					 	<div class="col-md-3">
					 		<div class="form-group">
					 			<label for="year" class="control-label">Year <span class="required">*</span></label>
								<form:select class="form-control" path="year" id="year">
									<form:option value="NONE" label="Select" />
									<form:options items="${sinalcialYears}" />
								</form:select> 
								<form:errors path="year" cssClass="error" element="div" />
							</div>
						</div>
					 	<div class="col-md-3">
					 		<div class="form-group">
					 			<label for="programGroupName" class="control-label">Bank Name <span class="required">*</span></label>
								<form:hidden path="rid" value="${bank.rid}" />
									<c:choose>
									<c:when test="${bank.name == null || bank.name == '' }">
										<form:input class="form-control" path="name" value="ICICI Bank"/>
									</c:when>
									<c:otherwise>
										<form:input class="form-control" path="name" value="${bank.name}"/>
									</c:otherwise>
								</c:choose>
								<form:errors path="name" cssClass="error" element="div" />
							</div>
						</div>
						<div class="col-md-3">
					 		<div class="form-group">
					 			<div class="row" >
									<c:choose>
											<c:when test="${ not empty bank.documentEntity.fileName}">
												<spring:url value="viewBankPDFFile/${bank.documentEntity.rid}_${bank.documentEntity.fileName}" 
														var="viewPDFUrl"></spring:url>
												<a id="fileName" href="javascript:viewFile('${viewPDFUrl}')" 
													style="display: inline-block;margin-bottom: 5px;">
													<span>${bank.documentEntity.fileName}</span>
												</a>
											</c:when>
											<c:otherwise>
												<a id="fileName" href="#" style="display: inline-block;margin-bottom: 5px;">
													<span>No File Attached</span>
												</a>
											</c:otherwise>
									</c:choose>
									<form:hidden path="documentEntity.rid"
											id="documentEntityRid"
											value="${bank.documentEntity.rid}" /> <form:hidden
											path="documentEntity.fileName" id="documentEntityFileName"
											value="${bank.documentEntity.fileName}" /> 
								</div>
								<div class="row">
									<button type="button" class="btn btn-default" data-toggle="modal" data-target="#fileUploadModal">
										<i class="fa fa-paperclip" aria-hidden="true"></i>  File Attach
									</button>
								</div>
							</div>
						 </div>
						<div class="col-md-3">
							<div class="form-group">
								<label for="sanctionOrder.totAmount" class="control-label">Total Amount</label>
								<input type ="text" name="totAmount" id="bank.totAmount" class="form-control" readonly value="${bank.totAmount}"/>
							</div>
						</div>
					</div> <!-- 1fst row end-->
					<div class="row form-group"><!-- 2nd row start-->
						<table id="SanOrdDtlTbl" class="table table-sm">
							<thead>
							 <tr>
								<th style="width:3%"><button id="addRow" class="btn btn-default"><i class="fa fa-plus-square-o" aria-hidden="true"></i></button></th>
								<th style="width:14%">Program Name <span class="required">*</span></th>
								<th style="width:5%">Deposit Date(dd/mm/yyyy) <span class="required">*</span></th>
								<th style="width:8%">Category <span class="required">*</span></th>
								<th style="width:9%">Share <span class="required">*</span></th>
								<th style="width:22%">Gr Order No. <span class="required">*</span></th>
								<th style="width:12%">Amt In Rs. <span class="required">*</span></th>
								<th style="width:9%"><span>Amt In Lakh</span></th>
							</tr>
							</thead>
							<tfoot>
								<tr>
									<td colspan="8" >
										<button type="button" id="delete-row" class="delete-row btn btn-default">
											<i class="fa fa-trash-o" aria-hidden="true"></i>
										</button>
									</td>
								</tr>
							</tfoot>
							<tbody>
							<c:forEach items="${bank.bankDetails}"
								var="bankDetail" varStatus="status">
								<tr>
									<td><input type="hidden"
										name="bankDetails[${status.index}].rid"
										value="${bankDetail.rid}"
										id="bankDetails${status.index}.rid" />
										
										<input type="hidden"
										name="bankDetails[${status.index}].grSanOrdDtlRid"
										value="${bankDetail.grSanOrdDtlRid}"
										id="bankDetails${status.index}.grSanOrdDtlRid" />
										
										<!-- This lastIndex var use for add new row  -->
										<script>
											var lastIndex = '<c:out value="${status.index}"/>'; 
										</script>
										
										<c:if test="${ status.index > 0 }">
											<input type='checkbox' name='record' id="${bankDetail.rid}"/>
										</c:if></td>
										<td><form:select path="bankDetails[${status.index}].grProgramName" id="bankDetails${status.index}.grProgramName" class="form-control">
												<form:option value="NONE" label="Select" selected="selected" />
												<form:options items="${programName}" />
											</form:select> <form:errors
												path="bankDetails[${status.index}].grProgramName"
												cssClass="error" element="div" /></td>
										<td>
											<div class="input-group date"  data-provide="datepicker" data-date-format="dd/mm/yyyy">
												<form:input path="bankDetails[${status.index}].depositDate"	value="" class="form-control" placeholder="dd/mm/yyyy"/>
												<div class="input-group-addon">
							                 		<i class="fa fa-calendar" aria-hidden="true"></i>
							                 	</div>
											</div>
											<form:errors path="bankDetails[${status.index}].depositDate" cssClass="error" element="div" />
										</td>
										<td><form:select path="bankDetails[${status.index}].category" id="bankDetails${status.index}.category" class="form-control">
												<form:option value="NONE" label="Select" />
												<form:options items="${categories}" />
											</form:select> <form:errors
												path="bankDetails[${status.index}].category"
												id="bankDetails${status.index}.category"
												cssClass="error" element="div" /></td>
										<td><form:select path="bankDetails[${status.index}].share" onchange="openSanctionOrd(this);" id="bankDetails${status.index}.share" 
												curObjIndex="${status.index}" class="form-control">
												<form:option value="NONE" label="Select" />
												<form:options items="${share}" />
											</form:select> <form:errors
												path="bankDetails[${status.index}].share"
												cssClass="error" element="div" /></td>
										<td>
											<input type="text"
												name="bankDetails[${status.index}].grSanOrdDtlNumber"
												id="bankDetails${status.index}.grSanOrdDtlNumber" 
												value="${bankDetail.grSanOrdDtlNumber}" class="form-control" readonly/>
											<form:errors
												path="bankDetails[${status.index}].grSanOrdDtlNumber"
												cssClass="error" element="div" />
										</td>
										<td><form:input
												path="bankDetails[${status.index}].amt"
												id="bankDetails${status.index}.amt"
												type="number" value="${bankDetail.amt}"
												onblur="amtConvertInLakh('${status.index}')" class="form-control" step=".01" /> 
												<form:errors path="bankDetails[${status.index}].amt" cssClass="error" element="div" /></td>
										<td><input type="text"
											name="bankDetails[${status.index}].amtInLakh"
											id="bankDetails${status.index}.amtInLakh"
											value="${bankDetail.amtInLakh}"  
											onkeypress="$('#addRow').click();" class="form-control" readonly/></td>
									</tr>
							</c:forEach>
							</tbody>
						</table>
					</div><!-- 2nd row end-->
					<div class="row form-group"> <!-- 3rd row start-->	
						<div class="col-md-9"></div>
					 	<div class="col-md-2">
								<a href="cancelBank" class="btn btn-link pull-right">Cancel</a>
						</div>
						<div class="col-md-1">
							<button type="submit" class="btn btn-primary pull-right" value="Save">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> Save </button>
						</div>
					</div> <!-- 3rd row end-->
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
			<div class="modal fade" id="viewSantionOrderModal"  >
				<div class="modal-dialog modal-lg" role="document" >
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<button aria-hidden="true" type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">GR Selection</h4>
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