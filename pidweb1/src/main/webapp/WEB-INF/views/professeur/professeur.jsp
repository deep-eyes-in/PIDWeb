<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>


<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Professeur Résultats ISFCE"/>
</jsp:include>


<div class="container">
	<h1>
		Détail de professeur:
		<c:out value="${professeur.nom}" default="-----"></c:out>
	</h1>

	<table>

 
		<tr>
			<td>Code:</td>
			<td><c:out value="${professeur.code}" /></td>
		</tr>

		<tr>
			<td>Nom:</td>
			<td><c:out value="${professeur.nom}" /></td>
		</tr>
		<tr>
			<td>Prénom:</td>
			<td><c:out value="${professeur.prenom}" /></td>
		</tr>		

		<tr>
			<td colspan="2">
			</td>
		</tr>
		<tr>
		</tr>
	</table>
	
</div>



	
	<jsp:include page="../fragments/footer.jsp" />

</html>