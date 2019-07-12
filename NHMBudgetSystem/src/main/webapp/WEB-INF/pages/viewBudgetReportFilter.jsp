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
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Sanction Order Filter</title>
<script type="text/javascript">
	function submitClick() {
		createInQueryOfYears();
		createInQueryOfPrograms();
		createInQueryOfCategories();
		createInQueryOfShareType();
		createInQueryOfSeasionType();
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
	function createInQueryOfShareType(){
		var shareTypes = [];
		$.each($("#shareType option:selected"), function(){            
			shareTypes.push(($(this).val()));
        });
        if(shareTypes.indexOf("All") != -1 ){
        	shareTypes.pop("All");
        	$.each($("#shareType option"), function(){            
        		shareTypes.push(($(this).val()));
            });
        }
    	$("#shareTypeHidden").val( shareTypes.join("','") );
	}
	function createInQueryOfSeasionType(){
		var seasionTypes = [];
		$.each($("#seasionType option:selected"), function(){            
			seasionTypes.push(($(this).val()));
        });
        if(seasionTypes.indexOf("All") != -1 ){
        	seasionTypes.pop("All");
        	$.each($("#seasionType option"), function(){            
        		seasionTypes.push(($(this).val()));
            });
        }
    	$("#seasionTypeHidden").val( seasionTypes.join("','") );
	}
</script>
</head>
<body>
	<div class="container">
		<section class="panel">
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Budget Report Filter</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
				<birt:parameterPage id="birtParmPage" 
					reportDesign="report/BudgetReport.rptdesign" name="sanctionFilterForm"
					pattern="preview" format="pdf" title="My Viewer Tag" style="overflow: hidden;"
					isCustom="true" showTitle="false" showToolBar="true" 
					showNavigationBar="true" target="_blank">
					<div class="form">
						<input type="hidden" name="yearHidden" id="yearHidden" value=""/>
						<input type="hidden" name="programsHidden" id="programsHidden" value=""/>
						<input type="hidden" name="categoriesHidden" id="categoriesHidden" value=""/>
						<input type="hidden" name="seasionTypeHidden" id="seasionTypeHidden" value=""/>
						<input type="hidden" name="shareTypeHidden" id="shareTypeHidden" value=""/>
						
						<form:form action="" method="POST"
							modelAttribute="pendingOrdFilter">
							<div class="row form-group">
								<div class="col-md-6">
									<label for="year" class="col-md-4 control-label">Year</label>
									<div class="col-md-8">
										<form:select path="year" id="year" class="form-control" >
											<form:options items="${sinalcialYears}" />
										</form:select>
									</div>
								</div>
								<div class="col-md-6">
									<label for="docFormatType" class="col-md-4 control-label">Document Format</label>
									<div class="col-md-8">
										<form:select path="docFormatType" id="docFormatType" class="form-control">
											<%-- <form:option value="html" label="HTML" /> --%>
											<form:options items="${docFormatType}" />
										</form:select>
									</div>
								</div>
							</div>
							<!-- End first row -->
							<div class="row form-group">
								<div class="col-md-6">
									<label class="col-md-4 control-label" for="shareType">Share Type</label>
									<div class="col-md-8">
					                 	<form:select path="shareType" id="shareType" class="form-control">
					                 		<form:option value="All" label="All" selected="selected" />
											<form:options items="${shareType}" />
										</form:select>
									</div>
								</div>
								<div class="col-md-6">
									<label for="seasionType" class="col-md-4 control-label">Seasons Type</label>
									<div class="col-md-8">
										<form:select path="seasionType" id="seasionType" class="form-control">
											<form:option value="All" label="All" selected="selected" />
											<form:options items="${seasionType}" />
										</form:select>
									</div>
								</div>
							</div>
							<!-- End first row -->
							<div class="row form-group">
								<div class="col-md-6">
									<label for="programs" class="col-md-4 control-label">Program</label>
									<div class="col-md-8">
										<form:select path="programs" multiple="true" id="programs"  class="form-control">
											<form:option value="All" label="All" selected="selected" />
											<form:options items="${programName}" />
										</form:select>
									</div>
								</div>
								<div class="col-md-6">
									<label for="categories" class="col-md-4 control-label">Category</label>
									<div class="col-md-8">
										<form:select path="categories" multiple="true" id="categories" class="form-control">
											<form:option value="All" label="All" selected="selected"/>
											<form:options items="${categories}" />
										</form:select>
									</div>
								</div>
							</div>
							<!-- End 2nd row -->
							<div class="row form-group">
								<div class="col-md-12 text-right">
									<button type="submit" name="viewReport" class="btn btn-primary" onclick="submitClick()" >View Report</button>
								</div>
							</div>
						</form:form>
					</div>
				</birt:parameterPage>
			</div>
		</section>
	</div>
</body>
</html>