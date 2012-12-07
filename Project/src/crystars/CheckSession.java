package crystars;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import json.JSONObject;

/**
 * Servlet implementation class CheckSession
 */
@WebServlet("/CheckSession")
public class CheckSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckSession() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setCharacterEncoding("utf8");
        response.setContentType("application/json");
        request.setCharacterEncoding("utf-8");
		
		String _id = request.getParameter("id");
		String _token = request.getParameter("token");
		HttpSession session= request.getSession();
		Object obj = session.getAttribute("viwer");
		UserDataObj udo =null;
		JSONObject jsonobj=null;
		try{
		jsonobj = new JSONObject();
		jsonobj.put("id", "empty");
		jsonobj.put("token", _token);
		
		}catch(Exception e){
			
		}
		if(obj==null){
			out.print(jsonobj.toString());
			out.close();
		}else{
			if(obj instanceof UserDataObj )
			{
				udo = (UserDataObj)obj;
				if(udo!=null && udo.getId().equals(_id))
				{
						udo.setToken(_token);
					 out.print(udo.getJsonString());
					 out.close();
					 return;
				}else{
					//out.print(udo.getId()+_id+ jsonobj.toString());
					out.print(jsonobj.toString());
					out.close();
				}
				
			}else{
				out.print(jsonobj.toString());
				out.close();
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
