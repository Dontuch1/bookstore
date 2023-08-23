package com.ezen.controller2;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezen.book.BookDAO;
import com.ezen.book.BookVO;
import com.ezen.book.BookscoreDAO;

/**
 * Servlet implementation class BookViewServlet
 */
@WebServlet("/bView")
public class BookViewServlet_X extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookViewServlet_X() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//encoding x
		//파라메타 받기  bno
		int bno=Integer.parseInt(request.getParameter("bno"));
		//페이지 관련 정보 받아서
		String strpage=request.getParameter("page");
		int page=1;
		if(strpage!=null)page=Integer.parseInt(strpage);
		String searchword=request.getParameter("searchword");
		String searchtype=request.getParameter("searchtype");
		//System.out.println("bView");
		//dao 객체 생성
		BookDAO dao=BookDAO.getInstance();
		//dao 메소드 호출 결과 받기 : BookVO (모든 필드 다 담겨있어야 한다)
		BookVO vo=dao.getBook(bno);
		//페이지 이동전 jsp 필요한데이터를 setAttribute로 담는다
		if(vo!=null) {//조회 완료
			//web-star : 현 도서의 평균평점을 조회해서 값을 저장해서 보내야 한다.
			BookscoreDAO sdao=BookscoreDAO.getInstanse();
			ArrayList<Number> score=sdao.getAvgScore(bno);
			System.out.println("score.get(0) :"+score.get(0));
			System.out.println("score.get(1) :"+score.get(1));
			request.setAttribute("avgScore1", score.get(0));
			request.setAttribute("avgCnt1", score.get(1));
			//web-star : end
			
			
			request.setAttribute("vo", vo);
			//page관련정보 담는다
			request.setAttribute("page", page);
			request.setAttribute("searchword", searchword);
			request.setAttribute("searchtype", searchtype);			
			RequestDispatcher rd=request.getRequestDispatcher("/book/bookView.jsp");
			//페이지 이동
			rd.forward(request, response);
		}else {
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
