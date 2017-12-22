<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<html>

<jsp:include page="fragments/header.jsp" >
 <jsp:param name="titre" value="Home page Résultats ISFCE"/>
</jsp:include>

	<div class="container">
	<h1> <s:message code="resultats.titre"/> </h1>
		<h2><s:message code="resultats.sujet"/>:  ${prof}</h2>
		
		<P>The time on the server is ${serverTime}.</P>

		<p>Serveur: ${pageContext.request.serverName}</p>
		<p>Adresse Locale: ${pageContext.request.localAddr}</p>
		<p>Locale: ${pageContext.request.locale } </p>
		<p>Locale cookie : ${cookie['maLocaleCookie'].value} </p>

		<a href="<s:url value = "/cours/liste" />"> Liste des cours</a>
	</div>

	<jsp:include page="fragments/footer.jsp" />
</html>
