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
			<h1><s:message code="cours.creer"/></h1>
		</c:when>
		<c:otherwise>
			<h1><s:message code="cours.modifier"/></h1>
		</c:otherwise>
	</c:choose>
	<br />

	<s:url value="/cours/add" var="coursActionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="cours"
		action="${coursActionUrl}">

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />


		<s:bind path="code">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="code" class="col-sm-2 control-label">
					<s:message code="cours.code" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="code" id="code" class="form-control"
						placeholder="Code du cours" />
					<sf:errors path="code" class="control-label" />

				</div>

			</div>
		</s:bind>

		<s:bind path="nom">
			<div class="form-group ${status.error ? 'has-error' : ''} ">
				<sf:label path="nom" class="col-sm-2 control-label">
					<s:message code="cours.nom" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="nom" id="nom" class="form-control"
						placeholder="Nom du cours" />
					<sf:errors path="nom" class="control-label" />
				</div>
			</div>
		</s:bind>

		<s:bind path="nbPeriodes">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="nbPeriodes" class="col-sm-2 control-label">
					<s:message code="cours.nbPeriodes" />
				</sf:label>
				<div class="col-sm-10">
					<sf:input path="nbPeriodes" id="nbPeriodes" class="form-control"
						placeholder="Nombre de périodes du cours" />
					<sf:errors path="nbPeriodes" class="control-label" />
				</div>
			</div>
		</s:bind>
		<s:bind path="langue">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="langue" class="col-sm-2 control-label">
					<s:message code="cours.langue" />
				</sf:label>
				<div class="col-sm-5">
					<s:message code="cours.langue.defaut" var="lblLangue" />
					<sf:select path="langue" class="form-control">
						<sf:option value="None" label="${lblLangue}" />
						<sf:options items="${languesList}" />
					</sf:select>
					<sf:errors path="langue" class="control-label" />
				</div>
				<div class="col-sm-5"></div>

			</div>
		</s:bind>
		
		<s:bind path="section">
			<div class="form-group ${status.error ? 'has-error' : ''}">
				<sf:label path="section" class="col-sm-2 control-label">
					<s:message code="cours.section" />
				</sf:label>
				<div class="col-sm-5">
					<s:message code="cours.section.defaut" var="lblSection" />
					<sf:select path="section" class="form-control">
						<sf:option value="None" label="${lblSection}" />
						<sf:options items="${sectionsList}" />
					</sf:select>
					<sf:errors path="section" class="control-label" />
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
							<s:message code="cours.btAJout" />
						</button>
					</c:when>
					<c:otherwise>
						<%-- Pour un update "savedId" doit avoir l'identifiant avant l'update --%>
						<button type="submit" name="savedId" value="${savedId}"
							class="btn-lg btn-primary pull-right">
							<s:message code="cours.btMaj" />
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

	</sf:form>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>