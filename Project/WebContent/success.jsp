<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>수행이 완료되었습니다.</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/base.css" rel="stylesheet">
	<script src="js/jquery-1.8.2.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
 	<div class="container">
 		<div class="alert alert-success">
 			${msg}
 		</div>
	 	<div class="form-action">
	 		<a href="BoardServlet.do" class="btn">목록으로</a>
	 	</div>
 	</div>
</body>
</html>