<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>




<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
       request.setCharacterEncoding("UTF-8");
%>


<html>

<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Cours Résultats ISFCE" />
</jsp:include>

<div class="container">
	
		<c:choose>
		<%--  Pour un Ajout le paramètre "savedId" ne doit pas exister --%>
		
		<c:when test="${empty competence.description}">
			<h1><c:out value="Ajout d'une compétence pour le cour : ${cours.nom}"></c:out></h1>
		</c:when>
		<c:otherwise>
			<h1><c:out value="Modifiation d'une compétence pour le cour : ${cours.nom}"></c:out></h1>
		</c:otherwise>
	</c:choose>
	

	<br />

	<s:url value="/competence/add" var="actionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="competence"  acceptCharset="UTF-8"
		action="${actionUrl}">
		

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />
		

		<s:bind path="competence">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="description" class="col-sm-2 control-label">
					<s:message code="module.code" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="description" id="description" class="form-control" value="${competence.description}"
						placeholder="Nom de la compétence" />
					<sf:errors path="description" class="control-label" />

				</div>

			</div>
		</s:bind>
	
	<sf:input path="cours.code" id="code" value="${cours.code}"	type="hidden" />
	<sf:input path="id" id="id" value="${competence.id}"	type="hidden" />
	
	<button type="submit" class="btn-lg btn-primary pull-right">
		Ajouter
	</button>
						
	</sf:form>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>