<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>

<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  
  <link rel="shortcut icon" href="./resources/img/favicon.png">

  <title>National Health Mission</title>

  <!-- Bootstrap CSS -->
  <link href="./resources/css/bootstrap.min.css" rel="stylesheet">
  
  <link href="./resources/css/bootstrap-theme.css" rel="stylesheet">
  
  <link href="./resources/css/elegant-icons-style.css" rel="stylesheet" />
  <link href="./resources/css/font-awesome.min.css" rel="stylesheet" />
  
  <link href="./resources/css/widgets.css" rel="stylesheet">
  <link href="./resources/css/style.css" rel="stylesheet">
  <link href="./resources/css/style-responsive.css" rel="stylesheet" />
  
  <!-- Datepickers -->
  <link rel="stylesheet" href="./resources/css/bootstrap-datepicker.min.css">
  <!-- DataTable -->
  <link rel="stylesheet" href="./resources/css/dataTables.bootstrap.min.css">
  
    <!-- javascripts -->
  <!-- <script src="./resources/js/jquery.js"></script> 
  <!-- <script src="./resources/js/jquery-ui-1.10.4.min.js"></script> -->
  <script src="./resources/js/jquery-1.12.4.js"></script>
  
  <!-- <script type="text/javascript" src="./resources/js/jquery-ui-1.9.2.custom.min.js"></script> -->
  <!-- bootstrap -->
  <script src="./resources/js/bootstrap.min.js"></script>
  <!-- Datepicker -->
  <script src="./resources/js/bootstrap-datepicker.min.js"></script>
  
  <!-- Datepicker -->
  <script src="./resources/js/jquery.dataTables.min.js"></script>
  <script src="./resources/js/dataTables.bootstrap.min.js"></script>
  <!-- nice scroll -->
  <script src="./resources/js/jquery.scrollTo.min.js"></script>
  <script src="./resources/js/jquery.nicescroll.js" type="text/javascript"></script>
  
  <!-- <script src="./resources/js/jquery.customSelect.min.js"></script> -->
  <!--custome script for all page-->
  <script src="./resources/js/scripts.js"></script>
  <script src="./resources/js/jquery.slimscroll.min.js"></script>
    <!-- <script>
      //knob
      
      $(function() {
        $(".knob").knob({
          'draw': function() {
            $(this.i).val(this.cv + '%')
          }
        })
      });

     $(function() {
        $('select.styled').customSelect();
      });

    </script> -->
 
</head>
<body>
  <!-- container section start -->
  <section id="container" class="">
    
    <!-- header start -->
    <header class="header dark-bg">
      <div class="toggle-nav">
        <div class="icon-reorder tooltips" data-original-title="Toggle Navigation" data-placement="bottom"><i class="icon_menu">
        </i></div>
      </div>

      <!--logo start-->
	  
      <a href="#" class="logo">National Health Mission</a>
      <!--logo end-->

      <div class="top-nav notification-row">
        <ul class="nav pull-right top-menu">

          <!-- user login dropdown start-->
          <li class="dropdown">
            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <!-- <span class="profile-ava">
                                <img alt="" src="img/avatar1_small.jpg">
                            </span> -->
                            <span class="username">Welcome ${username}</span>
                            <b class="caret"></b>
                        </a>
            <ul class="dropdown-menu extended logout">
              <!-- <li>
                <a href="/"><i class="icon_key_alt"></i> Log Out</a>
              </li> -->
              <li>
              	<a class="text-muted" href="${pageContext.request.contextPath}/logout"><i class="icon_key_alt"></i>Log Out</a>
              </li>
              <li>
              	<label class="text-muted">Version : 0.0.8</label>
              </li>	
            </ul>
          </li>
          <!-- user login dropdown end -->
          
        </ul>
      </div>
    </header>
    
    <!--sidebar start-->
    <aside>
      <tiles:insertAttribute name="menu" />
    </aside>
    <!--sidebar end-->
    
    
    <!--main content start-->
    <section id="main-content">
      <section class="wrapper">
        <!--overview start-->
        <!-- <div class="row">
          <div class="col-lg-12">
            <h3 class="page-header"><i class="fa fa-laptop"></i> Dashboard</h3>
            <ol class="breadcrumb">
              <li><i class="fa fa-home"></i><a href="index">Home</a></li>
              <li><i class="fa fa-laptop"></i>Dashboard</li>
            </ol>
          </div>
        </div> -->

        <div class="row">
        	<div class="col-sm-12">
          		<tiles:insertAttribute name="body" />
        	</div>
        </div>

        </section>
    </section>
    
 </section>
  <!-- container section end -->

 </body> 

</html>