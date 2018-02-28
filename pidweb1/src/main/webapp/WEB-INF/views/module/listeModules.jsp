<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>


<html>
<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Modules Résultats ISFCE" />
</jsp:include>


	<div class="jumbotron text-center">
		<h1>Liste des modules <c:out value=" ${userModules}" default=""/></h1>
		
		<c:if test="${fn:length(moduleList) == 0}">
			<h2>Liste Vide</h2>
		</c:if>
		<h2>La liste contient: ${fn:length(moduleList)} modules</h2>
	</div>
	




<div class="container">

	<!--	 Block avec 3 cours per line	-->
	<div class='row'>
		<c:set var="i" value="0"></c:set>
		<c:forEach items="${moduleList}" var="module">
			<!-- VAR URL -->
			<s:url value="${module.code}" var="detailUrl" />				
			<s:url value="/module/${module.code}/update" var="updateUrl" />
			<s:url value="/module/${module.code}/delete" var="deleteUrl" />
			
			<c:if test="${ userName != 'NULL' }">
				<s:url value="../${module.code}" var="detailUrl" />
			</c:if>

			<fmt:formatDate value="${module.dateDebut}" pattern="dd/MM/yyyy" var="dateD"/>
			<fmt:formatDate value="${module.dateFin}" pattern="dd/MM/yyyy" var="dateF"/>
			
				<div class='col-sm-4'>
				
			<div  onclick="location.href='${detailUrl}'">
					<h1>
						<c:out	value="${module.code}" />
					</h1> 
					<h4>
						<c:out value="${module.cours.nom}" />
					</h4>
					<h4>
						Dates: <c:out value="${module.moment}  ${dateD} ==> ${dateF}" />
					</h4>
					<h4>
						Prof: <c:out value="${module.prof.nom}"  default="---" />
					</h4>
					

									
			</div>

<!--	 BT DELETE UPDATE DETAIL	-->
					
					<button class="btn btn-info" 
						onclick="location.href='${detailUrl}'"> Détail</button>
						
						
		<!--  --> 
		<c:if test="${ userName == 'NULL' }">
				<c:if test="${ isAdmin}">

					<button class="btn btn-primary" 
						onclick="location.href='${updateUrl}'">Update</button>
						
					<button class="btn btn-danger"
						onclick="
						if (confirm('Are you sure ?')) {
						 this.disabled=true;
		                 post('${deleteUrl}',{'${_csrf.parameterName}': '${_csrf.token}'})}                             
		                                              ">Delete</button>	
				</c:if> 
				
				
				<c:if test="${ (isAdmin) || isProf &&  (userName == userConnected) }">
					<p>
						<c:if test="${ nbrSessionList[i] < 2   }">
							<s:url value="/evaluation/${module.code}/add" var="addEvaluation" />
							<button class="btn btn-success"
								onclick="location.href='${addEvaluation}' "> Add
								Evaluation</button>
						</c:if>
	
	
						<c:if test="${ nbrSessionList[i] == 1 || nbrSessionList[i] == 2 }">
							<s:url value="/evaluation/${module.code}/" 
								var="updateEvaluation1" />
							<button class="btn btn-success"
								onclick="
					                 post('${updateEvaluation1}', {'${_csrf.parameterName}': '${_csrf.token}'}   )                 
					                                              ">Update
								Evaluation S1</button>
								
						</c:if>
						<c:if test="${ nbrSessionList[i] == 2 }">
							<s:url value="/evaluation/${module.code}/2"
								var="updateEvaluation2" />
							<button class="btn btn-success"
								onclick="
					                 post('${updateEvaluation2}',  {'session':'2','${_csrf.parameterName}': '${_csrf.token}'}   )                  
					                                              ">Update
								Evaluation S2</button>
						</c:if>
					</p>
				</c:if>
		</c:if>
								
					

					

				
				</div>
		<c:set var="i" value="${i + 1}"></c:set>
		</c:forEach>
			
	</div>
	



</div>
<jsp:include page="../fragments/footer.jsp" />
</html>















