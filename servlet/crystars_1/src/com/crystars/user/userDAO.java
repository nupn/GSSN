package com.crystars.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class userDAO {
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
	

	
	//검색
	public static user findByNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		user _user;
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();
			
			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Member WHERE member_num = ?");
			stmt.setInt(1, id);
			// 수행
			rs = stmt.executeQuery();
			rs.next();
			
			_user = new user (rs.getInt("member_num"),
						URLDecoder.decode(rs.getString("facebook_id"),"utf-8"),
						URLDecoder.decode(rs.getString("Nickname"),"utf-8"),
						"http://graph.facebook.com/"+ rs.getString("facebook_id") +"/picture?type=large",
						URLDecoder.decode(rs.getString("home"),"utf-8"),
						URLDecoder.decode(rs.getString("email"),"utf-8"));
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return _user;
	}
	
public static user findUserByNum(int goods_num) throws NamingException, SQLException, UnsupportedEncodingException{

		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		user _user;
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();
			
			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Member INNER JOIN Goods WHERE Member_Num= GSales_MemberNum and Goods_Num= ?");
			stmt.setInt(1, goods_num);
			// 수행
			rs = stmt.executeQuery();
			rs.next();
			
			_user = new user (rs.getInt("member_num"),
						URLDecoder.decode(rs.getString("facebook_id"),"utf-8"),
						URLDecoder.decode(rs.getString("Nickname"),"utf-8"),
						"http://graph.facebook.com/"+ rs.getString("facebook_id") +"/picture?type=large",
						URLDecoder.decode(rs.getString("home"),"utf-8"),
						URLDecoder.decode(rs.getString("email"),"utf-8"));
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return _user;
	}
}
