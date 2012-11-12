document.write('<scr'+'ipt type="text/javascript" src="./commonStr.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="../jQuery/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="../jQuery/prototype-1.6.0.2.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="../jQuery/json2.js"><\/scr'+'ipt>');
function nevSearchBook(str,fuc){
alert(NEV_SEARCH+'?key='+NEV_KEY_SEARCH+"&target="+NEV_BOOK+"&query="+str);
new Ajax.Request( 'http://www.crystars.com:8080/nproxy', {
  method:  'get',
  parameters:{ 'key': NEV_KEY_SEARCH ,'target':NEV_BOOK,'query':str ,'display':10,'start':1},
  onComplete:function(response){
   	alert(response.responseText);
	//var channel = response.responseXML.getElementsByTagName('channel').item(0);
	//alert(channel.getElementsByTagName('title').item(0).firstChild.data );
	//alert(JSON.stringify(response));
	//fuc();
  },
  onFailure:  function(){
    alert('ERROR');
  }
});
}
