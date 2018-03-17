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
	<jsp:param name="titre" value="Competences Résultats ISFCE" />
</jsp:include>

<div class="container">
	
	<h1><c:out value="Module : ${module.cours.nom} "></c:out></h1>
	<h1><c:out value="Modifiation des compétence d'un étudiant : ${etudiant.username} "></c:out></h1>


	

	<%-- modelAttribute correspond à une clé dans le modèle --%>


		
			<div class="divTable blueTable">
				<div class="divTableHeading">
					<div class="divTableRow">
					<div class="divTableHead">Mastered</div>
					<div class="divTableHead">Description</div>
					<div class="divTableHead">Valider</div>
					<div class="divTableHead">Refuser</div>
					</div>
				</div>
				<div class="divTableBody">
				
					<c:forEach items="${validedCompetences}" var="comptValid">
							
						<!-- VAR URL -->
						<s:url value="/competence/${module.code}/${comptValid.competenceId}/${etudiant.username}/true" var="addActionUrl" />
						<s:url value="/competence/${module.code}/${comptValid.competenceId}/${etudiant.username}/false" var="removeActionUrl" />
						
						
					
						<div class="divTableRow">
							<div class="divTableCell"> 
								<c:if test="${comptValid.valided}">
									<c:out	value=" Acquis" />
								</c:if>
								<c:if test="${!comptValid.valided}">
									<c:out	value=" Non Acquis" />
								</c:if> 
							</div>
							
							<div class="divTableCell"> <c:out	value="${comptValid.description}" /> 	 </div>
							
							
							<div class="divTableCell">
								
								<c:if test="${comptValid.valided}">	
									<sf:form method="POST" class="form-horizontal" modelAttribute="CompetenceValid"
									action="${addActionUrl}">				
									<button class="btn btn-info" disabled="disabled">Valider</button>	
									</sf:form>
								</c:if> 
								<c:if test="${!comptValid.valided}">
									<sf:form method="POST" class="form-horizontal" modelAttribute="CompetenceValid"
									action="${addActionUrl}">
									<button class="btn btn-info">Valider</button>	
									</sf:form>
								</c:if> 
							</div>
							<div class="divTableCell">
								<c:if test="${!comptValid.valided}">
									<sf:form method="POST" class="form-horizontal" modelAttribute="CompetenceValid"
									action="${removeActionUrl}">						
									<button class="btn btn-warning" disabled="disabled">Refuser</button>
									</sf:form>
								</c:if> 
								<c:if test="${comptValid.valided}">
									<sf:form method="POST" class="form-horizontal" modelAttribute="CompetenceValid"
									action="${removeActionUrl}">	
									<button class="btn btn-warning">Refuser</button>
									</sf:form>	
								</c:if> 
								
							</div>
						</div>
									
					
									
									
					</c:forEach>
										
			
				</div>
			</div>

	

</div>
<jsp:include page="../fragments/footer.jsp" />
</html>