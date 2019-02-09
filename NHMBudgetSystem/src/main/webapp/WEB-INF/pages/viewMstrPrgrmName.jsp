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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
	

<title>View Sanction Order</title>
<script type="text/javascript">
$(document).ready(function() {
	$('[data-toggle="tooltip"]').tooltip();  
	var table = $('#viewBank').DataTable({
	});
});
function submitForm(mstrPrgrNameRid) {
	$("#mstrPrgrmNameRid").val(mstrPrgrNameRid);
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
							<div class="col-sm-11">View All Program Name</div>
							<div class="col-sm-1"> 
								<a class="btn btn-primary" href="addMstrPrgrmName" 
									data-toggle="tooltip" data-placement="left" title="Add" >
									<i class="icon_plus_alt2"></i>
								</a>
							</div>
						</div>
					</header>
					<div class="row col-sm-12">
						<form action="editMstrPrgrmName" method="post">
						<input type="hidden" name="mstrPrgrmNameRid" id="mstrPrgrmNameRid" value=""/>
						<table id="viewBank" class="table table-striped table-hover table-responsive" cellspacing="0" width="100%" style="font-size: 14px;" >
							<thead>
								<tr>
									<th>#</th>
									<th>Main Group Name</th>
									<th>Name In Marathi</th>
									<th>Name In English</th>
									<th>Name In Short</th>
									<th>Account Head</th>				
									<th>Category</th>				
									<th>Share Type</th>				
									<th>Operation</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="lastIndex"	value="0"></c:set>
								<c:forEach items="${mstrPrgrmNameList}" var="mstrPrgrmName"
									varStatus="status">
									<spring:url value="${mstrPrgrmName.rid}" var="editUrl"></spring:url>
									<spring:url value="deleteMstrPrgrmName/${mstrPrgrmName.rid}" var="deleteUrl"></spring:url>
									<c:set var="srNo"
													value="${ (lastIndex + 1) }"></c:set>
											<c:set var="lastIndex"	value="${(lastIndex + 1)}"></c:set>
									<tr>
											<td>${srNo}</td>
											<td>${programGroups[mstrPrgrmName.mstrMainGroupRid]}</td>
											<td>${mstrPrgrmName.inMarathi}</td>
											<td>${mstrPrgrmName.inEng}</td>
											<td>${mstrPrgrmName.inShort}</td>
											<td>${mstrPrgrmName.accountHead}</td>
											<td>${mstrPrgrmName.category}</td>
											<td>${mstrPrgrmName.shareType}</td>
											<td>
												<div class="btn-group">
													<a href="javascript:submitForm('${editUrl}')" class="btn btn-success btn-sm"
														data-toggle="tooltip" title="Edit">
															<i class="fa fa-pencil fa-lg" aria-hidden="true"></i>
													</a>
													<a href="javascript:confirmDelete('${deleteUrl}')" class="btn btn-danger btn-sm"
														data-toggle="tooltip" title="Delete">
															<i class="icon_close_alt2"></i>
													</a> 
												</div>
											</td>
									</tr>
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
										<button id="btnYesConfirmYesNo" type="button" class="btn btn-danger">Yes</button>
						                <button id="btnNoConfirmYesNo" type="button" class="btn btn-default">No</button>
									</div>
								</div>
							</div>
						</div>
						<!-- custom confirmation dialog ----- END	 -->
						</form>
					</div>
				</section>
			</div>
		</div>
		
	</div>
</body>
</html>