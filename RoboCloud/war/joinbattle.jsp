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
	     //eof ajax 
	     
	   function join()
	  {
		  
		  var var1= checkS( document.getElementById("countries"))
		  var var2=checkS( document.getElementById("countries1"))		  
		  if(var1=="1"&&var2=="1")
		  {
			  var va1 = $('#countries').val();
			  var va2 = $('#countries1').val();
			  joinbattle(va1,va2);
			
		  }
		  else
		  {
			  alert("Please Select only one value in both grids");
		  }
		  	
	  }
	  
	  function checkS(v)
	  {
		var noele =v;
		var cnt = 0;
		for (var i=0;i<noele.options.length;i++){
		if(noele[i].selected){
		cnt++;
		}
		}
		
		if(cnt!=1)
		{
			
			return "0";
		}
		else
		{
		return "1";
		}
	  }
	  
	  var joinxmlhttp;
		 function joinbattle(battlename,robot){
			 joinxmlhttp=GetXmlHttpObject();
			  if (joinxmlhttp==null)
			  {
			   alert ("Your browser does not support Ajax HTTP");
			   return;
			  }
			    var url="rest/robot/joinbattle/"+battlename+"/"+robot;			  
			    joinxmlhttp.onreadystatechange=joinoutput;
			    joinxmlhttp.open("GET",url,true);
			    joinxmlhttp.send(null);
		 }
		 
		 function joinoutput(){
			 if (joinxmlhttp.readyState==4){
				 var data = joinxmlhttp.responseText;
				 //var start = parseInt(data);
				 //if(start == 1){
					//TODO start the game
					var battlename = $('#countries').val();
					 joinxmlhttp=GetXmlHttpObject();
					  if (joinxmlhttp==null)
					  {
					   alert ("Your browser does not support Ajax HTTP");
					   return;
					  }					  
					    var url=data;
					    //joinxmlhttp.onreadystatechange=joinoutput;
					    //joinxmlhttp.open("GET",url,true);
					    //joinxmlhttp.send(null);
					    if(data.indexOf("http:") !== -1){
					    	alert("battle started");
					    	var win=window.open(url,'_self',false);
						  	win.focus();				  	
					    }else{
					    	alert("joined battle");
					    }
					    
				 //}
				 
			 }
		 }
	  
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
		    //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
		    xmlhttp.onreadystatechange=loadselect;
		    xmlhttp.open("GET",url,true);
		    xmlhttp.send(null);
	 }
	 
	 function loadselect()
	  {
		if (xmlhttp.readyState==4){
		 var v = document.getElementById("countries");
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
	 
	 var roboxmlhttp;
	 function loadRobots(){
		 roboxmlhttp=GetXmlHttpObject();

		  if (roboxmlhttp==null)
		  {
		   alert ("Your browser does not support Ajax HTTP");
		   return;
		  }
		  var level = 0;
		  var score = 0;
		    var url="rest/robot/loadFilterRobots/"+level+"/"+score;
		    //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
		    roboxmlhttp.onreadystatechange=loadrobot;
		    roboxmlhttp.open("GET",url,true);
		    roboxmlhttp.send(null);
	 }
 	 
 	function loadrobot()
	  {
 		if (roboxmlhttp.readyState==4){
 		var v = document.getElementById("countries1");
		var data = roboxmlhttp.responseText;
		//var data = "HARI,CHAKRI,AMRUT,TATA,LAILA";
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
			//loadselect(document.getElementById("countries"));
			loadSessions();
			loadRobots();
			//alert("passed");
			//loadrobot(document.getElementById("countries1"));		
			$.localise('ui-multiselect', {/*language: 'en',*/ path: 'js/locale/'});
			$(".multiselect").multiselect();
			$('#switcher').themeswitcher();
			alert("hi");
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
						<li><a href = "viewrecords.jsp">View Results</a></li>
						<li><a href="about.jsp">About Us </a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="abc">
		<div style="padding: 40px;">
			<div class="well">
				<table cellpadding="10px" cellspacing="1px" width="100%"
					align="center">
					<tr>
						<td>Max Players</td>
						<td><input type="text" name="maxp" style="height: 25px;"
							onkeypress="return isNumberKey(this)"></td>
						<td>Level</td>
						<td><input type="text" name="level" style="height: 25px;"
							onkeypress="return isNumberKey(this)"></td>
						<td>Hours Active</td>
						<td><input type="text" name="maxp" style="height: 25px;"
							onkeypress="return isNumberKey(this)"></td>
					</tr>
				</table>
				<center>
					<button class="btn btn-primary">Refresh Sessions</button>
				</center>
				<br /> <br />
				<table width="100%">
					<tr>
						<td width="50%"><form>
								<select id="countries" class="multiselect" multiple="multiple"
									name="countries[]" style="height: 300px">
								</select>
							</form></td>
						<td width=50%><select id="countries1" class="multiselect"
							multiple="multiple" name="countries1[]" style="height: 300px">
						</select></td>
					</tr>
				</table>
				<br> <br>
				<center>

					<button class="btn btn-primary" onClick="join()">Lets
						Play!</button>

				</center>

			</div>
		</div>
	</div>
	<script type="text/javascript"
		src="http://jqueryui.com/themeroller/themeswitchertool/">
	 
    </script>
</body>
</html>