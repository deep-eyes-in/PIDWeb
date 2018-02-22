<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>


<html>






	


<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="module Résultats ISFCE" />
</jsp:include>

<div class="container">

	<c:choose>
		<%--  Pour un Ajout le paramètre "savedId" ne doit pas exister --%>
		
		<c:when test="${empty savedId}">
			<h1><s:message code="module.creer"/></h1>
		</c:when>
		<c:otherwise>
			<h1><s:message code="module.modifier"/></h1>
		</c:otherwise>
	</c:choose>
	
	
	<br />

	<s:url value="/module/add" var="moduleActionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="module"
		action="${moduleActionUrl}">

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />




	</sf:form>
	
	
	
	
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>