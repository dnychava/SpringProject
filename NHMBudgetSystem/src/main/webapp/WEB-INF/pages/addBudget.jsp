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
			var seasonTypesOption = createOption(seasonTypes);
			var categorieOption = createOption(categories);
			var shareTypeOption = createOption(shareType);
			lastIndex++;
			var newRow = "<tr>"
				+"<td>"
					+"<input type='checkbox' name='record'/>"
					+"<input type='hidden' name='budgetDetails["+lastIndex+"].rid' id='budgetDetails_"+lastIndex+"_rid' value='null'/>"
					+"<input type='hidden' id='budgetDetails_"+lastIndex+"_mstrPrgrNameEnitityImpl_rid' name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.rid' value='null'/>" 
				+"</td>"
				+"<td>"
					+"<select autofocus id='budgetDetails_"+lastIndex+"_mstrPrgrNameEnitityImpl_shareType' name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.shareType' class='form-control'>"+shareTypeOption+"</select>"
					+"<form:errors name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.shareType' cssClass='error' element='div'/>"	
				+"</td>"
				+"<td>"
					+"<select name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.category' id='budgetDetails_"+lastIndex+"_mstrPrgrNameEnitityImpl_category' onchange='openMasterBudget(this);' curObjIndex='"+lastIndex+"' class='form-control' >"+categorieOption+"</select>"
					+"<form:errors path='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.category' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.accountHead' id='budgetDetails_"+lastIndex+"_mstrPrgrNameEnitityImpl_accountHead' class='form-control' readonly />"
					+"<form:errors path='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.accountHead' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.inEng' id='budgetDetails_"+lastIndex+"_mstrPrgrNameEnitityImpl_inEng' class='form-control' readonly />"
					+"<form:errors path='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.inEng' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' name='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.inShort' id='budgetDetails_"+lastIndex+"_mstrPrgrNameEnitityImpl_inShort' class='form-control' readonly />"
					+"<form:errors path='budgetDetails["+lastIndex+"].mstrPrgrNameEnitityImpl.inShort' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<select autofocus id='budgetDetails_"+lastIndex+"_seasionType' name='budgetDetails["+lastIndex+"].seasionType' class='form-control'>"+seasonTypesOption+"</select>"
					+"<form:errors name='budgetDetails["+lastIndex+"].seasionType' cssClass='error' element='div'/>"	
				+"</td>"
				+"<td>"
					+"<input type='number' value='0.00' name='budgetDetails["+lastIndex+"].amtInLakh' id='budgetDetails_"+lastIndex+"_amtInLakh' onblur='calTotAmt(this);' class='form-control' step='.01' />"
					+"<form:errors path='budgetDetails["+lastIndex+"].amtInLakh' cssClass='error' element='div'/>"
				+"</td>"
				+"</tr>";
			$("#SanOrdDtlTbl tbody").append(newRow);
		});		
		 
		// Find and remove selected table rows
        $("#delete-row").click(function(){
            $("table tbody").find('input[name="record"]').each(function(){
            	if($(this).is(":checked")){
            		var budgetDtlRid = $("#budgetDtlRidsDeleted").val();
            		//console.log("==B4==grSanOrdDtlRid["+grSanOrdDtlRid+"]");
            		$("#budgetDtlRidsDeleted").val(budgetDtlRid+"~~"+$(this).attr("id"));
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
            		var rid = document.getElementById("mstrBudget_"+indexId+"_rid").value;
            		var mstrBudgetInEng = document.getElementById("mstrBudget_"+indexId+"_inEng").value;
            		var mstrBudgetInShort = document.getElementById("mstrBudget_"+indexId+"_inShort").value;
            		var mstrBudgetAccountHead = document.getElementById("mstrBudget_"+indexId+"_accountHead").value;
            		
        		    $("#"+"budgetDetails_"+curObjIndexOfShareType+"_mstrPrgrNameEnitityImpl_rid").val(rid);
        		    $("#"+"budgetDetails_"+curObjIndexOfShareType+"_mstrPrgrNameEnitityImpl_inEng").val(mstrBudgetInEng);
        		    $("#"+"budgetDetails_"+curObjIndexOfShareType+"_mstrPrgrNameEnitityImpl_inShort").val(mstrBudgetInShort);
        		    $("#"+"budgetDetails_"+curObjIndexOfShareType+"_mstrPrgrNameEnitityImpl_accountHead").val(mstrBudgetAccountHead);
        		    
                    $("#viewSantionOrderModal .close").click();
                   /*  ordAmtElement.focus();
                    ordAmtElement.select(); */
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
	
	function calTotAmt(curElement) {
		var val1 = curElement.value;
		curElement.value = Number.parseFloat(val1).toFixed(2);
		var totAmt = 0;
		for (var i = 0; i <=lastIndex; i++) {
			var amt = document.getElementById("budgetDetails_"+ i + "_amtInLakh");
			if(amt!= null && amt != 'undefine'){
				totAmt = totAmt + Number.parseFloat(amt.value);
			}
		}
		document.getElementById("budget_totAmount").value=totAmt.toFixed(2);
	}
	
	/**
	* This function fetch the Master Budget Data using Ajax call
	* with provided param and show the popup dialog.
	* 1. mainGroupRid
	* 2. category
	* 3. shareType
	*/
	function openMasterBudget(curObj){
		var selectedVal = $(curObj).find('option:selected').val();
		if( selectedVal != "NONE" ){
			curObjIndexOfShareType = $(curObj).attr('curObjIndex');
			/* var elmntProg = document.getElementById("budgetDetails"+curObjIndexOfShareType+".grProgramName");
			var progVal = elmntProg.options[elmntProg.selectedIndex].value;  */
			var shareTypeVal = document.getElementById("budgetDetails_"+curObjIndexOfShareType+"_mstrPrgrNameEnitityImpl_shareType").value;
			var rid = "";
			var mainGroupRid = $("#mainGroupRid option:selected").val();
			requestDatas = "rid="+rid+"&category="+encodeURIComponent(selectedVal)+"&shareType="+encodeURIComponent(shareTypeVal)+"&mainGroupRid="+encodeURIComponent(mainGroupRid)+"&rand="+Math.random(); 
			
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
	          url: "getMstrBudgetData?"+requestDatas,
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
				<h3 class="page-header"><i class="fa fa-file-text-o"></i> Budget Form</h3>
			</div>
		</div>
	<section class="panel" >
				<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Add Budget Details</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div class="form">
					<form:form action="saveBudget" method="post" modelAttribute="budget">
					<script>
						var requestData='';
						var seasonTypes = '<c:out value="${seasonTypes}"/>'; 
						seasonTypes = seasonTypes.substring(1, (seasonTypes.length) - 1);
						var categories = '<c:out value="${categories}"/>';
						categories = categories.substring(1, (categories.length) - 1);
						var shareType = '<c:out value="${share}"/>';
						shareType = shareType.substring(1, (shareType.length) - 1);
					</script>
					<input type="hidden" name="formMode" id="formMode" value="${formMode}" />
					<input type="hidden" name="budgetDtlRidsDeleted" id="budgetDtlRidsDeleted" >
					<div class="row form-group">	<!-- 1fst row start -->
					 	<div class="col-md-3">
					 		<spring:bind path="year">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
						 			<label for="year" class="control-label">Year <span class="required">*</span></label>
									<form:select class="form-control" path="year" id="year">
										<form:option value="NONE" label="Select" />
										<form:options items="${sinalcialYears}" />
									</form:select> 
									<form:errors path="year" cssClass="help-block" element="span" />
								</div>
					 		</spring:bind>
						</div>
					 	<div class="col-md-3">
					 		<spring:bind path="mstrMainGroupEnity.rid">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
						 			<label for="mainGroupRid" class="control-label">Main Group <span class="required">*</span></label>
									<form:hidden path="rid" value="${budget.rid}" />
									<form:select class="form-control" path="mstrMainGroupEnity.rid" id="mainGroupRid">
										<form:option value="NONE" label="Select" />
										<form:options items="${programGroupsMap}" />
									</form:select>
									<form:errors path="mstrMainGroupEnity.rid" cssClass="help-block" element="span" />
								</div>
					 		</spring:bind>
						</div>
						<div class="col-md-3">
					 		<div class="form-group">
					 			<div class="row" >
									<c:choose>
											<c:when test="${ not empty budget.documentEntity.fileName}">
												<spring:url value="viewBankPDFFile/${budget.documentEntity.rid}_${budget.documentEntity.fileName}" 
														var="viewPDFUrl"></spring:url>
												<a id="fileName" href="javascript:viewFile('${viewPDFUrl}')" 
													style="display: inline-block;margin-bottom: 5px;">
													<span>${budget.documentEntity.fileName}</span>
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
								<label for="budget_totAmount" class="control-label">Total Amount</label>
								<input type ="text" name="totAmount" id="budget_totAmount" 
									class="form-control" value="${budget.totAmount}" readonly/>
							</div>
						</div>
					</div> <!-- 1fst row end-->
					<div class="row form-group"><!-- 2nd row start-->
						<table id="SanOrdDtlTbl" class="table table-sm">
							<thead>
							 <tr>
								<th style="width:3%"><button id="addRow" class="btn btn-default"><i class="fa fa-plus-square-o" aria-hidden="true"></i></button></th>
								<th style="width:8%">Share Type <span class="required">*</span></th>
								<th style="width:8%">Category <span class="required">*</span></th>
								<th style="width:9%">Account Head <span class="required">*</span></th>
								<th style="width:22%">Program Name In English <span class="required">*</span></th>
								<th style="width:9%">Short Name <span class="required">*</span></th>
								<th style="width:9%">Season Type <span class="required">*</span></th>
								<th style="width:10%"><span>Amt In Lakh</span></th>
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
							<c:forEach items="${budget.budgetDetails}"
								var="budgetDetail" varStatus="status">
								<tr>
									<td>
										<input type="hidden" name="budgetDetails[${status.index}].rid"
											value="${budgetDetail.rid}" id="budgetDetails_${status.index}_rid" />
										<input type="hidden" name="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.rid"
											value="${budgetDetail.mstrPrgrNameEnitityImpl.rid}" 
											id="budgetDetails_${status.index}_mstrPrgrNameEnitityImpl_rid" />
										
										<!-- This lastIndex var use for add new row  -->
										<script>
											var lastIndex = '<c:out value="${status.index}"/>'; 
										</script>
										
										<c:if test="${ status.index > 0 }">
											<input type='checkbox' name='record' id="${budgetDetail.rid}"/>
										</c:if>
									</td>
									<td>
										<form:select path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.shareType" 
											id="budgetDetails_${status.index}_mstrPrgrNameEnitityImpl_shareType" 
											 class="form-control">
											<form:option value="NONE" label="Select" />
											<form:options items="${share}" />
										</form:select> 
										<form:errors path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.shareType"
											cssClass="error" element="div" />
									</td>
									<td>
										<form:select path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.category" 
											id="budgetDetails_${status.index}_mstrPrgrNameEnitityImpl_category" 
											class="form-control" curObjIndex="${status.index}" onchange="openMasterBudget(this);">
											<form:option value="NONE" label="Select" />
											<form:options items="${categories}" />
										</form:select> 
										<form:errors
											path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.category"
											cssClass="error" element="div" />
									</td>
									
									<td>
										<input type="text"
											name="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.accountHead"
											id="budgetDetails_${status.index}_mstrPrgrNameEnitityImpl_accountHead" 
											value="${budgetDetail.mstrPrgrNameEnitityImpl.accountHead}" class="form-control" readonly/>
										<form:errors
											path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.accountHead"
											cssClass="error" element="div" />
									</td>
									<td>
										<input type="text"
											name="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.inEng"
											id="budgetDetails_${status.index}_mstrPrgrNameEnitityImpl_inEng"
											value="${budgetDetail.mstrPrgrNameEnitityImpl.inEng}" class="form-control" readonly/> 
										<form:errors path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.inEng" 
											cssClass="error" element="div" />
									</td>
									<td>
										<input type="text"
											name="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.inShort"
											id="budgetDetails_${status.index}_mstrPrgrNameEnitityImpl_inShort"
											value="${budgetDetail.mstrPrgrNameEnitityImpl.inShort}" class="form-control" readonly/> 
										<form:errors path="budgetDetails[${status.index}].mstrPrgrNameEnitityImpl.inShort" 
											cssClass="error" element="div" />
									</td>
									<td>
										<form:select path="budgetDetails[${status.index}].seasionType" 
											id="budgetDetails_${status.index}_seasionType" class="form-control">
											<form:option value="NONE" label="Select" />
											<form:options items="${seasonTypes}" />
										</form:select> 
										<form:errors
											path="budgetDetails[${status.index}].seasionType"
											cssClass="error" element="div" />
									</td>
									<td>
										<input type="number" name="budgetDetails[${status.index}].amtInLakh"
											id="budgetDetails_${status.index}_amtInLakh" value="${budgetDetail.amtInLakh}"
											 class="form-control" step=".01" onblur="calTotAmt(this);"/>
										<form:errors
											path="budgetDetails[${status.index}].amtInLakh"
											cssClass="error" element="div" />
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div><!-- 2nd row end-->
					<div class="row form-group"> <!-- 3rd row start-->	
						<div class="col-md-9"></div>
					 	<div class="col-md-2">
								<a href="cancelBudget" class="btn btn-link pull-right">Cancel</a>
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
							<h4 class="modal-title">Master Budget Selection</h4>
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