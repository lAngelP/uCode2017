$(document).ready(
	function(){
		$('#login').click(function(){
			var user = $("#user").val();
			var pass = $("#pass").val();
			if(user == "" || pass == ""){
				if(user == ""){
					$('div[id="user_container"]').addClass("has-error");
				}
				
				if(pass == ""){
					$('div[id="pass_container"]').addClass("has-error");
				}
			}else{
				$.post("/ucode2017/lib/login.php", {user:user, pass:pass},
						function(data){
							var json = $.parseJSON(data);
							$('#message').html(json.message);
							int error = json.error;
							
							if(error == 0){
								document.cookie = "username=" + user;
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
							int error = json.error;
							
							if(error == 0){
								document.cookie = "username=" + user;
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