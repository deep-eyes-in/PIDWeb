<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Etudiant Résultats ISFCE"/>
</jsp:include>


<div class="container">
	<h1>
		Détail de l'étudiant:
		<c:out value="${etudiant.nom}" default="-----"></c:out>
	</h1>

	<table>

<%-- 
		<tr>
			<td>Code:</td>
			<td><c:out value="${etudiant.id}" /></td>
		</tr>
--%>
		<tr>
			<td>Nom:</td>
			<td><c:out value="${etudiant.nom}" /></td>
		</tr>
		<tr>
			<td>Prénom:</td>
			<td><c:out value="${etudiant.prenom}" /></td>
		</tr>		

		<tr>
			<td colspan="2">
				&nbsp;
			</td>
		</tr>
			
		<tr>
			<td colspan="2">
				<s:url value="/module/liste/${etudiant.username}" var="showModules" />
				<button class="btn btn-success" onclick="this.disabled=true;post('${showModules}', {'${_csrf.parameterName}': '${_csrf.token}'})">Voir la liste des modules</button>
			</td>
		</tr>
		<tr>
		</tr>
	</table>
	
	<h1>
		Inscription de l'étudiant:
	</h1>
	
	

				
	<div class="divTable blueTable">
		<div class="divTableHeading">
			<div class="divTableRow">
			<c:if test="${ isAdmin }">
				<div class="divTableHead">Desinscrire</div>
			</c:if>
			<div class="divTableHead">Code du module</div>
			<div class="divTableHead">Nom du cours</div>
			<div class="divTableHead">Nombre de compétences validées</div>
			<div class="divTableHead">Status</div>
			<div class="divTableHead">Remarque du professeur</div>
		</div>
		</div>
		<div class="divTableBody">
			<c:set var="i" value="0"></c:set>
			<c:forEach items="${listModules}" var="module">
				<!-- VAR URL -->
				<s:url value="/evaluation/view/${eval.id}" var="detailUrl" />

			<div class="divTableRow">
				<c:if test="${ isAdmin }">
					<div class="divTableCell">  <a href="<s:url value = "/module/remove/${module.code}/${etudiant.username}" />"> X </a> </div>
				</c:if>
				<div class="divTableCell"><c:out value="${module.code}" /></div>
				<div class="divTableCell"><c:out value="${module.cours.nom}" /></div>
				<div class="divTableCell">
					<c:out value="${infosCompetences[i]}" />&nbsp;&nbsp;
					<a href="../competence/${module.code}/${etudiant.username}">Détails</a>
				</div>
				<div class="divTableCell"><c:out value="${statusModules[i]}" /></div>
				<div class="divTableCell"><c:out value="${evalComments[i]}" /></div>
			</div>
			<c:set var="i" value="${i + 1}"></c:set>
			</c:forEach>

		</div>
	</div>
	
</div>



	
	<jsp:include page="../fragments/footer.jsp" />

</html>