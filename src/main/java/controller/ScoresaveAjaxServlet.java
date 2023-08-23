package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import book.BookscoreDAO;
import book.BookscoreVO;


@WebServlet("/scoreAjaxsave")
public class ScoresaveAjaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// encoding
		request.setCharacterEncoding("utf-8");
		// 파라메타 값 받기
		String id=request.getParameter("id");
		String strbno=request.getParameter("bno");
		int bno=39;
		if(strbno!=null && strbno!="")bno=Integer.parseInt(strbno);
		String strscore=request.getParameter("score").trim();
		double score=0;
		if(strscore!=null && strscore!="")score=Double.parseDouble(strscore);
		String cmt=request.getParameter("cmt");
		// dao 객체 생성
		BookscoreDAO dao=BookscoreDAO.getInstanse();
		// vo 객체 생성
		BookscoreVO vo=new BookscoreVO(bno,id,score,cmt);
		// dao 삽입 메서드 수행
		dao.insertBookscore(vo);

		// 비동기 통신이기 때문에 페이지 이동이 필요 없다.
		// ㄴ 메소드를 수행하면 불렀던 곳으로 돌아가서 callback 수행.
		// ㄴ bookview에서 "평균 평점"에 필요한 데이터를 보내줘야 한다. 	
		BookscoreDAO sdao=BookscoreDAO.getInstanse();
		ArrayList<Number> avgscore=sdao.getAvgScore(bno);
		System.out.println("score.get(0) :"+avgscore.get(0));
		System.out.println("score.get(1) :"+avgscore.get(1));
		// json data로 보내주기.
		JsonObject ob=new JsonObject();
		ob.addProperty("avgscore", avgscore.get(0));
		ob.addProperty("cntscore", avgscore.get(1));
		//Json 파일을 Json 언어로 호출했던 곳으로 되돌려준다.
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.print(ob);
		out.flush();
		out.close();
	}

}
