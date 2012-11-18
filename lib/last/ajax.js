var xmlhttp;
var xmlDom = new ActiveXObject("Microsoft.XMLDOM");
var jsXmlDom; //jsxml.js ????..

function load_AJAX_XMLHttp() 
{
 	
    try 
    {
      xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
//      	xmlhttp = new ActiveXObject("Msxml2.XMLHTTP.3.0");
    }
    catch (e)
    {
      try 
      {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
      } 
      catch (E) 
      {
        xmlhttp = false;
      }
    }
    
   if(!xmlhttp && typeof XMLHttpRequest != 'undefined') 
   {
		try 
		{
		  xmlhttp = new XMLHttpRequest();
		} 
		catch (e) 
		{
		  xmlhttp = false;
		}
  }
  
}


function AJAX_Request(method,url,params,async,rpfunc)
{
	if(method.toUpperCase() == "GET")
	{
		xmlhttp.open(method, url+"?"+params, async);
		xmlhttp.onreadystatechange = eval(rpfunc);
		xmlhttp.send("");
	}
	else if(method.toUpperCase() == "POST")
	{
		xmlhttp.open(method, url, async);
		xmlhttp.onreadystatechange = eval(rpfunc);
		xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		xmlhttp.send(params);
	}
}

function formData2QueryString(docForm)
{
	var strSubmitContent = '';
	var formElem;
	var strLastElemName = '';
	
	for (i = 0; i < docForm.elements.length; i++) {
		
		formElem = docForm.elements[i];
		switch (formElem.type) {
			// Text fields, hidden form elements
			case 'text':
			case 'hidden':
			case 'password':
			case 'textarea':
			case 'select-one':
				strSubmitContent += formElem.name + '=' + encodeURIComponent(formElem.value) + '&'
				break;
				
			// Radio buttons
			case 'radio':
				if (formElem.checked) {
					strSubmitContent += formElem.name + '=' + encodeURIComponent(formElem.value) + '&'
				}
				break;
				
			// Checkboxes
			case 'checkbox':
				if (formElem.checked) {
					// Continuing multiple, same-name checkboxes
					if (formElem.name == strLastElemName) {
						// Strip of end ampersand if there is one
						if (strSubmitContent.lastIndexOf('&') == strSubmitContent.length-1) {
							strSubmitContent = strSubmitContent.substr(0, strSubmitContent.length - 1);
						}
						// Append value as comma-delimited string
						strSubmitContent += ',' + encodeURIComponent(formElem.value);
					}
					else {
						strSubmitContent += formElem.name + '=' + encodeURIComponent(formElem.value);
					}
					strSubmitContent += '&';
					strLastElemName = formElem.name;
				}
				break;
				
		}
	}
	
	// Remove trailing separator
	strSubmitContent = strSubmitContent.substr(0, strSubmitContent.length - 1);

	return strSubmitContent;
}


function parseXML(xmlDoc) 
{
//		alert(xmlDoc.xml);
		
//		var _tdhead = document.all.rphead;
//		var _sel = document.createElement("SELECT");

		var rphead = new Array();		
		var _head_items = xmlDoc.selectNodes("//HEAD/*");
//		alert(_head_items.length);
		for(var i=0; i<_head_items.length; i++)
		{
//			var _option = document.createElement("OPTION");
			var _node = _head_items.item(i);
			/*--- 배열에 저장하기 위한코드 --*/
			rphead[_node.nodeName] = _node.text;
//			alert(rphead[_node.nodeName]);
			/*-- 끝--*/
			//alert(_node.nodeName+":"+_node.text);
			
//			_option.value = _node.text;
//			_option.innerText = _node.nodeName;
//			_sel.appendChild(_option);
		}

//		_tdhead.appendChild(_sel); 	

		/*-- rphead 데이타 분석 처리 --*/
		
		if(rphead["error"] == "true")
		{
			alert(rphead["message"]);
			return false;
		}
		
		
		var _result_items = xmlDoc.selectNodes("//RESULT/*");
//		var _tdresult = document.all.rpresult;

		//1차원배열 설정 TR로 생각하면 됨..
		var rpdata = new Array(_result_items.length);
		
		for(var i=0; i<_result_items.length; i++)
		{
			var _nodeList =_result_items[i].childNodes;
//			_sel = document.createElement("SELECT");
			
//			alert(_nodeList.length);
			
			//node가 여러개 일경우...
			//2차원 배열로 계속 저장.. TD로 생각하면 됨..
			rpdata[i] = new Array();
			
			for(var j=0;j<_nodeList.length;j++)
			{		
				/*--- 배열에 저장하기 위한코드 --*/
				if(_nodeList.item(j).text == null)
					_nodeList.item(j).text = "";
				
				
				rpdata[i][_nodeList.item(j).nodeName] = _nodeList.item(j).text;
				
				//alert(_nodeList.item(j).nodeName+"="+rpdata[i][_nodeList.item(j).nodeName]);
				/*-- 끝 --*/
				
//				var _option = document.createElement("OPTION");			
//				_option.value= _nodeList.item(j).text;
//				_option.innerText= _nodeList.item(j).nodeName;
//				_sel.appendChild(_option);
			}
			
//			_tdresult.appendChild(_sel); 

		}
		
	/*-- tagname으로 접근하는 예제 */
	/*
		//record가 1개이므로
		rpdata["wFutJochiCode"] = xmlDoc.getElementsByTagName("wFutJochiCode").item(0).text;
		rpdata["acFutCode"] = xmlDoc.getElementsByTagName("acFutCode").item(0).text;
		rpdata["acFutJochi"] = xmlDoc.getElementsByTagName("acFutJochi").item(0).text;
		rpdata["acFutText"] = xmlDoc.getElementsByTagName("acFutText").item(0).text;
		rpdata["acFutReserved"] = xmlDoc.getElementsByTagName("acFutReserved").item(0).text;
		rpdata["wOptJochiCode"] = xmlDoc.getElementsByTagName("wOptJochiCode").item(0).text;
		rpdata["acOptCode"] = xmlDoc.getElementsByTagName("acOptCode").item(0).text;
		rpdata["acOptJochi"] = xmlDoc.getElementsByTagName("acOptJochi").item(0).text;
		rpdata["acOptText"] = xmlDoc.getElementsByTagName("acOptText").item(0).text;
		rpdata["acOptReserved"] = xmlDoc.getElementsByTagName("acOptReserved").item(0).text;
	*/																	
//		alert(rpdata["wFutJochiCode"]);
		
		//alert(rpdata);
		
		return rpdata;			
}

function getHead(element_name,xmlDoc) 
{
//		alert(xmlDoc.xml);
		
//		var _tdhead = document.all.rphead;
//		var _sel = document.createElement("SELECT");

		var rphead = new Array();		
		var _head_items = xmlDoc.selectNodes("//HEAD/*");
//		alert(_head_items.length);
		for(var i=0; i<_head_items.length; i++)
		{
//			var _option = document.createElement("OPTION");
			var _node = _head_items.item(i);
			/*--- 배열에 저장하기 위한코드 --*/
			rphead[_node.nodeName] = _node.text;
//			alert(rphead[_node.nodeName]);
			/*-- 끝--*/
			//alert(_node.nodeName+":"+_node.text);
			
//			_option.value = _node.text;
//			_option.innerText = _node.nodeName;
//			_sel.appendChild(_option);
		}

//		_tdhead.appendChild(_sel); 	

		/*-- rphead 데이타 분석 처리 --*/
		
		return rphead[element_name];
}