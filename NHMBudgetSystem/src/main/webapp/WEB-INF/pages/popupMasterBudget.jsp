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
 <!-- javascripts -->
  <script src="./resources/js/jquery.js"></script>
  <script src="./resources/js/jquery-1.8.3.min.js"></script>
  <!-- bootstrap -->
  <script src="./resources/js/bootstrap.min.js"></script>
<title>Popup SanctionOrder Details</title>
<script type="text/javascript">
$(document).ready(function() {
		console.log("In Document");
	    $('.list-group a').click(function(e) {
	        e.preventDefault()
	        $that = $(this);
	        var index = $that.attr("custId");
	        console.log( "====In popupSanction ===index["+index+"]");
	        $($that).find('input[name="selected_record"]').each(function(){
            	if($(this).is(":checked")){
            		$(this).attr("checked",false);
                }
            });
	        var checkElem = document.getElementById(""+index);
	        checkElem.checked = true;
	        
	        $that.parent().find('a').removeClass('active');
	        $that.addClass('active');
	    });
});
</script>
<style type="text/css">
	.bgHeader{
		background-color: lightgray;
		color: white;
	}
	.textAlignCenter{
		text-align: center;
		border-bottom: 1px solid;
	}
	.textAlignRight{
		text-align: right;
	}
</style>

</head>
<body>
	<c:choose>
		<c:when test="${ not empty msterBudgetViewList }">
			<div class="container-fluid">
				<div class="list-group">
					<div class='list-group-item bgHeader'>
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-3">Program Name In English</div>
							<div class="col-sm-3">Program Name In Marathi</div>
							<div class="col-sm-2">Program Name In Short</div>
							<div class="col-sm-1">Account Head</div>
							<div class="col-sm-1">Category</div>
							<div class="col-sm-1">Share Type</div>
						</div>
				</div>
				<c:forEach items="${msterBudgetViewList}" var="mstrBudget"
					varStatus="status">
					<a href="#" class="list-group-item list-group-item-action"  custId="${status.index}">
						<div class="row">
							<div class="col-sm-1">
								<input type="radio" name="selected_record" id="${status.index}" />
								<input type="hidden" id="mstrBudget_${status.index}_rid" value="${ mstrBudget.rid }"/>
							</div>
							<div class="col-sm-3">
								<input type="hidden" id="mstrBudget_${status.index}_inEng" value="${ mstrBudget.inEng}"/>
								${mstrBudget.inEng}
							</div>
							<div class="col-sm-3">${mstrBudget.inMarathi}</div>
							<div class="col-sm-2">
								<input type="hidden" id="mstrBudget_${status.index}_inShort" value="${ mstrBudget.inShort }"/>
								${mstrBudget.inShort}
							</div>
							<div class="col-sm-1">
								<input type="hidden" id="mstrBudget_${status.index}_accountHead" value="${ mstrBudget.accountHead }"/>
								${mstrBudget.accountHead}
							</div>
							<div class="col-sm-1">${mstrBudget.category}</div>
							<div class="col-sm-1">${mstrBudget.shareType}</div>
						</div>
					</a>
				</c:forEach>
			</div>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<h5>No Record Found !</h5>
			</div>
		</c:otherwise>
	</c:choose>
</body>
</html>
