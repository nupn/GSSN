package DAO;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;

import Bean.PageResult;

import Bean.BoardInfo;


public class BoardInfoDAO {
	public static DataSource getDataSource() throws NamingException {
		Context initCtx = null;
		Context envCtx = null;

		// Obtain our environment naming context
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		return (DataSource) envCtx.lookup("jdbc/test");
	}
	
public static PageResult<BoardInfo> getPage(int page,int number) throws SQLException, NamingException {
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;	
		
		if (page <= 0) {
			page = 1;
		}
		
		DataSource ds = getDataSource();
		
		PageResult<BoardInfo> result = new PageResult<BoardInfo>(page,number);

		try {
			conn = ds.getConnection();		
			
			stmt = conn.prepareStatement("SELECT * FROM boardinfo where groupnumber = ? ORDER BY b_number");
			stmt.setInt(1, number);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				BoardInfo boardinfo = new BoardInfo();
				
				boardinfo.setB_number(rs.getInt("b_number"));
				boardinfo.setGroupnumber(rs.getInt("groupnumber"));
				boardinfo.setB_content(rs.getString("b_content"));
				boardinfo.setB_id(rs.getString("b_id")); 
				boardinfo.setB_pwd(rs.getString("b_pwd"));
				
				result.getList().add(boardinfo);
				System.out.println("b_id = "+rs.getString("b_id"));
				
				}
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return result;		
	}
	
public static void create(BoardInfo boardinfo) throws SQLException, NamingException {
		
		int result;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		DataSource ds = getDataSource();
		System.out.println("여기까지 만들러 오니???");
		try {
			conn = ds.getConnection();
			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO boardinfo(groupnumber, b_content, b_id, b_pwd) " +
					"VALUES(?, ?, ?, ?)"
					);
			stmt.setInt(1,  boardinfo.getGroupnumber());
			stmt.setString(2,  boardinfo.getB_content());
			stmt.setString(3, boardinfo.getB_id());
			stmt.setString(4, boardinfo.getB_pwd());
		
			result=stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			if(result>0){
				System.out.println("게시글이 추가되었습니다.");
			}else{
				System.out.println("께시글이 그대로 입니다.");
			}
			
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
	}
	
public static boolean remove(String b_userid , String b_pwd) throws NamingException, SQLException {
	int result;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	DataSource ds = getDataSource();
	
	try {
		conn = ds.getConnection();

		// 질의 준비
		stmt = conn.prepareStatement("DELETE FROM boardinfo WHERE b_id= ? and b_pwd= ? ");
		stmt.setString(1,  b_userid);
		stmt.setString(2,  b_pwd);
		
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
