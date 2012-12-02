package com.crystars.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.crystars.message.message;
import com.crystars.message.messageDAO;
import com.crystars.item.item;
import com.crystars.item.itemDAO;


/**
 * Servlet implementation class User
 */
@WebServlet("/garretServlet")
public class garretServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public garretServlet() {
        super();
    }
    private int getIntFromParameter(String str, int defaultValue) {
		int num;
		
		try {
			num = Integer.parseInt(str);
		} catch (Exception e) {
			num = defaultValue;
		}
		return num;
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = request.getParameter("op");
		
		String actionUrl = "";
		//int id = Integer.parseInt(request.getParameter("id"));
		int id= 1127;
		boolean ret;
		List<item> _item = new ArrayList<item>();
		List<message> _tmessage = new ArrayList<message>();
		List<message> _fmessage = new ArrayList<message>();
		
		
		try {
			if(op==null || op=="garret"){
				_item = itemDAO.findByNum(id);
				_tmessage = messageDAO.findTMSByNum(id);
				request.setAttribute("messagesize",_tmessage.size());
				request.setAttribute("message", _tmessage);
				request.setAttribute("itemsize",_item.size());
				request.setAttribute("item", _item);
				actionUrl = "garret.jsp";
			}
			
		}catch (SQLException | NamingException e) {
		
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
			System.out.println("1");
			actionUrl = "error.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}

/*
	private int isRegisterMode(HttpServletRequest request) {
		String method = request.getParameter("_method");
		if(method==null || method.equals("POST")){
			return 1;
		}else if(method.equals("PUT")){
			return 0;
		}else if(method.equals("delete")){
			return 2;
		}
		return 1;
	}
*/
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 System.out.println("55");
		 String actionUrl = null;
		/*boolean ret = true;
		
		String msg;
		item board = new item();
		request.setCharacterEncoding("utf-8");
		
		int num=0;
		try {
			if(isRegisterMode(request) == 1)
			{
				num = itemDAO.getCountNum();
			}else if(isRegisterMode(request)==0 || isRegisterMode(request)==2){
				num = Integer.parseInt(request.getParameter("num"));
				board = itemDAO.findByNum(num);
			}
		} catch (NamingException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int page = 0;
		
		page = getIntFromParameter(request.getParameter("page"), 1);
		
		
		Timestamp date = new Timestamp(new Date().getTime());
		
		
		String title = request.getParameter("title");
	
		String contents = request.getParameter("contents");
		//String date = request.getParameter("date");
		String name = request.getParameter("name");
		//String count = request.getParameter("count");
		String pwd = request.getParameter("pwd");
		List<String> errorMsgs = new ArrayList<String>();

		board.setNum(num+1);
		board.setCount(0);
		board.setDate(date);
		if(isRegisterMode(request)!=2){
		if (pwd == null || pwd.length() < 4) {
			errorMsgs.add("비밀번호는 4자 이상 입력해주세요.");
			ret = false;
		}
		if (title == null || title.trim().length() == 0) {
			errorMsgs.add("제목을 반드시 입력해주세요.");
			ret = false;
		}else{
			board.setTitle(title);
		}
		
		if (contents == null || contents.trim().length() == 0) {
			errorMsgs.add("내용을 반드시 입력해주세요.");
			ret = false;
		}else{
			board.setContents(contents);
		}
		
		if (name == null || name.trim().length() == 0) {
			errorMsgs.add("이름을 반드시 입력해주세요.");
			ret = false;
		}else{
			board.setName(name);
		}
		}
		

		
		try {
			if(ret== true){
				if (isRegisterMode(request)==1) {
					board.setPwd(pwd);
					ret = itemDAO.create(board);
					msg = "<b>" + name + "</b>님의 글이 등록되었습니다.";
					request.setAttribute("msg", msg);
					actionUrl = "success.jsp";
				} else if(isRegisterMode(request)==0) {					
					if(!(pwd.equals(board.getPwd()))){
						errorMsgs.add("비밀번호가 일치하지않습니다.");
						ret = false;
						actionUrl = "error.jsp";
					}else{
						board.setNum(num);
						ret = itemDAO.update(board);
						msg = "<b>" + name + "</b>님의 글이 수정되었습니다.";
						request.setAttribute("msg", msg);
						actionUrl = "success.jsp";
					}
				}else{
					if(!(pwd.equals(board.getPwd()))){
						errorMsgs.add("비밀번호가 일치하지않습니다.");
						ret = false;
						actionUrl = "error.jsp";
					}else{
						num = Integer.parseInt(request.getParameter("num"));
						itemDAO.remove(num);
						msg = "<b>" + name + "</b>님의 글이 삭제되었습니다.";
						request.setAttribute("msg", msg);
						actionUrl = "success.jsp";
					}
					
				}
			}else{
				errorMsgs.add("변경에 실패했습니다.");
				actionUrl = "error.jsp";
			}		
		} catch (SQLException | NamingException e) {
			errorMsgs.add(e.getMessage());
			
			actionUrl = "error.jsp";
		}
		
		request.setAttribute("errorMsgs", errorMsgs);
		request.setAttribute("page", page);*/
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}

}
