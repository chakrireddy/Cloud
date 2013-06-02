<!DOCTYPE html>
<html lang="en">
	<head>
	<link type="text/css" rel="stylesheet" href="css/bootstrap.css" />
	<link type="text/css" rel="stylesheet" href="stylesheet.css" />
	<link type="text/css" href="css/ui.multiselect.css" rel="stylesheet" />
	<link type="text/css" rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/themes/ui-lightness/jquery-ui.css" />
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8.10/jquery-ui.min.js"></script>
	<script type="text/javascript" src="js/plugins/localisation/jquery.localisation-min.js"></script>
	<script type="text/javascript" src="js/plugins/scrollTo/jquery.scrollTo-min.js"></script>
	<script type="text/javascript" src="js/ui.multiselect.js"></script>
	<script type="text/javascript">
	function delRobo()
	{
		alert("This function will delete ROBO");
	}
	function loadRobo(){
		var select = document.getElementById("countries");
		var options = ["hARI","CHAKRI","AMRUT","TATA","LAILA"];
		for(var i = 0; i < options.length; i++) {
    	var opt = options[i];
    	var el = document.createElement("option");
     	el.textContent = opt;
    	el.value = opt;
    	select.appendChild(el);
	}

	}
	
		$(function(){
			loadRobo();
			$.localise('ui-multiselect', {/*language: 'en',*/ path: 'js/locale/'});
			$(".multiselect").multiselect();
			$('#switcher').themeswitcher();
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
              <li><a href = "home.html">Home </a></li>
              <li><a href = "rDetails.html">Robo Details</a></li>
              <li><a href = "uc.html">Profile</a></li>
              <li><a href = "home.html">Battle Details</a></li>
              <li><a href = "about.html">About </a></li>
            </ul>
      </div>
        </div>
  </div>
    </div>
<div class="abc">
      <div style = "padding:40px;">
    <div class = "well" >
          <table width="100%">
        <tr>
              <td width="33%"/>
              <td width="33%"><form>
                  <select id="countries" class="multiselect" multiple="multiple" name="countries[]" style="height:300px"  >
                </select>
                </td>
              <td width=33%/>
            </tr>
      </table>
          <br>
          <br>
          <center>
        <button class="btn btn-primary" onClick="delRobo()" > Delete Robo </button>
        </form>
      </center>
        </div>
  </div>
    </div>
<script type="text/javascript"
      src="http://jqueryui.com/themeroller/themeswitchertool/">
	 
    </script>
</body>
</html>