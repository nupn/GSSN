package com.crystars.item;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.crystars.trade.trade;


public class itemDAO {
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
	
	
	
	//검색
	public static List<item> findByNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		List<item> list = new ArrayList<item>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Goods WHERE GSales_MemberNum = ?");
			stmt.setInt(1, id);
			
			// 수행
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new item (rs.getInt("goods_num"),
						rs.getInt("gsales_membernum"),
						rs.getString("image"),
						rs.getInt("price"),
						rs.getTimestamp("regist_date"),
						rs.getInt("category_num"),						
						 URLDecoder.decode(rs.getString("book_name"),"utf-8"),						
						 URLDecoder.decode(rs.getString("content"),"utf-8"),
						 URLDecoder.decode(rs.getString("publisher"),"utf-8"),
						rs.getInt("quantity"),
						rs.getInt("quality"),
						rs.getInt("status"))
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
	
	//생성
	public static boolean create(item good) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO `Goods`(`Goods_Num`, `GSales_MemberNum`, `Image`, `Price`, `Regist_Date`, `Category_Num`, `Book_Name`, `Content`, `Publisher`, `Quantity`, `Quality`) " + 
					"VALUES (?,?,?,?,?,?,?,?,?,?,?)"
					);
			stmt.setInt(1,  good.getGoods_num());
			stmt.setInt(2,  good.getGsales_membernum());
			stmt.setString(3,  good.getImage());
			stmt.setInt(4,  good.getPrice());
			stmt.setTimestamp(5,  good.getRegist_date());
			stmt.setInt(6,  good.getCategory_num());
			stmt.setString(7,  good.getBook_name());
			stmt.setString(8,  good.getContent());
			stmt.setString(9,  good.getPublisher());
			stmt.setInt(10,  good.getQuantity());
			stmt.setInt(10,  good.getQuality());
			// 수행
			result = stmt.executeUpdate();
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return (result == 1);
	}
	
	public static int findByGoodsNum(int goods_num) throws NamingException, SQLException{

		int i=0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT status FROM Goods WHERE Goods_Num =? ");
			stmt.setInt(1, goods_num);
			
			// 수행
			rs = stmt.executeQuery();

			rs.next();
			i = rs.getInt(1);
			
				
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return i;
	}
	
	public static item findInfoByGoodsNum(int goods_num) throws NamingException, SQLException, UnsupportedEncodingException{

		int i=0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		item _item;
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Goods WHERE Goods_Num =? ");
			stmt.setInt(1, goods_num);
			
			// 수행
			rs = stmt.executeQuery();

			rs.next();
			_item = new item(rs.getInt("goods_num"),
			rs.getInt("gsales_membernum"),
			rs.getString("image"),
			rs.getInt("price"),
			rs.getTimestamp("regist_date"),
			rs.getInt("category_num"),						
			 URLDecoder.decode(rs.getString("book_name"),"utf-8"),						
			 URLDecoder.decode(rs.getString("content"),"utf-8"),
			 URLDecoder.decode(rs.getString("publisher"),"utf-8"),
			rs.getInt("quantity"),
			rs.getInt("quality"),
			rs.getInt("status"));
			
			
				
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return _item;
	}
	
	
	public static void finByGoodsNum(int goods_num) throws NamingException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			
			conn = ds.getConnection();
			stmt = conn.prepareStatement("UPDATE Goods SET Status=3 WHERE goods_num=? "
					);
			stmt.setInt(1, goods_num);
			stmt.executeUpdate();

			stmt = conn.prepareStatement(
					"UPDATE Trade SET Trade_Status=0 WHERE Trade_ItemNum=? "
					);
			stmt.setInt(1, goods_num);
			stmt.executeUpdate();
			
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
	}
	
	public static boolean deleteByGoodsNum(int goods_num) throws NamingException, SQLException{
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			conn = ds.getConnection();
			stmt = conn.prepareStatement("UPDATE Goods SET Status=4 WHERE goods_num=? "
					);
			stmt.setInt(1, goods_num);
			stmt.executeUpdate();

			
			// 수행
			result = stmt.executeUpdate();
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return (result == 1);		
	}
}
