<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>



<html>
<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Professeur Résultats ISFCE" />
</jsp:include>


	<div class="jumbotron text-center">
		<h1>Liste des Professeurs</h1>
		
		<c:if test="${fn:length(professeurList) == 0}">
			<h2>Liste Vide</h2>
		</c:if>
		<h2>La liste contient: ${fn:length(professeurList)} professeur</h2>
	</div>
	



<div class="container">


	<div class='row'>
		<h3>
			<a href="<s:url value = "/professeur/add" />">  Ajout d'un professeur </a>
		</h3>
	</div>
	



	<!--	 Block avec 3 cours per line	-->
	<div class='row'>
		
		<c:forEach items="${professeurList}" var="professeur">	
			<!-- VAR URL -->
			<s:url value="${professeur.username}" var="detailUrl" />
			<s:url value="/professeur/${professeur.username}/update" var="updateUrl" />
			<s:url value="/professeur/${professeur.username}/delete" var="deleteUrl" />
			
				<div class='col-sm-4'>
				
			<div  onclick="location.href='${detailUrl}'">
					<h1>
						<c:out	value="${professeur.username}" />
					</h1> 
					<h4>
						<c:out value="${professeur.nom}" />
						<c:out value="${professeur.prenom}" />
					</h4>
					<h4>
						
					</h4>
					<h4>
						Email: <c:out value="${professeur.email}" />
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




















