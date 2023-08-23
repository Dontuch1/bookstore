package com.ezen.book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ezen.utill.JDBCUtil;


public class BookscoreDAO {

	private static BookscoreDAO dao=new BookscoreDAO();
	private BookscoreDAO() { }
	public static BookscoreDAO getInstanse() {
		return dao;
	}
	
	// 삽입 메소드
	public int insertBookscore (BookscoreVO vo) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		String sql="insert into bookscore values(sno_seq.nextval,?,?,?,?,sysdate)";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getBno());
			pstmt.setString(2, vo.getId());
			pstmt.setDouble(3, vo.getScore());
			pstmt.setString(4, vo.getCmt());
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt);
		}
		return result;
	}
	public int getRowCount(int bno) {
		int result=0;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="select count(*) from bookscore where bno=?";
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return result;
	}
	public List<BookscoreVO> getBookscore(int page, int bno, int displayRow) {
		List<BookscoreVO> list=null;
		StringBuilder sb=new StringBuilder();
		sb.append("select * from (");
		sb.append(" select rownum rn, A.* from");
		sb.append("  (select * from bookscore where bno=? order by sno desc) A");
		sb.append(" where rownum<=?");
		sb.append(" ) where rn>=?");
		
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sb.toString());
			pstmt.setInt(1, bno);
			pstmt.setInt(2, page*displayRow);
			pstmt.setInt(3, page*displayRow-displayRow+1);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				list=new ArrayList<>();
				do {
					list.add(new BookscoreVO(rs.getInt("sno"),rs.getInt("bno"),
							rs.getString("id"),rs.getDouble("score"),rs.getString("cmt"),rs.getDate("regdate")));
				}while(rs.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		

		return list;
	}
	public ArrayList<Number> getAvgScore(int bno) {
		// double score=0;
		ArrayList<Number> list=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		String sql="select avg(score),count(*) from bookscore where bno=?";
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bno);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				//score=rs.getDouble(1);
				list=new ArrayList<>();
				list.add(rs.getDouble(1));
				list.add(rs.getInt(2));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return list;
	}
}
