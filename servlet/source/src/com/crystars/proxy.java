package com.crystars;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;


/**
 * Servlet implementation class proxy
 */
@WebServlet("/proxy")
public class proxy extends HttpServlet {
	private static final long serialVersionUID = 3L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public proxy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 request.setCharacterEncoding("utf-8");

		 String url = "http://openapi.naver.com/search?key=82ad9d814e2621b69aaab0cfbe455c19&target=book&query=%EC%82%BC%EA%B5%AD%EC%A7%80";
		 HttpClient client = new HttpClient();
		 GetMethod method = new GetMethod(url);
		 PrintWriter out = response.getWriter();
		 try{
		  int statusCode = client.executeMethod(method);
		  //out.clearBuffer();
		  response.reset();
		  response.setStatus(statusCode);
		  if(statusCode == HttpStatus.SC_OK){
		   String result = method.getResponseBodyAsString();
		   response.setContentType("text/xml; charset=utf-8");
		   out.println(result);
		  }
		 }finally{
		  if(method != null) method.releaseConnection();
		 }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
