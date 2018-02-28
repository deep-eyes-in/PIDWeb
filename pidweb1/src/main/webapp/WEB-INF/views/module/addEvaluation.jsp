<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>


<html>






<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="module Résultats ISFCE" />
</jsp:include>



<div class="container">

	<div class='row'>
		<h1>
			Mise a jours des evaluations du module :
			<c:out value="${module}" />
			<br /> Pour la session :
			<c:out value="${session}" />
			<br />
		</h1>
		<h3>
			Il y a
			<c:out value="${ fn:length(evaluationList) }" />
			etudiant(s)
		</h3>
	</div>


	<br />

	<s:url value="/evaluation/update" var="moduleActionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="evaluation"
		action="${moduleActionUrl}">

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />
		
		
		
		<c:forEach items="${evaluationList}" var="evaluation">
		--

						<c:out	value="${evaluation.key.etudiant.nom}" />

						<c:out	value="${evaluation.value}" />
						
						

					
					

		</c:forEach>


	</sf:form>
	
	
	
	
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>


















