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

</head>
<body onclick="false">
	<div class="container-fluid">
	<section class="panel" >
				<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Add Main Group Details</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<div class="form">
					<form:form action="saveMainGroup" method="post" modelAttribute="mainGroupBO">
					<input type="hidden" name="formMode" id="formMode" value="${formMode}" />
					<form:hidden path="rid" value="${mainGroupBO.rid}" />
					<div class="row form-group">	<!-- 1fst row start -->
					 	<div class="col-md-6">
					 		<spring:bind path="inEng">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
						 			<label for="inEng" class="control-label">Main Group Name In English <span class="required">*</span></label>
						 			<form:input class="form-control" path="inEng" id="inEng"/>
									<form:errors path="inEng" cssClass="help-block" element="span" />
								</div>
							</spring:bind>
						</div>
					 	<div class="col-md-6">
					 		<spring:bind path="inMarathi">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
						 			<label for="inMarathi" class="control-label">Main Group Name In Marathi <span class="required">*</span></label>
						 			<form:input class="form-control" path="inMarathi" id="inMarathi"/>
									<form:errors path="inMarathi" cssClass="help-block" element="span" />
								</div>
							</spring:bind>
						</div>
					</div> <!-- 1fst row end-->
					<div class="row form-group">	<!-- 2nd row start -->
					 	<div class="col-md-6">
					 		<spring:bind path="inShort">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
						 			<label for="inShort" class="control-label">Main Group Name In Short <span class="required">*</span></label>
						 			<form:input class="form-control" path="inShort" id="inShort"/>
									<form:errors path="inShort" cssClass="help-block" element="span" />
								</div>
							</spring:bind>
						</div>
					 	<div class="col-md-6">
					 		<spring:bind path="sequence">
						 		<div class="form-group ${status.error ? 'has-error' : ''}">
						 			<label for="sequence" class="control-label">Main Group Display Sequence No. <span class="required">*</span></label>
						 			<form:input class="form-control" path="sequence" id="sequence"/>
									<form:errors path="sequence" cssClass="help-block" element="span" />
								</div>
							</spring:bind>
						</div>
					</div> <!-- 2nd row end-->
					
					<div class="row form-group"> <!-- 3rd row start-->	
						<div class="col-md-9"></div>
					 	<div class="col-md-2">
								<a href="cancelMainGroup" class="btn btn-link pull-right">Cancel</a>
						</div>
						<div class="col-md-1">
							<button type="submit" class="btn btn-primary pull-right">
								<i class="fa fa-floppy-o" aria-hidden="true"></i> Save </button>
						</div>
					</div> <!-- 3rd row end-->
				</form:form>
				</div>
			</div>
		</section>
	</div>
</body>
</html>