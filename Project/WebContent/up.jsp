<%@ page contentType="text/html;charset=euc-kr" %><%@ page import="com.oreilly.servlet.MultipartRequest,
com.oreilly.servlet.multipart.DefaultFileRenamePolicy,java.util.*" %>

<%
 String savePath="C:/webp/Project/WebContent/Down"; //������ ���丮 (������)
 int sizeLimit = 5 * 1024 * 1024 ; // 5�ް����� ���� �Ѿ�� ���ܹ߻�
 try{
 MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
  Enumeration formNames=multi.getFileNames();  // ���� �̸� ��ȯ
 String formName = (String)formNames.nextElement(); // �ڷᰡ ���� ��쿣 while ���� ���
 String fileName = multi.getFilesystemName(formName); // ������ �̸� ���
 
 if(fileName == null) {   // ������ ���ε� ���� �ʾ�����
  out.print("���� ���ε� ���� �ʾ���");
 } else {  // ������ ���ε� �Ǿ�����
  fileName=new String(fileName.getBytes("8859_1"),"euc-kr"); // �ѱ����ڵ� - �������� ���
  out.print("User Name : " + multi.getParameter("userName") + "<BR>");
  out.print("Form Name : " + formName + "<BR>");
  out.print("File Name  : " + fileName);
 } // end if
 } catch(Exception e) {
 		//out.print("���� ��Ȳ �߻�..! ");
 		e.printStackTrace();
 } 
%>    