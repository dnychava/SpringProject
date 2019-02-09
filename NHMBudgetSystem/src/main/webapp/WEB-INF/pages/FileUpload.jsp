<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>-->
	
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#btnSubmit").click(function(event) {
			var errorMsg = "Testing";
			var fileName = $("#file").val();
			if( fileName == ""){
				alert("Please select file");
				return;
				
			} else if( !fileName.endsWith(".xlsx") && !fileName.endsWith(".xls")){
				alert("Selected file is not correct format. File only accept PNG or JPG format");
				return;
			}	        
	        
		});
	});
</script>

</head>
<body>
	<div class="container">
	
		<h2>Modal Example</h2>
		<form method="post" action="uploadExcelData"
							enctype="multipart/form-data" accept-charset="UTF-8" id="fileUploadForm">
							<div class="form-group">
								<label for="fileToUpload">Please select file</label> 
								<input name="file" id="file" type="file" accept=".xls,.xlsx"
									class="form-control" />
							</div>
							<button type="submit" id="btnSubmit" class="btn btn-primary">Upload</button>
						</form>
						

		<%-- <!-- The Modal -->
		<div class="modal fade" id="myModal">
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
							enctype="multipart/form-data;charset=UTF-8" accept-charset="UTF-8" id="fileUploadForm">
							<div class="form-group">
								<label for="fileToUpload">Please select file</label> 
								<input name="file" id="file" type="file"
									class="form-control" />
							</div>
						</form>
						<h5>Ajax Post Result</h5>
						<span id="result"></span>
					</div>

					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="button" class="btn btn-default"
							data-dismiss="modal">Close</button>
						<button type="submit" id="btnSubmit" class="btn btn-primary">Upload</button>
					</div>

				</div>
			</div>
		</div> --%>
	</div>
</body>
</html>