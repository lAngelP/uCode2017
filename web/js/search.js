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
	resulfb="<div class=\"carousel-inner\" role=\"listbox\"> "
	for(i=0;i<nf;++1){
		resultfb= resultfb+"<div class=\"carousel-item \"> <iframe src=\"https://www.facebook.com/plugins/post.php?href=https://www.facebook.com/"
		+face[i].user+"/posts/"+face[i].post+"/&width=500&show_text=true&height=290&appId\" width=\"500\" height=\"290\" style=\"border:none;overflow:hidden\" scrolling=\"no\" frameborder=\"0\" allowTransparency=\"true\"></iframe> </div>"
	}
	resultfb= resultfb+ "</div>";
 	document.getElementsById('carouselfb').innerHTML=resulfb;

	// tw
	result="<div class=\"carousel-inner\" role=\"listbox\"> "
	for(i=0;i<nt;++1){
		result= result+"<div class=\"carousel-item \"> <iframe border=0 frameborder=0 height=250 width=550 src=\"http://twitframe.com/show?url=https://twitter.com/"+tw[i].user+"status/"+tw[i].post+"\"></iframe> " 
	}
	result= result+ "</div>";
	document.getElementsById('carouseltw').innerHTML=resul;
}
		

$(document).ready(
	function(){
		$('#searchNormal').click(function(){
			var user = getCookie("user");
			var search = $("#searchWords").val();
			if(search == ""){
				if(pass == ""){
					$('div[id="pass_container"]').addClass("has-error");
				}
			}else{
				$.post("/ucode2017/lib/search.php", {user:user, search:search},
						function(data){
							var json = $.parseJSON(data);
							if(json.pId == 0){
								var error = json.error;
								//TODO SENT TO DIV
							}else{
								var twCount, tw, fbCount, fb;
								
							}
						}
					);
			}
		});
		
		$('#register').click(function(){
			var user = $("#user").val();
			var pass0 = $("#pass0").val();
			var pass1 = $("#pass1").val();
			var mail = $("#mail").val();
			if(user == "" || pass0 == "" || pass1 == "" || mail == ""){
				if(user == ""){
					$('div[id="user_container"]').addClass("has-error");
				}
				
				if(pass0 == ""){
					$('div[id="pass0_container"]').addClass("has-error");
				}
				
				if(pass1 == ""){
					$('div[id="pass1_container"]').addClass("has-error");
				}
				
				if(mail == ""){
					$('div[id="mail_container"]').addClass("has-error");
				}
			}else{
				$.post("/ucode2017/lib/register.php", {user:user, pass0:pass0, pass1:pass1, mail:mail},
						function(data){
							var json = $.parseJSON(data);
							$('#message').html(json.message);
							var error = json.error;
							
							//TODO SEND TO DIV
						}
					);
			}
		});
	}
);