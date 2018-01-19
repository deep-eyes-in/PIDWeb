<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%-- <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> --%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Cours Résultats ISFCE" />
</jsp:include>
<div class="container">
<h1>Liste des cours</h1>

<c:if test="${fn:length(coursList) == 0}">
	<h2>Liste Vide</h2>
</c:if>
<h2>La liste contient: ${fn:length(coursList)} cours</h2>
<a href="<s:url value = "/cours/add" />"> Ajout d'un cours</a>
<s:url value="">Ajout d'un cours</s:url>
<ul class="nivUn">

	<c:forEach items="${coursList}" var="cours">
		<li id="Code_<c:out value="${cours.code}"/>">
		<c:out	value="${cours.code}" /> 
		
			<s:url value="${cours.code}" var="coursUrl" />
			<button class="btn btn-info" 
				onclick="location.href='${coursUrl}'">Détail</button>
			
			<s:url value="/cours/${cours.code}/update" var="updateUrl" />
			<button class="btn btn-primary" 
				onclick="location.href='${updateUrl}'">Update</button>
			
			
			<s:url value="/cours/${cours.code}/delete" var="deleteUrl" />
			<button class="btn btn-danger"
				onclick="
				if (confirm('Suppression du cours  ?')) {
				 this.disabled=true;
                 post('${deleteUrl}',{'${_csrf.parameterName}': '${_csrf.token}'})}                             
                                              ">Delete</button>

			<ul class="nivDeux">
				<li><c:out value="${cours.nom}" /></li>
				<li>Nb Périodes: <c:out value="${cours.nbPeriodes}" />
				</li>
			</ul>

		</li>
	</c:forEach>
</ul>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>