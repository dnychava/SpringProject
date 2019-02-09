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
<meta charset="utf-8">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.6/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/js/bootstrap-datepicker.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.7.1/css/bootstrap-datepicker.css"> -->
<style type="text/css">
.msg {
	color: green;
	font-size: 3pt;
}
</style>
</head>
<body>
	<div class="container-fluid">
		<a href="pendingReportFilter">Back</a>
		<section class="panel" >
			<div class="panel-heading">
				<div class="row">
					<div class="col-md-12">
						<h4>Pending Report View</h4>
					</div>
				</div>
			</div>
			<div class="panel-body">
		
		<c:choose>
			<c:when test="${not empty programGroupHM }">
				<table class="table  table-hover table-sm table-bordered table-responsive">
					<thead class="table-active">
						<tr class="m-0">
							<th rowspan="2" class="align-middle text-center" >Sr. No.</th>
							<th rowspan="2" class="align-middle text-center w-75" >Name of program</th>
							<th colspan="4" class="text-center">Sanction Order</th>
							<!--State Received -->
							<c:if test="${shareType == 'State' }">
								<th colspan="4" class="text-center">State Share</th>
								<th colspan="4"class="text-center">Received</th>
							</c:if>
							<!--Central Received -->
							<c:if test="${shareType == 'Central' }">
								<th colspan="4"class="text-center">Received</th>
							</c:if>
							<th colspan="4" class="text-center">Yet to be received</th>
						</tr>
						<tr>
							<!--Sanction Order -->
							<td class="text-right" >General</td>
							<td class="text-right">SCP</td>
							<td class="text-right">TSP</td>
							<td class="text-right">Total</td>
							<c:if test="${shareType == 'State' }">
								<!--State Share -->
								<td class="text-right">General</td>
								<td class="text-right">SCP</td>
								<td class="text-right">TSP</td>
								<td class="text-right">Total</td>
								
								<!--State Share Received -->
								<td class="text-right">General</td>
								<td class="text-right">SCP</td>
								<td class="text-right">TSP</td>
								<td class="text-right">Total</td>
							</c:if>
							<c:if test="${shareType == 'Central' }">
								<!--Central Share Received -->
								<td class="text-right">General</td>
								<td class="text-right">SCP</td>
								<td class="text-right">TSP</td>
								<td class="text-right">Total</td>
							</c:if>
							
							<!--Pending -->
							<td class="text-right">General</td>
							<td class="text-right">SCP</td>
							<td class="text-right">TSP</td>
							<td class="text-right">Total</td>
						</tr>
					</thead>
					<tbody>
						<!-- Grand Total  -->
						<!-- Sanction Order total -->
						<c:set var="grandTotSanGeneral" value="${0}"></c:set>
						<c:set var="grandTotSanSCP" value="${0}"></c:set>
						<c:set var="grandTotSanTSP" value="${0}"></c:set>
						<c:set var="grandTotSanTotal" value="${0}"></c:set>
						<!-- Sanction Order State or Central total -->
						<c:set var="grandTotStateOrCentralGeneral" value="${0}"></c:set>
						<c:set var="grandTotStateOrCentralSCP" value="${0}"></c:set>
						<c:set var="grandTotStateOrCentralTSP" value="${0}"></c:set>
						<c:set var="grandTotStateOrCentralTotal" value="${0}"></c:set>
						<!-- Sanction Order Received total -->
						<c:set var="grandTotReceivedGeneral" value="${0}"></c:set>
						<c:set var="grandTotReceivedSCP" value="${0}"></c:set>
						<c:set var="grandTotReceivedTSP" value="${0}"></c:set>
						<c:set var="grandTotReceivedTotal" value="${0}"></c:set>
						<!-- Sanction Order Pending total -->
						<c:set var="grandTotPendingGeneral" value="${0}"></c:set>
						<c:set var="grandTotPendingSCP" value="${0}"></c:set>
						<c:set var="grandTotPendingTSP" value="${0}"></c:set>
						<c:set var="grandTotPendingTotal" value="${0}"></c:set>
						
						<c:forEach var="programGroupMap" items="${ programGroupHM }" varStatus="status" >
							<!-- Sanction Order total -->
							<c:set var="totSanGeneral" value="${0}"></c:set>
							<c:set var="totSanSCP" value="${0}"></c:set>
							<c:set var="totSanTSP" value="${0}"></c:set>
							<c:set var="totSanTotal" value="${0}"></c:set>
							<!-- Sanction Order State or Central total -->
							<c:set var="totStateOrCentralGeneral" value="${0}"></c:set>
							<c:set var="totStateOrCentralSCP" value="${0}"></c:set>
							<c:set var="totStateOrCentralTSP" value="${0}"></c:set>
							<c:set var="totStateOrCentralTotal" value="${0}"></c:set>
							<!-- Sanction Order Received total -->
							<c:set var="totReceivedGeneral" value="${0}"></c:set>
							<c:set var="totReceivedSCP" value="${0}"></c:set>
							<c:set var="totReceivedTSP" value="${0}"></c:set>
							<c:set var="totReceivedTotal" value="${0}"></c:set>
							<!-- Sanction Order Pending total -->
							<c:set var="totPendingGeneral" value="${0}"></c:set>
							<c:set var="totPendingSCP" value="${0}"></c:set>
							<c:set var="totPendingTSP" value="${0}"></c:set>
							<c:set var="totPendingTotal" value="${0}"></c:set>
							<c:forEach var="programNameMap" items="${programGroupMap.value}" varStatus="progStatus" >
								<c:set var="rowspan" value="${ (progStatus.count) }"></c:set>
							</c:forEach>
							<c:forEach var="programNameMap" items="${programGroupMap.value}" varStatus="progStatus" >
								<tr class="m-0">
									<c:if test="${ progStatus.index == 0 }">
										<th rowspan="${rowspan }">${status.index + 1}</th>
									</c:if>
									<td class="w-75">${programNameMap.key}</td>
									<!--Sanction Order table - START -->
									<td class="text-right">${programNameMap.value.sanctionOrder.general }</td>
									<td class="text-right">${programNameMap.value.sanctionOrder.scp }</td>
									<td class="text-right">${programNameMap.value.sanctionOrder.tsp }</td>
									<td class="text-right">${programNameMap.value.sanctionOrder.total }</td>
									<c:set var="totSanGeneral" value="${totSanGeneral+programNameMap.value.sanctionOrder.general}"></c:set>
									<c:set var="totSanSCP" value="${totSanSCP+programNameMap.value.sanctionOrder.scp}"></c:set>
									<c:set var="totSanTSP" value="${ totSanTSP+programNameMap.value.sanctionOrder.tsp}"></c:set>
									<c:set var="totSanTotal" value="${totSanTotal+programNameMap.value.sanctionOrder.total}"></c:set>
									<!--Sanction Order table - END -->
									
									<c:if test="${shareType == 'State' }">
										<!--stateShare table - START -->
										<td class="text-right">${programNameMap.value.stateShare.general }</td>
										<td class="text-right">${programNameMap.value.stateShare.scp }</td>
										<td class="text-right">${programNameMap.value.stateShare.tsp }</td>
										<td class="text-right">${programNameMap.value.stateShare.total }</td>
										<c:set var="totStateOrCentralGeneral" value="${ totStateOrCentralGeneral + programNameMap.value.stateShare.general}"></c:set>
										<c:set var="totStateOrCentralSCP" value="${totStateOrCentralSCP + programNameMap.value.stateShare.scp}"></c:set>
										<c:set var="totStateOrCentralTSP" value="${totStateOrCentralTSP + programNameMap.value.stateShare.tsp}"></c:set>
										<c:set var="totStateOrCentralTotal" value="${totStateOrCentralTotal + programNameMap.value.stateShare.total}"></c:set>
										<!--stateShare table - END -->
										<!--stateShareRecevied table - START -->
										<td class="text-right">${programNameMap.value.stateShareRecevied.general }</td>
										<td class="text-right">${programNameMap.value.stateShareRecevied.scp }</td>
										<td class="text-right">${programNameMap.value.stateShareRecevied.tsp }</td>
										<td class="text-right">${programNameMap.value.stateShareRecevied.total }</td>
										<c:set var="totReceivedGeneral" value="${totReceivedGeneral + programNameMap.value.stateShareRecevied.general }"></c:set>
										<c:set var="totReceivedSCP" value="${totReceivedSCP + programNameMap.value.stateShareRecevied.scp}"></c:set>
										<c:set var="totReceivedTSP" value="${totReceivedTSP + programNameMap.value.stateShareRecevied.tsp}"></c:set>
										<c:set var="totReceivedTotal" value="${totReceivedTotal + programNameMap.value.stateShareRecevied.total}"></c:set>
										<!--Sanction Order table - END -->
									</c:if>
									
									<c:if test="${shareType == 'Central' }">
										<!--centralShareRecevied - START -->
										<td class="text-right">${programNameMap.value.centralShareRecevied.general }</td>
										<td class="text-right">${programNameMap.value.centralShareRecevied.scp }</td>
										<td class="text-right">${programNameMap.value.centralShareRecevied.tsp }</td>
										<td class="text-right">${programNameMap.value.centralShareRecevied.total }</td>
										<c:set var="totReceivedGeneral" value="${totReceivedGeneral + programNameMap.value.centralShareRecevied.general }"></c:set>
										<c:set var="totReceivedSCP" value="${totReceivedSCP + programNameMap.value.centralShareRecevied.scp}"></c:set>
										<c:set var="totReceivedTSP" value="${totReceivedTSP + programNameMap.value.centralShareRecevied.tsp}"></c:set>
										<c:set var="totReceivedTotal" value="${totReceivedTotal + programNameMap.value.centralShareRecevied.total}"></c:set>
										<!--centralShareRecevied - END -->
									</c:if>
									
									<!--Pending - START -->
									<td class="text-right">${programNameMap.value.pending.general }</td>
									<td class="text-right">${programNameMap.value.pending.scp }</td>
									<td class="text-right">${programNameMap.value.pending.tsp }</td>
									<td class="text-right">${programNameMap.value.pending.total }</td>
									<c:set var="totPendingGeneral" value="${ totPendingGeneral + programNameMap.value.pending.general}"></c:set>
									<c:set var="totPendingSCP" value="${totPendingSCP + programNameMap.value.pending.scp}"></c:set>
									<c:set var="totPendingTSP" value="${totPendingTSP + programNameMap.value.pending.tsp}"></c:set>
									<c:set var="totPendingTotal" value="${ totPendingTotal + programNameMap.value.pending.total}"></c:set>
									<!--Pending - END -->
								</tr>
							</c:forEach>
							<!--Sanction Program Main Group Info -->
							<tr class="table-active">
									<td colspan="2"> Total ${programGroupMap.key}</td>
									<!--Sanction Order table - START -->
									<td class="text-right">${totSanGeneral}</td>
									<td class="text-right">${totSanSCP}</td>
									<td class="text-right">${totSanTSP}</td>
									<td class="text-right">${totSanTotal}</td>
									<c:set var="grandTotSanGeneral" value="${grandTotSanGeneral + totSanGeneral }"></c:set>
									<c:set var="grandTotSanSCP" value="${grandTotSanSCP + totSanSCP}"></c:set>
									<c:set var="grandTotSanTSP" value="${grandTotSanTSP + totSanTSP}"></c:set>
									<c:set var="grandTotSanTotal" value="${grandTotSanTotal + totSanTotal }"></c:set>
									<!--Sanction Order table - END -->
									
									<c:if test="${shareType == 'State' }">
										<!--stateShare table - START -->
										<td class="text-right">${totStateOrCentralGeneral}</td>
										<td class="text-right">${totStateOrCentralSCP}</td>
										<td class="text-right">${totStateOrCentralTSP}</td>
										<td class="text-right">${totStateOrCentralTotal}</td>
										<c:set var="grandTotStateOrCentralGeneral" value="${grandTotStateOrCentralGeneral + totStateOrCentralGeneral}"></c:set>
										<c:set var="grandTotStateOrCentralSCP" value="${grandTotStateOrCentralSCP + totStateOrCentralSCP}"></c:set>
										<c:set var="grandTotStateOrCentralTSP" value="${grandTotStateOrCentralTSP + totStateOrCentralTSP}"></c:set>
										<c:set var="grandTotStateOrCentralTotal" value="${grandTotStateOrCentralTotal + totStateOrCentralTotal}"></c:set>
										<!--stateShare table - END -->
										<!--Sanction Order table - END -->
									</c:if>
									<!--stateShareRecevied table - START -->
									<td class="text-right">${totReceivedGeneral}</td>
									<td class="text-right">${totReceivedSCP}</td>
									<td class="text-right">${totReceivedTSP}</td>
									<td class="text-right">${totReceivedTotal}</td>
									<!-- Sanction Order Received total -->
									<c:set var="grandTotReceivedGeneral" value="${grandTotReceivedGeneral + totReceivedGeneral}"></c:set>
									<c:set var="grandTotReceivedSCP" value="${grandTotReceivedSCP + totReceivedSCP}"></c:set>
									<c:set var="grandTotReceivedTSP" value="${grandTotReceivedTSP + totReceivedTSP}"></c:set>
									<c:set var="grandTotReceivedTotal" value="${grandTotReceivedTotal + totReceivedTotal}"></c:set>
									
									<!--Pending - START -->
									<td class="text-right">${totPendingGeneral}</td>
									<td class="text-right">${totPendingSCP}</td>
									<td class="text-right">${totPendingTSP}</td>
									<td class="text-right">${totPendingTotal}</td>
									<c:set var="grandTotPendingGeneral" value="${grandTotPendingGeneral + totPendingGeneral}"></c:set>
									<c:set var="grandTotPendingSCP" value="${grandTotPendingSCP + totPendingSCP}"></c:set>
									<c:set var="grandTotPendingTSP" value="${grandTotPendingTSP + totPendingTSP}"></c:set>
									<c:set var="grandTotPendingTotal" value="${grandTotPendingTotal + totPendingTotal}"></c:set>
									<!--Pending - END -->
								</tr>
						</c:forEach>
						
					</tbody>
					<tfoot>
						<tr class="table-active">
							<th colspan="2"> Grand Total </th>
							<!--Sanction Order table - START -->
							<th class="text-right">${grandTotSanGeneral}</th>
							<th class="text-right">${grandTotSanSCP}</th>
							<th class="text-right">${grandTotSanTSP}</th>
							<th class="text-right">${grandTotSanTotal}</th>
							<!--Sanction Order table - END -->
							
							<c:if test="${shareType == 'State' }">
								<!--stateShare table - START -->
								<th class="text-right">${grandTotStateOrCentralGeneral}</th>
								<th class="text-right">${grandTotStateOrCentralSCP}</th>
								<th class="text-right">${grandTotStateOrCentralTSP}</th>
								<th class="text-right">${grandTotStateOrCentralTotal}</th>
								<!--Sanction Order table - END -->
							</c:if>
							<!--stateShareRecevied table - START -->
							<th class="text-right">${grandTotReceivedGeneral}</th>
							<th class="text-right">${grandTotReceivedSCP}</th>
							<th class="text-right">${grandTotReceivedTSP}</th>
							<th class="text-right">${grandTotReceivedTotal}</th>
							<!-- Sanction Order Received total -->
							
							<!--Pending - START -->
							<th class="text-right">${grandTotPendingGeneral}</th>
							<th class="text-right">${grandTotPendingSCP}</th>
							<th class="text-right">${grandTotPendingTSP}</th>
							<th class="text-right">${grandTotPendingTotal}</th>
							<!--Pending - END -->
						</tr>
					</tfoot>
				</table>
		<!-- <table class="table table-bordered">
        <thead>
            <tr class="m-0">
                <th class="w-25">25</th>
                <th class="w-50">50</th>
                <th class="w-25">25</th>
            </tr>
        </thead>
        <tbody>
            <tr class="m-0">
                <td class="w-25">123
                </td>
                <td class="w-50">
                    <p>3 wolf moon retro jean shorts chambray sustainable roof party. Shoreditch vegan artisan Helvetica. Tattooed Codeply Echo Park Godard kogi, next level irony ennui twee squid fap selvage. Meggings flannel Brooklyn literally small batch,
                        mumblecore PBR try-hard kale chips. Brooklyn vinyl lumbersexual bicycle rights, viral fap cronut leggings squid chillwave pickled gentrify mustache. 3 wolf moon hashtag church-key.</p>
                </td>
                <td class="w-25">789
                </td>
            </tr>
        </tbody>
        </table> -->
			</c:when>
			<c:otherwise>
				<div>
					<table>
						<tbody>
							<tr>
								<th align="center"><h3>No Record Found</h3></th>
							</tr>
						</tbody>	
						
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		</div>
		</section>
	</div>

</body>
</html>