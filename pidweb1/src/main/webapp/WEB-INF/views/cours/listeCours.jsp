<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>



<html>

	<jsp:include page="../fragments/header.jsp">
		<jsp:param name="titre" value="Cours Résultats ISFCE" />
	</jsp:include>
	
	
	
	<div class="jumbotron text-center">
		<h1>Liste des cours</h1>
		
		<c:if test="${fn:length(coursList) == 0}">
			<h2>Liste Vide</h2>
		</c:if>
		<h2>La liste contient: ${fn:length(coursList)} cours</h2>
	</div>



<div class="container">

	<div class='row'>
		<h3>
			<a href="<s:url value = "/cours/add" />">  Ajout d'un cours </a>
		</h3>
	</div>
	



	<!--	 Block avec 3 cours par ligne	-->
	<div class='row'>
		
		<c:forEach items="${coursList}" var="cours">
			<!-- VAR URL -->
			<s:url value="${cours.code}" var="detailUrl" />
			<s:url value="/cours/${cours.code}/update" var="updateUrl" />
			<s:url value="/cours/${cours.code}/delete" var="deleteUrl" />
			<s:url value="/competence/${cours.code}/add" var="addCompetUrl" />
			
			<div class='col-sm-4'  style="min-height: 250px;"  >
				
				<div  onclick="location.href='${coursUrl}'">
						<h1>
							<c:out	value="${cours.code}" />
						</h1> 
						<h4>
							<c:out value="${cours.nom}" />
						</h4>
						<h4>
							Nb Périodes: <c:out value="${cours.nbPeriodes}" />
						</h4>
				</div>

	
	
				<!--	 BT DELETE UPDATE DETAIL	-->
				<button class="btn btn-info" 
					onclick="location.href='${detailUrl}'"> Détail</button>
					
				<c:if test="${ isAdmin}">
					<button class="btn btn-primary" 
						onclick="location.href='${updateUrl}'">Update</button>
					
					
					<button class="btn btn-danger"
						onclick="
						if (confirm('Suppression du cours  ?')) {
						 this.disabled=true;
		                 post('${deleteUrl}',{'${_csrf.parameterName}': '${_csrf.token}'})}">Delete
					</button>
					<button class="btn btn-success" 
						onclick="location.href='${addCompetUrl}'">Add compétence
					</button>
				</c:if>
			</div>
		
		</c:forEach>

	</div>







</div>
<jsp:include page="../fragments/footer.jsp" />
</html>