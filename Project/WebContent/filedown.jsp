<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
   <%
    ///폼에서 넘어온 한글 데이터가 깨지지 않도록 하기 위해 인코딩 값을 지정한다.
    request.setCharacterEncoding("euc-kr");
    String name=request.getParameter("name");
    String subject=request.getParameter("subject");
   //String filename1=request.getParameter("filename1");
   String filename2=request.getParameter("filename2");
   String filename1="Down/putty.exe";
  %>
  <html>
  <head>
  <title>파일 업로드 확인 및 다운로드</title>
  </head>
  <body>
   올린 사람 : <%=name %><br>
   제목 : <%=subject %><br>
   <!-- 파일을 다운로드 할 수 있도록 파일을 링크한다. -->
   파일명1 : <a href="<%=filename1 %>"><%=filename1 %></a><br>
   파일명2 : <a href="upload/<%=filename2 %>"><%=filename2 %></a><p>
  </body>
  </html>
[출처] [jsp] 파일 업로드 해보기|작성자 빛나