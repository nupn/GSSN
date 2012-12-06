<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<% 
	String filename = (String)request.getAttribute("filename");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%=filename%>
</body>
</html>
<script language="javascript" type="text/javascript" src='./lib/jquery.js'></script>
<script language="javascript" type="text/javascript" src='./lib/crystal.js'></script>
<script type="text/javascript">
var receiveImg;
$(function(){
		var source="http://www.crystars.com/upload/"+ '<%=filename%>';
		opener.document.getElementById('img1').src = source;
		cst_selfClose();	
	
});

</script>