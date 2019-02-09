<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<html>
<!-- BEGIN HEAD -->
<head>
     <meta charset="UTF-8" />
    <title>NHM | Login Page</title>
    <!-- PAGE LEVEL STYLES -->
     <link rel="stylesheet" href="./resources/css/bootstrap.css" />
    <link rel="stylesheet" href="./resources/css/login.css" />
    <link rel="stylesheet" href="./resources/css/magic.css" />
     <!-- END PAGE LEVEL STYLES -->
   
</head>
    <!-- END HEAD -->

    <!-- BEGIN BODY -->
<body onload="document.loginForm.j_username.focus();">

   <!-- PAGE CONTENT --> 
    <div class="container">
    <!-- <div class="text-center">
        <img src="assets/img/logo.png" id="logoimg" alt=" Logo" />
    </div> -->
    <div></br></br></br></br></br></br></br></br></div>
    <div class="tab-content">
        <div id="login" class="tab-pane active">
               
			<c:url var="loginUrl" value="/login" />
            <form action="${loginUrl}" method="post" class="form-signin">
                <p class="text-muted text-center btn-block btn btn-primary btn-rect">
                    Login to System
                </p>
                <input type="text" name="username" id="username" placeholder="Username" class="form-control" />
                <input type="password" name="password" id="password" placeholder="Password" class="form-control" />
                <button class="btn text-muted text-center btn-danger" type="submit">Sign in</button>
            </form>
				<table align="center">
					<tr>
						<td><c:if test="${not empty message}">
								<td style="font-style: italic; color: red;">${message}</td>
							</c:if>
						</td>
					</tr>
				</table>
		</div>
        <div id="forgot" class="tab-pane">
            <form action="index.html" class="form-signin">
                <p class="text-muted text-center btn-block btn btn-primary btn-rect">Enter your valid e-mail</p>
                <input type="email"  required="required" placeholder="Your E-mail"  class="form-control" />
                <br />
                <button class="btn text-muted text-center btn-success" type="submit">Recover Password</button>
            </form>
        </div>
        <div id="signup" class="tab-pane">
            <form action="index.html" class="form-signin">
                <p class="text-muted text-center btn-block btn btn-primary btn-rect">Please Fill Details To Register</p>
                 <input type="text" placeholder="First Name" class="form-control" />
                 <input type="text" placeholder="Last Name" class="form-control" />
                <input type="text" placeholder="Username" class="form-control" />
                <input type="email" placeholder="Your E-mail" class="form-control" />
                <input type="password" placeholder="password" class="form-control" />
                <input type="password" placeholder="Re type password" class="form-control" />
                <button class="btn text-muted text-center btn-success" type="submit">Register</button>
            </form>
        </div>
    </div>
    <div class="text-center">
        <ul class="list-inline">
            <li><a class="text-muted" href="#login" data-toggle="tab">Login</a></li>
            <li><a class="text-muted" href="#forgot" data-toggle="tab">Forgot Password</a></li>
            <li><a class="text-muted" href="#signup" data-toggle="tab">Signup</a></li>
            <%-- <li><a class="text-muted" href="${pageContext.request.contextPath}/home"><span>Home</span>
                      </a></li> --%>
        </ul>
    </div>


</div>

	  <!--END PAGE CONTENT -->     
	      
      <!-- PAGE LEVEL SCRIPTS -->
      <script src="./resources/js/jquery-2.0.3.min.js"></script>
      <script src="./resources/js/bootstrap.js"></script>
   <script src="./resources/js/login.js"></script>
      <!--END PAGE LEVEL SCRIPTS -->

</body>
    <!-- END BODY -->
</html>
