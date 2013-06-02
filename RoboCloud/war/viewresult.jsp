
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreServiceFactory" %>
<%@ page import="com.google.appengine.api.datastore.DatastoreService" %>
<%@ page import="com.google.appengine.api.datastore.Query" %>
<%@ page import="com.google.appengine.api.datastore.Entity" %>
<%@ page import="com.google.appengine.api.datastore.FetchOptions" %>
<%@ page import="com.google.appengine.api.datastore.Key" %>
<%@ page import="com.google.appengine.api.datastore.KeyFactory" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- link rel="stylesheet" href="css/styles.css"-->
<script src="jquery.min.js"></script>
<style>body{background:url(img/bg.png) center;margin: 0 auto;width: 960px;padding-top: 50px}.footer{margin-top:50px;text-align:center;color:#666;font:bold 14px

Arial}.footer a{color:#999;text-decoration:none}</style>
<script>
function test(data)
{

	alert(data);
var select = document.getElementById("selectNumber");
//var options = ["1", "2", "3", "4", "5"];

for(var k=0;k<select.options.length;k++){
	k=0;
	select.remove(k);
}
//select.remove(0);
var options = data.split(",");
for(var i = 0; i < options.length; i++) {
    var opt = options[i];
    var el = document.createElement("option");
    el.textContent = opt;
    el.value = opt;
    select.appendChild(el);
}
}
</script>
<style>
.select
{
  font-weight: bold;
  padding: 10px;
  background-color: #A0A0A0;
  color: white;
  font-size: 15px;
  height : 350px
  }

.Text
{
  font-size: 25px;
  font-weight: bold;
  background: transparent;
  margin: 1px;
  padding: 20px;
  color: white;
}
#menu ul
{
    margin: 1px;
    padding: 1px;
    list-style-type: none;
}

#menu a
{
    display: block;
    width: 8em;
    color: white;
    background-color: #A0A0A0;
    text-decoration: none;
    text-align: center;
    font-size: 25px;
    font-weight: bold;
}

#menu a:hover
{
    background-color: #8CACB9;
}
#menu li
{
    float: left;
    margin-right: 0.5em;
}
$(document).ready(function() {
    /* $.ajax({  
       type: "GET",
       data : "{}",
       contentType: "application/json",
       //url: 'myfile.json',
       url: 'http://chakrirest.appspot.com/rest/robot/listrobots',
       dataType: "text",
       success: function(data) {
         test(data)
        });
     },  
     error: function(data){  
           alert("error");
     } 
       */
     loadContent();
  });
});
</style>
<script>
//ajax

var xmlhttp

function loadContent()
{
 xmlhttp=GetXmlHttpObject();

  if (xmlhttp==null)
  {
   alert ("Your browser does not support Ajax HTTP");
   return;
  }
    //var url="rest/robot/listrobots";
    var level = 0;
    var maxplayer = 0;
    var url="rest/robot/sessionfilter/"+level+"/"+maxplayer;
    alert(url);
    //url=url+"?studentId="+document.getElementById("sid").value+"&drop=true";
    xmlhttp.onreadystatechange=getOutput;
    xmlhttp.open("GET",url,true);
    xmlhttp.send(null);
}

function getOutput()
{
  if (xmlhttp.readyState==4)
  {
	  //alert("response text: "+xmlhttp.responseText);
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
</script>
<script language="javascript">
   
    function writeSummary(summary) {
   /*      summaryElem =
            document.getElementById("summary");
        summaryElem.innerHTML += "<br>";
        summaryElem.innerHTML += summary; */
    }
    
    function startGame(){
    	//alert("hi");
    	var robots = $('#selectNumber').val();
    	//alert("robots: "+robots);
    	/* var indexes = getSelectedIndexes(document.getElementById("selectNumber"));
    	alert("index length: "+indexes.length);
    	// var rob='';
    	for (int j=0; j<indexes.length;j++){
    		int k = indexes[j];
    		alert(k);
    		  if(j==0){
    			var x = document.getElementById("selectNumber").options;
    			alert("text: "+x[k].text);
    			rob = x[k].text;    			
    		}else{
    			var x = document.getElementById("selectNumber").options;  
    			alert("text: "+x[k].text);
    			rob=rob+","+x[k].text;
    		}  
    	}  */
    	 //document.getElementById("selectNumber").value;
    	//alert("robots"+rob);
        var attributes = {codebase:'.',
                          code:'robocode.Robocode',
                          archive:'applet9.jar',
                          width:800, height:600} ;
        var parameters = {fontSize:16,robot_names:robots,numOfRounds:'4'} ;
        var version = '1.7' ;
        deployJava.runApplet(attributes, parameters, version);

    }
    function getSelectedIndexes(select) {
    	   var selected = [];
    	   for (var i = 0; i  < select.options.length; i++) {
    	       if(select.options[i].selected ) {
    	            selected.push(i);
    	            //alert("index: "+i);
    	       }
    	   }
    	   return selected;
    	}
    </script>
    <%
UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user == null)  {
%>

<%
response.sendRedirect(userService.createLoginURL(request.getRequestURI()));
    }
%>
<table width=100% >
<tr>
<td>
</td>
<td>
<tr>
<td>
</td>
<td>
<div id="menu">
    <ul>
        <li><a href="robo.jsp">Home</a></li>
        <li><a href="robo.jsp">join</a></li>
        <li><a href="battle.jsp">Where</a></li>
        <li><a href="help.jsp">Help</a></li>
    </ul>
</div>
<td>
</tr>
</table>
<script src =
      "http://www.java.com/js/deployJava.js"></script>
    <script> 
        <!-- ... -->
         deployJava.runApplet(attributes,
            parameters, '1.7'); 
    </script>          
    <!-- ... -->
    <p id="summary">  </p>
    <noscript>A browser with JavaScript enabled is required for this page to operate properly.</noscript>
    <!-- h1>Dynamic Tree Applet Demo</h1>
    <h2>This applet has been deployed with the applet tag <em>without</em> using JNLP</h2-->
    <!-- applet alt = "Dynamic Tree Applet Demo" 
        code = 'robocode.Robocode'
        archive = "applet9.jar" 
        codebase = "."
        width = 800,
        height = 600 >
        <param name="robot_names" value="Crazy,Corners">
        <param name="numOfRounds" value="4">
        </applet-->
<center>
<div class="Text"> Load robots and select the robots to play</div>
<table cellpadding=20px>
<tr>
<td>
<select class="select" id="selectNumber" multiple>
    <option>Choose a Robot</option>
</select>
</td>
</tr>
<tr><td><button onclick="loadContent()">LoadRobots</button></td></tr>
<tr></tr>
<tr><td><button onclick="startGame()">StartBattle</button></td></tr>
</table>
</center>