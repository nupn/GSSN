package com.crystars.message;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import com.crystars.item.item;
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
	public static List<message> findTMSByNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		List<message> list = new ArrayList<message>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;		
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// Member테이블과 조인해서 닉네임도 같이가져옴
			stmt = conn.prepareStatement("SELECT msg_num, nickname, title, content, to_date, to_memnum, from_memnum, yesno "
					+ "FROM Member INNER JOIN Message "
					+ "WHERE Member.Member_Num=Message.to_memnum AND to_memnum= ? ");
			stmt.setInt(1, id);
			// 수행
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new message (
						rs.getInt("msg_num"),
						URLDecoder.decode(rs.getString("nickname"),"utf-8"),
						URLDecoder.decode(findNick(rs.getInt("from_memnum")),"utf-8"), // 보낸사람 닉네임 검색
						URLDecoder.decode(rs.getString("title"),"utf-8"),
						URLDecoder.decode(rs.getString("content"),"utf-8"),
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
	public static List<message> findFMSByNum(int id) throws NamingException, SQLException, UnsupportedEncodingException{

		List<message> list = new ArrayList<message>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// Member테이블과 조인해서 닉네임도 같이가져옴
			stmt = conn.prepareStatement("SELECT msg_num, nickname, title, content, to_date, to_memnum, from_memnum, yesno "
					+ "FROM Member INNER JOIN Message "
					+ "WHERE Member.Member_Num=Message.to_memnum AND from_memnum= ? ");
			stmt.setInt(1, id);
			// 수행
			rs = stmt.executeQuery();

			while(rs.next()) {
				list.add(new message (rs.getInt("msg_num"),
						URLDecoder.decode(rs.getString("nickname"),"utf-8"),
						URLDecoder.decode(findNick(rs.getInt("to_memnum")),"utf-8"), // 받은사람 닉네임 찾기
						URLDecoder.decode(rs.getString("title"),"utf-8"),
						URLDecoder.decode(rs.getString("content"),"utf-8"),
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
	public static String findNick(int id) throws NamingException, SQLException, UnsupportedEncodingException{
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
				name = URLDecoder.decode(rs.getString("Nickname"),"utf-8");
			

		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return name;
	}
	
	public static int countNotConfirmMsg(int id) throws NamingException, SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int count=0;
		
		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT COUNT(yesno) From Message Where to_memnum= ? and yesno=false ");
			stmt.setInt(1, id);

			rs = stmt.executeQuery();
			rs.next();
			
			count = rs.getInt(1);
			

		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return count;
	}
	
	public static boolean sendMsg(message msg) throws SQLException, NamingException, UnsupportedEncodingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Timestamp date = new Timestamp(new Date().getTime());
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();
			
			stmt = conn.prepareStatement(" SELECT MAX(msg_num) FROM Message ");
			rs = stmt.executeQuery();
			rs.next();
			int msg_num = rs.getInt(1)+1;
			System.out.println(msg.getTitle());
			stmt = conn.prepareStatement(
					"INSERT INTO `Message`(`msg_num`, `to_memnum`, `from_memnum`, `title`, `content`, `to_date`, `yesno`) "
					+ "VALUES (?,?,?,?,?,?,?) "
					);
			stmt.setInt(1,  msg_num);
			stmt.setInt(2,  msg.getTo_memnum());
			stmt.setInt(3,  msg.getFrom_memnum());
			stmt.setString(4,  URLEncoder.encode(msg.getTitle(),"utf-8"));
			stmt.setString(5,  URLEncoder.encode(msg.getContent(),"utf-8"));			
			stmt.setTimestamp(6,  date);
			stmt.setBoolean(7,  false);
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
	
	public static message findMSGByMsgNum(int msg_num) throws NamingException, SQLException, UnsupportedEncodingException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		message _message =new message();
		
		DataSource ds = getDataSource();
		try {
			conn = ds.getConnection();
			stmt = conn.prepareStatement("SELECT * From Message Where msg_num= ? ");
			stmt.setInt(1, msg_num);

			rs = stmt.executeQuery();
			
			rs.next();
				_message =new message (rs.getInt("msg_num"),
						URLDecoder.decode(findNick(rs.getInt("from_memnum")),"utf-8"),						
						URLDecoder.decode(findNick(rs.getInt("to_memnum")),"utf-8"), // 받은사람 닉네임 찾기
						URLDecoder.decode(rs.getString("title"),"utf-8"),
						URLDecoder.decode(rs.getString("content"),"utf-8"),
						rs.getTimestamp("to_date"),
						rs.getInt("to_memnum"),
						rs.getInt("from_memnum"),
						rs.getBoolean("yesno")
				);
			stmt = conn.prepareStatement("UPDATE `Message` SET `yesno`=true WHERE msg_num=? ");
			stmt.setInt(1, msg_num);
			stmt.executeUpdate();
				
			
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return _message;
	}
}
