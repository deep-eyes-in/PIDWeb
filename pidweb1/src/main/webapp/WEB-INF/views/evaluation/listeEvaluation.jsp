<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>


<html>






<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="module Résultats ISFCE" />
</jsp:include>


	<div class="jumbotron text-center">
		<h1>
			Liste des evaluations du module :
			<c:out value="${module}" />
			<br /> Pour la session :
			<c:out value="${session}" />
			<br />
		</h1>
		
		
		<c:if test="${fn:length(evaluationList) == 0}">
			<h2>Liste Vide : (Add Students to this module first) </h2>
		</c:if>
		<h2>La liste contient: ${fn:length(evaluationList)} evaluations</h2>
	</div>
	
	

<div class="container">



	<br />
	
	
	
<div id="myTable" class="divTable blueTable">
	<div class="divTableHeading">
		<div class="divTableRow">
		<div class="divTableHead" onclick="sortTable()" >Students</div>
		<div class="divTableHead">Results</div>
		<div class="divTableHead">Mananger</div>
		</div>
	</div>
	<div class="divTableBody">
	
		<c:forEach items="${evaluationList}" var="eval">
			<!-- VAR URL -->
			<s:url value="/evaluation/view/${eval.id}" var="detailUrl" />

			
		
			<div class="divTableRow">
				<div class="divTableCell"> <c:out	value="${eval.etudiant.nom}" />  <c:out	value="${eval.etudiant.prenom}" />	 </div>
				
				<c:if test="${ eval.resultat >= 0 }">
					<div class="divTableCell"> <c:out	value="${eval.resultat}" /> </div>
				</c:if>
				<c:if test="${ eval.resultat < 0 }">
					<div class="divTableCell"> <c:out	value="Abandon" /> </div>
				</c:if>
				
				
				<div class="divTableCell">
				
<!--	 BT DELETE UPDATE DETAIL	-->

					<button class="btn btn-info" 
						onclick="location.href='${detailUrl}'"> Détail</button>
						

									
				</div>
			</div>
						

						
						
		</c:forEach>
							

	</div>
</div>

	
	



<div class="blueTable outerTableFooter">
	<div class="tableFootStyle">
		<div class="links">
			<a href="#">&laquo;</a> 
			<a class="active" href="#">1</a> 
			<a href="#">&raquo;</a>
		</div>
	</div>
</div>


	
	
	
	
</div>
<jsp:include page="../fragments/footer.jsp" />
</html>

















<!--
eval.key.etudiant.nom
eval.value
-->







