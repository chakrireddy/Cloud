<!DOCTYPE html>
<html lang="en">
	<head>
    <title> USER CONTROL</title>
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
	<link type="text/css" rel="stylesheet" href="stylesheet.css" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>

	<script>
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
		    var url="rest/robot/getUserScore";
		    //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
		    xmlhttp.onreadystatechange=loadselect;
		    xmlhttp.open("GET",url,true);
		    xmlhttp.send(null);
	 }
	 
	 function loadselect()
	  {
		if (xmlhttp.readyState==4){
		 var v = document.getElementById("score");
		var data = xmlhttp.responseText;
		$('#score').text(" Influence point: "+data);
		//var data = "HARI,CHAKRI,AMRUT,TATA,LAILA";
		/* var options = data.split(",");
		var select = v;
		for(var i = 0; i < options.length; i++) {
			var opt = options[i];
			var el = document.createElement("option");
			el.textContent = opt;
			el.value = opt;
			select.appendChild(el);
			//alert(opt);
			} */
		}
	  }
	  
      $(document).ready(function () {
			//loadSession();
      	loadSessions();
         
      });
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
              <li><a href = "robo.jsp">Home </a></li>
              <li><a href = "rDetails.html">Robo Details</a></li>
              <li><a href = "control.jsp">Profile</a></li>
              <li><a href = "robo.jsp">Battle Details</a></li>
              <li><a href = "about.jsp">About </a></li>
              
            </ul>
      </div>
        </div>
  </div>
    </div>
<div class="abc">
      <div style = "padding:40px;">
    <div class = "well" >
    <label id="score"></label>
          PAGE UNDER CONSTRUCTION
        </div>
  </div>
    </div>

</body>
</html>