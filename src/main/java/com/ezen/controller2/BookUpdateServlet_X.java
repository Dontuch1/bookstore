package com.ezen.controller2;

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
 * Servlet implementation class BookUpdateServlet
 */
@WebServlet("/bEdit")
public class BookUpdateServlet_X extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookUpdateServlet_X() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String upload="d:/upload/img";
		//mr 객체만 생성하면 mr안에 모든 넘어온 파라메타가 다 저정되고, 업로드 파일이 있으면 파일 저장폴더에 저장 된다
		MultipartRequest mr=new MultipartRequest(request, upload, 2*1024*1024
				, "utf-8", new DefaultFileRenamePolicy());
		// 넘어오는 데이터를 받아서 수정작업
		// MultipartRequest  사용 
		// 첨부파일 있는지 없는지 확인?? 있다면 수정, 없다면 수정 x
		if(mr!=null) {
			//System.out.println("mr.getOriginalFileName="+mr.getOriginalFileName("file"));
			//System.out.println("mr.getFilesystemName="+mr.getFilesystemName("file"));
			// BookVO 넘어온 데이터를 담고 수정작업
			//넘어온 데이터 받기
			//bno 받기
			int bno=Integer.parseInt(mr.getParameter("bno"));
			String title=mr.getParameter("title").trim();
			String writer=mr.getParameter("writer").trim();
			String publisher=mr.getParameter("publisher").trim();
			String content=mr.getParameter("content").trim();
			int price=Integer.parseInt(mr.getParameter("price"));
			String oFileName=mr.getOriginalFileName("file");
			if(oFileName==null)upload=null;
			BookVO vo=new BookVO(bno,title, writer, price, publisher
					, content,oFileName
					,mr.getFilesystemName("file"),upload);
			//dao 객체 만들기
			BookDAO dao=BookDAO.getInstance();			
			//dao 수정 메소드 호출 
			int result=dao.updateBook(vo);
			if(result==1) {//업데이트 완료		
				//상세화면 bookView.jsp 여기로 보내주는 서블릿 /bView?bno
				RequestDispatcher rd=request.getRequestDispatcher("bView?bno="+bno);
				rd.forward(request, response);
			}
		}
	}

}
