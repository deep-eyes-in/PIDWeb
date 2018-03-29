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



    <h2>Submitted Information : JSON</h2>
    
    
    
    
    
    
    <div id="images"></div>
    

    
    
	<button>Send an HTTP GET request to a page and get the result back</button>
    

    
    

</div>

<jsp:include page="../fragments/footer.jsp" />


	<script>
	$(document).ready(function(){
	    $("button").click(function(){
	        $.get("http://entretenimento.r7.com/apocalipse/videos/apocalipse-03012018-bloco-1-03012018", function(data, status){
	            alert("Data: " + data + "\nStatus: " + status);
	        });
	    });
	});
	</script>
	


    <script >
	(function() {
		  var flickerAPI = "http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";
//		  var flickerAPI = "http://entretenimento.r7.com/apocalipse/videos/apocalipse-03012018-bloco-1-03012018" ;
		  
		  jQuery.getJSON( flickerAPI, {
//		    tags: "mount rainier",
//		    tagmode: "any",
		    format: "html"			//	json
		  })
		    .done(function( data ) {
		    	console.log( data )
		    	
		    	
/*		    	
		      $.each( data.items, function( i, item ) {
		        $( "<img>" ).attr( "src", item.media.m ).appendTo( "#images" );
		        if ( i === 3 ) {
		          return false;
		        }
		      });
*/

		    	
		    });
		})();
	
	</script>
	
</html>









	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	