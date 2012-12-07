package crystars;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import json.JSONObject;
import org.omg.CORBA.portable.ApplicationException;

/**
 * Servlet implementation class MVCtest
 */
@WebServlet("/MVCtest")
public class MVCtest extends HttpServlet {
	private static final long serialVersionUID = 2L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MVCtest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 try{
			  request.setCharacterEncoding("utf8");
		        //response.setCharacterEncoding("utf8");
		        response.setContentType("application/json");
		        PrintWriter out = response.getWriter();
		       // JSONObject jsonObj = (JSONObject) JSONValue.parse(request.getParameter("para"));
		       // System.out.println(jsonObj.get("message"));
		        JSONObject obj = new JSONObject();
		        obj.put("message", "hello from server");
		        out.print(obj);
	        
	        
		  }catch(Exception e){
			  
		  }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//String str = request.getParameter("name");
		//*
		PrintWriter out = response.getWriter();
		
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		  
		  out.print("<html>");
		  out.print("<body>");
		 //*/
		  
		  Connection con;
		  Statement stmt;
		  ResultSet rs;
		  /*
		  try{
			  Class.forName("com.mysql.jdbc.Driver").newInstance();
		  }catch(Exception e){
			  out.print("fuck1");
		  }*/
		  
		  try{
//			  request.setCharacterEncoding("utf8");
		        //response.setCharacterEncoding("utf8");
//		        response.setContentType("application/json");
//		        PrintWriter out = response.getWriter();
		       // JSONObject jsonObj = (JSONObject) JSONValue.parse(request.getParameter("para"));
		       // System.out.println(jsonObj.get("message"));
//		        JSONObject obj = new JSONObject();
//		        obj.put("message", "hello from server");
//		        out.print(obj);
	        
	        
		  }catch(Exception e){
			  
		  }
		  try{
			  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
			  
			  try{
				  stmt = con.createStatement();
				  rs=stmt.executeQuery("SELECT * FROM Member");
				  
				  
				  while(rs.next())
				  {
					  out.print(rs.getInt(1));
					  out.print(rs.getString(2));
				  }
				  
				  rs.close();
				  
				  stmt.close();
				  con.close();
				  
			  }catch(Exception e){
				  //out.print("fuck23");
			  }
		  }catch(Exception e){
		  	//e.printStackTrace(out);
		  }
		  
		 

		  out.print("</body>");
		  out.print("</html>");
	}

}
