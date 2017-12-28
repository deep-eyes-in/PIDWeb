<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>
<html>
<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Cours Résultats ISFCE" />
</jsp:include>

<div class="container">
	<c:choose>
		<%--  Pour un Ajout le paramètre "savedId" ne doit pas exister --%>
		
		<c:when test="${empty savedId}">
			<h1><s:message code="etudiant.creer"/></h1>
		</c:when>
		<c:otherwise>
			<h1><s:message code="etudiant.modifier"/></h1>
		</c:otherwise>
	</c:choose>
	<br />

	<s:url value="/etudiant/add" var="etudiantActionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="etudiant"
		action="${etudiantActionUrl}">

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />



		<s:bind path="nom">
			<div class="form-group ${status.error ? 'has-error' : ''} ">
				<sf:label path="nom" class="col-sm-2 control-label">
					<s:message code="etudiant.nom" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="nom" id="nom" class="form-control"
						placeholder="Nom de l'étdiant" />
					<sf:errors path="nom" class="control-label" />

				</div>

			</div>
		</s:bind>
		
		<s:bind path="prenom">
			<div class="form-group ${status.error ? 'has-error' : ''} ">
				<sf:label path="prenom" class="col-sm-2 control-label">
					<s:message code="etudiant.prenom" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="prenom" id="prenom" class="form-control"
						placeholder="Prénom de l'étdiant" />
					<sf:errors path="prenom" class="control-label" />

				</div>

			</div>
		</s:bind>



		<s:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="email" class="col-sm-2 control-label">
					<s:message code="etudiant.email" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="email" id="email" class="form-control"
						placeholder="email de l'étudiant" />
					<sf:errors path="email" class="control-label" />
				</div>
			</div>
		</s:bind>



		<s:bind path="tel">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="tel" class="col-sm-2 control-label">
					<s:message code="etudiant.tel" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="tel" id="tel" class="form-control"
						placeholder="tel de l'étudiant" />
					<sf:errors path="tel" class="control-label" />
				</div>
			</div>
		</s:bind>




		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${empty savedId}">
						<%--  Pour un Ajout le "savedId" ne doit pas exister --%>
						<button type="submit" class="btn-lg btn-primary pull-right">
							<s:message code="etudiant.btAJout" />
						</button>
					</c:when>
					<c:otherwise>
						<%-- Pour un update "savedId" doit avoir l'identifiant avant l'update --%>
						<button type="submit" name="savedId" value="${savedId}"
							class="btn-lg btn-primary pull-right">
							<s:message code="etudiant.btMaj" />
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</sf:form>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>