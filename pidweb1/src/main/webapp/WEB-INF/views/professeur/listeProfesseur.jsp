<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Professeur Résultats ISFCE" />
</jsp:include>
<div class="container">
<h1>Liste des Professeurs</h1>

<c:if test="${fn:length(professeurList) == 0}">
	<h2>Liste Vide</h2>
</c:if>
<h2>La liste contient: ${fn:length(professeurList)} professeur</h2>
<a href="<s:url value = "/professeur/add" />"> Ajout d'un professeur</a>
<s:url value="">Ajout d'un professeur</s:url>

<ul class="nivUn">

	<c:forEach items="${professeurList}" var="professeur">	
		<li id="Code_<c:out value="${professeur.username}"/>">
		<c:out	value="${professeur.username}" /> 
		
			<s:url value="${professeur.username}" var="professeurUrl" />
			<button class="btn btn-info" 
				onclick="location.href='${professeurUrl}'">Détail</button>
			
			<s:url value="/professeur/${professeur.username}/update" var="updateUrl" />
			<button class="btn btn-primary" 
				onclick="location.href='${updateUrl}'">Update</button>
			
			
			<s:url value="/professeur/${professeur.username}/delete" var="deleteUrl" />
			<button class="btn btn-danger"
				onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>


			<ul class="nivDeux">
				<li>	Nom:  <c:out value="${professeur.nom}" />		</li>
				<li>	Prenom: <c:out value="${professeur.prenom}" /></li>
				<li>	email:  <c:out value="${professeur.email}" />		</li>

			</ul>
			
		
					

		</li>
	</c:forEach>

</ul>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>