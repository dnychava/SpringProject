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
		<c:when test="${ not empty sanOrdDtlViewList }">
			<div class="container-fluid">
				<div class="list-group">
					<div class='list-group-item bgHeader'>
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-1 ">Program</div>
							<div class="col-sm-2">Date</div>
							<div class="col-sm-3">Order No.</div>
							<div class="col-sm-1">Category</div>
							<div class="col-sm-1">Share Type</div>
							<div class="col-sm-3">
								<div class="textAlignCenter">Amount</div>
								<div class="row">
									<div class="col-sm-6 textAlignRight">Rs.</div>
									<div class="col-sm-6 textAlignRight">In Lakh</div>
								</div>
							</div>
						</div>
				</div>
				<c:forEach items="${sanOrdDtlViewList}" var="sanctionOrder"
					varStatus="status">
					
					<c:set var="disabledVal" value="${ (sanctionOrder.radioBtn) ? '' : 'disabled' }"></c:set>
					
					<a href="#" class="list-group-item list-group-item-action ${disabledVal}"  custId="${status.index}">
					<div class="row">
						<div class="col-sm-1">
							<input type="radio" name="selected_record" id="${status.index}" />
							<input type="hidden" name="sanOrdDtl_rid"
								id="sanOrdDtl_${status.index}_rid"
								value="${ sanctionOrder.rid }">
						</div>
						<div class="col-sm-1">${sanctionOrder.orderProgramName}</div>
						<div class="col-sm-2">
							<fmt:formatDate value="${sanctionOrder.orderDate}"
								var="dateString" pattern="dd/MM/yyyy" />
							${dateString}
						</div>
						<div class="col-sm-3">
							<input type="hidden" id="sanOrdDtl_${status.index}_orderNumber"
								name="sanOrdDtl_orderNumber" value="${sanctionOrder.orderNumber}">
							${sanctionOrder.orderNumber}</div>
						<div class="col-sm-1">${sanctionOrder.orderCategory}</div>
						<div class="col-sm-1">${sanctionOrder.sharType}</div>
						<div class="col-sm-3">
							<div class="row">
								<div class="col-sm-6 textAlignRight">
									<input type="hidden" id="sanOrdDtl_${status.index}_amt"
										name="sanOrdDtl_amt" value="${sanctionOrder.orderAmt}">
										${sanctionOrder.orderAmt}
								</div>
								<div class="col-sm-6 textAlignRight">${sanctionOrder.orderAmtInLakh}</div>
							</div>
						</div>
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
