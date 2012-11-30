<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>에러</title>
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/base.css" rel="stylesheet">
  <script src="js/jquery-1.8.2.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
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
      <div class="form-action">
	 		<a href="board" class="btn">목록으로</a>
	 	</div>
  </div>
  
</body>
</html>
