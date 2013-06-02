<!DOCTYPE html>
<html lang="en">
	<head>

	<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
	<link type="text/css" rel="stylesheet" href="stylesheet.css" />

    <style type="text/css" title="currentStyle">
			@import "css/jquery.dataTables_themeroller.css";
			@import "css/jquery-ui-1.8.4.custom.css";
		</style>
    <script type="text/javascript" src="js/jquery.min.js"></script>
    
    <script type="text/javascript" src="js/jquery.dataTables.min.js"></script>

    <script type="text/javascript" charset="utf-8">
	
	function ShowResults()
	{
		 $('#example').dataTable({
			 "bDestroy": true,
		"bJQueryUI": true,
                "sPaginationType": "full_numbers",
		"bProcessing": true,
		"sAjaxSource": "rest/robot/getBattleData/"+$('#Battle').val()
            });
	}
	
	/* function loadSession()
	  {
		var data = "HARI,CHAKRI,AMRUT,TATA,LAILA";
		var options = data.split(",");
		var select = document.getElementById("Battle");
		
		for(var i = 0; i < options.length; i++) {
    	var opt = options[i];
    	var el = document.createElement("option");
    	el.textContent = opt;
    	el.value = opt;
    	select.appendChild(el);
		}
	  } */
	
	var xmlhttp;
	  
	  function GetXmlHttpObject()
	     {
	         if (window.XMLHttpRequest)
	         {
	            return new XMLHttpRequest();
	         }
	         if (window.ActiveXObject)
	         {
	           return new ActiveXObject("Microsoft.XMLHTTP");
	         }
	      return null;
	     }
	  
	 function loadSessions(){
		 xmlhttp=GetXmlHttpObject();
		  if (xmlhttp==null)
		  {
		   alert ("Your browser does not support Ajax HTTP");
		   return;
		  }
		  var level = 0;
		  var maxplayer = 0;
		    //var url="rest/robot/sessionfilter/"+level+"/"+maxplayer;
		    var url="rest/robot/sessionfilter/"+level+"/"+maxplayer;
		    //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
		    xmlhttp.onreadystatechange=loadselect;
		    xmlhttp.open("GET",url,true);
		    xmlhttp.send(null);
	 }
	 
	 function loadselect()
	  {
		if (xmlhttp.readyState==4){
		 var v = document.getElementById("Battle");
		var data = xmlhttp.responseText;
		
		//var data = "HARI,CHAKRI,AMRUT,TATA,LAILA";
		var options = data.split(",");
		var select = v;
		for(var i = 0; i < options.length; i++) {
  			var opt = options[i];
  			var el = document.createElement("option");
  			el.textContent = opt;
  			el.value = opt;
  			select.appendChild(el);
  			//alert(opt);
			}
		}
	  }
	  
        $(document).ready(function () {
			//loadSession();
        	loadSessions();
           
        });
        function loadrobots(){
        	alert($('#Battle').val());
        }
	</script>

	</head>
	<body>
<div class="contain">
        <div class="row">
    <div class="navbar  navbar-fixed-top">
            <div class="row">
        <div class="cool">
                <p class="logo"> WELCOME TO ROBO WAR</p>
              </div>
      </div>
            <div class="navbar-inner">
        <ul class="nav" style="padding:0 0 0 9%">
                <li><a href = "home.html">Home </a></li>
                <li><a href = "JoinGame.html">Join Balttle</a></li>
                <li><a href = "uc.html">User Control</a></li>
                <li><a href = "about.html">About Us </a></li>
              </ul>
      </div>
          </div>
  </div>
      </div>
    <div class="abc">
      <div style = "padding:40px;">
        <div id="container" >
        <div class = "well" >
        <center>
        <h3 style="color:#666"> Plese Select a Battle from the list Below to see result</h3>
        <select class = "select" id="Battle"  style="text-align:right" onchange="ShowResults()">
    
</select>
<br>
<button class="btn btn-primary" onClick="ShowResults()">Show Me!<button>
</center>
</div>
        <table cellpadding="0" cellspacing="0" border="0" class="display" id="example" width="100%">
	<thead>
	    <tr>
		<th>Robot Name</th>
		<th>Score</th>
		<th>CreatedBy</th>
		<th>Level</th>		

	    </tr>
	</thead>
	
        <tbody>

        </tbody>

	<tfoot></tfoot>

    </table>
 
      </div>
    </div>

</bod>
</html>