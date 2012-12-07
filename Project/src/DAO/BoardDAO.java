package DAO;

import java.sql.*;
import javax.naming.*;
import javax.sql.*;
import Bean.PageResult;
import Bean.Board;


public class BoardDAO {
	public static DataSource getDataSource() throws NamingException {
		Context initCtx = null;
		Context envCtx = null;

		// Obtain our environment naming context
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		return (DataSource) envCtx.lookup("jdbc/test");
	}
	
	public static PageResult<Board> getPage(int page, int numItemsInPage) 
			throws SQLException, NamingException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		
		
		if (page <= 0) {
			page = 1;
		}
		
		DataSource ds = getDataSource();
		
		PageResult<Board> result = new PageResult<Board>(numItemsInPage, page);
		
		int startPos = (page - 1) * numItemsInPage;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
		
	 		rs = stmt.executeQuery("SELECT COUNT(*) FROM board");
			rs.next();
			
			result.setNumItems(rs.getInt(1));
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			System.out.println("페이지에 정볼를 전달합니다.");
	 		// users 테이블 SELECT
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM board ORDER BY number LIMIT " + startPos + ", " + numItemsInPage);
			
			while(rs.next()) {
				result.getList().add(new Board(
						rs.getInt("number"),
						rs.getString("title"),
						rs.getString("content"), 
						rs.getString("userid"),
						rs.getString("pwd"),
						rs.getInt("count"),
						rs.getString("file")
					));
				}
			System.out.println("여기까지 못왔니???");
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return result;		
	}
	
public static void create(Board board) throws SQLException, NamingException {
		
		int result;
		System.out.println("여기까지 는 왔니~~~???");
		Connection conn = null;
		PreparedStatement stmt = null;
			
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();
			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO board(title,content,userid,pwd,file) " +
					"VALUES(?, ?, ?, ?, ?)"
					);
			stmt.setString(1,  board.getTitle());
			stmt.setString(2,  board.getContent());
			stmt.setString(3, board.getUserid());
			stmt.setString(4, board.getPwd());
			stmt.setString(5, board.getFile());
		
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

public static Board findByNumber(int number) throws SQLException, NamingException  {
	
	Board board = null;
	
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	DataSource ds = getDataSource();
	
	try {
		conn = ds.getConnection();

		// 질의 준비
		stmt = conn.prepareStatement("SELECT * FROM board WHERE number = ?");
		stmt.setInt(1, number);
		
		// 수행
		rs = stmt.executeQuery();
		
		if (rs.next()) {
			int count=rs.getInt("count");
			int currentnumber=rs.getInt("number");
			
			board = new Board(
					rs.getInt("number"),
					rs.getString("title"),
					rs.getString("content"), 
					rs.getString("userid"),
					rs.getString("pwd"),
					rs.getInt("count"),
					rs.getString("file"));
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			
			count++;
			
			stmt = conn.prepareStatement(
					"UPDATE board " +
					"SET  count = ? "+
					"WHERE number = ?"
					);
			stmt.setInt(1, count);
			stmt.setInt(2,  currentnumber);
			
			stmt.executeUpdate();
			
			}	
	} finally {
		// 무슨 일이 있어도 리소스를 제대로 종료
		if (rs != null) try{rs.close();} catch(SQLException e) {}
		if (stmt != null) try{stmt.close();} catch(SQLException e) {}
		if (conn != null) try{conn.close();} catch(SQLException e) {}
	}
	
	return board;
	}
	
public static boolean remove(String userid , String pwd) throws NamingException, SQLException {
	int result;
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	
	DataSource ds = getDataSource();
	
	try {
		conn = ds.getConnection();

		// 질의 준비
		stmt = conn.prepareStatement("DELETE FROM board WHERE userid= ? and pwd= ? ");
		stmt.setString(1,  userid);
		stmt.setString(2,  pwd);
		
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
