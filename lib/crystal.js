
document.write('<scr'+'ipt type="text/javascript" src="http://www.crystars.com/lib/chromeless.js"><\/scr'+'ipt>');
var CST_HOME = "http://www.crystars.com";
var CST_IMG_ROOT = CST_HOME+"/upload/";
var CRYSTAL_APP_ID ='399761313426588';
var CRYSTAL_APP_SECRET ='3b13ae4c8d62eb522ce0b4d41ffc0b4d';//삭제해야뎀
var CRYSTAL_GET='GET';
var CRYSTAL_POST='POST';

var CRYSTAL_EMPTY="empty";

var CRYSTAR_STATUS_LOGIN="1";
var CRYSTAR_STATUS_DAUTH="2";
var CRYSTAR_STATUS_LOGOUT="3";

var CRYSTAR_ITEM_PRICE="Price";
var CRYSTAR_ITEM_GRADE="Grade";
var CRYSTAR_ITEM_DATE="Goods\.Regist_Date";

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

function getFacebookPortrait(id)
{
	return 'http://graph.facebook.com/'+id+'/picture';
		
}

function cst_movePage(url,data,method)
{
	var form = getDynamicDOMExtend("form" , [{"name":"action","value":url},{"name":"method","value":method}]);
	var socket;
	$(data).each(function(index, element) {
        socket =  getDynamicDOMExtend("input" , [{"name":"type","value":"hidden"},{"name":"name","value":element.name},{"name":"value","value":element.value}]);
		
			
		form.appendChild(socket);
    });
	
	
	
	
	$('body').append(form);
	form.submit();
	
}

function callJson(url,method,desct,listener){
  //setAjaxFilter();
	
	//desct.callback=jsonrecieve;
	
    $.ajax({
        url: url
        , crossDomain: true
		, dataType : "json"
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
			alert(jqXHR+textStatus);
        }
    });
}

function jsonrecieve(){
}

function callJsonP(url,method,desct,listener){
	//setAjaxFilter();
	
	
    $.ajax({
        url: url
        , crossDomain: true
		, dataType: 'jsonp'
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

function removeTag(str) {
	var pos1 = str.indexOf('<');

	if (pos1 == -1) return str;
	else {
		var pos2 = str.indexOf('>', pos1);

		if (pos2 == -1) return str;
		return removeTag(str.substr(0, pos1)+str.substr(pos2+1));
	}
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

function cst_selfClose(depth) { 

	if(depth==null || depth==0)
	{
		if (window.name !== 'window_id') {
		window.open('about:blank','_self');
		opener=window;
		window.close();
		}
	}else if(depth==1)
	{
		window.open('about:blank','_self');
		parent.opener=window;
		window.close();
	}
} 

function newchromeLess(theURL,W,H,X,Y, wname) { 
CLOSEdwn = "http://www.crystars.com/img/close_dwn.gif"
CLOSEup = "http://www.crystars.com/img/close_up.gif" 
CLOSEovr = "http://www.crystars.com/img/close_ovr.gif"
MINIdwn = "http://www.crystars.com/img/mini_dwn.gif"
MINIup = "http://www.crystars.com/img/mini_up.gif" 
MINIovr = "http://www.crystars.com/img/mini_ovr.gif"
NONEgrf = "http://www.crystars.com/img/none.gif" 
CLOCKgrf = "http://www.crystars.com/img/clock.gif" 
titHTML = "<font face=verdana size=2>이미지등록</font>" 
titWIN = "이미지등록" 
winBORDERCOLOR = "#666666"
winBORDERCOLORsel = "#000000" 
winBGCOLOR = "#666666"
winBGCOLORsel = "#000000" 
return openchromeless(theURL, wname, W, H, X, Y, NONEgrf, CLOSEdwn, CLOSEup, CLOSEovr, MINIdwn, MINIup, MINIovr, CLOCKgrf, titHTML, titWIN, winBORDERCOLOR, winBORDERCOLORsel, winBGCOLOR, winBGCOLORsel)
}


function openNewWindow(url) {
//var popup = window.open (url,"NewWindow","left=100, top=100, toolbar=yes, location=yes, directories=yes, status=yes, menubar=yes, scrollbars=yes, resizable=yes, width=600, height=420");

var popup =window.open(url,'name','menubar=yes,resizable=yes,scrollbars=yes,status=yes,titlebar=yes,toolbar=yes,location=yes,width=1024,height=768');
return popup;
}

function setAjaxFilter(){
	$.ajaxPrefilter('json', function(options, orig, jqXHR) {
			return 'jsonp';
		});
}

function ajaxReset(){
		$.ajaxSetup({
	   jsonp: null,
	   jsonpCallback: null
	});
		var oldCallbacks = [],
		rquestion = /\?/,
		rjsonp = /(=)\?(?=&|$)|\?\?/,
		nonce = $.now();
		
		$.ajaxPrefilter( "json jsonp", function( s, originalSettings, jqXHR ) {
	
		var callbackName, overwritten, responseContainer,
			data = s.data,
			url = s.url,
			hasCallback = s.jsonp !== false,
			replaceInUrl = hasCallback && rjsonp.test( url ),
			replaceInData = hasCallback && !replaceInUrl && typeof data === "string" &&
				!( s.contentType || "" ).indexOf("application/x-www-form-urlencoded") &&
				rjsonp.test( data );
	
		// Handle iff the expected data type is "jsonp" or we have a parameter to set
		if ( s.dataTypes[ 0 ] === "jsonp" || replaceInUrl || replaceInData ) {
	
			// Get callback name, remembering preexisting value associated with it
			callbackName = s.jsonpCallback = jQuery.isFunction( s.jsonpCallback ) ?
				s.jsonpCallback() :
				s.jsonpCallback;
			overwritten = window[ callbackName ];
	
			// Insert callback into url or form data
			if ( replaceInUrl ) {
				s.url = url.replace( rjsonp, "$1" + callbackName );
			} else if ( replaceInData ) {
				s.data = data.replace( rjsonp, "$1" + callbackName );
			} else if ( hasCallback ) {
				s.url += ( rquestion.test( url ) ? "&" : "?" ) + s.jsonp + "=" + callbackName;
			}
	
			// Use data converter to retrieve json after script execution
			s.converters["script json"] = function() {
				if ( !responseContainer ) {
					jQuery.error( callbackName + " was not called" );
				}
				return responseContainer[ 0 ];
			};
	
			// force json dataType
			s.dataTypes[ 0 ] = "json";
	
			// Install callback
			window[ callbackName ] = function() {
				responseContainer = arguments;
			};
	
			// Clean-up function (fires after converters)
			jqXHR.always(function() {
				// Restore preexisting value
				window[ callbackName ] = overwritten;
	
				// Save back as free
				if ( s[ callbackName ] ) {
					// make sure that re-using the options doesn't screw things around
					s.jsonpCallback = originalSettings.jsonpCallback;
	
					// save the callback name for future use
					oldCallbacks.push( callbackName );
				}
	
				// Call if it was a function and we have a response
				if ( responseContainer && jQuery.isFunction( overwritten ) ) {
					overwritten( responseContainer[ 0 ] );
				}
	
				responseContainer = overwritten = undefined;
			});
	
			// Delegate to script
			return "script";
		}
	});
	
	$.ajaxSetup({
		jsonp: "callback",
		jsonpCallback: function() {
			var callback = oldCallbacks.pop() || ( jQuery.expando + "_" + ( nonce++ ) );
			this[ callback ] = true;
			return callback;
		}
	});

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