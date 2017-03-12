function setCookie(cname, cvalue, exdays) {
	var d = new Date();
	d.setTime(d.getTime() + (exdays*24*60*60*1000));
	var expires = "expires="+ d.toUTCString();
	document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}
function getCookie(cname) {
	var name = cname + "=";
	var decodedCookie = decodeURIComponent(document.cookie);
	var ca = decodedCookie.split(';');
	for(var i = 0; i <ca.length; i++) {
		var c = ca[i];
		while (c.charAt(0) == ' ') {
			c = c.substring(1);
		}
		if (c.indexOf(name) == 0) {
			return c.substring(name.length, c.length);
		}
	}
	return "";
}

function generateCarousel(nf, face, nt, tw){
	// fb
	resultfb="<div class=\"carousel-inner\" role=\"listbox\"> "
	for(var i=0;i<nf;++i){
		resultfb = resultfb + "<div align=\""+(i%2 == 0 ? "right" : "left")+"\" class=\"col-md-3\">"
		resultfb= resultfb+"<div class=\"carousel-item \"> <iframe src=\"https://www.facebook.com/plugins/post.php?href=https://www.facebook.com/"
		+face[i].user+"/posts/"+face[i].post+"/&width=325&show_text=true&height=500&appId\" width=\"325\" height=\"500\" style=\"border:none;overflow:hidden\" scrolling=\"no\" frameborder=\"0\" allowTransparency=\"true\"></iframe> </div>"
		resultfb= resultfb+ "</div>";
	}
	resultfb= resultfb+ "</div>";
 	$('#facebookInner').html(resultfb);

	// tw
	result="<div class=\"row\">"
	resIndicators = "";
	for(var i=0;i<nt;++i){
		result = result + "<div align=\""+(i%2 == 0 ? "right" : "left")+"\" class=\"col-md-3\">"
		result= result+"<iframe border=0 frameborder=0 height=500 width=325 src=\"http://twitframe.com/show?url=https://twitter.com/"+tw[i].user+"/status/"+tw[i].post+"\"></iframe>" 
		result = result + "</div>";
		
		resIndicators = resIndicators + "<li data-target=\"#carouseltw\" data-slide-to="+i + " " + (i== 0 ? "class=\"active\"" : "") + "></li>";
	}
	result= result+ "</div>";
	$('#twitterInner').html(result);
	$('#twitterIndic').html(resIndicators);

}

function generateRadioButton(n,atributos){
	var radiosOpcionales = "";
	for (var i = n - 1; i >= 0; i--) {
		radiosOpcionales = radiosOpcionales + "<div class=\"col-md-offset-3 col-md-6\">";
		radiosOpcionales = radiosOpcionales + "<div class=\"well well-lg valign\">";
		radiosOpcionales = radiosOpcionales + "<div class=\"container-fluid\"><div class=\"row\"><div class=\"col-md-3\">";
		radiosOpcionales = radiosOpcionales + "<input type=\"text\" class=\"form-control pull-left\" disabled value=\""+atributos[i].value+"\">";
		radiosOpcionales = radiosOpcionales + "</div><div class=\"col-md-3\">";
		radiosOpcionales = radiosOpcionales + "<input type=\"text\" class=\"form-control pull-left\" disabled value=\""+getModeStr(atributos[i].mode)+"\">";
		radiosOpcionales = radiosOpcionales + "</div><div class=\"col-md-6\">";
		radiosOpcionales = radiosOpcionales + "<button type=\"button\" class=\"btn btn-info\" style=\"width:100px;\" onclick=\"executeFilter("+atributos[i].id+",'"+atributos[i].value+"')\"><i class=\"fa fa-info\" aria-hidden=\"true\"></i>&nbsp;&nbsp;Load</button>";
		radiosOpcionales = radiosOpcionales + "<button type=\"button\" class=\"btn btn-danger\" style=\"width:100px;\" onclick=\"removeFilter("+atributos[i].id+")\"><i class=\"fa fa-eraser\" aria-hidden=\"true\"></i>&nbsp;Delete</button>";
		radiosOpcionales = radiosOpcionales + "</div></div></div></div></div>";
	}
	$('#filtersRadio').html(radiosOpcionales);
}

function setMessage(el, type, message){
	if(type == 0){
		$("#" + el).html("<div class=\"alert alert-success\"><strong>Success!</strong> "+message+"</div>");
	}else{
		$("#" + el).html("<div class=\"alert alert-danger\"><strong>Error!</strong> "+message+"</div>");
	}
}

function getModeStr(i){
	if(i == 0){
		return "Mixed";
	}else if(i == 1){
		return "Recent";
	}else if(i == 2){
		return "Popular";
	}
	
	return "";
}

function addFilter(){
	var name = getCookie("user");
	var filter = $("#newFilter").val();
	var mode = $("#newFilterMode").val();
	$.post("/ucode2017/lib/addFilter.php", {user:name, filter:filter, mode:mode},
		function(data){
			var json = $.parseJSON(data);
			var error = json.error[0];
			setMessage("filterHeader", error, json.message[0]);
			if(error == 0){
				loadFilters();
			}
		}
	);
}

function discardFilter(){
	$("#newFilter").val("");
}

function executeFilter(i, filterName){
	var name = getCookie("user");
	$("#searchWords").val(filterName);
	setMessage("filterHeader", 0, "Loading posts... Please wait.");
	$.post("/ucode2017/lib/searchFilter.php", {user:name, filter:i},
		function(data){
			var json = $.parseJSON(data);
			if(json.pId[0] == 0){
				var error = json.error[0];
				setMessage("filterHeader", error, json.message[0]);
			}else{
				generateCarousel(json.fbCount, json.fb[0], json.twCount, json.tw[0]);
				setMessage("filterHeader", 0, "Posts have been loaded.");
				window.location.hash = '#searchWords';
			}
		}
	);
}

function removeFilter(i){
	var name = getCookie("user");
	$.post("/ucode2017/lib/removeFilter.php", {user:name, filter:i},
		function(data){
			var json = $.parseJSON(data);
			var error = json.error[0];
			setMessage("filterHeader", error, json.message[0]);
			if(error == 0){
				loadFilters();
			}
		}
	);
}

function loadFilters(){
	var user = getCookie("user");
	$.post("/ucode2017/lib/getFilters.php", {user:user},
		function(data){
			var json = $.parseJSON(data);
			if(json.pId == 0){
				var error = json.error;
				setMessage("filterHeader", error, json.message[0]);
			}else{
				generateRadioButton(json.filterCount, json.filters[0]);
			}
		}
	);
}

$(document).ready(function() {
	var user = getCookie("user");
	if(user == ""){
		window.location.replace("/ucode2017/index.html");
	}else{
		$('#nameUser').html(user);
		loadFilters();
		
	}
});

$(document).ready(
	function(){
		$('#searchNormal').click(function(){
			var user = getCookie("user");
			var search = $("#searchWords").val();
			if(search == ""){
				if(search == ""){
					setMessage("searchHeader", 1, "No keyword given.");
				}
			}else{
				setMessage("searchHeader", 0, "Loading posts... Please wait.");
				$.post("/ucode2017/lib/search.php", {user:user, search:search},
					function(data){
						var json = $.parseJSON(data);
						if(json.pId == 0){
							var error = json.error;
							setMessage("searchHeader", error, json.message[0]);
						}else{
							generateCarousel(json.fbCount, json.fb[0], json.twCount, json.tw[0]);
							setMessage("searchHeader", 0, "Posts have been loaded.");
						}
					}
				);
			}
		});
	}
);