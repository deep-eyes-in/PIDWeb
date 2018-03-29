<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>


<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Cours Résultats ISFCE"/>
</jsp:include>
<div class="container">
	<h1>
		Détail du cours:
		<c:out value="${cours.nom}" default="-----"></c:out>
	</h1>

	<table>
		<tr>
			<td>Code:</td>
			<td><c:out value="${cours.code}" /></td>
		</tr>
		<tr>
			<td>Nom:</td>
			<td><c:out value="${cours.nom}" /></td>
		</tr>
		<tr>
			<td>NbPeriodes:</td>
			<td><c:out value="${cours.nbPeriodes}" /></td>
		</tr>
		<tr>
			<td>Langue:</td>
			<td><c:out value="${cours.langue}" /></td>
		</tr>
	</table>
	
	<br />
	
	<div class="divTable blueTable">
		<div class="divTableHeading">
			<div class="divTableRow">
			<div class="divTableHead">Description</div>
			<c:if test="${ isAdmin }">
				<div class="divTableHead">Modifier</div>
			</c:if>
		</div>
		
		</div>
		<div class="divTableBody">
			<c:forEach items="${competenceList}" var="competence">
				<div class="divTableRow">
				<div class="divTableCell"><c:out value="${competence.description}" /></div>
				<c:if test="${ isAdmin }">
					<div class="divTableCell">  
						<s:url value="/competence/update/${competence.id}" var="updateUrl" />
						<button class="btn btn-info" onclick="location.href='${updateUrl}'">Modifier</button>
					</div>
				</c:if>
				</div>
			</c:forEach>
		</div>
	</div>
	
	<br />
	
	<div class="divTable blueTable">
		<div class="divTableHeading">
			<div class="divTableRow">
			<div class="divTableHead">Code du module</div>
			<div class="divTableHead">Détails</div>
		</div>
		
		</div>
		<div class="divTableBody">
			<c:forEach items="${moduleList}" var="module">
				<div class="divTableRow">
				<div class="divTableCell"><c:out value="${module}" /></div>
				<div class="divTableCell"><a href="../module/${module}/">Détails</a></div>
				</div>
			</c:forEach>
		</div>
	</div>
	
</div>	
	<jsp:include page="../fragments/footer.jsp" />

</html>