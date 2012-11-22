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
    private final int maxCount = 12;
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
				  JSONObject resultJson=new JSONObject();
				 
				  int total=-1;
				  int start;
				  
				  
				  try{
					  con = DriverManager.getConnection("jdbc:mysql://www.crystars.com:3306/mydb","root","dudah2");
					  
					  try{
						  status = needTotal;
						  if(needTotal!=null && needTotal.equals("total"))
						  {
							  stmt = con.prepareStatement("SELECT Count(Id) FROM Item");
							  rs = stmt.executeQuery();
							  
							  status="ready:"+needTotal;
							  if(rs.next())
							  {
								  status="inital";
								  total=rs.getInt(1);
							  }
						  }
						  
						  //stmt = con.createStatement();
						  stmt = con.prepareStatement("SELECT * FROM Item order by ? desc LIMIT ? OFFSET ?");
						  stmt.setString(1, itemType);
						  stmt.setInt(2, maxCount);//retrevCount
						  stmt.setInt(3, (page-1)*maxCount);//startCount start 0
						  rs = stmt.executeQuery();

						  
						  
						  resultJson.put("status", status);
						  resultJson.put("page", page);
						  resultJson.put("total", total);
						  
						  int i=0;
						 while(rs.next()==true) 
						 {
							 initem = new JSONObject(); 
							 initem.put("id", rs.getInt(1));
							 initem.put("owner", rs.getString(2));
							 initem.put("image", rs.getString(3));
							 initem.put("title", rs.getString(4));
							 initem.put("desc", rs.getString(5));
							 initem.put("price", rs.getInt(6));
							 initem.put("publisher", rs.getString(7));
							 initem.put("date", rs.getInt(8));
							 initem.put("status", rs.getInt(9));
							 initem.put("grade", rs.getInt(10));
							 initem.put("booktitle", rs.getString(11));
							 
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
			        	
			        	out.print(resultJson.toString());
			        }
			        out.close();
			      //*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
