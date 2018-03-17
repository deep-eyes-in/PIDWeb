function post(path, params, method) {
	method = method || "post"; 
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path);
	
	for ( var key in params) {
		if (params.hasOwnProperty(key)) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);
			form.appendChild(hiddenField);
		}
	}

	document.body.appendChild(form);
	form.submit();
}



 var orderby  = false;
 
 
 function sortTable() {
		orderby  = !orderby;
		console.log("sortTable " + orderby) ;
		
		  var table, rows, switching, i, x, y, shouldSwitch;
		  table = document.getElementById("myTable");
		  switching = true;
		  /*Make a loop that will continue until
		  no switching has been done:*/
		  while (switching) {
		    //start by saying: no switching is done:
		    switching = false;
		    rows = table.getElementsByClassName("divTableRow");   //table.getElementsByTagName("TR");
		    /*Loop through all table rows (except the
		    first, which contains table headers):*/
		    for (i = 1; i < (rows.length - 1); i++) {
		      //start by saying there should be no switching:
		      shouldSwitch = false;
		      /*Get the two elements you want to compare,
		      one from current row and one from the next:*/
		      x = 	rows[i].getElementsByClassName("divTableCell")[0];   //	rows[i].getElementsByTagName("TD")[0];
		      y = rows[i+1].getElementsByClassName("divTableCell")[0];   //	rows[i + 1].getElementsByTagName("TD")[0];
		      //check if the two rows should switch place:
		      if ((!orderby && x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) || (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase() && orderby)) {
		        //if so, mark as a switch and break the loop:
		        shouldSwitch= true;
		        break;
		      }
		    }
		    if (shouldSwitch) {
		      /*If a switch has been marked, make the switch
		      and mark that a switch has been done:*/
		      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		      switching = true;
		    }
		  }
		  
	  }
	  
 
 







