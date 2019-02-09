<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<div id="sidebar" class="nav-collapse ">
        <!-- sidebar menu start-->
        <ul class="sidebar-menu">
          <li class="active">
            <a class="" href="${pageContext.request.contextPath}/home">
                          <i class="icon_house_alt"></i>
                          <span>Home</span>
                      </a>
          </li>
          <li class="sub-menu">
            <a href="javascript:;" class="">
                          <i class="icon_document_alt"></i>
                          <span>Forms</span>
                          <span class="menu-arrow arrow_carrot-right"></span>
                      </a>
            <ul class="sub">
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
              	<li><a href="${pageContext.request.contextPath}/viewSanctionOrder">Sanction</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
              	<li><a href="${pageContext.request.contextPath}/viewGrSanctionOrder">GR Order</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
              	<li><a href="${pageContext.request.contextPath}/viewBank">Bank Detail</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
              	<li><a href="${pageContext.request.contextPath}/viewBudget">Budget</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
              	<li><a href="${pageContext.request.contextPath}/viewDataUploadPage">Data Upload</a></li>
              </sec:authorize>
            </ul>
          </li>
          <li class="sub-menu">
            <a href="javascript:;" class="">
                          <i class="icon_document_alt"></i>
                          <span>Master Forms</span>
                          <span class="menu-arrow arrow_carrot-right"></span>
                      </a>
            <ul class="sub">
              <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
              	<li><a href="${pageContext.request.contextPath}/viewMainGroup">Main Group</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
              	<li><a href="${pageContext.request.contextPath}/viewMstrPrgrmName">Budget</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN')">
              	<li><a href="${pageContext.request.contextPath}/fileUpload">Upload Data</a></li>
              </sec:authorize>
            </ul>
          </li>
          <li class="sub-menu">
            <a href="javascript:;" class="">
                          <i class="icon_table"></i>
                          <span>Reports</span>
                          <span class="menu-arrow arrow_carrot-right"></span>
                      </a>
            <ul class="sub">
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
			  		<li><a href="${pageContext.request.contextPath}/viewSanctionOrderFilter">Sanction Order</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
			  		<li><a href="${pageContext.request.contextPath}/pendingReportFilter">Pending</a></li>
              </sec:authorize>
              <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
			  		<li><a href="${pageContext.request.contextPath}/viewDelayDaysReportFilter">Delay Days</a></li>
              </sec:authorize>
            </ul>
          </li>
          
        </ul>
        <!-- sidebar menu end-->
      </div>