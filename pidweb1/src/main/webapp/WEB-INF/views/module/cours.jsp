<!DOCTYPE html>
<%@ page session="false" language="java"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> --%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> --%>

<c:url var="home" value="/" scope="request" />


<html>
<jsp:include page="../fragments/header.jsp" >
 <jsp:param name="titre" value="Cours Résultats ISFCE"/>
</jsp:include>
<div class="container">


	<h1>
		Détail du cours: 		${home}
	</h1>
	
	
	<button>Send an HTTP GET request to a page and get the result back</button>


</div>	




<jsp:include page="../fragments/footer.jsp" />


<script>



$(document).ready(function(){
	console.log( "READY success" );
    $("button").click(function(){
    	console.log( "CLICKED success" );
        $.get("${home}/module/cours", function(data, status){
        	console.log( " success" );
        	console.log( "Data: " + data + "\nStatus: " + status );
        	
            alert("Data: " + data + "\nStatus: " + status);
        });
    });
});



var jqxhr = $.getJSON( "${home}/module/cours2", function() {
	  console.log( "getJSON: success" );
	})
	  .done(function() {
	    console.log( "getJSON: second success" );
	  })
	  .fail(function() {
	    console.log( "getJSON: error" );
	  })
	  .always(function() {
	    console.log( "getJSON: complete" );
	  });
	  
	  
function searchAjax() {
	var data = {}
	data["query"] = $("#query").val();

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "${home}search/api/getSearchResult",
		data : JSON.stringify(data),
		dataType : 'json',
		timeout : 100000,
		success : function(data) {
			console.log("SUCCESS: ", data);
			display(data);
		},
		error : function(e) {
			console.log("ERROR: ", e);
			display(e);
		},
		done : function(e) {
			console.log("DONE");
		}
	});
}
	  
	  
	  
 

</script>  


</html>