package com.ezen.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ezen.book.BookDAO;
import com.ezen.book.BookVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

/**
 * Servlet implementation class BookInsertServlet
 */
@WebServlet("/bNew")
public class BookInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 데이터일 입력받는 jsp 페이지 이동
		RequestDispatcher rd=request.getRequestDispatcher("/book/bookNew.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//encoding request, response
		request.setCharacterEncoding("utf-8");
		//업로드 폴더 설정
		String uploadPath="d:/upload/img";
		File path=new File(uploadPath);
		if(!path.exists()) {//폴더가 없으면 폴더 만들기
			path.mkdirs();
		}
		// MultipartRequest 객체 생성하면 파일업로드 완료
		MultipartRequest mr=new MultipartRequest(request, uploadPath
				,2*1024*1024, "utf-8", new DefaultFileRenamePolicy());
		if(mr!=null) {
			//넘어온 데이터 받기
			String title=mr.getParameter("title").trim();
			String writer=mr.getParameter("writer").trim();
			String publisher=mr.getParameter("publisher").trim();
			String content=mr.getParameter("content").trim();
			int price=Integer.parseInt(mr.getParameter("price"));
			//System.out.println(title);
			//VO 객체에 넘어온 데이터 저장
			BookVO vo=new BookVO(title, writer, price, publisher, content
					,mr.getOriginalFileName("file")
					,mr.getFilesystemName("file"),uploadPath);
			//dao 객체 생성
			BookDAO dao=BookDAO.getInstance();
			//dao 객체 insertBook 메소드 수행
			int result=dao.insertBook(vo);
			//페이지이동 리스트 
			if(result==1) {//삽입완료 - 전체목록 이동 
				RequestDispatcher rd=request.getRequestDispatcher("bList");
				rd.forward(request, response);
			}else {//삽입실패
				
			}
		}

	}

}
