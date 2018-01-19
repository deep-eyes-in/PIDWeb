<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html >
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Log in</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <form method="POST" action="${contextPath}/login" class="form-signin">
    <c:if test="${param.error != null}">
		<span class="form-group has-error">
		<s:message code="login.erreur" text="Ajoute un étudiant" />
			
		</span>
	</c:if>
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${param.error != null ? 'has-error' : ''}">       
            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>           
        </div>
    </form>

</div>
<!-- /container -->

<s:url value="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	var="JQuery" />
<s:url
	value="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js"
	var="Popper" />
<s:url value="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" var="BootstrapJs"/>

<s:url value="/resources/js/bootstrap.min.js" var="BootstrapJs" />

<script src="${JQuery}"></script>
<script src="${Popper}"></script>

<script src="${BootstrapJs}"></script>
</body>
</html>
