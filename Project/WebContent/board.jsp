<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>고객센터</title>
  <link href="bootstrap/css/aa.css" rel="stylesheet">
  <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
  <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
 	<script src="bootstrap/js/bootstrap.js"></script>
<script>
    $(function(){
    	 $( "#accordion" ).accordion();
 });
</script>
</head>
<body>
<form id="hidden" action="/crystars_board/Page" method="get">
          <input id="status" type="hidden" name="status" value="update"/>
          <input id="page"type="hidden" name="page" value="1"/>
</form>

  <div class="container">
  <div class="row">
			<div class="span12 page-info">
				<div class="pull-left">
					Total <b>${result.numItems }</b> users 
				</div>
				<div class="pull-right">
					<b>${result.page }</b> page / total <b>${result.numPages }</b> pages
				</div>
 			</div>
 		</div>
 <table class="table table-striped">
              <thead>
                <tr>
                  <th>글번호</th>
                  <th>제목</th>
                  <th>작성자</th>
                  <th>조회수</th>
                </tr>
              </thead>
             <tbody>
				<c:forEach var="board" items="${result.list }">
				<tr>
					<td><a href="BoardServlet.do?number=${board.number}"><c:out value="${board.number}"/></a></td>
					<td><c:out value="${board.title}"/></td>
					<td><c:out value="${board.userid}"/></td>
					<td><c:out value="${board.count}"/></td>
				</tr>
				</c:forEach>
			</tbody>
 </table>
            
     <jsp:include page="page.jsp">
      <jsp:param name="currentPage" value="${result.page}"/>
      <jsp:param name="url" value="BoardServlet.do"/>
      <jsp:param name="startPage" value="${result.startPageNo}"/>
      <jsp:param name="endPage" value="${result.endPageNo}"/>
      <jsp:param name="numPages" value="${result.numPages}"/>
	   </jsp:include>
	
	 <a href="#myModal" data-toggle="modal">글쓰기</a>
	 
	 <form method="POST" action="BoardServlet.do" enctype="multipart/form-data">
	 <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-header">
      	<input class="hello" id="disabledInput" type="text" value="${loginID}" disabled/>
      	<input type="hidden" name="userid" value="${loginID}"/>
  			<input type="password" name=pwd class="hello" placeholder="Password">
    	</div>
      <div class="modal-body">
       	제목  <br/> 
       <input type="text" id="aaa" name="title" class="input-xxlarge" placeholder="제목을 입력하시기 바랍니다."><br/>
   			내용  <br/>
    	<textarea rows="10" id="aaa" cols="60" name="content" placeholder="내용을 입력하시기 바랍니다."></textarea>
      	파일 : <input type="file" name="upfile">
   		</div>
   			<div class="modal-footer"><input class ="btn btn-primary" type="submit" value="게시글 등록"></div>
 		</div>
 	</form>
</body>
</html>