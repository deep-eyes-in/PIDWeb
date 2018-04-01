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
			<h1><s:message code="module.creer"/></h1>
		</c:when>
		<c:otherwise>
			<h1><s:message code="module.modifier"/></h1>
		</c:otherwise>
	</c:choose>
	<br />

	<s:url value="/module/signup" var="moduleActionUrl" />
		Maintenez le bouton CTRL pour en séléctionner plusieurs
	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="signUp"
		action="${moduleActionUrl}">

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />




		<s:bind path="modules">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="modules" class="col-sm-2 control-label">
					<s:message code="module.code" />
				</sf:label>
				<div class="col-sm-5">
					<s:message code="module.code" var="lblLangue" />
					<sf:select path="modules" class="form-control">

						<sf:options items="${moduleList}" />
					</sf:select>
					<sf:errors path="modules" class="control-label" />
				</div>
				<div class="col-sm-5"></div>

			</div>
		</s:bind>
		
		
		<s:bind path="etudiant">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="modules" class="col-sm-2 control-label">
					<s:message code="etudiant.username" />
				</sf:label>
				<div class="col-sm-5">
					<s:message code="cours.langue.defaut" var="lblLangue" />
					<sf:select path="etudiant" class="form-control">
						<sf:option value="None" label="-- Choisissez l'étudiant --" />
						<sf:options items="${etudiantList}" />
					</sf:select>
					<sf:errors path="etudiant" class="control-label" />
				</div>
				<div class="col-sm-5"></div>

			</div>
		</s:bind>
		

		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${empty savedId}">
						<%--  Pour un Ajout le "savedId" ne doit pas exister --%>
						<button type="submit" class="btn-lg btn-primary pull-right">
							<s:message code="module.btAJout" />
						</button>
					</c:when>
					<c:otherwise>
						<%-- Pour un update "savedId" doit avoir l'identifiant avant l'update --%>
						<button type="submit" name="savedId" value="${savedId}"
							class="btn-lg btn-primary pull-right">
							<s:message code="module.btMaj" />
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</sf:form>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>