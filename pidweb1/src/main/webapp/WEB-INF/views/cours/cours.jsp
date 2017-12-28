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
			<td>Sections :</td>
			<td>
			<c:forEach items="${cours.section}" var="section">
				<span> [	<c:out value="${section}"/>	 	] </span>
			</c:forEach>
			</td>
		</tr>
	</table>
</div>	



	<jsp:include page="../fragments/footer.jsp" />

</html>