<!DOCTYPE html>
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
		<tr>
		</tr>
	</table>
</div>	
	<jsp:include page="../fragments/footer.jsp" />

</html>