<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>



<html>

	<jsp:include page="../fragments/header.jsp">
		<jsp:param name="titre" value="Professeur Résultats ISFCE" />
	</jsp:include>
	
	
	
	<div class="jumbotron text-center">
		<h1>Liste des etudiant</h1>
		
		<c:if test="${fn:length(etudiantList) == 0}">
			<h2>Liste Vide</h2>
		</c:if>
		<h2>La liste contient: ${fn:length(etudiantList)} etudiant</h2>
	</div>



<div class="container">


	<div class='row'>
		<h3>
			<a href="<s:url value = "/etudiant/add" />">  Ajout d'un etudiant </a>
		</h3>
	</div>
	



	<!--	 Block avec 3 cours per line	-->
	<div class='row'>
		
		<c:forEach items="${etudiantList}" var="etudiant">	
			<!-- VAR URL -->
			<s:url value="${etudiant.username}" var="detailUrl" />
			<s:url value="/etudiant/${etudiant.username}/update" var="updateUrl" />
			<s:url value="/etudiant/${etudiant.username}/delete" var="deleteUrl" />
			
				<div class='col-sm-4'>
				
			<div  onclick="location.href='${detailUrl}'">
					<h1>
						<c:out	value="${etudiant.username}" />
					</h1> 
					<h4>
						<c:out value="${etudiant.nom}" />
						<c:out value="${etudiant.prenom}" />
					</h4>
					<h4>
						
					</h4>
					<h4>
						Email: <c:out value="${etudiant.email}" />
					</h4>
			</div>


	
<!--	 BT DELETE UPDATE DETAIL	-->
					
					<button class="btn btn-info" 
						onclick="location.href='${detailUrl}'"> Détail</button>
					
					
					<button class="btn btn-primary" 
						onclick="location.href='${updateUrl}'">Update</button>
					
					
					<button class="btn btn-danger"
						onclick="
						if (confirm('Are you sure ?')) {
						 this.disabled=true;
		                 post('${deleteUrl}',{'${_csrf.parameterName}': '${_csrf.token}'})}                             
		                                              ">Delete</button>	
				
				</div>
		
		</c:forEach>
	
			
		
	</div>



</div>
<jsp:include page="../fragments/footer.jsp" />
</html>