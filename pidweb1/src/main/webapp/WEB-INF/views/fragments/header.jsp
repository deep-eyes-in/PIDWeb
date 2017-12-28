<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<title>${param.titre}</title>
<!-- Required meta tags for bootstrap-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<!-- chargement des styles avec bootstrap first -->
<%-- <s:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" var="bootstrapCss" /> --%>
<s:url value="/resources/css/bootstrap.min.css" var="bootstrapCss" />
<s:url value="/resources/css/style.css" var="styleCss" />
<link rel="stylesheet" href="${bootstrapCss}"  />
<link rel="stylesheet"  href="${styleCss}" />

</head>
<body>
<!-- Image and text -->
<nav class="navbar navbar-inverse  ">
	<div class="container">
		<div class="navbar-header">
			<img src="<s:url value="/resources/img/logoISFCE.png"/>" width="50"
				height="50" class="d-inline-block align-top-left" /> <a
				href="http://isfce.org" target="blank">ISFCE</a>
		</div>
		<ul class="nav navbar-nav">
			<li class="active"><a href="<s:url value="/"/>">Home</a></li>
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">Cours <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="<s:url value = "/cours/liste"/>">liste</a></li>
					<li><a href="<s:url value = "/cours/add" />">add</a></li>
				</ul></li>
				
			<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">Etudiants <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="<s:url value = "/etudiant/liste"/>">liste</a></li>
					<li><a href="<s:url value = "/etudiant/add" />">add</a></li>
				</ul></li>
				
				<li class="dropdown"><a class="dropdown-toggle"
				data-toggle="dropdown" href="#">Professeur <span class="caret"></span></a>
				<ul class="dropdown-menu">
					<li><a href="<s:url value = "/professeur/liste"/>">liste</a></li>
					<li><a href="<s:url value = "/professeur/add" />">add</a></li>
				</ul></li>
				
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li><a href=""><span class="glyphicon glyphicon-log-out disabled"></span>
					Logout</a></li>
			<li><a href="<s:url value="/"/>"><span
					class="glyphicon glyphicon-log-in"></span> Login</a></li>
		</ul>
	</div>
</nav>

