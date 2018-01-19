<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>



<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Cours Résultats ISFCE"/>
</jsp:include>
<div class="container">
	<h1>
		Détail du module:
		<c:out value="${module.code}" default="-----"></c:out>
	</h1>

	<table>
		<tr>
			<td>Code:</td>
			<td><c:out value="${module.code}" /></td>
		</tr>
		<tr>
			<td>Nom du cours:</td>
			<td><c:out value="${module.cours.nom}" /></td>
		</tr>
		<tr>
			<td>Nombre de périodes:</td>
			<td><c:out value="${module.cours.nbPeriodes}" /></td>
		</tr>
		<tr>
			<td>Langue:</td>
			<td><c:out value="${module.cours.langue}" /></td>
		</tr>
		<tr>
			<td>Professeur:</td>
			<td><c:out value="${module.prof.nom}" /></td>
		</tr>
	</table>
</div>	



	<jsp:include page="../fragments/footer.jsp" />

</html>