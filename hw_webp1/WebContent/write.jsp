<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"  import="java.sql.*" 
    import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<c:set var="met" value="${method}" /> 
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>게시판</title>
  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/write.css" rel="stylesheet">
  <script src="js/jquery-1.8.2.min.js"></script>
  <script src="js/bootstrap.min.js"></script>
</head>
<body>
	<div id="set">
	<form class="form-horizontal" action="board" method="POST">
		<br/>
		<input type="hidden" name="num" value="${board.num}">
		<c:choose>
				<c:when test="${method =='POST'}"> <!--  글쓰기 일때 -->
					<h1 id="title2"> 글쓰기</h1>
				</c:when>
				<c:when test="${method =='PUT'}"> <!-- 수정일때 -->
					<h1 id="title2"> 수정</h1>
          		</c:when>
				<c:otherwise>
					<h1 id="title2"> Show</h1>
				</c:otherwise>
		</c:choose>	
		
		
		<br/>
		
		<div class="lay1"> <!-- 작성자 -->
			<!-- 작성자: <input class="name" name="name" type="text" placeholder=".span2">-->
			<c:choose>
				<c:when test="${method =='POST'}"> <!--  글쓰기 일때 -->
					작성자: <input type="text" class="name" name="name" type="text" >
				</c:when>
				<c:when test="${method =='PUT'}"> <!-- 수정일때 -->
					<div class="name">작성자: ${board.name}</div>
					<input type="hidden" class="name" name="name" value="${board.name}"/>
          			<input type="hidden" name="_method" value="PUT"/>
          		</c:when>
				<c:otherwise>
					<div class="name" >작성자: ${board.name}</div>
					<div class="date">작성일자: ${board.date}</div>
					<input type="hidden" name="name" value="${board.name}" />
          			
				</c:otherwise>
			</c:choose>				
		</div>
		
		<div class="lay2"> <!-- 비밀번호 -->
		&nbsp;&nbsp;&nbsp;
			<c:choose>
			<c:when test="${method =='POST'}">
				비밀번호:   <input class="pwd" name="pwd" type="text" >
				</c:when>
				<c:when test ="${method == 'PUT'}">
				비밀번호:   <input class="pwd" name="pwd" type="text" >
				</c:when>
				<c:otherwise>
				 <input class="pwd" name="pwd" type="hidden" >
				</c:otherwise>
			</c:choose>
			
		</div>
		<br/>
		<div class="lay3"> <!-- 제목 -->
			<c:choose>
				<c:when test="${method =='POST'}">
				제목: <input class="title" name="title" type="text">
				</c:when>
				<c:when test="${method =='PUT'}">
				제목: <input class="title" name="title" value="${board.title}" type="text">
				</c:when>
				<c:otherwise>
				
				<div class="title">제목:  ${board.title}</div>
				<input name="title"  value="${board.title}" type="hidden" >
				</c:otherwise>
			</c:choose>
			
		</div>
		<br/>
		<div class="lay4"> <!-- 내용 -->
			<c:choose>
				<c:when test="${method =='POST'}">
				<textarea class="textarea" rows="15" name="contents"  ></textarea>
				</c:when>
				<c:when test="${method =='PUT'}">
				<textarea class="textarea" rows="15" name="contents"">${board.contents}</textarea>
				</c:when>
				<c:otherwise>
				<div class="textarea" rows="15">${board.contents}</div>
<!-- 				<textarea  name="contents" rows="20" disabled ></textarea>-->
				</c:otherwise>
			</c:choose>
			<!--  <textarea class="textarea" name="contents" rows="20" >test</textarea> -->
		</div>
		<br/>
		<div class="lay5">
		<a href="board" type="button" class="btn" id="btn2">목록으로</a>
		<c:choose>
		
				<c:when test="${method =='POST'}"> <!-- 글쓰기일때 -->
					<input type="submit" class="btn btn-success" value="등록">
				</c:when>
				<c:when test="${method =='PUT'}"> <!-- 수정일때 -->
					<input type="submit"  class="btn btn-primary" value="등록">
					<!--<a href="board?op=update2" type="button" class="btn btn-success">등록</a>-->
				</c:when>
				<c:otherwise> <!-- show일때 -->
					<div class="btn3">
					<a href="board?op=update&num=${board.num}" type="button" class="btn btn-primary" >수정</a>
					&nbsp;
					<a href="board?op=delete&num=${board.num}" type="button" class="btn btn-danger" >삭제</a>
					</div>
					
				</c:otherwise>
			</c:choose>
			
		
			&nbsp;&nbsp;
			
		</div>
		</div>
		</form>
	</div>
</body>
</html>
<script type='text/javascript'>

</script>
