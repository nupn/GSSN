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
@WebServlet("/garretServlet")
public class garretServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public garretServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserDataObj udo = (UserDataObj) session.getAttribute("viwer");
		int g_num = udo.getPid();
		int o_num = Integer.parseInt(request.getParameter("userid")); // 접속한 다락방의 주인 회원번호
		
		String op = request.getParameter("op"); // 눌러졋을때 상태	
	
		
		
		//int o_num = 1127; //주인 임시 회원번호
		//int g_num = 1127; // 로그인 한사람 임시 회원번호
		double sum=0;
		int tp=0;
		

		
		String actionUrl = "";
		
		int[][] A = new int[2][12];
		List<item> _item = new ArrayList<item>();
		List<message> _tmessage = new ArrayList<message>();
		List<trade> _trade = new ArrayList<trade>(); boolean bool_trade;
		List<evaluation> _evaluation = new ArrayList<evaluation>();
		user _user = new user();
		//List<message> _fmessage = new ArrayList<message>(); // 보낸메시지 보기 용 List 안쓸듯? 
		
		
		try {
			if(op==null || op=="garret"){
				A = tradeDAO.findBySalesCountNum(o_num);
				request.setAttribute("salescount", A[0]);
				request.setAttribute("purchasecount", A[1]);
				_user = userDAO.findByNum(o_num);
				request.setAttribute("user", _user);
				
				_item = itemDAO.findByNum(o_num);
				request.setAttribute("itemsize",_item.size());
				request.setAttribute("item", _item);
				// 거래중인가 확인후..거래중이면우 엔 다른걸 넘겨줌
				request.setAttribute("user", _user);
				
				_tmessage = messageDAO.findTMSByNum(o_num);
				request.setAttribute("messagesize",_tmessage.size());
				request.setAttribute("notconfirmmsgsize", messageDAO.countNotConfirmMsg(o_num));
				request.setAttribute("message", _tmessage);
				
				_trade = tradeDAO.findByNum(o_num);
				bool_trade = tradeDAO.findTRHistory(o_num, g_num);
				String g_name = tradeDAO.findNameByNum(g_num);
				request.setAttribute("g_name",g_name);
				request.setAttribute("bool_trade", bool_trade);
				request.setAttribute("tradesize", _trade.size());
				request.setAttribute("trade", _trade);	
								
				_evaluation = evaluationDAO.findByNum(o_num);
				request.setAttribute("reviewsize", _evaluation.size());
				request.setAttribute("review", _evaluation);
				for(int i=0;i<_evaluation.size(); i++){
					sum +=_evaluation.get(i).getEvaluation_rating();
				}
				tp =(int)(sum/_evaluation.size());
				request.setAttribute("mineralC", tp);
				request.setAttribute("mineralB",5-tp);
				
				request.setAttribute("o_num",o_num);
				request.setAttribute("g_num",g_num);
				// 아이템, 메시지, 거래 기록을 넘겨줌
				
				if(o_num == g_num){
					request.setAttribute("identity", "owner");
				}else{
					request.setAttribute("identity", "guest");
				}
				actionUrl = "garret.jsp";
			}
			
		}catch (SQLException | NamingException e) {
		
			request.setAttribute("error", e.getMessage());
			e.printStackTrace();
			actionUrl = "error.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean test = true;
		String actionUrl = null;
		evaluation eval = new evaluation();
		
		request.setCharacterEncoding("utf-8");
		List<String> errorMsgs = new ArrayList<String>();
		String method = request.getParameter("_method");
		
		
		if(method==null || method.equals("review")){
			Timestamp date = new Timestamp(new Date().getTime());
			String review = request.getParameter("review");
			double rating = Double.parseDouble(request.getParameter("rating"));
			int esales_memnum = Integer.parseInt(request.getParameter("o_num"));
			int epurchase_memnum = Integer.parseInt(request.getParameter("g_num"));
			
			if(review == null || review.trim().length() < 10){
				errorMsgs.add("후기는 10자 이상 입력해주세요");
				test = false;
			}
						
			eval.setEsales_memnum(esales_memnum);
			eval.setEpurchase_memnum(epurchase_memnum);
			eval.setDate(date);
			eval.setEvaluation_post(review);
			eval.setEvaluation_rating(rating);
			
			
			
			try {
				evaluationDAO.insert(eval);
				String msg = "후기가 등록되었습니다.";
				request.setAttribute("msg", msg);
				actionUrl = "success.jsp";  
			} catch (NamingException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(method.equals("goods")){
			String status = request.getParameter("status");
			String[] choice = request.getParameterValues("choice"); 
			trade _trade = new trade();
			
			

			//체크된 물품번호가 배열로 넘어옴
			
			if(status.equals("거래")){
				try {
					int purchase_memnum = Integer.parseInt(request.getParameter("purchase_memnum"));
					_trade = tradeDAO.findByGoodsNum(Integer.parseInt(choice[0]));
					_trade.setTpurchase_memnum(purchase_memnum);
					tradeDAO.tradeinsert(_trade);
					String msg = "거래가 등록되었습니다.";
					request.setAttribute("msg", msg);
					actionUrl = "success.jsp";  
				} catch (NumberFormatException | NamingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(status.equals("거래완료")){
				
					try {
						for(int i=0;i<choice.length;i++){
							if(itemDAO.findByGoodsNum(Integer.parseInt(choice[i])) == 1){
								itemDAO.finByGoodsNum(Integer.parseInt(choice[i]));
								String msg = "거래가 완료되었습니다.";
								request.setAttribute("msg", msg);
								actionUrl = "success.jsp";  
							}else{
								errorMsgs.add("거래가 진행중인 물건만 완료할수있습니다.");
								actionUrl = "error.jsp";  
							}
						}
					} catch (NumberFormatException | NamingException
							| SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}else if(status.equals("물품삭제")){
				try {
					if(itemDAO.findByGoodsNum(Integer.parseInt(choice[0])) == 1){
						errorMsgs.add("거래가 진행중인 물건은 삭제 하실수 없습니다.");
						actionUrl = "error.jsp"; 
					}else{
						itemDAO.deleteByGoodsNum(Integer.parseInt(choice[0]));
						String msg = "삭제가 완료되었습니다.";
						request.setAttribute("msg", msg);
						actionUrl = "success.jsp";  
					}
				} catch (NumberFormatException | NamingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		}
		
		if(method.equals("msg")){
			message _msg = new message();
			int tmemnum = Integer.parseInt(request.getParameter("toid"));
			int fmemnum = Integer.parseInt(request.getParameter("fromid"));
			String title = request.getParameter("title");
			String contents = request.getParameter("contents");
			
			_msg.setTo_memnum(tmemnum);
			_msg.setFrom_memnum(fmemnum);
			_msg.setTitle(title);
			_msg.setContent(contents);
			
			try {
				messageDAO.sendMsg(_msg);
				String msg = "메시지가 전송 되었습니다.";
				request.setAttribute("msg", msg);
				actionUrl = "success.jsp";  
			} catch (SQLException | NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				actionUrl = "error.jsp";
			}
			
			
		}
		
		
		request.setAttribute("errorMsgs", errorMsgs);
		RequestDispatcher dispatcher = request.getRequestDispatcher(actionUrl);
		dispatcher.forward(request,  response);
		
	}

}
