<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE html>
<html>
<head>
    <title>National Health Mission</title>
     <meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
     
    <!-- GLOBAL STYLES -->
    <link rel="stylesheet" href="./resources/uibs/assets/plugins/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="./resources/uibs/assets/css/main.css" />
    <link rel="stylesheet" href="./resources/uibs/assets/css/theme.css" />
    <link rel="stylesheet" href="./resources/uibs/assets/css/MoneAdmin.css" />
    <link rel="stylesheet" href="./resources/uibs/assets/plugins/Font-Awesome/css/font-awesome.css" />
    <!--END GLOBAL STYLES -->

    <!-- PAGE LEVEL STYLES -->
    <link href="./resources/uibs/assets/css/layout2.css" rel="stylesheet" />
       <link href="./resources/uibs/assets/plugins/flot/examples/examples.css" rel="stylesheet" />
       <link rel="stylesheet" href="./resources/uibs/assets/plugins/timeline/timeline.css" />
    <!-- END PAGE LEVEL  STYLES -->
     
</head>
<!-- BEGIN BODY -->
<body class="padTop53" >
    <!-- MAIN WRAPPER -->
    <div id="wrap" >
      <!-- HEADER SECTION -->
      <div id="top">

            <nav class="navbar navbar-inverse navbar-fixed-top " style="padding-top: 10px;">
                <a data-original-title="Show/Hide Menu" data-placement="bottom" data-tooltip="tooltip" class="accordion-toggle btn btn-primary btn-sm visible-xs" data-toggle="collapse" href="#menu" id="menu-toggle">
                    <i class="icon-align-justify"></i>
                </a>
                <!-- LOGO SECTION -->
                <!-- <header class="navbar-header">

                    <a href="index.html" class="navbar-brand">
                    <img src="assets/img/logo.png" alt="" />
                        
                        </a>
                </header> -->
                <!-- END LOGO SECTION -->
                <ul class="nav navbar-top-links navbar-right">

                    <!--ADMIN SETTINGS SECTIONS -->
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="icon-user "></i>&nbsp; <i class="icon-chevron-down "></i>
                        </a>

                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="#"><i class="icon-user"></i> User Profile </a>
                            </li>
                            <li><a href="#"><i class="icon-gear"></i> Settings </a>
                            </li>
                            <li class="divider"></li>
                            <li><a href="login.html"><i class="icon-signout"></i> Login </a>
                            </li>
                        </ul>

                    </li>
                    <!--END ADMIN SETTINGS -->
                </ul>

            </nav>

        </div>
        <!-- END HEADER SECTION -->
        <!-- MENU SECTION -->
       <div id="left" >
            <tiles:insertAttribute name="menu" />
       </div>
        <!--END MENU SECTION -->
        <!--PAGE CONTENT -->
        <div id="content">
             
            <div class="inner" style="min-height: 700px;">
                <div class="row">
                    <div class="col-lg-12">
                        <h1> Admin Dashboard </h1>
                    </div>
                </div>
                  <hr />
                 <!--BLOCK SECTION -->
                 <div class="row">
                    <tiles:insertAttribute name="body" />
                </div>
                  <!--END BLOCK SECTION -->
                
            </div>

        </div>
        <!--END PAGE CONTENT -->
    
    </div>

<!-- GLOBAL SCRIPTS -->
    <script src="./resources/uibs/assets/plugins/jquery-2.0.3.min.js"></script>
     <script src="./resources/uibs/assets/plugins/bootstrap/js/bootstrap.min.js"></script>
    <script src="./resources/uibs/assets/plugins/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <!-- END GLOBAL SCRIPTS -->

    <!-- PAGE LEVEL SCRIPTS -->
    <script src="./resources/uibs/assets/plugins/flot/jquery.flot.js"></script>
    <script src="./resources/uibs/assets/plugins/flot/jquery.flot.resize.js"></script>
    <script src="./resources/uibs/assets/plugins/flot/jquery.flot.time.js"></script>
     <script  src="./resources/uibs/assets/plugins/flot/jquery.flot.stack.js"></script>
    <script src="./resources/uibs/assets/js/for_index.js"></script>
   
    <!-- END PAGE LEVEL SCRIPTS -->

</body>
</html>