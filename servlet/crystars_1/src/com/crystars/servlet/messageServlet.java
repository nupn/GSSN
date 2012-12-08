package com.crystars.servlet;
import java.io.IOException;
import java.net.URLDecoder;
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
import javax.servlet.http.HttpSession;

import com.crystars.evaluation.evaluation;
import com.crystars.evaluation.evaluationDAO;
import com.crystars.item.item;
import com.crystars.item.itemDAO;
import com.crystars.message.message;
import com.crystars.message.messageDAO;
import com.crystars.trade.trade;
import com.crystars.trade.tradeDAO;
import com.crystars.user.*;

import com.crystars.*;


/**
 * Servlet implementation class User
 */
@WebServlet("/messageServlet")
public class messageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public messageServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String actionUrl = "";
		
		int op = Integer.parseInt(request.getParameter("sendtype")); // 일반전송 = 1, 답장 = 2
		System.out.println(op);
		message _message =new message();
		/*
			
		messageServlet.do?sendtype="A"&fromid="보내는사람회원번호"&toid="받는사람회원번호번호"
		 //  받은 쪽지 내용확인할때 A=0 or 답장을 보내려고할때 A=2
		 
		messageServlet.do?sendtype="A"&fromid="보내는사람회원번호"
		 //  일반적인 메시지 전송창을 열때 (받는사람 회원번호 입력이없음) A= 1
		  * 메시지를 작성하고 전송은 post로 
		
		 */
		if(op==1){ // 일반메일 전송
			
			String fromid = request.getParameter("fromid");
			request.setAttribute("op", op);
			request.setAttribute("fromid", fromid);
			actionUrl = "message.jsp";
		}
		else if(op==0 || op==2){ // 받은 메시지를 읽었을때 or 답장을 보내려고할때
			String fromid = request.getParameter("fromid");
			String toid = request.getParameter("toid"); 
			request.setAttribute("op",op);
			request.setAttribute("toid", toid);			
			request.setAttribute("fromid", fromid);
			if(op==0){ // 받은 메시지를 읽엇을때
				int msg_num = Integer.parseInt(request.getParameter("msg_num"));
				try {
					_message = messageDAO.findMSGByMsgNum(msg_num);
					request.setAttribute("msg", _message);
				} catch (NamingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actionUrl = "message.jsp";
		}else if(op==3){ // 아이템정보보기일때
			System.out.println(request.getParameter("goods_num"));
			int goods_num = Integer.parseInt(request.getParameter("goods_num"));
			System.out.println(goods_num);
			try {
				request.setAttribute("item", itemDAO.findInfoByGoodsNum(goods_num));
				request.setAttribute("user", userDAO.findUserByNum(goods_num));
				actionUrl="show.jsp";
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			request.setAttribute("status", 1);
			actionUrl = "error.jsp";
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean ret = true;
		String actionUrl = "";
		request.setCharacterEncoding("utf-8");
		message _msg = new message();
		List<String> errorMsgs = new ArrayList<String>();
		int tmemnum=0,fmemnum;
		String title="", contents="";
		fmemnum = Integer.parseInt(request.getParameter("fromid"));
		System.out.println(request.getParameter("title"));
		if(request.getParameter("toid") ==null)
		{
			ret = false;
			errorMsgs.add("받으시는분의 회원번호를 정확하게 입력해주세요.");
		}
		else
			tmemnum = Integer.parseInt(request.getParameter("toid"));
		
		if(request.getParameter("title") == null)
		{
			ret =false;
			errorMsgs.add("제목이 비어있습니다.");
		}
		else title = request.getParameter("title");
		
		if(request.getParameter("contents") == null)
		{
			ret = false;
			errorMsgs.add("내용이 비어있습니다.");
		}
		else contents = request.getParameter("contents");
		
		if(ret == true){
			_msg.setTo_memnum(tmemnum);
			_msg.setFrom_memnum(fmemnum);
			_msg.setTitle(title);
			_msg.setContent(contents);
		
		

			try {
				messageDAO.sendMsg(_msg);
				String msg = "메시지가 전송 되었습니다.";
				request.setAttribute("msg", msg);
				request.setAttribute("status", 1);
				actionUrl = "success.jsp";  
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			errorMsgs.add("메시지 전송에 실패했습니다.");
			request.setAttribute("o_num", fmemnum);
			request.setAttribute("status", 1);
			actionUrl="error.jsp";
		}
		
		request.setAttribute("errorMsgs", errorMsgs);
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}

}
