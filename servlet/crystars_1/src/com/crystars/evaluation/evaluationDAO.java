package com.crystars.evaluation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;


public class evaluationDAO {
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
	public static List<evaluation> findByNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		List<evaluation> list = new ArrayList<evaluation>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM Evaluation WHERE ESales_MemberNum = ?");
			stmt.setInt(1, id);
			
			// 수행
			rs = stmt.executeQuery();
		
			
			while(rs.next()) {
				list.add(new evaluation (rs.getInt("post_num"),
						rs.getInt("esales_membernum"),
						rs.getInt("epurchase_membernum"),
						URLDecoder.decode(rs.getString("evaluation_post"),"utf-8"),
						rs.getDouble("evaluation_rating"),
						findNick(rs.getInt("epurchase_membernum")),
						rs.getTimestamp("evaluation_date")
						)); 
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return list;
	}
	
	public static String findNick(int id) throws NamingException, SQLException, UnsupportedEncodingException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String name = "";
		
		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT Nickname From Member INNER JOIN Trade "
					+ "Where TPurchase_MemberNum = Member_Num and TPurchase_MemberNum= ?");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
	 
			
			while(rs.next())
				name = URLDecoder.decode(rs.getString("Nickname"),"utf-8");
			
	 

		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		name = URLDecoder.decode(name,"utf-8");
		return name;
	}
	
	public static boolean insert(evaluation eval) throws NamingException, SQLException, UnsupportedEncodingException{
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int post_num;
		Timestamp date = new Timestamp(new Date().getTime());
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(" SELECT MAX(post_num) FROM Evaluation ");
			rs = stmt.executeQuery();
			rs.next();
			post_num = rs.getInt(1)+1;
			
			
			stmt = conn.prepareStatement(
					"INSERT INTO Evaluation(post_num, esales_membernum, epurchase_membernum," +
					" evaluation_post, evaluation_rating, evaluation_date) " +
					"VALUES(?, ?, ?, ?,  ?, ?)"
					);
			
			stmt.setInt(1,  post_num);
			stmt.setInt(2,  eval.getEsales_memnum());
			stmt.setInt(3,  eval.getEpurchase_memnum());
			stmt.setString(4, URLEncoder.encode(eval.getEvaluation_post(),"utf-8"));
			stmt.setDouble(5, eval.getEvaluation_rating());
			stmt.setTimestamp(6, date);
			
			//System.out.println(post_num+" "+eval.getEsales_memnum()+" "+ eval.getEpurchase_memnum()+" " +eval.getEvaluation_post()+ " "+eval.getEvaluation_rating()+" "+ date);
			
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
