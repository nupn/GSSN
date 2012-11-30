package board.user;

import java.sql.*;

import javax.naming.*;
import javax.sql.*;
import java.sql.Timestamp;

public class boardDAO {
	// 데이터소스를 이용해 디비와 연동
	public static DataSource getDataSource() throws NamingException {
		Context initCtx = null;
		Context envCtx = null;

		// Obtain our environment naming context
		initCtx = new InitialContext();
		envCtx = (Context) initCtx.lookup("java:comp/env");

		// Look up our data source
		return (DataSource) envCtx.lookup("jdbc/board");
	}
	
	// 페이지와 한페이지의 아이템 갯수를 받아서, 그범위내의 데이터를 다 가져옴
	public static PageResult<boardinfo> getPage(int page, int numItemsInPage) 
			throws SQLException, NamingException {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;		
	
		
		if (page <= 0) {
			page = 1;
		}
		// 페이지가 하나도 없다면 1로
		
		DataSource ds = getDataSource();
		PageResult<boardinfo> result = new PageResult<boardinfo>(numItemsInPage, page);
		// 결과값이 들어갈 PageResult타입 result선언
		
		
		int startPos = (page - 1) * numItemsInPage;
		
		try {
			conn = ds.getConnection();
			stmt = conn.createStatement();
			// 디비 접속
			
	 		rs = stmt.executeQuery("SELECT COUNT(*) FROM boardinfo"); // boardinfo 테이블의 갯수 쿼리
			rs.next();
			
			
			result.setNumItems(rs.getInt(1));
			
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			
	 		// boardinfo 테이블 
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM boardinfo ORDER BY num LIMIT " + startPos + ", " + (numItemsInPage-1));
			// boardinfo 테이블에서 num순으로 정렬 startPos부터 numItemsInPage갯수까지/
		
			while(rs.next()) {
				result.getList().add(new boardinfo(
							rs.getInt("num"),
							rs.getString("title"),
							rs.getString("contents"),
							rs.getTimestamp("date"),
							rs.getString("name"),
							rs.getInt("count"),
							rs.getString("pwd")
						));
			}
			// 검색된 데이터끝까지 다 가져옴
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return result;		
	} 
	
	public static int getCountNum() throws NamingException, SQLException{
		boardinfo board = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		 int result=0;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT MAX(num) FROM boardinfo");
			rs = stmt.executeQuery();
	        if(rs.next())
	        {
	          result = rs.getInt(1);
	        }

			
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		return result;
	}
	
	//검색
	public static boardinfo findByNum(int num) throws NamingException, SQLException{
		boardinfo board = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement("SELECT * FROM boardinfo WHERE num = ?");
			stmt.setInt(1, num);
			
			// 수행
			rs = stmt.executeQuery();

			if (rs.next()) {
				board = new boardinfo(rs.getInt("num"),
						rs.getString("title"),
						rs.getString("contents"),
						rs.getTimestamp("date"),
						rs.getString("name"),
						rs.getInt("count"),
						rs.getString("pwd"));
			}	
		} finally {
			// 무슨 일이 있어도 리소스를 제대로 종료
			if (rs != null) try{rs.close();} catch(SQLException e) {}
			if (stmt != null) try{stmt.close();} catch(SQLException e) {}
			if (conn != null) try{conn.close();} catch(SQLException e) {}
		}
		
		return board;
	}
	
	//생성
	public static boolean create(boardinfo board) throws SQLException, NamingException {
		int result;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		DataSource ds = getDataSource();
		
		try {
			conn = ds.getConnection();

			// 질의 준비
			stmt = conn.prepareStatement(
					"INSERT INTO boardinfo(num,title, contents, date, name, count, pwd) " +
					"VALUES(?, ?, ?, ?, ?, ?, ?)"
					);
			stmt.setInt(1,  board.getNum());
			stmt.setString(2,  board.getTitle());
			stmt.setString(3,  board.getContents());
			stmt.setTimestamp(4,  board.getDate());
			stmt.setString(5,  board.getName());
			stmt.setInt(6,  board.getCount());
			stmt.setString(7,  board.getPwd());
			
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
	public static boolean update(boardinfo board) throws SQLException, NamingException {
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
	public static boolean countupdate(boardinfo board) throws SQLException, NamingException {
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
}
