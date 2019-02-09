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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.msg {
	color: green;
	font-size: 3pt;
}
</style>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sanctionOrderList }">
			
			<div>
				<table border="0" style="border-collapse: collapse; width: 100%">
					<tr>
						<th>
							<h3>
								<a href="viewSanctionOrderFilter">Back</a>
							</h3>
						</th>
						<th colspan="8" align="center"><h3>Sanction Order Report</h3></th>

					</tr>
					<tr style="border-bottom: 1pt solid black;">
						<th align="left"><span>Sr. No.</span></th>
						<th align="left"><span>Year</span></th>
						<th align="left">Program Name</th>
						<th align="left">Sanction Order Date</th>
						<th>Sanction Order No.</th>
						<th align="left">Category</th>
						<th align="right">Amount In Rs.</th>
						<th align="right">Amount In Lakh</th>
					</tr>
					<c:forEach items="${sanctionOrderList}" var="sanctionOrder"
						varStatus="status">
						<tr style="border-bottom: 1pt solid gray;">
							<c:forEach items="${sanctionOrder.sanctionOrderDetails}"
								var="orderDetail" varStatus="rowNum">
								<c:set var="totRowOfSanOrdDtl"
									value="
						${rowNum.count + 1}"></c:set>
							</c:forEach>
							<td rowspan="${totRowOfSanOrdDtl}"><span>${status.count}</span></td>
							<td rowspan="${totRowOfSanOrdDtl}"><span>${sanctionOrder.year}</span></td>
						</tr>
						<c:forEach items="${sanctionOrder.sanctionOrderDetails}"
							var="orderDetail" varStatus="status">
							<tr style="border-bottom: 1pt solid gray;">
								<td><span>${orderDetail.orderProgramName}</span></td>
								<td><fmt:formatDate value="${orderDetail.orderDate}"
										var="dateString" pattern="dd/MM/yyyy" /> <span>${dateString}</span>
								</td>
								<td><span>${orderDetail.orderNumber}</span></td>
								<td><span>${orderDetail.orderCategory}</span></td>
								
								<td align="right"><span>${orderDetail.orderAmt}</span></td>
								<td align="right"><span>${orderDetail.orderAmtInLakh}</span></td>
								<c:set var="totAmt"
									value="${ totAmt+orderDetail.orderAmt }"></c:set>
								<c:set var="totAmtInLakh"
									value="${ totAmtInLakh+orderDetail.orderAmt }"></c:set>
							</tr>
						</c:forEach>

					</c:forEach>
					<tr>
						<th colspan="6" >Total</th>
						<th align="right">${ totAmt }</th>
						<th align="right" >${ ( totAmtInLakh / 100000) }</th>
					</tr>
				</table>
				<c:out value=""></c:out>
			</div>
		</c:when>
		<c:otherwise>
			<div>
				<table border="0" style="border-collapse: collapse; width: 100%">
					<tr>
						<th>
							<h3>
								<a href="viewSanctionOrderFilter">Back</a>
							</h3>
						</th>
					</tr>
					<tr>
						<th  align="center"><h3>No Record Found</h3></th>
					</tr>
				</table>
			</div>
		</c:otherwise>
	</c:choose>


</body>
</html>