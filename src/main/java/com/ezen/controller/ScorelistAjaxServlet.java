package com.ezen.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezen.book.BookscoreDAO;
import com.ezen.book.BookscoreVO;
import com.ezen.utill.PageVO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class ScorelistServlet
 */
@WebServlet("/scoreAjaxlist")
public class ScorelistAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=request.getRequestDispatcher("score/score_view_ajax.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 지금까지의 ajax는 받고 다시 돌려주지 않았지만 지금은 메서드처럼 다시 돌려줘야 한다.
		// encoding : 2가지
		request.setCharacterEncoding("utf-8");
		
		System.out.println("scoreAjaxlist post메서드");
		// 파라메타 값 받기
		int bno=47;
		String strbno=request.getParameter("bno").trim();
		if(strbno!=null && strbno!="") {
			bno=Integer.parseInt(strbno);
		}
		String strpage=request.getParameter("page").trim();
		int page=1;
		if(strpage!=null || strpage!="") {
			page=Integer.parseInt(request.getParameter("page"));
		}
		//페이지
		BookscoreDAO dao=BookscoreDAO.getInstanse();
		//특정도서 (bno값) 전체 개수 세기
		int rowCnt=dao.getRowCount(bno);
		
		int displayRow=5;
		PageVO pvo=new PageVO(page, rowCnt, displayRow,  0);
		boolean next=pvo.nextPageScore();
		System.out.println("next="+next);
		
		//해당 페이지 검색
		List<BookscoreVO> list=dao.getBookscore(page,bno,displayRow);
		
		// json으로 데이터를 조립 후 호출한 곳으로 다시 보내서 출력한다.
		if(list!=null) {
			
		
		JsonObject jobj=new JsonObject();
		jobj.addProperty("next", next); // 더보기 버튼 활성화를 결정 짓는 property
		// json 객체 만들기
		JsonObject data=null;
		// list를 json array로 만들기
		JsonArray Jlist=new JsonArray();
		for(BookscoreVO vo:list) {
			data=new JsonObject(); 
			data.addProperty("score", vo.getScore());
			data.addProperty("id", vo.getId());
			data.addProperty("cmt", vo.getCmt());
			Jlist.add(data);
		}
		jobj.add("arr", Jlist);
		Gson gson=new Gson();
		System.out.println("gson.toJson(jobj)"+gson.toJson(jobj));
		// json데이터 조립하는 방식은 gson라이브러리 사용.		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.print(jobj);
		out.flush();
		out.close();
	} else {
		
	}

		
	}

}
