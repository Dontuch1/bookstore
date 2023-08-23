package com.ezen.controller;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImgFileDownload
 */
@WebServlet("/imgDown")
public class ImgFileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImgFileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//encoding
		request.setCharacterEncoding("utf-8");
		// 파라메타값 받아오기
		String originFname=request.getParameter("originFname");//원본파일명
		String upload=request.getParameter("upload");//경로
		String saveFname=request.getParameter("saveFname");//저장된 파일명
		String filename=upload+"/"+saveFname;
		System.out.println("filename="+filename);
		//웹브라우저의 종류 확인
		String agent=request.getHeader("User-Agent");
		System.out.println(agent);
		// ie 7 또는 edge
		boolean ieBrowser=(agent.indexOf("Trident")>-1)||(agent.indexOf("Edge")>-1);
		if(ieBrowser) {
			originFname=URLEncoder.encode(originFname,"utf-8").replace("\\","%20");
		}else {// edge, 파이어폭스, 크롬
			originFname=new String(originFname.getBytes("utf-8"),"iso-8859-1");
		}
		response.setContentType("image/jpg");
		//다운로드 되는 파일명 설정
		response.setHeader("Content-Disposition", "attachment;filename="+originFname);
		FileInputStream in=new FileInputStream(filename);//파일 open
		//출력할 곳
		BufferedOutputStream out=new BufferedOutputStream(response.getOutputStream());
		int numRead;
		byte b[]=new byte[4096];//4K만큼
		while((numRead=in.read(b,0,b.length))!=-1) {
			out.write(b,0,numRead);
		}//end while
		out.flush();//버퍼에 남은것 출력
		in.close();
		out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
