// JavaScript Document
document.write('<scr'+'ipt type="text/javascript" src="../lib/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="../lib/crystal.js"><\/scr'+'ipt>');

function fb_checkSession(id,token,callback){
	var data={};
	data.id = id;
	data.token = token;
 	callJsonP( "/CheckSession.do",CRYSTAL_GET,data,callback);
}
function fb_setUserInDB(raw,callback){
	
 	callJsonP( "/User.do",CRYSTAL_GET,raw,callback);
}

