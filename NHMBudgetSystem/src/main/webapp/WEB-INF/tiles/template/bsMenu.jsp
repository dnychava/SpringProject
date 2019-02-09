<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<div>
<ul id="menu" class="collapse">
                <li class="panel active">
                    <a href="${pageContext.request.contextPath}/home" >
                        <i class="icon-table"></i> Dashboard
                    </a>                   
                </li>

                <li class="panel ">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle collapsed" data-target="#form-nav">
                        <i class="icon-pencil"></i> Forms
	   
                        <span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                          &nbsp; <span class="label label-success">2</span>&nbsp;
                    </a>
                    <ul class="collapse" id="form-nav">
                        
                        <sec:authorize access="hasRole('ROLE_ADMIN')">
			              <li class=""><a href="${pageContext.request.contextPath}/viewSanctionOrder">Sanction</a></li>
			            </sec:authorize>
			            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
						  <li class=""><a href="${pageContext.request.contextPath}/viewSanctionOrderFilter">Sanction Order</a></li>
			            </sec:authorize>
                        <!-- <li class=""><a href="forms_general.html"><i class="icon-angle-right"></i> General </a></li>
                        <li class=""><a href="forms_advance.html"><i class="icon-angle-right"></i> Advance </a></li>
                        <li class=""><a href="forms_validation.html"><i class="icon-angle-right"></i> Validation </a></li>
                        <li class=""><a href="forms_fileupload.html"><i class="icon-angle-right"></i> FileUpload </a></li>
                        <li class=""><a href="forms_editors.html"><i class="icon-angle-right"></i> WYSIWYG / Editor </a></li> -->
                    </ul>
                </li>

                <li class="panel">
                    <a href="#" data-parent="#menu" data-toggle="collapse" class="accordion-toggle" data-target="#pagesr-nav">
                        <i class="icon-table"></i> Reports
	   
                        <span class="pull-right">
                            <i class="icon-angle-left"></i>
                        </span>
                          &nbsp; <span class="label label-info">3</span>&nbsp;
                    </a>
                    <ul class="collapse" id="pagesr-nav">
                        <li><a href="#"><i class="icon-angle-right"></i> Report1 </a></li>
                        <li><a href="#"><i class="icon-angle-right"></i> Report2 </a></li>
                        <li><a href="#"><i class="icon-angle-right"></i> Report3 </a></li>
                    </ul>
                </li>
            </ul>
</div>            