package crystars;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import json.JSONObject;
/**
 * Servlet implementation class User
 */
@WebServlet("/User")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1123L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public User() {
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
				String _id = request.getParameter("id");
				String _name = request.getParameter("name");
				String _email = request.getParameter("email");
				String _home = request.getParameter("link");
				String _portrait = request.getParameter("portrait");
				String _token = request.getParameter("token");
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
						  stmt = con.prepareStatement("SELECT * FROM Member WHERE Facebook_ID=?");
						  stmt.setString(1, _id);
						  rs = stmt.executeQuery();
						  
						  udo=new UserDataObj();
						  udo.initial( _id, _name, _email, _home,_portrait,_token);
							 
						 if(rs.next()==true) 
						 {
							 
							 udo.setId( rs.getString(2) );
							 udo.setName( rs.getString(3) );
							 udo.setGrade(  rs.getInt(4) );
							 udo.setEmail(rs.getString(5) );
							 udo.setHome(rs.getString(6) );
							 
						  }else{
							 stmt = con.prepareStatement("INSERT INTO Member (Facebook_ID,Nickname,Grade,email,home) VALUES (?,?,?,?,?)");
							
							 stmt.setString(1, udo.getId());
							 stmt.setString(2, udo.getName());
							 stmt.setInt(3, udo.getGrade());
							 stmt.setString(4, udo.getEmail() );
							 stmt.setString(5, udo.getHome() );
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
				  
				  
				  HttpSession session = request.getSession();
				  session.setAttribute("viwer",udo);
				  
			        if(udo!=null)
			        {
			        out.print(udo.getJsonString());
			        }
			      //*/
					 	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
