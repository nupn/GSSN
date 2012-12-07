package Servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Bean.Board;
import Bean.BoardInfo;
import Bean.PageResult;
import DAO.BoardDAO;
import DAO.BoardInfoDAO;
import crystars.UserDataObj;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.util.*;

@WebServlet("/board")

public class BoardServlet extends HttpServlet{

	private static final long serialVersionUID = -1134970795612430112L;
	 
	public BoardServlet() {
        super();
    }
	
	List<String> errorMsgs = new ArrayList<String>();
	
	
	private int getIntFromParameter(String str, int defaultValue) {
		int id;
		
		try {
			id = Integer.parseInt(str);
		} catch (Exception e) {
			id = defaultValue;
		}
		return id;
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("This is get");
		
		int number = getIntFromParameter(request.getParameter("number"), -1);
		
		String op = request.getParameter("op");
		
		if (op == null && number > 0) {
			op = "show";
		}
		
		String actionUrl = "";
		boolean aa=true;
		BoardInfo boardinfo = new BoardInfo();
		
		boolean ret = false;
		
		
		String savePath="C:/webp/Project/WebContent/Down"; //저장할 디렉토리 (절대경로)
		
		
		int groupnumber = getIntFromParameter(request.getParameter("groupnumber"),-1);
		
		/*HttpSession session = request.getSession();
		  UserDataObj udo = (UserDataObj) session.getAttribute("viwer");
		
		String loginID = udo.getId();*/
		
		String loginID = "bluesky6096";
		String file = request.getParameter("upfile");
		try {
			
		if(file != null){
		 
			int sizeLimit = 5 * 1024 * 1024 ; // 5메가까지 제한 넘어서면 예외발생
			
			MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
		 
			Enumeration formNames=multi.getFileNames();  // 폼의 이름 반환
		 
			String formName = (String)formNames.nextElement(); // 자료가 많을 경우엔 while 문을 사용
		 
			String fileName = multi.getFilesystemName(formName); // 파일의 이름 얻기
		 
		 if(fileName == null) {   // 파일이 업로드 되지 않았을때
		  System.out.print("파일 업로드 되지 않았음");
		 } else {  // 파일이 업로드 되었을때
		  fileName=new String(fileName.getBytes("8859_1"),"euc-kr"); // 한글인코딩 - 브라우져에 출력
		  System.out.print("User Name : " + multi.getParameter("userName") + "<BR>");
		  System.out.print("Form Name : " + formName + "<BR>");
		  System.out.print("File Name  : " + fileName);
		 }
		 // end if
		
		}
		
		
		
		
		String userid = request.getParameter("loginID");
		
		String userpwd = request.getParameter("userpwd");
		
		String id = request.getParameter("b_id");
		String pwd = request.getParameter("b_pwd");
		String content = request.getParameter("b_content");
		
		
		boardinfo.setGroupnumber(groupnumber);
		boardinfo.setB_id(id);
		boardinfo.setB_pwd(pwd);
		boardinfo.setB_content(content);
		
		
			if(op == null) {
				int page = getIntFromParameter(request.getParameter("page"), 1);
				
				PageResult<Board> result = BoardDAO.getPage(page,10);
				request.setAttribute("result", result);
				request.setAttribute("page", page);
				request.setAttribute("loginID", loginID);
				
				actionUrl = "board.jsp";
				
			}else if(op.equals("create")){
				int page = getIntFromParameter(request.getParameter("page"), 1);
				request.setAttribute("loginID", userid);
				
				BoardInfoDAO.create(boardinfo);
				
				Board board = BoardDAO.findByNumber(number);
				request.setAttribute("board", board);
				PageResult<BoardInfo> result = BoardInfoDAO.getPage(page,number);
				request.setAttribute("result", result);
				
				actionUrl = "show.jsp";
			
			}else if (op.equals("show")) {
				int page = getIntFromParameter(request.getParameter("page"), 1);
				Board board = BoardDAO.findByNumber(number);
				request.setAttribute("board", board);
				PageResult<BoardInfo> result = BoardInfoDAO.getPage(page,number);
				request.setAttribute("result", result);
				
				actionUrl = "show.jsp";
			} else if (op.equals("delete")) {
				
				ret = BoardDAO.remove(userid,userpwd);
				request.setAttribute("result", ret);
				
				if (ret) {
					request.setAttribute("msg", "게시글이 삭제되었습니다.");
					actionUrl = "success.jsp";
				} else {
					request.setAttribute("error", "입력한 정보와 맞지 않습니다.");
					actionUrl = "error.jsp";
				}
			}else if (op.equals("b_delete")) {
				
				ret = BoardInfoDAO.remove(id,pwd);
				request.setAttribute("result", ret);
				
				if (ret) {
					request.setAttribute("msg", "댓글이 삭제되었습니다.");
					actionUrl = "success.jsp";
				} else {
					request.setAttribute("error", "입력한 정보와 맞지 않습니다.");
					actionUrl = "error.jsp";
				}
			} 
		}catch (SQLException | NamingException e) {
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
			actionUrl = "error.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}
	
	private boolean isRegisterMode(HttpServletRequest request) {
		String method = request.getParameter("_method");
		return method == null || method.equals("POST");
		
	}
	
	/*
	 *  @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
		System.out.println("this is post");	
		
		boolean ret = false;
		String actionUrl;
		
		Board board = new Board();

		request.setCharacterEncoding("utf-8");
		
		String savePath="C:/webp/Project/WebContent/Down"; //저장할 디렉토리 (절대경로)
		
		int page = getIntFromParameter(request.getParameter("page"), -1);
		
		//String loginID = request.getParameter("loginID");
		
			 
			int sizeLimit = 5 * 1024 * 1024 ; // 5메가까지 제한 넘어서면 예외발생
			
			MultipartRequest multi=new MultipartRequest(request, savePath, sizeLimit, new DefaultFileRenamePolicy());
		 
			Enumeration formNames=multi.getFileNames();  // 폼의 이름 반환
		 
			String formName = (String)formNames.nextElement(); // 자료가 많을 경우엔 while 문을 사용
		 
			String fileName = multi.getFilesystemName(formName); // 파일의 이름 얻기
		 
		
		
		//MultipartRequest multi=new MultipartRequest(request, saveDirectory)
		
		
		String userid = multi.getParameter("userid");
		String pwd = multi.getParameter("pwd");
		String title = multi.getParameter("title");
		String content = multi.getParameter("content");
		
		
		List<String> errorMsgs = new ArrayList<String>();
		System.out.println("title>>"+title);
		System.out.println("userid>>"+userid);
		System.out.println("file>>"+fileName);
		board.setUserid(userid);
		board.setPwd(pwd);
		board.setTitle(title);
		board.setContent(content);
		board.setFile(fileName);
		
		try {
				if (isRegisterMode(request)) {
					PageResult<Board> result = BoardDAO.getPage(page,10);
					request.setAttribute("result", result);
					request.setAttribute("page", page);
					request.setAttribute("loginID", userid);
					
					
					actionUrl = "board.jsp";
				} else {
					
				}
				if (ret != true) {
					
					request.setAttribute("board", board);
					BoardDAO.create(board);
					
					PageResult<Board> result = BoardDAO.getPage(page,10);
					request.setAttribute("result", result);
					request.setAttribute("page", page);
					request.setAttribute("loginID", userid);
					
					actionUrl = "board.jsp";
				} else {
					actionUrl = "error.jsp";
				}
			} catch (SQLException | NamingException e) {
				
				errorMsgs.add(e.getMessage());
				actionUrl = "error.jsp";
			}
			request.setAttribute("errorMsgs", errorMsgs);
			RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
			dispatcher.forward(request,  response);
		}
	
}
