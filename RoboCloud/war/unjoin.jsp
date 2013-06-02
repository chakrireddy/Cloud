<!DOCTYPE html>
<html lang="en">
<head>
<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
<link type="text/css" rel="stylesheet" href="stylesheet.css" />
<link type="text/css" href="css/ui.multiselect.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet"
	href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/themes/ui-lightness/jquery-ui.css" />
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.min.js"></script>
<script type="text/javascript"
	src="js/plugins/localisation/jquery.localisation-min.js"></script>
<script type="text/javascript"
	src="js/plugins/scrollTo/jquery.scrollTo-min.js"></script>
<script type="text/javascript" src="js/ui.multiselect.js"></script>
<script type="text/javascript">
	  
	    var xmlhttp;
	 function loadSessions(){
		 xmlhttp=GetXmlHttpObject();
		  if (xmlhttp==null)
		  {
		   alert ("Your browser does not support Ajax HTTP");
		   return;
		  }
		  	var level = 0;
		  	var maxplayer = 0;
		    var url="rest/robot/sessionfilter/"+level+"/"+maxplayer;
		    xmlhttp.onreadystatechange=loadselect;
		    xmlhttp.open("GET",url,true);
		    xmlhttp.send(null);
	 }
	 
	  
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
		 
		 
	  
	  
	  function unjoin()
	  {
		alert("hii write u service call here");
		var noele = document.getElementById("unjoin");;
		var cnt = 0;
		for (var i=0;i<noele.options.length;i++){
		if(noele[i].selected){
		cnt++;
		}
		}
		if(cnt!=1)
		{
			alert("Please Select only one Session");
		}
	  } 

		
		 var roboxmlhttp;
		 function loadSessions(){
			 alert("jjjj");
			 roboxmlhttp=GetXmlHttpObject();

			  if (roboxmlhttp==null)
			  {
			   alert ("Your browser does not support Ajax HTTP");
			   return;
			  }
				var url = "http://robowarsj.appspot.com/rest/robot/unjoinlist";

			    //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
			    roboxmlhttp.onreadystatechange=loadsession;
			    roboxmlhttp.open("GET",url,true);
			    roboxmlhttp.send(null);
			    alert("call");
		 }
	  
	 	 
	 	function loadsession()
		  {
	 		if (roboxmlhttp.readyState==4){
	 		var v = document.getElementById("countries");
			var data = roboxmlhttp.responseText;	
			alert(data);
			var options = data.split(",");
			var select = v;	
			for(var i = 0; i < options.length; i++) {
	  		var opt = options[i];
	  		var el = document.createElement("option");
	  			el.textContent = opt;
	  			el.value = opt;
	  			select.appendChild(el);
			}
		  }
		  }
	  
		$(function(){
			loadSessions();			
			$.localise('ui-multiselect', {/*language: 'en',*/ path: 'js/locale/'});
			$(".multiselect").multiselect();
			$('#switcher').themeswitcher();
		});
	</script>
<SCRIPT language=Javascript>
     function isNumberKey(evt)
      {
         var charCode = (evt.which) ? evt.which : event.keyCode
         if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;

         return true;
      }
	  </script>
</head>
<body>
	<div class="contain">
		<div class="row">
			<div class="navbar  navbar-fixed-top">
				<div class="row">
					<div class="cool">
						<p class="logo">WELCOME TO ROBO WAR</p>
					</div>
				</div>
				<div class="navbar-inner">
					<ul class="nav" style="padding: 0 0 0 9%">
						<li><a href="robo.jsp">Home </a></li>
						<li><a href="joinbattle.jsp">Join Balttle</a></li>
						<li><a href="control.jsp">User Control</a></li>
						<li><a href="about.jsp">About Us </a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="abc">
		<div style="padding: 40px;">
			<div class="well">
				<center>
					<h3>
						<b> Please select One session from the grid</b>
					</h3>
				</center>
				<br /> <br />
				<table width="100%">
					<tr>
						<td width="33%" />
						<td width="33%"><form>
								<select id="countries" class="multiselect" multiple="multiple"
									name="countries[]" style="height: 300px">
								</select>
							</form></td>
						<td width=33% />
					</tr>
				</table>
				<br> <br>
				<center>
					<button class="btn btn-primary" onClick="unjoin()">Let Me
						GO!</button>
				</center>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="http://jqueryui.com/themeroller/themeswitchertool/">
	 
    </script>
</body>
</html>