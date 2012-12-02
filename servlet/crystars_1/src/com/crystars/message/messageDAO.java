package com.crystars.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;


public class messageDAO {
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
	
	
	
	//id를 받아서 받은메시지를 검색함
	public static List<message> findTMSByNum(int id) throws NamingException, SQLException{

		List<message> list = new ArrayList<message>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// Member테이블과 조인해서 닉네임도 같이가져옴
			stmt = conn.prepareStatement("SELECT nickname, title, content, to_date, to_memnum, from_memnum, yesno "
					+ "FROM Member INNER JOIN Message "
					+ "WHERE Member.Member_Num=Message.to_memnum AND to_memnum= ? ");
			stmt.setInt(1, id);
			// 수행
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new message (
						rs.getString("nickname"),
						findNick(rs.getInt("from_memnum")), // 보낸사람 닉네임 검색
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("to_date"),
						rs.getInt("to_memnum"),
						rs.getInt("from_memnum"),
						rs.getBoolean("yesno"))
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
	
	
	
	// 보낸사람 검색 
	public static List<message> findFMSByNum(int id) throws NamingException, SQLException{

		List<message> list = new ArrayList<message>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// Member테이블과 조인해서 닉네임도 같이가져옴
			stmt = conn.prepareStatement("SELECT nickname, title, content, to_date, to_memnum, from_memnum, yesno "
					+ "FROM Member INNER JOIN Message "
					+ "WHERE Member.Member_Num=Message.to_memnum AND from_memnum= ? ");
			stmt.setInt(1, id);
			// 수행
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new message (rs.getString("nickname"),
						findNick(rs.getInt("to_memnum")), // 받은사람 닉네임 찾기
						rs.getString("title"),
						rs.getString("content"),
						rs.getTimestamp("to_date"),
						rs.getInt("to_memnum"),
						rs.getInt("from_memnum"),
						rs.getBoolean("yesno"))
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
	
	// 닉네임 검색 함수
	public static String findNick(int id) throws NamingException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String name = "";
		
		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT Nickname From Member Where Member_Num= ? ");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			while(rs.next())
				name = rs.getString("Nickname");
			

		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return name;
	}
	/*
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
	
	//업데이트
	public static boolean update(item board) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"UPDATE boardinfo " +
					"SET  title=?, contents=?" +
					"WHERE num=?"
					);
			stmt.setString(1,  board.getTitle());
			stmt.setString(2,  board.getContents());
			stmt.setInt(3,  board.getNum());

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
	
	//조회수
	public static boolean countupdate(item board) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"UPDATE boardinfo " +
					"SET  count=? "+
					"WHERE num=?"
					);
			stmt.setInt(1,  board.getCount());
			stmt.setInt(2,  board.getNum());

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
	
	//제거
	public static boolean remove(int num) throws NamingException, SQLException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("DELETE FROM boardinfo WHERE num=?");
			stmt.setInt(1,  num);
			
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
	*/
}
