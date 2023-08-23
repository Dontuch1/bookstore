package com.ezen.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ezen.member.MemberDAO;
import com.ezen.member.MemberVO;

public class MemberService {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private final String path="/WEB-INF/views/member/";
	
	public MemberService(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;
		this.response=response;
	}

	public String exec() {
		String cmd=request.getParameter("cmd");
		String view=null;
		if(cmd.equals("login")) {
			return LoginService();
		} else if(cmd.equals("logout")){
			HttpSession s=request.getSession(); //세션얻기
			s.invalidate(); //세션 지우기
			return "book?cmd=list"; //페이지이동-List
		}
		return view;
	}

	private String LoginService() {
		String method=request.getMethod().toUpperCase(); // Get,get이든 >> GET으로 바꾸는 함수 사용(toUpperCase())
		if(method.equals("GET")) { // GET방식
			/* RequestDispatcher rd=request.getRequestDispatcher("/member/login.jsp");
				rd.forward(request, response); servlet에 이런 방식으로 보낸 걸 밑의 방법으로 간단하게 보낸다. */
			return path+"login.jsp";
		} else { // POST방식
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
				return "book?cmd=list";
			}else {//로그인 실패	
				request.setAttribute("message", "로그인 실패!");
				return path+"login.jsp";
			}
		}
	}
}
