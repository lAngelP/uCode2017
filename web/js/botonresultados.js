function myFunction(nf,nt,face,tw){
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
function myFunction(n,atributos){
	for (var i = n - 1; i >= 0; i--) {
		radiosOpcionales="<input type=\"radio\" name=\"gender\" value=\""+atributos[i]+"\">" atributos[i];  
	}
	document.getElementsById('carouselfb').innerHTML=resul;
}