package com.ezen.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/book")
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get,post 방식 모두 service에서 받는다.
		request.setCharacterEncoding("utf-8"); // encoding
		response.setContentType("text/html;charse=utf-8"); // encoding
		// 서비스 객체 생성 : 수행 메소드 실행 
		// do get, post는 html 객체가 있기 때문에 response, request를 사용할 수 있었는데 사용 할 수 있도록 밑에 
		// service객체를 만들었다.
		String view=new BookService(request,response).exec(); // view는 이동할 jsp 페이지
		if(view!=null) request.getRequestDispatcher(view).forward(request, response);
	}

}
