<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>initalComplete</title>
</head>
<body>
	<jsp:useBean id="usr" class="com.crystars.UserDataObj" scope="request" />
	<jsp:getProperty name="usr" property="home" />
	${usr.name}
	
</body>
</html>
<%
out.println("<scirpt scr=\"./lib/jquery.js\"></scirpt>");
out.println("<script type=\"text/javascript\">");
out.println("$(function(){");
out.println("alert(\"aa\");");
out.println("	}); ");	
out.println( "</script>" );
%>