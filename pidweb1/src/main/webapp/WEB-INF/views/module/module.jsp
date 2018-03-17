<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>









<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Module Résultats ISFCE"/>
</jsp:include>




<div class="container">
	<h1>
		Détail du module:
		<c:out value="${module.code}" default="-----"></c:out>
	</h1>

	<table>
		<tr>
			<td>Code:</td>
			<td><c:out value="${module.code}" /></td>
		</tr>
		<tr>
			<td>Nom du cours:</td>
			<td><c:out value="${module.cours.nom}" /></td>
		</tr>
		<tr>
			<td>Nombre de périodes:</td>
			<td><c:out value="${module.cours.nbPeriodes}" /></td>
		</tr>
		<tr>
			<td>Langue:</td>
			<td><c:out value="${module.cours.langue}" /></td>
		</tr>
		<tr>
			<td>Professeur:</td>
			<td><c:out value="${module.prof.nom}" /></td>
		</tr>
	</table>
	
	
	
	<h1>
		Détail des etudiants du module:
	</h1>
	
	
	
<div class="divTable blueTable">
	<div class="divTableHeading">
		<div class="divTableRow">
		<div class="divTableHead">Students</div>
		<div class="divTableHead">email</div>
		<div class="divTableHead">Mananger</div>
		</div>
	</div>
	<div class="divTableBody">
	
		<c:forEach items="${etudiantList}" var="etudiant">
			<!-- VAR URL -->
			<s:url value="/etudiant/${etudiant.username}" var="detailUrl" />
			<s:url value="/competence/${module.code}/${etudiant.username}" var="competencesUrl" />
			
			

			
		
			<div class="divTableRow">
				<div class="divTableCell"> <c:out	value="${etudiant.nom}" />  <c:out	value="${etudiant.prenom}" />	 </div>
				<div class="divTableCell"> <c:out	value="${etudiant.email}" /> </div>
				<div class="divTableCell">
				
<!--	 BT DELETE UPDATE DETAIL	-->

					<button class="btn btn-info" 
						onclick="location.href='${detailUrl}'"> Détail etudiant</button>
						
					<button class="btn btn-info" 
						onclick="location.href='${competencesUrl}'"> Valider competences </button>
						

									
				</div>
			</div>
						

						
						
		</c:forEach>
							

	</div>
</div>




	
	
	
</div>	






	<jsp:include page="../fragments/footer.jsp" />

</html>


























