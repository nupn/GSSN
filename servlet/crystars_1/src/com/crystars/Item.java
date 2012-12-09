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
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Item
 */
@WebServlet("/Item")
public class Item extends HttpServlet {
	private static final long serialVersionUID = 1L;
    static public final int maxCount = 12;
    /*
    private final String BY_PRICE = "Price";
    private final String BY_GRADE = "Grade";
    private final String BY_TIME = "Time";
    */
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Item() {
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
				String itemType = request.getParameter("itemType");
				String needTotal = request.getParameter("needTotal");
				int page = Integer.parseInt( request.getParameter("page") );
				PrintWriter out = response.getWriter();

				/*
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html");
				  out.print("<html>");
				  out.print("<body>");
				 //*/
				  String status="";
				  Connection con = null;
				  PreparedStatement stmt =null;
				  ResultSet rs =null;
				  JSONArray valv=new JSONArray();
				  JSONObject initem=null;
				  JSONObject inuser=null;
				  JSONObject resultJson=new JSONObject();
				 
				  int total=-1;
				  int start;
				  
				  
				  try{
					  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
					  
					  try{
						  status = needTotal;
						  if(needTotal!=null && needTotal.equals("total"))
						  {
							  stmt = con.prepareStatement("SELECT Count(Goods_Num) FROM Goods");
							  rs = stmt.executeQuery();
							  
							  status="ready:"+needTotal;
							  if(rs.next())
							  {
								  status="inital";
								  total=rs.getInt(1);
							  }
						  }
						  
						  //SELECT Goods.GSales_MemberNum, Member.Member_Num FROM  Goods ,Member order by Goods.Regist_Date desc LIMIT 5 OFFSET 10
						  //stmt = con.createStatement();
						  stmt = con.prepareStatement("SELECT * FROM  Goods ,Member Where Goods.GSales_MemberNum = Member.Member_Num order by Regist_Date DESC LIMIT ? OFFSET ?");
						  //stmt.setString(1, itemType);
						  stmt.setInt(1, maxCount);//retrevCount
						  stmt.setInt(2, (page-1)*maxCount);//startCount start 0
						  rs = stmt.executeQuery();

						  
						  
						  resultJson.put("status", status);
						  resultJson.put("page", page);
						  resultJson.put("total", total);
						  
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
			      //*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf8");
		
		String _callback = request.getParameter("callback");
		
				//
				int _GSales_MemberNum = Integer.parseInt(request.getParameter("owner"));
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
				int _Isbn=Integer.parseInt(request.getParameter("isbn"));
				String _ImgDetail=request.getParameter("imgDetail");
				String _Title = request.getParameter("title");
				PrintWriter out = response.getWriter();

				/*
				PrintWriter out = response.getWriter();
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html");
				  out.print("<html>");
				  out.print("<body>");
				 //*/
				  String er="";
				  Connection con = null;
				  PreparedStatement stmt =null;
				  ResultSet rs =null;
				  UserDataObj udo=null;
				  try{
					  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
					  
					  try{
						  //stmt = con.createStatement();
						  	stmt = con.prepareStatement("INSERT INTO Goods (GSales_MemberNum,Image,Price,Book_Name,Content,Publisher,Quality,Isbn,ImgDetail,Title) VALUES (?,?,?,?,?,?,?,?,?,?)");
							
							 stmt.setInt(1, _GSales_MemberNum);
							 stmt.setString(2, _Image);
							 stmt.setInt(3, _Price);
							 stmt.setString(4, _Book_Name );
							 stmt.setString(5, _Content );
							 stmt.setString(1, _Publisher);
							 stmt.setInt(2, _Quality);
							 stmt.setInt(3, _Isbn);
							 stmt.setString(4, _ImgDetail );
							 stmt.setString(5, _Title );
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
				  
			        out.print(_callback+"("+"{'status':'success'}"+")");
			        out.close();
			      //*/
	}

}
