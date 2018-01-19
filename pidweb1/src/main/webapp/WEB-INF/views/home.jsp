<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>

<jsp:include page="fragments/header.jsp">
	<jsp:param name="titre" value="Home page Résultats ISFCE" />
</jsp:include>

<div class="container">
	<h1>
		<s:message code="resultats.titre" />
	</h1>
	<h2>
		<s:message code="resultats.sujet" />
		: ${prof}
	</h2>

	<P>The time on the server is ${serverTime}.</P>

	<p>Serveur: ${pageContext.request.serverName}</p>
	<p>Adresse Locale: ${pageContext.request.localAddr}</p>
	<p>Locale: ${pageContext.request.locale }</p>
	<p>Locale cookie : ${cookie['maLocaleCookie'].value}</p>
	<sec:authorize access="isAuthenticated()">
		<h2>
			<sec:authentication property="principal.username" />
		</h2>

		<!-- Affiche la liste des roles -->
		<sec:authentication property="authorities" var="roles" scope="page" />
		Your roles are:
		<ul>
			<c:forEach var="role" items="${roles}">
				<li>${role}</li>
			</c:forEach>
		</ul>
	</sec:authorize>

	<p><a href="<s:url value = "/cours/liste" />"> Liste des cours</a></p>
	<P><a href="<s:url value = "/module/liste" />"> Liste des modules</a></p>
</div>

<jsp:include page="fragments/footer.jsp" />
</html>
