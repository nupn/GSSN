<%@ page contentType="text/html;charset=euc-kr" %><%@ page import="com.oreilly.servlet.MultipartRequest,
com.oreilly.servlet.multipart.DefaultFileRenamePolicy,java.util.*" %>

<%
 String savePath="C:/webp/Project/WebContent/Down"; //저장할 디렉토리 (절대경로)
 int sizeLimit = 5 * 1024 * 1024 ; // 5메가까지 제한 넘어서면 예외발생
 try{
 MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
  Enumeration formNames=multi.getFileNames();  // 폼의 이름 반환
 String formName = (String)formNames.nextElement(); // 자료가 많을 경우엔 while 문을 사용
 String fileName = multi.getFilesystemName(formName); // 파일의 이름 얻기
 
 if(fileName == null) {   // 파일이 업로드 되지 않았을때
  out.print("파일 업로드 되지 않았음");
 } else {  // 파일이 업로드 되었을때
  fileName=new String(fileName.getBytes("8859_1"),"euc-kr"); // 한글인코딩 - 브라우져에 출력
  out.print("User Name : " + multi.getParameter("userName") + "<BR>");
  out.print("Form Name : " + formName + "<BR>");
  out.print("File Name  : " + fileName);
 } // end if
 } catch(Exception e) {
 		//out.print("예외 상황 발생..! ");
 		e.printStackTrace();
 } 
%>    