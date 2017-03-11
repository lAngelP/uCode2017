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
		

$(document).ready(
	function(){
		$('#loginBtn').click(function(){
			alert("Login");
			var user = $("#userLogin").val();
			var pass = $("#passLogin").val();
			if(user == "" || pass == ""){
				if(user == ""){
					$('div[id="user_container"]').addClass("has-error");
				}
				
				if(pass == ""){
					$('div[id="pass_container"]').addClass("has-error");
				}
			}else{
				alert("Recv0");
				$.post("/ucode2017/lib/login.php", {user:user, pass:pass},
						function(data){
							alert("Recv");
							var json = $.parseJSON(data);
							$('#messageLogin').html(json.message);
							var error = json.error;
							
							if(error == 0){
								setCookie("user", user, 30);
								window.location.replace("/ucode2017/busqueda.html");
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
							
							if(error == 0){
								setCookie("user", user, 30);
								window.location.replace("/ucode2017/busqueda.html");
							}
						}
					);
			}
		});
	}
);

function validateMail(mail){
	var exp = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
	return exp.test(mail);
}