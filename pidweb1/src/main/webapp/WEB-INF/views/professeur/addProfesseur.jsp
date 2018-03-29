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
			<h1><s:message code="professeur.creer"/></h1>
		</c:when>
		<c:otherwise>
			<h1><s:message code="professeur.modifier"/> : "${professeur.username }"</h1>
		</c:otherwise>
	</c:choose>
	<br />

	<s:url value="/professeur/add" var="professeurActionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="professeur"
		action="${professeurActionUrl}">

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />

<s:bind path="username">
			<div class="form-group ${status.error ? 'has-error' : ''} ">

				<div class="col-sm-10">
					<sf:input path="username" id="username" class="form-control"
						placeholder="username du professeur" type="hidden" />
					<sf:errors path="username" class="control-label" />

				</div>

			</div>
		</s:bind>
		
		

		<s:bind path="password">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="password" class="col-sm-2 control-label">
					<s:message code="professeur.password" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="password" id="password" class="form-control"
						placeholder="password de professeur" value="*******" />
					<sf:errors path="password" class="control-label" />
				</div>
			</div>
		</s:bind>
		
			
		
		

		<s:bind path="nom">
			<div class="form-group ${status.error ? 'has-error' : ''} ">
				<sf:label path="nom" class="col-sm-2 control-label">
					<s:message code="professeur.nom" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="nom" id="nom" class="form-control"
						placeholder="Nom de professeur" />
					<sf:errors path="nom" class="control-label" />

				</div>

			</div>
		</s:bind>
		
		<s:bind path="prenom">
			<div class="form-group ${status.error ? 'has-error' : ''} ">
				<sf:label path="prenom" class="col-sm-2 control-label">
					<s:message code="professeur.prenom" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="prenom" id="prenom" class="form-control"
						placeholder="Prénom de professeur" />
					<sf:errors path="prenom" class="control-label" />

				</div>

			</div>
		</s:bind>



		<s:bind path="email">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="email" class="col-sm-2 control-label">
					<s:message code="professeur.email" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="email" id="email" class="form-control"
						placeholder="email de professeur" />
					<sf:errors path="email" class="control-label" />
				</div>
			</div>
		</s:bind>







		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${empty savedId}">
						<%--  Pour un Ajout le "savedId" ne doit pas exister --%>
						<button type="submit" class="btn-lg btn-primary pull-right">
							<s:message code="professeur.btAJout" />
						</button>
					</c:when>
					<c:otherwise>
						<%-- Pour un update "savedId" doit avoir l'identifiant avant l'update --%>
						<button type="submit" name="savedId" value="${savedId}"
							class="btn-lg btn-primary pull-right">
							<s:message code="professeur.btMaj" />
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</sf:form>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>