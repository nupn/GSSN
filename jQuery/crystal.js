
var CRYSTAL_APP_ID ='399761313426588';
var CRYSTAL_APP_SECRET ='3b13ae4c8d62eb522ce0b4d41ffc0b4d';//삭제해야뎀
var CRYSTAL_GET='GET';

var CRYSTAL_EMPTY="empty";

var CRYSTAR_STATUS_LOGIN="1";
var CRYSTAR_STATUS_DAUTH="2";
var CRYSTAR_STATUS_LOGOUT="3";

var CRYSTAR_ITEM_PRICE="Price";
var CRYSTAR_ITEM_GRADE="Grade";
var CRYSTAR_ITEM_DATE="Date";

var CRYSTAR_BROWSER_IE="1";
var CRYSTAR_BROWSER_NIE="2";
var currentBrowser;



$(function crystarinit(){
	jQuery.support.cors = true;
	
  	if (window.ActiveXObject) {
        currentBrowser=CRYSTAR_BROWSER_IE;
    }
    else if (window.XMLHttpRequest) {
        currentBrowser=CRYSTAR_BROWSER_NIE;
    }
	
});

function callJson(url,method,desct,listener){
	
  $.ajaxPrefilter('json', function(options, orig, jqXHR) {
        return 'jsonp';
    });
 
  
    $.ajax({
        url: url
        , crossDomain: true
		, dataType: 'json'
        , type: method
        , data: desct
        , success: function( data, textStatus, jqXHR )
        {
			if(listener!=null && data!=null)
			{
				listener(data);
			}
			
        }
        , error: function( jqXHR, textStatus, errorThrown )
        {
        }
    });
}

function callJsonP(url,method,desct,listener){
	
 
  
    $.ajax({
        url: url
        , crossDomain: true
		, dataType: 'json'
        , type: method
        , data: desct
        , success: function( data)
        {
			if(listener!=null && data!=null)
			{
				listener(data);
			}
			
        }
        , error: function( jqXHR, textStatus, errorThrown )
        {
			alert(jqXHR+textStatus);
        }
    });
}
function getDynamicDOMExtend(ptype,pram){
	
	var item ;
	if(currentBrowser==CRYSTAR_BROWSER_IE)
	{
		item= document.createElement(ptype);
		
		$(pram).each(function(index, element) {
			item[element.name]=element.value;
		});
		
		
	}else{
		
		item= document.createElement(ptype);
		
		
		$(pram).each(function(index, element) {
			item.setAttribute(element.name , element.value );
		});
	
		
	}
	
	return item;
}

function getDynamicDOM(ptype,pclass,pid){
	
	var item ;
	if(currentBrowser==CRYSTAR_BROWSER_IE)
	{
		item= document.createElement(ptype);
		
		if(pclass!=null)
		item.className =  pclass;
		
		if(pid!=null)
		item.id = pid;
		
		
	}else{
		
		item= document.createElement(ptype);
		
		if(pclass!=null)
		item.setAttribute("class",pclass);
		
		if(pid!=null)
		item.setAttribute("id",pid);
		
		
		
	}
	
	return item;
}

function getDynamicDOMLink(ptype,pclass,pid,href){
	var item ;
	if(currentBrowser==CRYSTAR_BROWSER_IE)
	{
		item= document.createElement(ptype);
		
		if(pclass!=null)
		item.className =  pclass;
		
		if(pid!=null)
		item.id = pid;
		
		if(href!=null)
		item.href =href;
		
	}else{
		
		item= document.createElement(ptype);
		
		if(pclass!=null)
		item.setAttribute("class",pclass);
		
		if(pid!=null)
		item.setAttribute("id",pid);
		
		if(href!=null)
		item.setAttribute("href",href);
		
	}
	
	return item;
}

function cst_show(id){
	$(id).show();
 if($.browser.msie){
   $(id).css({"visibility":"visible"});
 }
}

function cst_hide(id){
	$(id).hide();
  if($.browser.msie){
   $(id).css({"visibility":"hidden"});
  }
}

function getDynamicDOMImg(psrc,pclass,pid,palt){
	
	var item ;
	if(false)
	{
		
		item= document.createElement('scr');
		
		if(psrc!=null)
		item.src =  psrc;
		
		if(palt!=null)
		item.alt = palt;
		
		if(pclass!=null)
		item.className =  pclass;
		
		if(pid!=null)
		item.id = pid;
		
		
	}else{
		
		item= document.createElement('img');
		
		if(pclass!=null)
		item.setAttribute("class",pclass);
		
		if(pid!=null)
		item.setAttribute("id",pid);
		
		if(psrc!=null)
		item.setAttribute("src",psrc);
		
		if(palt!=null)
		item.setAttribute("alt",palt);
	}
	
	return item;
}

function cst_clearNodeById(elementId)
{
	var elem = document.getElementById(elementId);
	if(elem!=null)
	{
		while (elem.hasChildNodes())
		elem.removeChild(elem.lastChild );
	}
}

function cst_addSimpleTimer(func,time){
var timer=setInterval(endTimer,time);
	
	function endTimer() {
			clearInterval( timer );
			func();
	}
}


//desprecated
function cst_strip_tags (input, allowed) {
    allowed = (((allowed || "") + "").toLowerCase().match(/<[a-z][a-z0-9]*>/g) || []).join(''); // making sure the allowed arg is a string containing only tags in lowercase (<a><b><c>)
    var tags = /<\/?([a-z][a-z0-9]*)\b[^>]*>/gi,
        commentsAndPhpTags = /<!--[\s\S]*?-->|<\?(?:php)?[\s\S]*?\?>/gi;
    return input.replace(commentsAndPhpTags, '').replace(tags, function ($0, $1) {        return allowed.indexOf('<' + $1.toLowerCase() + '>') > -1 ? $0 : '';
    });
}

function htmlEntityDecode(str){
	//$("<div/>").html(str).text()
	return $("<div/>").html(str).text();
}

Array.prototype.isItem = function(object) { 
	for (var i = 0, length = this.length; i < length; i++) 
		if (this[i] == object) return i; 
		return -1; 
	
} 

/*
function cst_clearNodeByClass(classtId)
{
	var elem = document.getElementsByClassName(classtId);
	while (elem.hasChildNodes())
    elem.removeChild(elem.lastChild );
}*/

/*//ie 9이하처리 참고
		var domscr = "<"+ptype+" " ;
		
		if(pclass!=null)
			domscr +="class=\""+ pclass+"\"";
		
		if(pid!=null)
			domscr +="id="+"\""+ pid+"\"";
			
		
		domscr +="></"+ptype+">";
		//domscr +="/>";
		
		item = document.createElement(domscr);
		
		
		var domscr = "<img ";
		
		if(psrc!=null);
		 domscr+="src="+"\""+psrc+"\"";
		
		if(pclass!=null)
			domscr +="class="+"\""+ pclass+"\"";
		
		if(pid!=null)
			domscr +="id="+"\""+ pid+"\"";
			
		if(palt!=null)
			domscr +="alt="+"\""+ palt+"\"";
		
		domscr +="/>";
		
		item = document.createElement(domscr);
		*/