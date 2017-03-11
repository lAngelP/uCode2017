$(document).ready(
	function(){
		$('#checkKey').click(function(){
			var user = $("#user").val();
			var key = $("#key").val();
			
			if(user == "" || key == ""){
				if(user == ""){
					$('div[id="user_container"]').addClass("has-error");
				}
				
				if(key == ""){
					$('div[id="key_container"]').addClass("has-error");
				}
			}else{
				$('div[id="user_container"]').removeClass("has-error");
				$('div[id="key_container"]').removeClass("has-error");
				
				$.post("/lib/register1.php", {username:user, key:key},
						function(data){
							var json = $.parseJSON(data);
							var code = json.code;
							var error = true;
							
							if(code == "register.error.success"){
								error = false;
							}
							
							if(error){
								$('div[id="message1"]').removeClass("text-success bg-success");
								$('div[id="message1"]').addClass("text-danger bg-danger");
								$('div[id="user_container"]').addClass("has-error");
								
								if(code == "register.error.notFound"){
									$('div[id="user_container"]').addClass("has-error");
								}else{
									$('div[id="key_container"]').addClass("has-error");
								}
							}else{
								$('div[id="message1"]').removeClass("text-danger bg-danger");
								$('div[id="message1"]').addClass("text-success bg-success");
								
								$('div[id="panelStep1"]').removeClass("panel-primary");
								$('div[id="panelStep1"]').addClass("panel-success");
								$('div[id="panelStep2"]').addClass("panel-primary");
								
								$('input[id="user"]').prop('disabled', true);
								$('input[id="key"]').prop('disabled', true);
								
								$('button[id="checkKey"]').prop('disabled', true);
								$('button[id="goTo2"]').show("slow");
								$('button[id="checkKey"]').removeClass("btn-primary");
								$('button[id="checkKey"]').addClass("btn-disabled");
							}
							
							$('#message1').html(json.display);
						}
					);
			}
		});
		
		$('#sendPassword').click(function(){
			var user = $("#user").val();
			var pass0 = $("#pass0").val();
			var pass1 = $("#pass1").val();
			
			if(user == "" || pass0 == "" || pass1 == ""){
				if(pass0 == ""){
					$('div[id="pass0_container"]').addClass("has-error");
				}
				
				if(pass1 == ""){
					$('div[id="pass1_container"]').addClass("has-error");
				}
			}else{
				
				$.post("/lib/register2.php", {username:user, pass0:pass0, pass1:pass1},
						function(data){
							var json = $.parseJSON(data);
							var code = json.code;
							var error = true;
							
							if(code == "register.error.success1"){
								error = false;
							}
							
							$('div[id="pass0_container"]').removeClass("has-error");
							$('div[id="pass1_container"]').removeClass("has-error");
							
							if(error){
								$('div[id="message2"]').removeClass("text-success bg-success");
								$('div[id="message2"]').addClass("text-danger bg-danger");
								//$('div[id="user_container"]').addClass("has-error");
								if(code == "register.error.invalidPassword"){
									$('div[id="pass0_container"]').addClass("has-error");
									$('div[id="pass1_container"]').addClass("has-error");
								}
							}else{
								$('div[id="message2"]').removeClass("text-danger bg-danger");
								$('div[id="message2"]').addClass("text-success bg-success");
								
								$('div[id="panelStep2"]').removeClass("panel-primary");
								$('div[id="panelStep2"]').addClass("panel-success");
								$('div[id="panelStep3"]').addClass("panel-primary");
								
								$('input[id="pass0"]').prop('disabled', true);
								$('input[id="pass1"]').prop('disabled', true);
								
								$('button[id="sendPassword"]').prop('disabled', true);
								$('button[id="goTo3"]').show("slow");
								$('button[id="sendPassword"]').removeClass("btn-primary");
								$('button[id="sendPassword"]').addClass("btn-disabled");
							}
							
							$('#message2').html(json.display);
						}
					);
			}
		});
		
		$('#sendMail').click(function(){
			var user = $("#user").val();
			var pass0 = $("#pass0").val();
			var mail = $("#mail").val();
			
			if(user == "" || pass0 == "" || mail == ""){
				if(mail == ""){
					$('div[id="mail_container"]').addClass("has-error");
				}
			}else{
				
				$.post("/lib/register3.php", {username:user, pass:pass0, mail:mail},
						function(data){
							console.log(data);
							var json = $.parseJSON(data);
							var code = json.code;
							var error = true;
							
							if(code == "register.error.success2"){
								error = false;
							}
							
							$('div[id="mail_container"]').removeClass("has-error");
							
							if(error){
								$('div[id="message2"]').removeClass("text-success bg-success");
								$('div[id="message2"]').addClass("text-danger bg-danger");
								//$('div[id="user_container"]').addClass("has-error");
								if(code == "register.error.invalidPassword"){
									$('div[id="pass0_container"]').addClass("has-error");
									$('div[id="pass1_container"]').addClass("has-error");
								}
							}else{
								$('div[id="message3"]').removeClass("text-danger bg-danger");
								$('div[id="message3"]').addClass("text-success bg-success");
								
								$('div[id="panelStep3"]').removeClass("panel-primary");
								$('div[id="panelStep3"]').addClass("panel-success");
								
								$('input[id="mail"]').prop('disabled', true);
								
								$('button[id="sendMail"]').prop('disabled', true);
								$('button[id="sendMail"]').removeClass("btn-primary");
								$('button[id="sendMail"]').addClass("btn-disabled");
							}
							
							$('#message3').html(json.display);
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