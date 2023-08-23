package com.ezen.controller;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezen.book.BookDAO;
import com.ezen.book.BookVO;
import com.ezen.utill.PageVO;

public class BookService {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private final String path="/WEB-INF/views/book/";
	
	public BookService(HttpServletRequest request, HttpServletResponse response) {
		this.request=request;
		this.response=response;
	}

	public String exec() {
		String cmd=request.getParameter("cmd");
		String view=null;
		if(cmd==null ||cmd.equals("list")) {
			return BookListService();
		}
		
		return null;
	}

	private String BookListService() {
		BookDAO dao=BookDAO.getInstance();
		//List<BookVO> list=dao.getBookList();// 전체 행을 출력	
		//페이징을 위한 변수 선언
		
		String parmaPage=request.getParameter("page"); //parmaPage : 파라메타(?뒤에 ?page=${i})---현재 페이지 설정
		int page=0;
		if(parmaPage==null)page=1;
		else page=Integer.parseInt(parmaPage);
		
		int displayRow=5;
		int displayPage=5;
		int rowCnt=0;
		List<BookVO> list=null;
		
		//검색어 관련 파라메타 받아서 저장
		String searchtype=request.getParameter("searchtype");
		String searchword=request.getParameter("searchword");;
		System.out.println("searchtype="+searchtype);
		if(searchword==null || searchword.equals("")) {
			list=dao.getBookList(page,displayRow);
			rowCnt=dao.getRowConut();//booktbl 전체 행의 개수
		}else {//검색어 있음
			//searchtype=
			list=dao.getBookList(page,displayRow,searchtype,searchword);
			rowCnt=dao.getRowConut(searchtype,searchword);//booktbl 해당검색어가 있는 전체 행의 개수
		}
		
		// PageVO 객체 생성
		PageVO pVo=new PageVO(page,rowCnt,displayRow,displayPage);
		pVo.setSearchword(searchword);//검색어를 PageVO 객체에 저장
		pVo.setSearchtype(searchtype);
		//pVo.setPage(page);//page설정
		//pVo.setPage(6);
		//pVo.setTotalCount(rowCnt);
		//브라우저에서 접근가능한 객체에 list 저장
		request.setAttribute("list", list);
		request.setAttribute("pVo", pVo);
		

		return path+"booklist.jsp"; //페이지이동 webapp/WEB-INF/views/book/booklist.jsp
	}
}
