<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>성공</title>
	<link href="css/base.css" rel="stylesheet">
	<link href="css/bootstrap.css" rel="stylesheet">
 	<script src='js/jquery-1.8.2.js'></script>
</head>
<body>
	<br/>
 	<div class="container">
 		<div class="alert alert-success">
 			${msg}
 		</div>
 		<c:choose>
 		<c:when test="${status == 1 }">
	 	<div class="form-action">
	 		<input id="closewindow" type="button" class="btn btn-danger" value="창닫기">
	 	</div>
	 	</c:when>
	 	<c:otherwise>
	 	<a href="javascript:history.go(-1)"><input type="button" class="btn btn-warning" value="뒤로"></a>
	 	</c:otherwise>
	 	</c:choose>
 	</div>
 
 			
</body>
</html>
<script type='text/javascript'>
	$("#closewindow").click(function(e){
		opener.location.reload();
		self.close();
	});
</script>