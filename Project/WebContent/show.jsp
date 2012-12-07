<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"  import="java.sql.*" 
    import="org.apache.commons.lang3.StringUtils" import="com.oreilly.servlet.MultipartRequest" 
    import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>QNA</title>
  <link href="bootstrap/css/aa.css" rel="stylesheet">
  <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css" />
  <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
  <script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
  <script src="bootstrap/js/bootstrap.js"></script>
</head>
<body>

 <div class="container">
    <div>
    <form action="up.jsp" enctype="multipart/form-data" method="post">	
      <table class="table table-condensed">
      	<tr>
      		<th>글 번호: <c:out value="${board.number}"/></th>
      		<th>글 제목: <c:out value="${board.title}"/></th>
      		<th>ID: <c:out value="${board.userid}"/></th>
      	</tr>
      	<tr>
      		<td colspan="3"><div class="well"><c:out value="${board.content}"/></div></td>
      	</tr>
			<tr>	
      <td colspan="3">첨부 파일 :<a href="Down/${board.file}"/> <c:out value="${board.file}"/></a></td>
    	</tr>
    	
      	<c:forEach var="boardinfo" items="${result.list }">
				<tr>
					<td colspan="2"><span id="asd"> RE: <c:out value="${boardinfo.b_content}"/></span><button id="asdf" class="close" href="#myModal2" data-toggle="modal">&times;</button></td>
					<td> ID : <c:out value="${boardinfo.b_id}"/> </td>
				</tr>
				</c:forEach>
      </table>
 </form>
    </div>
          
	  <div class="form-actions" >
	  <form method="post" action="BoardServlet.do">
	    <a href="BoardServlet.do" class="btn">목록으로</a>
 	    <a id="bb" class="btn btn-primary">게시글 댓글</a>
      <a id="cc" href="#myModal1" data-toggle="modal" class="btn btn-danger" >게시글 삭제</a>
      </form>
    </div>
 
   
 		
<form method="get" action="BoardServlet.do">
    <div class="form-actions" id="aaaa" style="display: none">
      <div class="span11">
    <span class="hi">PWD: <input type="password" name="b_pwd"></span>
    <span class="hi">ID: <input type="text" name="b_id"></span>
    <span class="hi"><input class ="nice2" type="text" name="b_content" placeholder="내용을 입력하시기 바랍니다."></span>
		<input type="hidden" name="groupnumber" value="${board.number}"/>
		<input type="hidden" name="number" value="${board.number}"/>
		<input type="hidden" name="op" value="create"/>
    <span class="hi"><input id="fff" class ="btn btn-primary" type="submit" value="댓글등록"></span>
      </div>
    </div>
 </form>
 
 </div>
 	<div id="myModal1" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
     <div class="qqqqq"> 
      <form method="GET" action="BoardServlet.do">
      <span class="asd">PWD: <input type="password" name="userpwd" ></span>
    <input class ="btn btn-danger" id="dfg" type="submit" value="삭제">
   	<input type="hidden" name="userid" value="JIC">
		<input type="hidden" name="groupnumber" value="${board.number}"/>
		<input type="hidden" name="op" value="delete"/>
 			</form>
 </div>
 </div>
 

 </body>
	<script>
	  $(function(){
	    $("a[data-action='delete']").click(function() {
	      if (confirm("정말로 삭제하시겠습니까?")) {
	        location = 'board?op=delete&number=' + $(this).attr('data-id');
	      }
	      return false;
	    });
	  });
	  $( "#bb" ).click(function() {
		    $( "#aaaa" ).toggle( "fold", 1000 );
		});
	  $( "#cc" ).click(function() {
		    $( "#qwe" ).toggle( "fold", 1000 );
		});
	</script> 
 

</html>