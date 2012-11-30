<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"  import="java.sql.*" 
    import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시판</title>
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/board.css" rel="stylesheet">
  <script src="js/jquery-1.8.2.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
</head>
<body>

	<div id="set">
		<br/>
		<h1 id="title2"> 게시판</h1>
		<br/>
		<div class="writebtn">

			<a href="board?op=write" id="writebtn" class="btn btn-info" type="button">글쓰기</a>

		</div>
		<br/>
		<table id="boardset" class="table table-striped">
			<thead >
				<tr>
					<td id="num">글 번호</td>
					<td id="title">글 제목</td>
					<td id="name">작성자</td>
					<td id="count">조회수</td>
				</tr>
            </thead>
            <tbody>
	            <c:forEach var="board" items="${board.list}">
				<tr>
					<td><c:out value="${board.num}"/></td>
					<td><a href="board?op=show&num=${board.num}" ><c:out value="${board.title}"/></a></td>
					<td><c:out value="${board.name}"/></td>
					<td><c:out value="${board.count}"/></td>
				</tr>
				</c:forEach>
            </tbody>

         </table>
         
         <br/>
    <jsp:include page="page.jsp">
      <jsp:param name="currentPage" value="${board.page}"/>
      <jsp:param name="url" value="board"/>
      <jsp:param name="startPage" value="${board.startPageNo}"/>
      <jsp:param name="endPage" value="${board.endPageNo}"/>
      <jsp:param name="numPages" value="${board.numPages}"/>
    </jsp:include>

     </div>
    </body>
</html>
<script type='text/javascript'>
$(function (){
	/*
	$('#writebtn').click(function(){
		
		window.open("write.jsp","글쓰기","_self");
	});*/

})
</script>
