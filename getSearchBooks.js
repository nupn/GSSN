document.write('<scr'+'ipt type="text/javascript" src="./jQuery/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./jQuery/prototype-1.6.0.2.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./jQuery/ajax.js"><\/scr'+'ipt>');

var xmlHttp;
function createXMLHttpRequest() {
    if (window.ActiveXObject) {
        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    else if (window.XMLHttpRequest) {
        xmlHttp = new XMLHttpRequest();
    }
}
   
function startRequest() {
    createXMLHttpRequest();
    xmlHttp.onreadystatechange = handleStateChange;
    xmlHttp.open("get", "/MVCtest.do", true);
    xmlHttp.send(null);
}
   
function handleStateChange() {
	alert(xmlHttp.status);
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {
           alert("The server replied with: " + xmlHttp.responseText);
				
				//xmlDom.loadXML(xmlHttp.responseTEXT);
				//var channel = xmlDoc.getElementsByTagName('channel').item(0);
				//alert(channel.getElementsByTagName('title').item(0).firstChild.data );
			
        }
    }
}
