package com.crystars;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class RegistItem
 */
@WebServlet("/RegistItem")
public class RegistItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");

		String _callback = request.getParameter("callback");
				//
		
				int _GSales_MemberNum = Integer.parseInt( request.getParameter("pid"));
				String _Image = request.getParameter("image");
				int _Price = Integer.parseInt(request.getParameter("price"));
				//
				//
				String _Book_Name = request.getParameter("booktitle");
				String _Content	= request.getParameter("desc");
				String _Publisher = request.getParameter("publisher");
				//
				int _Quality= Integer.parseInt(request.getParameter("quality"));
				//
				String _Isbn= request.getParameter("isbn");
				String _ImgDetail=request.getParameter("imgDetail");
				String _Title = request.getParameter("title");
				String _Owner =request.getParameter("owner");
				PrintWriter out = response.getWriter();

				/*
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html");
				  out.print("<html>");
				  out.print("<body>");
				 //*/
				  Connection con = null;
				  PreparedStatement stmt =null;
				  ResultSet rs =null;
				  try{
					  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
					  
					  try{
						  	stmt = con.prepareStatement("INSERT INTO Goods (GSales_MemberNum,Image,Price,Book_Name,Content,Publisher,Quality,Isbn,ImgDetail,Title,Owner) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
							
						  	 stmt.setInt(1, _GSales_MemberNum);
							 stmt.setString(2, _Image);
							 stmt.setInt(3, _Price);
							 stmt.setString(4, _Book_Name );
							 stmt.setString(5, _Content );
							 stmt.setString(6, _Publisher);
							 stmt.setInt(7, _Quality);
							 stmt.setString(8, _Isbn);
							 stmt.setString(9, _ImgDetail );
							 stmt.setString(10, _Title );
							 stmt.setString(11, _Owner);
							 stmt.executeUpdate();
						  
						  
						 if(rs!=null)
						  rs.close();
						 
						  stmt.close();
						  con.close();
						  
					  }catch(Exception e){
						  e.printStackTrace(out);
					  }
				  }catch(Exception e){
					  e.printStackTrace(out);
				  }finally{
					  if(rs!=null)try{rs.close();}catch(SQLException e){}
					  if(stmt!=null)try{stmt.close();}catch(SQLException e){}
					  if(con!=null)try{con.close();}catch(SQLException e){}
				  }
				  
//				  out.print("</body>");
//				  out.print("</html>");
//				  out.close();
				  
//				  request.setAttribute("usr", udo);
//				  RequestDispatcher rd = request.getRequestDispatcher("userinitComplete.jsp");
//		          rd.forward(request, response);
				  //*
				  
				  JSONObject jo =new JSONObject();
				  try{
				  jo.put("status", "funcking");
				  }catch(JSONException e)
				  {
					  
				  }
			        out.print(_callback+"("+jo.toString()+")");
			        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
