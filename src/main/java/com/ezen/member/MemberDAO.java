package com.ezen.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ezen.utill.JDBCUtil;

public class MemberDAO {
	private static MemberDAO dao=new MemberDAO();
	private MemberDAO() {}
	public static MemberDAO getInstance() {
		return dao;
	}
	//------ 로그인 메소드 -파라메타(아이디,패스워드), 반환 MemberVO(아이디,이름,등급)
	public MemberVO login(String id,String pwd) {
		MemberVO vo=null;
		Connection conn=JDBCUtil.getConnection();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement("select name,grade from membertbl where id=? and pwd=?");
			pstmt.setString(1, id);pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			if(rs.next()) {//로그인 성공
				vo=new MemberVO(id,rs.getString("name"),rs.getString("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(conn, pstmt, rs);
		}
		return vo;
	}
}
