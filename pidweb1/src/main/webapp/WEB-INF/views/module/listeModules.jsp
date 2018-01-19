<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Modules Résultats ISFCE" />
</jsp:include>
<div class="container">
<h1>Liste des modules <c:out value=" ${userModules}" default=""/></h1>

<c:if test="${fn:length(moduleList) == 0}">
	<h2>Liste Vide</h2>
</c:if>
<h2>La liste contient: ${fn:length(moduleList)} modules</h2>

<ul class="nivUn">

	<c:forEach items="${moduleList}" var="module">
		<li id="Code_<c:out value="${module.code}"/>">
		<c:out	value="${module.code}" /> 
		
<%-- 			<s:url value="${module.code}" var="moduleUrl" /> --%>
<!-- 			<button class="btn btn-info"  -->
<%-- 				onclick="location.href='${moduleUrl}'">Détail</button> --%>
			
<%-- 			<s:url value="/module/${module.code}/update" var="updateUrl" /> --%>
<!-- 			<button class="btn btn-primary"  -->
<%-- 				onclick="location.href='${updateUrl}'">Update</button> --%>
			
			
<%-- 			<s:url value="/module/${module.code}/delete" var="deleteUrl" /> --%>
<!-- 			<button class="btn btn-danger" -->
<%-- 				onclick="this.disabled=true;post('${deleteUrl}',{'${_csrf.parameterName}': '${_csrf.token}'})">Delete</button> --%>

			<ul class="nivDeux">
			<fmt:formatDate value="${module.dateDebut}" pattern="dd/MM/yyyy" var="dateD"/>
			<fmt:formatDate value="${module.dateFin}" pattern="dd/MM/yyyy" var="dateF"/>
				<li><c:out value="${module.cours.nom}" /></li>
				<li>Dates: <c:out value="${module.moment}  ${dateD} ==> ${dateF}" /> </li>
				<li><c:out value="${module.prof.nom}" default="---"/></li>
			</ul>
					<s:url value="${module.code}" var="moduleUrl" />
					<button class="btn btn-info" 
						onclick="location.href='${moduleUrl}'">Détail</button>
					
		<!--  -->
					
					<s:url value="/module/${module.code}/update" var="updateUrl" />
					<button class="btn btn-primary" 
						onclick="location.href='${updateUrl}'">Update</button>
					
					
					<s:url value="/module/${module.code}/delete" var="deleteUrl" />
					<button class="btn btn-danger"
						onclick="this.disabled=true;post('${deleteUrl}')">Delete</button>
		</li>
	</c:forEach>
</ul>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>