<%
/**
=====================================================================
Template Type : 
=====================================================================
Module : 네이버 API를 AJAX로 호출시 보안에러발생 따라서 proxy를 통해서 호출하도록 한다.
---------------------------------------------------------------------
Date			Developer		Description
---------------------------------------------------------------------
2005/05/04		홍성동	신규작성
=====================================================================
**/
%>
<%@page session="true" %>
<%@page import="java.util.*" %>
<%@page import="java.net.*" %>
<%@page import="java.io.*" %>
<%@page import="daishin.DASS.*" %>
<%@page import="daishin.DASS.common.*" %>
<%@page import="daishin.DASS.core.*" %>
<%@page import="daishin.DASS.session.*" %>
<%@page import="daishin.DASS.configure.*" %>
<%@page import="daishin.DASS.webrequest.*" %>
<%@page import="daishin.DASS.displayProxy.*" %>
<%@page import="daishin.DASS.tujajungbo.*" %>
<%@page import="daishin.DASS.communicator.DBAdapter.*" %>
<%@page import="daishin.DASS.trace.msg.TraceStream" %>
<%@page import="daishin.web.scheduler.*" %>
<%@page import="com.initech.iniplugin.*" %>
<%@page extends="daishin.DASS.webrequest.HttpJspDASSBase" %>
<%@page pageEncoding="euc-kr"%>
<%@page contentType="text/xml; charset=euc-kr" %>
<%@ taglib uri="/dsTagLib" prefix="dt" %>
<%	

	Hashtable paramHash = null;
	
	String paramName = null;
	String paramValue = null;
	String param = "";
	Enumeration paramNames =  request.getParameterNames();
	while(paramNames.hasMoreElements())
	{
		paramName = (String)paramNames.nextElement();
		paramValue = (String)getValue(paramName, request);
		
		param += paramName+"="+paramValue+"&";		
		
//		System.out.println(paramName +"="+paramValue);
	}
	
//	System.out.println("param = "+param);
	
	byte[] byteResult = this.callUrlConnect("http://220.73.158.70/search?"+param,paramHash,false,"UTF-8");
	
//	System.out.println("byteResult = "+byteResult+" length="+byteResult.length);
	
	String sXML = new String(byteResult);

//	System.out.println("RSS XML = "+sXML);
%>
<%=sXML%>

<%!
public	static byte[] callUrlConnect(String jspUrl,Hashtable hs,boolean bBinary,String lang) throws java.net.MalformedURLException
{
//	System.out.println("callUrlConnect URL = "+jspUrl);
	
	URL url = null;
	URLConnection connection = null;
			
	//binary
	OutputStream outStream = null;
	InputStream inStream = null;
	BufferedInputStream bufInStream = null;
	
	//text
	Writer writer = null;
	java.io.Reader reader = null;
	BufferedReader buf = null;
	
	String output = null;
	StringBuffer sb = new StringBuffer();
	
	byte[] bufferResult = null;
	
	try
	{
		EventLogManager.log("> Start URL 타임 : "+Common.getToday(),1);

	
		url = new URL(jspUrl);

		connection = url.openConnection();

		connection.setDoOutput(true); //post방식:true
		connection.setDoInput(true);	//데이타 첨부되는 경우
		//헤더 셋팅- 웹서버마다 헤더정보가 부족하면 405에러를 반환하기도 한다.
		connection.setRequestProperty("Accept","*/*");
		connection.setRequestProperty("Accept-Charset",lang);
//		connection.setRequestProperty("host",getHostIP());
		connection.setRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0; i-NavFourF; .NET CLR 1.1.4322)");
		

		outStream = connection.getOutputStream();

		
		//일반 text 파일 요청시	
		//서버로 데이타를 보낼때.. writer를 사용
		writer = new OutputStreamWriter(outStream);
		//DataOutputStream doutput = new DataOutputStream(outStream);

		// form data 를 넣어줄때
		if(hs != null)
		{
			String sKey = null;
			String param = "";
			for (Enumeration e = hs.keys() ; e.hasMoreElements() ;) 
			{
				sKey = (String)e.nextElement();
				param += sKey+"="+(String)hs.get(sKey)+"&";			 
			}

//			System.out.println("param = "+param);

			writer.write(param);				
			//doutput.writeBytes(param);
			
//			writer.write("&pnum1="+URLEncoder.encode(pnum1));
//			writer.write("&pnum2="+URLEncoder.encode(pnum2));
		}
		
		
		writer.flush();//내부버퍼에 있는 모든 데이타를 전송하도록 한다.
		writer.close();
		
//		doutput.flush();
//		doutput.close();
		
		outStream.close();
		
//		doutput = null;
		writer = null;
		outStream = null;

//		int response_code = connection.getResponseCode();
//		EventLogManager.log("HTTP response code : "+response_code,1);
		
		//보낸데이타를 받는다.
		inStream = connection.getInputStream();
		
		
		//text 읽을때..
		if(bBinary == false)
		{
			//스트링 버퍼로 변환하기 위해서는 아래처럼 한다.
			//euc-kr로 안하면 euc-kr로 인코딩 안된 문서는 utf-8로 읽어들이면서 sun.io.MalformedInputException을 발생 
			reader = new InputStreamReader(inStream,lang);
			buf = new BufferedReader(reader);
			
//			log("----- callUrl Result START---------------------\n");
			while((output = buf.readLine()) != null)
			{
//				log(output+"\n");
				if(!output.equals(""))
				{
					output.trim();
					sb.append(output+"\r\n");
				}
			}
			
			bufferResult = sb.toString().getBytes();
			
		}else
		{
			//binary로 읽을 때..
			bufInStream = new BufferedInputStream(inStream);
			
			int bufferOffset = 0;
			int nReadSize = 0;
			int bufferCapacity = bufInStream.available()+1;
			
			System.out.println("--------------------------------------");
			System.out.println("[binary] 초기버퍼사이즈 = "+bufferCapacity);
			
			bufferResult = new byte[bufferCapacity];
			
//			log("----- callUrl Result START---------------------\n");
//			while((i = bufInStream.read(bResult)) != -1);	
			
			//BufferedInputStream가 끝이 아닐때까지 돌면서 버퍼에 채우고 채워진 버퍼를 화면에 출력
			while((nReadSize = bufInStream.read(bufferResult, bufferOffset, 
					bufferCapacity-bufferOffset)) != -1)
			{ 
				bufferOffset += nReadSize;

				System.out.println("[binary] nReadSize = "+nReadSize+"/ bufferOffset = "+bufferOffset);
				
				if(bufferOffset >= bufferCapacity)
				{
					System.out.println("[binary] 버퍼확장 bufferSize = bufferCapacity "+bufferOffset+" = "+bufferCapacity);	
					//bufferResult를 확장한다.
					bufferCapacity *= 2;
					byte[] newBuffer = new byte[bufferCapacity];
					System.arraycopy(bufferResult,0,newBuffer,0,bufferOffset);
					
					//reference를 변경
					bufferResult = newBuffer;
				}
			}
			
			System.out.println("[binary] 최종 읽은 사이즈 = "+bufferResult.length);
			System.out.println("--------------------------------------");				
/*				
* 			binary file 생성 test
			File newFile = new File("C:/scheduler_home/_product/logo2.gif");
			
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(newFile));
			
			bout.write(bResult);	
			
			bout.flush();
			bout.close();	
*/							
		}


		
		EventLogManager.log("> End URL 타임 : "+Common.getToday(),1);
	
	}catch(java.net.ConnectException e)
	{
		sb = null;
		sb = new StringBuffer();
		sb.append(EventLogManager._CONNECT_ERROR_+" | "+e.toString());
		
		System.out.println("error : "+sb.toString());
		
	}catch(java.net.SocketException e)
	{
		sb = null;
		sb = new StringBuffer();
		sb.append(EventLogManager._SOCKET_ERROR_+" | "+e.toString());
		
		System.out.println("error : "+sb.toString());		
	}catch(java.io.IOException e)
	{
		EventLogManager.log("[SchedulerUtil::IOException callUrlConnect()] "+e.toString(),0);
		sb = null;
		sb = new StringBuffer();
		sb.append(EventLogManager._URLCALL_ERROR_+" | "+e.toString());
		
		System.out.println("error : "+sb.toString());		
	}finally
	{
		
		try
		{

//			System.out.println("통신 스트림 객체 해제중...");
			
			if(writer != null)
			{
				writer.close();
				writer = null;
			}
			
			if(outStream != null)
			{
				outStream.close();
				outStream = null;
			}
			
			if(inStream != null)
			{
				inStream.close();
				inStream = null;
			}
			
			if(reader != null)
			{
				reader.close();
				reader = null;
			}
			
			if(buf != null)
			{
				buf.close();
				buf = null;
			}
			
			if(bufInStream != null)
			{
				bufInStream.close();
				bufInStream = null;
			}					
			
		}catch(java.io.IOException e1)
		{
			e1.printStackTrace();
			
			System.out.println("error : "+e1.toString());			
		}
	}

	return bufferResult;
}

%>