<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
   <%
    ///������ �Ѿ�� �ѱ� �����Ͱ� ������ �ʵ��� �ϱ� ���� ���ڵ� ���� �����Ѵ�.
    request.setCharacterEncoding("euc-kr");
    String name=request.getParameter("name");
    String subject=request.getParameter("subject");
   //String filename1=request.getParameter("filename1");
   String filename2=request.getParameter("filename2");
   String filename1="Down/putty.exe";
  %>
  <html>
  <head>
  <title>���� ���ε� Ȯ�� �� �ٿ�ε�</title>
  </head>
  <body>
   �ø� ��� : <%=name %><br>
   ���� : <%=subject %><br>
   <!-- ������ �ٿ�ε� �� �� �ֵ��� ������ ��ũ�Ѵ�. -->
   ���ϸ�1 : <a href="<%=filename1 %>"><%=filename1 %></a><br>
   ���ϸ�2 : <a href="upload/<%=filename2 %>"><%=filename2 %></a><p>
  </body>
  </html>
[��ó] [jsp] ���� ���ε� �غ���|�ۼ��� ����