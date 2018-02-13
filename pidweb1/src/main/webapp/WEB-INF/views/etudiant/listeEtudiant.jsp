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

<c:if test="${fn:length(etudiantList) == 0}">
	<h2>Liste Vide</h2>
</c:if>
<h2>La liste contient: ${fn:length(etudiantList)} etudiant</h2>
<a href="<s:url value = "/etudiant/add" />"> Ajout d'un etudiant</a>
<s:url value="">Ajout d'un etudiant</s:url>

<ul class="nivUn">

	<c:forEach items="${etudiantList}" var="etudiant">	
		<li id="Code_<c:out value="${etudiant.username}"/>">
		<c:out	value="${etudiant.username}" /> 
		
			<s:url value="${etudiant.username}" var="etudiantUrl" />
			<button class="btn btn-info" 
				onclick="location.href='${etudiantUrl}'">Détail</button>
			
			<s:url value="/etudiant/${etudiant.username}/update" var="updateUrl" />
			<button class="btn btn-primary" 
				onclick="location.href='${updateUrl}'">Update</button>
			
			
			<s:url value="/etudiant/${etudiant.username}/delete" var="deleteUrl" />
			<button class="btn btn-danger"
				onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>


			<ul class="nivDeux">
				<li>	Nom:  <c:out value="${etudiant.nom}" />		</li>
				<li>	Prenom: <c:out value="${etudiant.prenom}" /></li>
				<li>	email:  <c:out value="${etudiant.email}" />		</li>

			</ul>
			
		
					

		</li>
	</c:forEach>

</ul>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>