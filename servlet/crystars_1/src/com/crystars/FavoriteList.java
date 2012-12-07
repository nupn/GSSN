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
 * Servlet implementation class FavoriteList
 */
@WebServlet("/FavoriteList")
public class FavoriteList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FavoriteList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf8");

				String _callback = request.getParameter("callback");
				int pid = Integer.parseInt( request.getParameter("pid") );
				int page = Integer.parseInt( request.getParameter("page") );
				PrintWriter out = response.getWriter();

				  String status="";
				  Connection con = null;
				  PreparedStatement stmt =null;
				  ResultSet rs =null;
				  JSONArray valv=new JSONArray();
				  JSONObject initem=null;
				  JSONObject resultJson=new JSONObject();
				 
				  int total=-1;
				  int start;
				  
				  
				  try{
					  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
					  
					  try{
						  
						  stmt = con.prepareStatement("SELECT Count(Goods_Num) FROM  Goods,Member,Favorite Where Goods.Goods_Num = Favorite.Favorite_ItemNum AND Member.Member_Num=Favorite.Member_Num AND Favorite.Member_Num =?");
						  stmt.setInt(1, pid);
						  rs = stmt.executeQuery();
						  
						  if(rs.next())
						  {
							  total=rs.getInt(1);
						  }
						  
						  stmt = con.prepareStatement("SELECT * FROM  Goods,Member,Favorite Where Goods.Goods_Num = Favorite.Favorite_ItemNum AND Member.Member_Num=Favorite.Member_Num AND Favorite.Member_Num =? LIMIT ? OFFSET ?");
						  stmt.setInt(1, pid);
						  stmt.setInt(2, Item.maxCount);//retrevCount
						  stmt.setInt(3, (page-1)*Item.maxCount);//startCount start 0
						  rs = stmt.executeQuery();
						  
						  resultJson.put("page", page);
						  resultJson.put("total", total);
						  JSONObject inuser=null;
						  int i=0;
						 while(rs.next()==true) 
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
							 
							 valv.put(i,initem);
							 i++;
						  }
						 
						 resultJson.put("result", valv);
//						try{
//						 initem = valv.toJSONObject(valv);
//						}catch(JSONException e){
//							
//						}
						
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
