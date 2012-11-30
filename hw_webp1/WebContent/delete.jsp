<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>삭제</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/base.css" rel="stylesheet">
	<script src="js/jquery-1.8.2.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
</head>
<body>
	
	<form class="form-horizontal" action="board" method="POST">
 	<div class="container">
 	
		<input type="hidden" name="num" value="${board.num}">
 		<input type="hidden" name="_method" value="delete"/>
 		<div class="alert alert-success">
 			패스워드: 	<input type="password" name="pwd">
 		</div>
 		
	 	<div class="form-action">
	 		<a href="board" class="btn">목록으로</a>
	 		
	 		<input type="submit"  class="btn btn-danger" value="삭제">
	 	</div>
 	</div>
 	</form>
 	
</body>
</html>