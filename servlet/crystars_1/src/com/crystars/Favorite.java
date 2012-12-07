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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Favorite
 */
@WebServlet("/Favorite")
public class Favorite extends HttpServlet {
	private static final long serialVersionUID = 12998L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Favorite() {
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
				String action = request.getParameter("Status");
				int _Member_Num = Integer.parseInt( request.getParameter("pid"));
				int _Favorite_ItemNum = Integer.parseInt(request.getParameter("itemid"));
				PrintWriter out = response.getWriter();
				ResultSet rs =null;
				String status="";
				int itemCount;
				
				  Connection con = null;
				  PreparedStatement stmt =null;
				  try{
					  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
					  
					  try{
						  if(action.equals("insert"))
						  {
							  stmt = con.prepareStatement("SELECT Count(Favorite.Member_Num) FROM Favorite where Favorite.Member_Num=? AND 	Favorite.Favorite_ItemNum =?");
							  stmt.setInt(1, _Member_Num);
							  stmt.setInt(2, _Favorite_ItemNum);
							  rs = stmt.executeQuery();
							  
							  status="new";
							  if(rs.next())
							  {
								  itemCount=rs.getInt(1);
								  if(itemCount>0){
									  status="hasit";
								  }
							  }

							  stmt = con.prepareStatement("INSERT INTO Favorite (Member_Num,Favorite_ItemNum) VALUES (?,?)");
							  stmt.setInt(1, _Member_Num);
							  stmt.setInt(2, _Favorite_ItemNum);
							  stmt.executeUpdate();
						  }else if(action.equals("delete"))
						  {
							  stmt = con.prepareStatement("DELETE FROM Favorite WHERE Favorite.Member_Num=? AND Favorite.Favorite_ItemNum =?");
							  stmt.setInt(1, _Member_Num);
							  stmt.setInt(2, _Favorite_ItemNum);
							  stmt.executeUpdate();
							  
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
				  
//				  out.print("</body>");
//				  out.print("</html>");
//				  out.close();
				  
//				  request.setAttribute("usr", udo);
//				  RequestDispatcher rd = request.getRequestDispatcher("userinitComplete.jsp");
//		          rd.forward(request, response);
				  //*
				  
				  JSONObject jo =new JSONObject();
				  try{
				  jo.put("status", status);
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
		
	}

}
