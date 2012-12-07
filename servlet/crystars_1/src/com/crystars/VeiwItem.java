package com.crystars;

import java.io.IOException;
import java.io.PrintWriter;
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

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class VeiwItem
 */
@WebServlet("/VeiwItem")
public class VeiwItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VeiwItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf8");

		String _callback = request.getParameter("callback");
		int id= Integer.parseInt( request.getParameter("id") );
		PrintWriter out = response.getWriter();

		  Connection con = null;
		  PreparedStatement stmt =null;
		  ResultSet rs =null;
		  JSONObject resultJson=new JSONObject();
		 
		  
		  
		  try{
			  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
			  
			  try{
				  
				  stmt = con.prepareStatement("SELECT * FROM  Goods,Member Where Member.Member_Num=Goods.GSales_MemberNum AND Goods.Goods_Num=?");
				  stmt.setInt(1, id);//retrevCount
				  rs = stmt.executeQuery();
				  JSONObject initem = new JSONObject(); 
				  JSONObject inuser=null;
				 if(rs.next()==true) 
				 {
					 initem = new JSONObject(); 
					 initem.put("id", rs.getInt(1));
					 initem.put("userpid", rs.getInt(2));
					 initem.put("image", rs.getString(3));
					 initem.put("price", rs.getInt(4));
					 initem.put("date", rs.getString(5));
					 initem.put("category", rs.getInt(6));
					 initem.put("booktitle", Tool.decode(rs.getString(7)));
					 initem.put("content", Tool.decode(rs.getString(8)));
					 initem.put("publisher", Tool.decode(rs.getString(9)));
					 initem.put("quantity", rs.getInt(10));
					 initem.put("quality", rs.getInt(11));
					 initem.put("Status", rs.getInt(12));
					 initem.put("isbn", rs.getString(13));
					 initem.put("imgdetail", rs.getString(14));
					 initem.put("title", Tool.decode(rs.getString(15)));
					 initem.put("userid", rs.getString(16));
					 
					 inuser = new JSONObject();
					 inuser.put("pid", rs.getInt(17));
					 inuser.put("id", rs.getString(18));
					 inuser.put("name", Tool.decode(rs.getString(19)));
					 inuser.put("grade", rs.getInt(20));
					 inuser.put("email", rs.getString(21));
					 inuser.put("home", rs.getString(22));
					 inuser.put("portrait",  "http://graph.facebook.com/"+rs.getString(18)+"/picture");
					 
					 initem.put("user", inuser);
					 
					 resultJson.put("result", initem); 
				  }
				 
				 
				 
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
		  
		  
	        if(resultJson!=null)
	        {
	        	
	        	out.print(_callback+"("+resultJson.toString()+")");
	        }
	        out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
