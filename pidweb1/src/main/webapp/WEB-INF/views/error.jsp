<%@ page session="false"%>
<%-- <%@ taglib prefix="s" uri="http://www.springframework.org/tags"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>

<jsp:include page="fragments/header.jsp" />

<body>

	<div class="container">

		<h1>Erreur</h1>

		<p>Message: <c:out value="${exception.message}"/></p>
		<p>URL: <c:out value="${url}"/></p>

	</div>

	<jsp:include page="fragments/footer.jsp" />

</body>
</html>