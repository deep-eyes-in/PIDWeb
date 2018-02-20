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
				<button class="btn btn-success" onclick="this.disabled=true;post('${showModules}', {'${_csrf.parameterName}': '${_csrf.token}'})">Voir ses modules
				</button>
			</td>
		</tr>
		<tr>
		</tr>
	</table>
	
	<h1>
		Inscription de l'étudiant:
	</h1>
	
				<table>
					<tr>
						<td> Desinscrire  </td>
						<td>Code du module</td>
						<td>Nom du cours</td>
					</tr>
					<c:forEach items="${listModules}" var="module">
						<tr>
							<td> <a href="<s:url value = "/module/remove/${module.code}/${etudiant.username}" />"> X </a>  </td>
							<td><c:out value="${module.code}" /></td>
							<td><c:out value="${module.cours.nom}" /></td>
						</tr>
					</c:forEach>

				</table>
	
</div>



	
	<jsp:include page="../fragments/footer.jsp" />

</html>