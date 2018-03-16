<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>

<%@ page  pageEncoding="UTF-8"%>



<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Cours Résultats ISFCE"/>
</jsp:include>



<div class="container">
	<h1>
		Détail du cours:
		<c:out value="${cours.nom}" default="-----"></c:out>
	</h1>

	<table>
		<tr>
			<td>Code:</td>
			<td><c:out value="${cours.code}" /></td>
		</tr>
		<tr>
			<td>Nom:</td>
			<td><c:out value="${cours.nom}" /></td>
		</tr>
		<tr>
			<td>NbPeriodes:</td>
			<td><c:out value="${cours.nbPeriodes}" /></td>
		</tr>
		<tr>
			<td>Langue:</td>
			<td><c:out value="${cours.langue}" /></td>
		</tr>
		<tr>
			<td>Compétences:</td>
			<td>
				<c:forEach items="${competenceList}" var="competence">
					<c:out value="${competence.description}" />
					<c:if test="${ isAdmin}">
						<s:url value="/competence/update/${competence.id}" var="updateUrl" />
						<button class="btn btn-info" 
							onclick="location.href='${updateUrl}'">Modifier</button>
					</c:if>
					<br />
				</c:forEach>
			</td>
		</tr>
	</table>
	
	

<c:if test="${fn:length(moduleList) > 0}">
	
	<h1>
		Liste des module(s):
	</h1>
	
	
	<div class="divTable blueTable">
		<div class="divTableHeading">
			<div class="divTableRow">
			<div class="divTableHead">Code</div>
			<div class="divTableHead">Periode:</div>
			<div class="divTableHead">Mananger</div>
			</div>
		</div>
		<div class="divTableBody">
		
			<c:forEach items="${moduleList}" var="item">
				<!-- VARS -->
					<fmt:formatDate value="${item.dateDebut}" pattern="dd/MM/yyyy" var="dateD"/>
					<fmt:formatDate value="${item.dateFin}" pattern="dd/MM/yyyy" var="dateF"/>
				<!-- VAR URL -->
					<s:url value="/module/${item.code}" var="detailUrl" />
					<s:url value="/module/${item.code}/update" var="updateUrl" />
				
				
	
				
			
				<div class="divTableRow">
					<div class="divTableCell"> <c:out	value="${item.code}" /> </div>
					<div class="divTableCell"> 
						 <c:out value="${dateD} ==> ${dateF}" />
					 </div>
					<div class="divTableCell">
					
	<!--	 BT DELETE UPDATE DETAIL	-->
	
						<button class="btn btn-info" 
							onclick="location.href='${detailUrl}'"> Détail module</button>
							
						<c:if test="${ isAdmin}">
							<button class="btn btn-info" 
								onclick="location.href='${updateUrl}'">  Edit module </button>
						</c:if>
										
					</div>
				</div>
							
	
							
							
			</c:forEach>
								
	
		</div>
	</div>

</c:if>

	
	
</div>	
	<jsp:include page="../fragments/footer.jsp" />

</html>