<!DOCTYPE html>

<%@ page import="com.isfce.pidw.data.*" %>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>


<html>

<jsp:include page="../fragments/header.jsp">
	<jsp:param name="titre" value="Home page Résultats ISFCE" />
</jsp:include>

<div class="container">



    <h2>Submitted Information : ${filter} </h2>
    
    
    
    
    
    
    <div id="images"></div>
    

    
    
	<s:url value="/test/date" var="actionUrl" />

	<%-- modelAttribute correspond à une clé dans le modèle --%>
	<sf:form method="POST" class="form-horizontal" modelAttribute="filter"
		action="${actionUrl}">
		
    
    
    
		<s:bind path="myDate">
			<div class="form-group ${status.error ? 'has-error' : ''} ">
				<div class="col-sm-10">
						<sf:input path="myDate" value="${ dates.format(filter.loadingStartDate, dateFormat) }" id="myDate" pattern="yyyy-MM-dd"  type="date" class="form-control"
							 />
						<sf:errors path="myDate" class="control-label" />
				</div>
			</div>
		</s:bind>

		
		
		

    
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<c:choose>
					<c:when test="${empty savedId}">
						<%--  Pour un Ajout le "savedId" ne doit pas exister --%>
						<button type="submit" class="btn-lg btn-primary pull-right">
							<s:message code="module.btAJout" />
						</button>
					</c:when>
					<c:otherwise>
						<%-- Pour un update "savedId" doit avoir l'identifiant avant l'update --%>
						<button type="submit" name="savedId" value="${savedId}"
							class="btn-lg btn-primary pull-right">
							<s:message code="module.btMaj" />
						</button>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		
    
	</sf:form>    
    
    

    
    

</div>

<jsp:include page="../fragments/footer.jsp" />

    <script >
	(function() {
		  var flickerAPI = "http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";
		  jQuery.getJSON( flickerAPI, {
		    tags: "mount rainier",
		    tagmode: "any",
		    format: "json"
		  })
		    .done(function( data ) {
		      $.each( data.items, function( i, item ) {
		        $( "<img>" ).attr( "src", item.media.m ).appendTo( "#images" );
		        if ( i === 3 ) {
		          return false;
		        }
		      });
		    });
		})();
	
	</script>
	
</html>





	