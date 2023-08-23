package com.ezen.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.member.MemberDAO;
import com.ezen.member.MemberVO;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//페이지 이동
		RequestDispatcher rd=request.getRequestDispatcher("/member/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//encoding x
		//파라메타 받기
		String id=request.getParameter("userid");
		String pwd=request.getParameter("pwd");
		//dao 객체생성
		MemberDAO dao=MemberDAO.getInstance();
		//메소드 수행 결과 받기 
		MemberVO mvo=dao.login(id, pwd);
		if(mvo!=null) {//로그인 성공
			//세션 저장->request를 통해서 세션객체 얻기
			HttpSession session=request.getSession();
			session.setAttribute("mvo", mvo);
			//페이지에서 필요로 하는 정보를 저장 ??
			//페이지이동 : list 쪽으로 이동
			RequestDispatcher rd=request.getRequestDispatcher("bList");
			rd.forward(request, response);
		}else {//로그인 실패
			request.setAttribute("message", "로그인 실패!");
			RequestDispatcher rd=request.getRequestDispatcher("/member/login.jsp");
			rd.forward(request, response);
		}
		
	}

}
