<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>메시지</title>
	<link href="css/message.css" rel="stylesheet">
	<link href="css/bootstrap.css" rel="stylesheet">
 	<script src='js/jquery-1.8.2.js'></script>
 	<script src='js/bootstrap-tab.js'></script>
</head>

<body>
<form class="form-horizontal" action="messageServlet.do" method="POST">
<input type="hidden" name="_method" value="msg"/>
	<br/>
	<div id="laytitle">
		<c:if test="${op == 1 || op == 2 }">
		<h3>메시지 보내기</h3>
		</c:if>
		<c:if test="${op==0}">
		<h3>메시지 읽기</h3>
		</c:if>
	</div>
	<br/>
	
	<div id="lay1">		
		<c:if test="${op == 1 || op ==2 }">
			<div id="lay1-1">
				<div id="lay1-1-1">
					받는분 회원번호
				</div>
				<div id="lay1-1-2">
					<c:if test="${op == 1 }"> <!-- 일반전송이라면, 받는사람 번호가 없다면 -->
						<input id="inputidset" type="text" name="toid">
						<input id="inputidset" type="hidden" name="fromid" value="${fromid}">
					</c:if>
					<c:if test="${op == 2 }"> <!-- 답장이라면, 받는사람 번호가 있다면-->
						<c:out value="${fromid}"/>
					</c:if>
				</div>
			</div>
		</c:if>
		
		<c:if test="${op== 0 }">
			<div id="lay1-1">
				<div id="lay1-1-1">
					보내신분 회원번호
				</div>
				<div id="lay1-1-2">
					<c:out value="${msg.from_memnum}"/>
				</div>	
				<br/><br/>
				<div id="lay1-1-1">
					보내신분 닉네임
				</div>
				<div id="lay1-1-2">
					<c:out value="${msg.myNickname}"/>
				</div>	
			</div>	
		</c:if>
		
		<c:if test="${op==0 }">
			<div id="lay1-2">
				<div id="lay1-1-1">
					보낸시간
				</div>
				<div id="lay1-1-2">
					<c:out value="${msg.to_date}"/>
				</div>
				
			</div>
			<br/><br/>
		</c:if>		
	</div>
	<div id="lay2">
		<div id="lay2-1">
			제목
		</div>
		<div id="lay2-2">
			<c:choose>
				<c:when test="${op == 0 }">
					<c:out value="${msg.title}"/>
				</c:when>
				<c:otherwise>
					<input id="inputtitleset" type="text" name="title" id="title">
				</c:otherwise>
			</c:choose>
		</div>
	</div>
	<br/>
	
		<c:choose>
			
			<c:when test="${op == 0 }">
			<div id="lay3">
				<c:out value="${msg.content}"/>
			</div>
			</c:when>
			
			<c:otherwise>
			<div id="lay3">
				<textarea name="contents" id="contents">
				</textarea>
			</div>
			</c:otherwise>
		</c:choose>
		<br/>
	
	<div id="lay4">
		<c:if test="${op != 0 }">
			<input id="submitbtn" class="btn btn-primary" type="submit" value="메시지보내기">
		</c:if>
		<c:if test="${op ==0 }">
			<a href="messageServlet.do?sendtype=2&fromid=${msg.to_memnum}&toid=${msg.from_memnum}" ><input id="submitbtn" class="btn btn-primary" type="button" value="답장보내기"></a>
		</c:if>
	</div>
	</form>
</body>
</html>