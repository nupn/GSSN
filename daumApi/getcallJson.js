// JavaScript 
document.write('<scr'+'ipt type="text/javascript" src="../jQuery/jquery.js"><\/scr'+'ipt>');

function sendCall(){
	
	var datas={};
    datas.apikey = '056bdbabcaa675a7383729e2da0f9e42405eadf2';
    datas.q = 'nation';
  	datas.result = '10';
    datas.output = 'json';
	
 
  /*  jQuery.ajaxPrefilter( "script", function( s ) {
		return "script";
});
 */
  $.ajaxPrefilter('json', function(options, orig, jqXHR) {
        return 'jsonp';
    });
 
  
    $.ajax({
        url: "http://apis.daum.net/search/book"
        , crossDomain: true
		, dataType: 'json'
        , type: 'GET'
        , data: datas
        , success: function( data, textStatus, jqXHR )
        {
			alert(data.channel.item.length);
			//xmlDoc = $.parseXML( dt );
			//var $xml = $(xmlDoc);
            //alert("sccess : " + $xml.find("rss").find( "channel" ).find("title").text() );
        }
        , error: function( jqXHR, textStatus, errorThrown )
        {
            alert( textStatus + ", " + errorThrown );
        }
    });
}
