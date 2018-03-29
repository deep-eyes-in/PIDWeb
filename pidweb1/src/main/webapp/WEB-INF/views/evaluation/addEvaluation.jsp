<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>









<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Evaluation Résultats ISFCE"/>
</jsp:include>



	<div class="jumbotron text-center">
		<h1> Detail de l'evaluation <c:out value=" ${evaluation.id}" default=""/></h1>
		

		<h2> Student :  <c:out	value="${evaluation.etudiant.nom}" />  <c:out	value="${evaluation.etudiant.prenom}" />     </h2>
	</div>
	



<div class="container">



	<s:url value="/evaluation/update" var="actionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="evaluation"
		action="${actionUrl}">
		

		<%-- affiche une div avec toutes les erreurs --%>
		<sf:errors path="*" element="div" cssClass="alert alert-danger" />
		
		

	<table>
		<tr>
			<td>Modules :</td>
			<td><c:out value="${evaluation.module.code}" /></td>
		</tr>
		<tr>
			<td>Nom:</td>
			<td><c:out value="${evaluation.etudiant.nom}" /></td>
		</tr>
		<tr>
			<td>prenom:</td>
			<td><c:out value="${evaluation.etudiant.prenom}" /></td>
		</tr>
		
		<tr>
			<td>resultat:</td>
			<td>

				<s:bind path="evaluation">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<div class="col-sm-10">
							<sf:input path="resultat" id="resultat" class="form-control"
								placeholder="Code du cours" />
							<sf:errors path="resultat" class="control-label" />
						</div>
					</div>
				</s:bind>
			
			</td>
		</tr>
		
		<tr>
			<td>Abandon: </td>
			<td><input type="checkbox" id="myCheck"  onclick="abandon()"></td>
		</tr>
		
		
		
		<tr>
			<td>Comments:</td>
			<td>

				<s:bind path="evaluation">
					<div class="form-group ${status.error ? 'has-error' : ''}">
						<div class="col-sm-10">
							<sf:textarea path="comments" rows="5" cols="50" class="form-control"
								placeholder="Code du cours" />

							<sf:errors path="comments" class="control-label" />
						</div>
					</div>
				</s:bind>
			
			</td>
		</tr>
		

		
		<tr>
		</tr>
	</table>
	
	
	<sf:input path="id" id="id"	type="hidden" />
	
	<button type="submit" class="btn-lg btn-primary pull-right">
		Modifier
	</button>
	
	</sf:form>
	
	
	


</div>	



	<jsp:include page="../fragments/footer.jsp" />
	
<script>
var checkBox = document.getElementById("myCheck");
var text = document.getElementById("resultat");

function abandon() {
    if (checkBox.checked == true){
        text.style.display = "none";
        text.value = -1 ;
    } else {
        text.style.display = "block";
        text.value = 0 ;
    }
}

if (text.value < 0){
	checkBox.checked = true ;
	text.style.display = "none";
}
</script>

</html>















