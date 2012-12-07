document.write('<scr'+'ipt type="text/javascript" src="./jQuery/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./jQuery/prototype-1.6.0.2.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./jQuery/json2.js"><\/scr'+'ipt>');
function nevSearchBook(str,fuc){
new Ajax.Request( 'www.crystars.com:8080/MVCtest', {
  method:  'post',
  parameters:{ 'name': "nupn" },
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
