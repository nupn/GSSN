package com.crystars.trade;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import com.crystars.message.messageDAO;


public class tradeDAO  {
	// 데이터소스를 이용해 디비와 연동
	public static DataSource getDataSource() throws NamingException {
		Context initCtx = null;
		Context envCtx = null;

		// Obtain our environment naming context
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		return (DataSource) envCtx.lookup("jdbc/test");
	}
	
	// 페이지와 한페이지의 아이템 갯수를 받아서, 그범위내의 데이터를 다 가져옴
	
	
	
	// 판매자 아이디로 검색
	public static List<trade> findByNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		List<trade> list = new ArrayList<trade>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Trade WHERE TSales_MemberNum = ? or Tpurchase_membernum= ?");
			stmt.setInt(1, id);
			stmt.setInt(2, id);
			
			// 수행
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new trade (rs.getInt("trade_num"),
						rs.getInt("tsales_membernum"),
						rs.getInt("tpurchase_membernum"),
						rs.getTimestamp("trade_date"),
						rs.getInt("trade_price"),
						rs.getBoolean("trade_status"),
						URLDecoder.decode(findTRNum(rs.getInt("trade_num")),"utf-8"),
						rs.getInt("Trade_ItemNum"))
				);
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return list;
	}
	
	public static int[][] findBySalesCountNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		int[][] A = new int[2][12];
		int i=1;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String tp="";
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			for(int k=0;k<2;k++){
				for(i=1;i<=12;i++){
					A[k][i-1] = 0;
					
					if(i<10){
						tp = "'2012-0"+i+"%'";
					}else{
						tp = "'2012-"+i+"%'";
					}
					
					if(k==0)
						stmt = conn.prepareStatement("SELECT COUNT(*) FROM `Trade` WHERE `Trade_Date` like " + tp +" and `TSales_MemberNum`= ?");
					else if(k==1)
						stmt = conn.prepareStatement("SELECT COUNT(*) FROM `Trade` WHERE `Trade_Date` like " + tp +" and `TPurchase_MemberNum`= ?");
					
					stmt.setInt(1, id);
					rs = stmt.executeQuery();
	
					rs.next();
					A[k][i-1] = rs.getInt(1);
				}
			}
			
			
			// 수행
			
				
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return A;
	}
	
	//거래번호로 책이름 찾음
	public static String findTRNum(int num) throws NamingException, SQLException, UnsupportedEncodingException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String name = "";
		
		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT Book_Name From Goods INNER JOIN Trade "
					+ "Where Trade_ItemNum=Goods_Num and Trade_Num= ? ");
			stmt.setInt(1, num);

			rs = stmt.executeQuery();
			while(rs.next())
				name =  URLDecoder.decode(rs.getString("Book_Name"),"utf-8");
			
			

		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return name;
	}
	
	public static boolean findTRHistory(int owner, int guest) throws NamingException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		boolean test = true;
		
		String name = "";
		
		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT count(Trade_Num) FROM Trade WHERE TSales_MemberNum= ? and TPurchase_MemberNum= ? ");
			stmt.setInt(1, owner);
			stmt.setInt(2, guest);

			rs = stmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0)
				test = false;
			
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return test;
	}
	
	public static String findNameByNum(int guest) throws NamingException, SQLException, UnsupportedEncodingException{
		String name = "";

		name = messageDAO.findNick(guest);
		name = URLDecoder.decode(name,"utf-8");
		
		return name;
	}
	
	// 거래가 들어오면 상대자의 회원번호를 입력하고 물품을 거래테이블에 올림
	public static void tradeinsert(trade _trade) throws NamingException, SQLException{
		int trade_num;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Timestamp date = new Timestamp(new Date().getTime());
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT Max(trade_num) FROM Trade");
			rs = stmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0)
				trade_num = 1000;
			else{
				trade_num = rs.getInt(1)+1;
			}

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO Trade (trade_num, tsales_membernum, tpurchase_membernum, trade_date, trade_price, trade_status, trade_itemnum) " 
					+ "VALUES (?,?,?,?,?,?,?)"
					);
			stmt.setInt(1, trade_num);
			stmt.setInt(2,  _trade.getTsales_memnum());
			stmt.setInt(3,  _trade.getTpurchase_memnum());
			stmt.setTimestamp(4,  date);
			stmt.setInt(5,  _trade.getTrade_price());
			stmt.setInt(6,  1);
			stmt.setInt(7, _trade.getTrade_itemnum());
			// 수행
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement(
					"UPDATE Goods SET Status=2 WHERE goods_num=? "
					);
			stmt.setInt(1, _trade.getTrade_itemnum());
			
			// 수행
			stmt.executeUpdate();
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
	}
	
	//상품번호를 가지고 item검사
	public static trade findByGoodsNum(int goods_num) throws NamingException, SQLException{

		trade _trade = new trade();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT GSales_MemberNum, price FROM Goods WHERE Goods_Num =? ");
			stmt.setInt(1, goods_num);
			
			// 수행
			rs = stmt.executeQuery();

			rs.next();
			
			_trade.setTrade_itemnum(goods_num);
			_trade.setTsales_memnum(rs.getInt("gsales_membernum"));
			_trade.setTrade_price(rs.getInt("price"));
			
				
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return _trade;
	}
	
	/*
	//거래 완료
	public static int tradefinish(int goods_num) throws NamingException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		

		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT count(Trade_Num) FROM Trade WHERE TSales_MemberNum= ? and TPurchase_MemberNum= ? ");
			stmt.setInt(1, owner);
			stmt.setInt(2, guest);

			rs = stmt.executeQuery();
			rs.next();
			if(rs.getInt(1)==0)
				test = false;
			
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return test;
	}
	*/

	
}
