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
<h1>Liste des module</h1>

<c:if test="${fn:length(moduleList) == 0}">
	<h2>Liste Vide</h2>
</c:if>


<h2>La liste contient: ${fn:length(moduleList)} module</h2>


<a href="<s:url value = "/module/add" />"> Ajout d'un module</a>
<s:url value="">Ajout d'un module</s:url>
<ul class="nivUn">

	<c:forEach items="${moduleList}" var="module">
	
		<li id="Code_<c:out value="${module.code}"/>">
		<c:out	value="${module.code}" /> 
		
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
				
<!--  -->

			<ul class="nivDeux">
				<li>Nb dateDebut: <c:out value="${module.dateDebut}" />			</li>
				<li>Nb dateFin: <c:out value="${module.dateFin}" />				</li>
				<li>Nb moment: <c:out value="${module.moment}" />				</li>
<%-- 				<li>Nb cours: <c:out value="${module.cours}" />					</li> --%>
				<li>Nb professeur: <c:out value="${module.professeur}" />		</li>
								
				
<c:forEach items="${intitule( module.cours )}" var="cours">
     Intitulé du cours: ${cours.name}
</c:forEach>

				
				
			</ul>
			

		</li>
		
	</c:forEach>
	
	
	
</ul>
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>