package hello.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

/**
 * JDBC - DriverManager 사용
 * 
 *
 */
@Slf4j
public class MemberRepositoryV0 {

	public Member save(Member member) throws SQLException {
		String sql = "insert into member(member_id, money) values (?, ?)";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setInt(2, member.getMoney());
			pstmt.executeUpdate();
			return  member;
		} catch (SQLException e) {
			log.error("db error", e);
			throw e;
		} finally {
			close(con, pstmt, null);
		}
		
	}
	
	private void close(Connection con, Statement stmt, ResultSet re) {
		
		if(re != null) {
			try {
				re.close();
			} catch (Exception e) {
				log.info("error", e);
			}
			
		}
		
		if(stmt != null) {
			try {
				stmt.close();
				
			} catch (Exception e) {
				log.info("error", e);
			}
		}
		
		if(con != null) {
			try {
				con.close();
				
			} catch (Exception e) {
				log.info("error", e);
			}
		}
	}
	
	private Connection getConnection() {
		return DBConnectionUtil.getConnection();
	}
}
