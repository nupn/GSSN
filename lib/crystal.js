
document.write('<scr'+'ipt type="text/javascript" src="jquery.js"><\/scr'+'ipt>');

var CRYSTAL_APP_ID ='399761313426588';
var CRYSTAL_APP_SECRET ='3b13ae4c8d62eb522ce0b4d41ffc0b4d';//삭제해야뎀
var CRYSTAL_GET='GET';

var CRYSTAL_EMPTY="empty";

var CRYSTAR_STATUS_LOGIN="1";
var CRYSTAR_STATUS_DAUTH="2";
var CRYSTAR_STATUS_LOGOUT="3";



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
        }
    });
}
