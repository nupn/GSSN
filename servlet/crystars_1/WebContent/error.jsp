<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>에러</title>
	<link href="css/base.css" rel="stylesheet">
	<link href="css/bootstrap.css" rel="stylesheet">
 	<script src='js/jquery-1.8.2.js'></script>
</head>
<body>
	<br/>
  <div class="container">
    <div class="alert alert-error">
      <c:out value="${error}"/>
      
      <c:if test="${errorMsgs != null || errorMsgs.size() > 0 }">
        <h3>Errors:</h3>
        <ul>
          <c:forEach var="msg" items="${errorMsgs}">
            <li>${errorMsgs}</li>
          </c:forEach>
        </ul>
      </c:if>
    
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
		self.close();
	});
</script>
	
