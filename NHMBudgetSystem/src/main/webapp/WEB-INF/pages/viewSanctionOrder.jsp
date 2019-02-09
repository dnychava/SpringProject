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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css">
 -->
<spring:url value="/resources/app/css/main.css" var="mainCss" />
<spring:url value="/resources/app/js/comman.js" var="comman" />
<spring:url value="/resources/app/js/appFileUpload.js" var="fileUpload" />

<link href="${mainCss}" rel="stylesheet">
<script src="${comman}"></script>
<script src="${fileUpload}"></script>


<title>View Sanction Order</title>
<script type="text/javascript">
$(document).ready(function() {

	$('[data-toggle="tooltip"]').tooltip();  
	var table = $('#example').DataTable({
	      //'rowsGroup': [0,1,2]
	   });
}); 
function submitForm(sanOrdRid) {
	$("#sanOrdRid").val(sanOrdRid);
	$("form").submit();
}
</script>
<style type="text/css">
	.msg{
		text-align: center;
	}
</style>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-sm-12">
				<div class="msg">
					<c:if test="${not empty param.msg}">
						<div class="alert alert-${param.css} alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true">×</span>
							</button>
							<strong>${param.msg}</strong>
				    </div>
					</c:if>
				</div>
			</div>
		</div>
		<div class="row" >
			<div class="col-sm-12">
				<section class="panel">
					<header class="panel-heading"> 
						<div class="row">
							<div class="col-sm-11"> View All Sanction Order Records </div>
							<div class="col-sm-1"> 
								<a class="btn btn-primary" href="addSanctionOrder" 
									data-toggle="tooltip" data-placement="left" title="Add" >
									<i class="icon_plus_alt2"></i>
								</a>
							</div>
						</div>
					</header>
						<form action="edit" method="post" id="viewForm">
							<input type="hidden" name="sanOrdRid" id="sanOrdRid" value=""/>
							<table id="example" class="table table-striped table-hover table-responsive" cellspacing="0" style="font-size: 14px;">
								<thead>	
									<tr>
										<th>#</th>
										<th>Year</th>
										<th>Program Group Name</th>
										<th>File Name</th> 
										<th>Program Name</th>
										<th>Sanction Order Date</th>
										<th>Sanction Order No.</th>
										<th>Category</th>
										<th>Amount In Rs.</th>
										<th>Amount In Lakh</th> 
										<th>Operation</th>
									</tr>	
								</thead>
								<tbody>
									<c:set var="lastIndex"	value="0"></c:set>
									<c:forEach items="${sanctionOrderList}" var="sanctionOrder"
									varStatus="status">
									<spring:url value="${sanctionOrder.rid}" var="editUrl"></spring:url>
									<spring:url value="delete/${sanctionOrder.rid}" var="deleteUrl"></spring:url>
									
									<c:forEach items="${sanctionOrder.sanctionOrderDetails}"
										var="orderDetail" varStatus="status1">
										<c:set var="totRowOfSanOrdDtl"
												value="${ (lastIndex + 1) }"></c:set>
										<c:set var="lastIndex"	value="${(lastIndex + 1)}"></c:set>
										<tr>
											<th>${totRowOfSanOrdDtl}</th>
											<td ><span>${sanctionOrder.year}</span></td>
											<td ><span>${sanctionOrder.programGroupName}</span></td>
											<c:choose>
												<c:when test="${ not empty sanctionOrder.documentEntity.fileName}">
													<td>
														<spring:url value="viewSanctionOrdPDFFile/${sanctionOrder.documentEntity.rid}_${sanctionOrder.documentEntity.fileName}" 
															var="viewPDFUrl"></spring:url>
														<a id="fileName" href="javascript:viewFile('${viewPDFUrl}')">
															<span>${sanctionOrder.documentEntity.fileName}</span>
														</a>
													</td>
												</c:when>
												<c:otherwise>
													<td>No file attached</td>
												</c:otherwise>
											</c:choose>
											<td><span>${orderDetail.orderProgramName}</span></td>
											<td><fmt:formatDate value="${orderDetail.orderDate}"
													var="dateString" pattern="dd/MM/yyyy" /> <span>${dateString}</span>
											</td>
											<td><span>${orderDetail.orderNumber}</span></td>
											<td><span>${orderDetail.orderCategory}</span></td>
											<td><span>${orderDetail.orderAmt}</span></td>
											<td><span>${orderDetail.orderAmtInLakh}</span></td>
											<td>
												<div class="btn-group">
												<%-- <c:if test="${ orderDetail.completed != 'Y' }"> --%>
													<a href="javascript:submitForm('${editUrl}')" class="btn btn-success btn-sm" 
														data-toggle="tooltip" title="Edit">
														<i class="fa fa-pencil fa-lg" aria-hidden="true"></i>
													</a>
													<a href="javascript:confirmDelete('${deleteUrl}')" id="delete" 
														class="btn btn-danger btn-sm" data-toggle="tooltip" title="Delete">
														<i class="icon_close_alt2"></i>
													</a> 
												<%-- </c:if> --%>
												</div>
											</td>
										</tr>
									</c:forEach>
								</c:forEach>
								</tbody>
							</table>
							
							<!-- custom confirmation dialog ----- START	 -->
							<div class="modal fade" id="modalConfirmYesNo" tabindex="-1" role="dialog">
								<div class="modal-dialog" role="document" >
									<div class="modal-content"  >
					
										<!-- Modal Header -->
										<div class="modal-header">
											<button aria-hidden="true" type="button" class="close" data-dismiss="modal">&times;</button>
											<h5 class="modal-title" id="lblTitleConfirmYesNo" >Confirmation</h5>
										</div>
					
										<!-- Modal body -->
										<div class="modal-body">
											<p id="lblMsgConfirmYesNo"></p>
										</div>
					
										<!-- Modal footer -->
										<div class="modal-footer">
											<button id="btnYesConfirmYesNo" type="button" class="btn">Yes</button>
							                <button id="btnNoConfirmYesNo" type="button" class="btn btn-default">No</button>
										</div>
									</div>
								</div>
							</div>
							<!-- custom confirmation dialog ----- END	 -->
						</form>
				</section>
			</div>
		</div>
	</div>
	
</body>
</html>