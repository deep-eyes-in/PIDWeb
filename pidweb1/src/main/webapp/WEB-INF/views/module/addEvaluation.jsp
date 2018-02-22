<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>

<html>

<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="module Résultats ISFCE" />
</jsp:include>

<div class="container">


	<h1><s:message code="module.modifier"/></h1>

	
	<br />

	<s:url value="/evaluation/update" var="evaluationActionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	
	<sf:form method="POST" class="form-horizontal" modelAttribute="listeEvaluations"
		action="${evaluationActionUrl}">
		
		<c:forEach items="${evaluationList}" var="evaluation">
		
		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />
		
		<s:bind path="infos">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="resultat" class="col-sm-2 control-label">
					<c:out value="${ evaluation.infos.etudiant.nom }" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="resultat" id="result" class="form-control"
						placeholder="Résultat de l'évaluation" />
					<sf:errors path="resultat" class="control-label" />

				</div>

			</div>
		</s:bind>
		
		<s:bind path="id">
			<input type="hidden" name="id" value="${ evaluation.id }">
		</s:bind>
		
		</c:forEach>
		
				<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">

				<%-- Pour un update "savedId" doit avoir l'identifiant avant l'update --%>
				<button type="submit"
					class="btn-lg btn-primary pull-right">
					<s:message code="module.btMaj" />
				</button>

			</div>
		</div>
		
	</sf:form>
	
	
	
	
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>