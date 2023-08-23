package com.ezen.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/member")
public class MemberController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8"); // encoding
		response.setContentType("text/html;charse=utf-8"); // encoding
		// MemberService 객체 생성 -> exec()메서드 호출 -> 이동할 주소 반환
		String view= new MemberService(request,response).exec();
		if(view!=null)request.getRequestDispatcher(view).forward(request, response);
		// 
	}
       

}
