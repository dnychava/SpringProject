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
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/smoothness/jquery-ui.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script> -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>

<spring:url value="/resources/app/css/main.css" var="mainCss" />
<spring:url value="/resources/app/js/comman.js" var="comman" />
<spring:url value="/resources/app/js/appFileUpload.js" var="fileUpload" />

<script src="${comman}"></script>
<script src="${fileUpload}"></script>

<script type="text/javascript">
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
					+"<select autofocus id='sanctionOrderDetails["+lastIndex+"].orderProgramName' name='sanctionOrderDetails["+lastIndex+"].orderProgramName'>"+proNameOption+"</select>"
					+"<form:errors name='sanctionOrderDetails["+lastIndex+"].orderProgramName' cssClass='error' element='div'/>"	
				+"</td>"
				+"<td>"
					+"<input type='text' name='sanctionOrderDetails["+lastIndex+"].orderDate' class='input_date'/>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderDate' cssClass='error' element='div'/>"
				+"</td>"					
				+"<td>"
					+"<input name='sanctionOrderDetails["+lastIndex+"].orderNumber'/>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderNumber' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<select name='sanctionOrderDetails["+lastIndex+"].orderCategory'>"+categorieOption+"</select>"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderCategory' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='number' value='0' name='sanctionOrderDetails["+lastIndex+"].orderAmt' id='sanctionOrderDetails["+lastIndex+"].orderAmt' onblur='amtConvertInLakh("+lastIndex+");' />"
					+"<form:errors path='sanctionOrderDetails["+lastIndex+"].orderAmt' cssClass='error' element='div'/>"
				+"</td>"
				+"<td>"
					+"<input type='text' value='0' name='sanctionOrderDetails["+lastIndex+"].orderAmtInLakh' id='sanctionOrderDetails["+lastIndex+"].orderAmtInLakh' readonly='readonly'/>"
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
        });
		
	});
	 
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
		var amtInRs = document.getElementById("sanctionOrderDetails["+ indexOfObj + "].orderAmt");
		var amtInLakh = document.getElementById("sanctionOrderDetails["+ indexOfObj + "].orderAmtInLakh");
		var totInLack = (amtInRs.value / 100000);
		if (amtInLakh != 'undefine' && amtInLakh != null) {
			amtInLakh.value = totInLack;
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
<body>
	<div class="container">
		<div>
			<form:form action="save" method="post" modelAttribute="sanctionOrder">
				<script>
					var programName = '<c:out value="${programName}"/>'; 
					programName = programName.substring(1, (programName.length) - 1);
					var categories = '<c:out value="${categories}"/>';
					categories = categories.substring(1, (categories.length) - 1);
				</script>
				<input type="hidden" name="formMode" id="formMode" value="${formMode}" />
				<input type="hidden" name="sanOrdDtlRidsDeleted" id="sanOrdDtlRidsDeleted" >
				<table border="1" id="SanOrdDtlTbl">
					
					<tr>
						<th colspan="7">Add New Sanction Order</th>
					</tr>
					<tr>
						<td></td>
						<td>
							Year :
							<form:hidden path="rid" value="${rid}" /> <form:select
								path="year">
								<form:option value="NONE" label="Select" />
								<form:options items="${sinalcialYears}" />
							</form:select> <form:errors path="year" cssClass="error" element="div" /></td>
						<td>Program Group Name :</td>
						<td><form:select path="programGroupName">
								<form:option value="NONE" label="Select" />
								<form:options items="${programGroups}" />
							</form:select> <form:errors path="programGroupName" cssClass="error"
								element="div" /></td>
						<td colspan="2">
						<c:choose>
								<c:when test="${ not empty sanctionOrder.documentEntity.fileName}">
									<a id="fileName" href="viewPDFFile/${sanctionOrder.documentEntity.rid}_${sanctionOrder.documentEntity.fileName}">
										<span>${sanctionOrder.documentEntity.fileName}</span>
									</a>
								</c:when>
								<c:otherwise>
									<a id="fileName" href="" target="_blank"><span>No
											file Attached</span></a>
								</c:otherwise>
						</c:choose></td>
						<td><form:hidden path="documentEntity.rid"
								id="documentEntityRid"
								value="${sanctionOrder.documentEntity.rid}" /> <form:hidden
								path="documentEntity.fileName" id="documentEntityFileName"
								value="${sanctionOrder.documentEntity.fileName}" /> <!-- Button to Open the Modal -->
							<button type="button" class="" data-toggle="modal"
								data-target="#fileUploadModal">File Attach</button></td>
					</tr>
					<tr>
						<td><button id="addRow">+</button></td>
						<td>Program Name</td>
						<td>Sanction Order Date(dd/mm/yyyy)</td>
						<td>Sanction Order No.</td>
						<td>Category</td>
						<td>Amount In Rs.</td>
						<td colspan="2"><span>Amount In Lakh </span>
							</td>
					</tr>
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
									id="sanctionOrderDetails[${status.index}].orderProgramName">
									<form:option value="NONE" label="Select" selected="selected" />
									<form:options items="${programName}" />
								</form:select> <form:errors
									path="sanctionOrderDetails[${status.index}].orderProgramName"
									cssClass="error" element="div" /></td>
							<td><form:input
									path="sanctionOrderDetails[${status.index}].orderDate"
									value="${dateString}" class="input_date" /> <form:errors
									path="sanctionOrderDetails[${status.index}].orderDate"
									cssClass="error" element="div" /></td>
							<td><form:input
									path="sanctionOrderDetails[${status.index}].orderNumber"
									value="${orderDetail.orderNumber}"
									id="sanctionOrderDetails[${status.index}].orderNumber" /> <form:errors
									path="sanctionOrderDetails[${status.index}].orderNumber"
									cssClass="error" element="div" /></td>
							<td><form:select
									path="sanctionOrderDetails[${status.index}].orderCategory">
									<form:option value="NONE" label="Select" />
									<form:options items="${categories}" />
								</form:select> <form:errors
									path="sanctionOrderDetails[${status.index}].orderCategory"
									cssClass="error" element="div" /></td>
							<td><form:input
									path="sanctionOrderDetails[${status.index}].orderAmt"
									id="sanctionOrderDetails[${status.index}].orderAmt"
									type="number" value="${orderDetail.orderAmt}"
									onblur="amtConvertInLakh('${status.index}')" /> <form:errors
									path="sanctionOrderDetails[${status.index}].orderAmt"
									cssClass="error" element="div" /></td>
							<td><input type="text"
								name="sanctionOrderDetails[${status.index}].orderAmtInLakh"
								id="sanctionOrderDetails[${status.index}].orderAmtInLakh"
								value="${orderDetail.orderAmtInLakh}" readonly="readonly" /></td>
						</tr>
					</c:forEach>
				</table>
				<div align="center">
					<table >
						<tr align="right" >
							<td ><button type="button" id="delete-row" class="delete-row">-</button></td>
							<td ><a href="cancelSanctionOrder">Cancel</a></td>
							<td ><input type="submit" value="Save" /></td>
						</tr>
					</table>
				</div>
			</form:form>
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
	</div>

</body>
</html>