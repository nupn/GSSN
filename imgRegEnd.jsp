<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<% 
	String filename = (String)request.getAttribute("filename");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/R/html4/loose.dtd">
<html>
<head>
<meta charset=EUC-KR">
<title>success upload</title>
</head>
<body>
	<%=filename%> success!
</body>
</html>
<script language="javascript" type="text/javascript" src='./lib/jquery.js'></script>
<script language="javascript" type="text/javascript" src='./lib/crystal.js'></script>
<script type="text/javascript">
var receiveImg;
$(function(){
		
		var source="http://www.crystars.com/upload/"+ '<%=filename%>';
		try{
		parent.opener.document.getElementById('img1').src = source;
		}catch(err){}
		try{
			opener.document.getElementById('img1').src = source;
		}catch(err){}
		
		
		cst_selfClose(1);	
		
	
});

</script>