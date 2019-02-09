<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/birt.tld" prefix="birt"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"> -->
<!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css"> -->
	
<title>Pending Report</title>
<script type="text/javascript">
function submitClick() {
	createInQueryOfYears();
	createInQueryOfPrograms();
	createInQueryOfCategories();
	var formAction = $("form").attr("action");
	var docFormatType="pdf";
	$.each($("#docFormatType option:selected"), function(){
		docFormatType = $(this).val();
    });
	var formatVal = "format="+docFormatType;
	
	if( formAction.indexOf("pdf") != -1 ){
		formAction = formAction.replace("format=pdf", formatVal );
	}else if( formAction.indexOf("xlsx") != -1 ){
		formAction = formAction.replace("format=xlsx", formatVal );
	}else if( formAction.indexOf("docx") != -1 ){
		formAction = formAction.replace("format=docx", formatVal );
	}else if( formAction.indexOf("pptx") != -1 ){
		formAction = formAction.replace("format=pptx", formatVal );
	}
	else if( formAction.indexOf("html") != -1 ){
		formAction = formAction.replace("format=html", formatVal );
	}
	if( formAction.indexOf("html") != -1 ){
		formAction = formAction.replace("preview", "frameset" );
	} else {
		formAction = formAction.replace("frameset", "preview" );
	}
	
	$("form").attr("action", formAction);
	return false;
}
function createInQueryOfYears(){
	var years = [];
	$.each($("#year option:selected"), function(){            
		years.push(($(this).val()));
    });
    if(years.indexOf("All") != -1 ){
    	years.pop("All");
    	$.each($("#year option"), function(){            
    		years.push(($(this).val()));
        });
    }
	$("#yearHidden").val( years.join("','") );
	
}
function createInQueryOfPrograms(){
	var programs = [];
	$.each($("#programs option:selected"), function(){            
    	programs.push(($(this).val()));
    });
    if(programs.indexOf("All") != -1 ){
    	programs.pop("All");
    	$.each($("#programs option"), function(){            
        	programs.push(($(this).val()));
        });
    }
	$("#programsHidden").val( programs.join("','") );
    
}
function createInQueryOfCategories(){
	var categories = [];
	$.each($("#categories option:selected"), function(){            
		categories.push(($(this).val()));
    });
    if(categories.indexOf("All") != -1 ){
    	categories.pop("All");
    	$.each($("#categories option"), function(){            
    		categories.push(($(this).val()));
        });
    }
	$("#categoriesHidden").val( categories.join("','") );
}
</script>
<style type="text/css">
</style>
</head>
<body>
	<div class="container">
		
		<section class="panel" >
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Delay Days Report Filter</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
					<birt:parameterPage id="birtParmPage" 
						reportDesign="report/DelayDaysReport.rptdesign" name="delayDaysFilterForm"
						pattern="preview" format="pdf" title="Pending Sanction Report" style="overflow: hidden;"
						isCustom="true" showTitle="true" showToolBar="true" 
						showNavigationBar="true" target="_blank">
					<input type="hidden" name="yearHidden" id="yearHidden" value=""/>
					<input type="hidden" name="programsHidden" id="programsHidden" value=""/>
					<input type="hidden" name="categoriesHidden" id="categoriesHidden" value=""/>
				<div class="form">
				<form:form class="form-validate" action="viewPendingReport" method="POST"
					modelAttribute="pendingOrdFilter">
					<div class="row form-group">	
				 	<div class="col-md-4">
				 		<div class="form-group">
							<label for="year" class="col-md-4 control-label">Year</span></label>
							<div class="col-md-8">
				                 <form:select path="year" id="year" class="form-control">
											<form:option value="All" label="All" />
											<form:options items="${sinalcialYears}" />
								</form:select>
								<form:errors path="year" class="control-label" />
							</div>
						</div>
				 	</div>
				 	<div class="col-md-4">
				 		<spring:bind path="fromDate">
						  <div class="form-group ${status.error ? 'has-error' : ''}">
							<label class="col-md-4 control-label" for="fromDate">From Date</label>
							<div class="col-md-8">
								<div class="input-group date"  data-provide="datepicker" data-date-format="${dispDateFormatter}" >
								 	<form:input path="fromDate" id="fromDate" class="form-control" placeholder="${dispDateFormatter}"/>
				                 	<div class="input-group-addon">
				                 		<i class="fa fa-calendar" aria-hidden="true"></i>
				                 	</div>
				                 </div>
								<form:errors path="fromDate" class="control-label" />
							</div>
						  </div>
						</spring:bind>
				 	</div>
				 	<div class="col-md-4">
				 		<spring:bind path="toDate">
						  <div class="form-group  ${status.error ? 'has-error' : ''}">
							<label class="col-md-4 control-label" for="toDate">To Date</label>
							<div class="col-md-8">
								<div class="input-group date" data-provide="datepicker" data-date-format="${dispDateFormatter}" >
								 	<form:input path="toDate" id="toDate" class="form-control" placeholder="${dispDateFormatter}"/>
				                 	<div class="input-group-addon">
				                 		<i class="fa fa-calendar" aria-hidden="true"></i>
				                 	</div>
				                 </div>
								<form:errors path="toDate" class="control-label" />
							</div>
						  </div>
						</spring:bind>
				 	</div>				 	
				 </div> <!--End 1st row -->
				 <div class="row form-group">
				 	<div class="col-md-4">
				 		<spring:bind path="programs">
					 		<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-md-4 control-label" for="programs">Program</label>
								<div class="col-md-8">
					                 <form:select multiple="true" id="programs" path="programs" class="form-control">
												<form:option value="All" label="All" selected="selected"/>
												<form:options items="${programName}" />
									</form:select>
									<form:errors path="programs" class="control-label" />
								</div>
							</div>
						</spring:bind>
				 	</div>
				 	<div class="col-md-4">
				 		<spring:bind path="categories">
					 		<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-md-4 control-label" for="categories">Category</label>
								<div class="col-md-8">
					                 <form:select multiple="true" id="categories" path="categories" class="form-control">
												<form:option value="All" label="All" selected="selected" />
												<form:options items="${categories}" />
									</form:select>
									<form:errors path="programs" class="control-label" />
								</div>
							</div>
						</spring:bind>
				 	</div>
				 	<div class="col-md-4">
				 		<spring:bind path="shareType">
					 		<div class="form-group ${status.error ? 'has-error' : ''}">
								<label class="col-md-4 control-label" for="shareType">Share Type</label>
								<div class="col-md-8">
					                 <form:select path="shareType" id="shareType" class="form-control">
												<form:options items="${shareType}" />
									</form:select>
									<form:errors path="shareType" class="control-label" />
								</div>
							</div>
						</spring:bind>
				 	</div>
				 </div><!--End 2nd row -->
				 
				 <!-- 3rd row -->
				 <div class="row form-group">
				 	<div class="col-md-4">
				 		<spring:bind path="docFormatType">
					 		<div class="form-group ${status.error ? 'has-error' : ''}">
								<label for="docFormatType" class="col-md-4 control-label">Document Format</label>
								<div class="col-md-8">
					                 <form:select path="docFormatType" id="docFormatType" class="form-control">
										<%-- <form:option value="html" label="HTML" /> --%>
										<form:options items="${docFormatType}" />
									</form:select>
								</div>
							</div>
						</spring:bind>
				 	</div>
				 	<div class="col-md-4">
				 	</div>
				 	<div class="col-md-4">
				 		<div class="col-md-12 text-right">
				 			<button type="submit" name="viewReport" class="btn btn-primary" onclick="submitClick()">View Report</button>
				 			<!-- <button type="submit" name="viewReport" class="btn btn-primary" onclick="submitClick()">View Report</button> -->
				 		</div>
				 		
				 	</div>
				 </div><!--End 3rd row -->
				 </form:form>
				 </div>
				 </birt:parameterPage>
			</div>
		</section>
	</div>
	
</body>
</html>