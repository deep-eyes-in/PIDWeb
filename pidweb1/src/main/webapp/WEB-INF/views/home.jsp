<!DOCTYPE html>
<%@ include file="/WEB-INF/views/fragments/taglibs.jspf" %>



<html>


<jsp:include page="fragments/header.jsp">
	<jsp:param name="titre" value="Home page Resultats ISFCE" />
</jsp:include>


<div class="jumbotron text-center">
	<h1>
		<s:message code="home.titre" />
	</h1>
	<h2>
		<s:message code="home.sujet" />
	</h2>
</div>


<div class="container">


	
	<div class='row'>
	
<!-- SERVER INFO -->
		<div class='col-sm-4'>
	<h1>
		Server Info
	</h1>
	<P>The time on the server is ${serverTime}.</P>
	<p>Serveur: ${pageContext.request.serverName}</p>
	<p>Adresse Locale: ${pageContext.request.localAddr}</p>
	<p>Locale: ${pageContext.request.locale }</p>
	<p>Locale cookie : ${cookie['language'].value}</p>
		</div>
		
		
<!-- USER INFO -->
		<div class='col-sm-4'>
	<sec:authorize access="isAuthenticated()">
	
		<h1>
			<sec:authentication property="principal.username" />
		</h1>


		<!-- Affiche la liste des roles -->
		<sec:authentication property="authorities" var="roles" scope="page" />
		Your roles are:
		<ul>
			<c:forEach var="role" items="${roles}">
				<li>${role}</li>
			</c:forEach>
		</ul>
		
	</sec:authorize>
		</div>
		
		
		
<!-- USER SHORTCUTS -->
		<div class='col-sm-4'>
	<h1>
		Shortcuts
	</h1>
		
	<p><a href="<s:url value = "/cours/liste" />"> Liste des cours</a></p>
	<P><a href="<s:url value = "/module/liste" />"> Liste de tout les modules</a></p>
	
	
	<c:if test="${isAuth}">
		<P><a href="<s:url value = "/module/liste/${username}" />"> Liste de mes modules</a></p>
		<P><a href="<s:url value = "/${fn:toLowerCase(  fn:substring(roles[0], 5, -1) ) }/${username}" />"> Voir mon profil</a></p>
	</c:if>
		</div>
		
	</div>
		

	
	
</div>





<jsp:include page="fragments/footer.jsp" />




</html>

















