<!DOCTYPE html>
<html lang="en">
   <head>
   <title>Create new battle</title>
   <link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
   <link type="text/css" rel="stylesheet" href="stylesheet.css" />
   <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
   <SCRIPT language=Javascript>
     
      function isNumberKey(evt)
      {
         var charCode = (evt.which) ? evt.which : event.keyCode
         if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;

         return true;
      }
     function createBattle(){
    	 var battlename = document.getElementById('battlename').value;
    	 var maxplayers = document.getElementById('maxp').value;
    	 var maxround = document.getElementById('maxr').value;
    	 loadContent("rest/robot/createbattle"+"/"+battlename+"/"+maxplayers+"/"+maxround);
    	 //call a service to create battle
    	 /* $.ajax({
        	type: "GET",
        	url: "rest/robot/createbattle",
        	//data: 'battlename='+battlename+"&maxp"+maxplayers+"&maxr"+maxr,	//with the page number as a parameter
        	data: '/'+battlename+'/'+maxplayers+'/'+maxr,
        	dataType: "text",	//expect html to be returned
        	success: function(msg){
        		alert("message: "+msg);
        		//TODO in failure alert message
        	}
    	 }); */
    	 //WebRequest req = HttpWebRequest.Create("rest/robot/createbattle"+"/"+battlename+"/"+maxplayers+"/"+maxr);
    	 //WebResponse webResponse = req.GetResponse();
    	 
     }
     
   //ajax

     var xmlhttp

     function loadContent(txt)
     {
      xmlhttp=GetXmlHttpObject();

       if (xmlhttp==null)
       {
        alert ("Your browser does not support Ajax HTTP");
        return;
       }
         //var url="rest/robot/createbattle";
         var url = txt;
         //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
         xmlhttp.onreadystatechange=getOutput;
         xmlhttp.open("GET",url,true);
         xmlhttp.send(null);
     }

     function getOutput()
     {
       if (xmlhttp.readyState==4)
       {
     	  alert("response text: "+xmlhttp.responseText);
     	  test(xmlhttp.responseText);
       //document.getElementById("table").innerHTML=xmlhttp.responseText;
       }
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
     //eof ajax
     
   </SCRIPT>
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
             <li><a href = "createbattle.jsp">Create Battle</a></li>
             <li><a href = "joinbattle.jsp">Join Battle</a></li>
             <li><a href = "control.jsp">User Control</a></li>
             <li><a href = "viewrecords.jsp">View Results</a></li>
             <li><a href = "about.jsp">About us</a></li>
           </ul>
      </div>
       </div>
  </div>
   </div>
<div class="abc">
     <div style = "padding:40px;">
    <div class = "well" >
         <form>
        <ul>
             <li style = "list-style: none; text-align:center;">
            <h2>Battle Details</h2>
          </li>
             <hr>
             <li> Battle Name*:<br/>
            <input type = "text" name = "bname" id="battlename"  style="height:25px;">
          </li>
             <li> Maximum players*:<br/>
            <input type = "text" name = "maxp" id="maxp"  style="height:25px;" onkeypress="return isNumberKey(this)" >
          </li>
             <!-- li> edit*:<br/>
            <input type = "text" name = "edit" style="height:25px;">
          </li-->
             <li> Maximum Rounds*:<br/>
            <input type = "text" name = "round" id="maxr" style="height:25px;" onkeypress="return isNumberKey(this)" >
            
          </li>
           </ul>
           
      </form>
      <button class="btn btn-primary" onClick="createBattle()" > Create Battle </button>
       </div>
  </div>
   </div>
</body>
</html>