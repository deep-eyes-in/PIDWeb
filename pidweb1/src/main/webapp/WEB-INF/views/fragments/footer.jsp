<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<div class="container"
>
	<hr>
	<footer>
		<p>Projet d'Intégration et de développement Van Oudenhove Didier
			(ISFCE)</p>
	</footer>
</div>

	
	
														<%-- https://code.jquery.com/jquery-3.2.1.slim.min.js --%>
<s:url 
	value="https://code.jquery.com/jquery-3.1.1.min.js"			
	var="JQuery" />
	
		
	
<s:url
	value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
	var="Popper" />
<%-- <s:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" var="BootstrapJs"/> --%>
<s:url value="/resources/js/monApplication.js" var="MyAppJs" />
<s:url value="/resources/js/bootstrap.min.js" var="BootstrapJs" />

<script src="${JQuery}"></script>
<script src="${Popper}"></script>
<script src="${MyAppJs}"></script>
<script src="${BootstrapJs}"></script>
</body>
