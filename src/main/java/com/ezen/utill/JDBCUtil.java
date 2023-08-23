package com.ezen.utill;
//오라클과 연동하기 위한 공통 모듈 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class JDBCUtil {
	
	public static Connection getConnection() {
		Connection conn=null;
		try {
		Context ctx=new InitialContext();
		DataSource ds=(DataSource) ctx.lookup("java:comp/env/jdbc/bookoracle");
		conn=ds.getConnection();
		}catch (Exception e) {
		e.printStackTrace();
		} 
		return conn;
	}	
	// 닫기
	public static void close(Connection conn,PreparedStatement pstmt) {		
		try {
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection conn,PreparedStatement pstmt,ResultSet rs) {		
		try {
			if(rs!=null)rs.close();
			if(pstmt!=null)pstmt.close();
			if(conn!=null)conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
