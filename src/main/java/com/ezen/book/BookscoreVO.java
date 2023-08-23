package com.ezen.book;

import java.sql.Date;

public class BookscoreVO {
	private int sno, bno;
	private String id;
	private double score;
	private String cmt;
	private Date regdate;
	
	
	public BookscoreVO() {
		super();
	}
	
	public BookscoreVO(int bno, String id, double score, String cmt) {
		super();
		this.bno = bno;
		this.id = id;
		this.score = score;
		this.cmt = cmt;
	}

	public BookscoreVO(int sno, int bno, String id, double score, String cmt, Date regdate) {
		super();
		this.sno = sno;
		this.bno = bno;
		this.id = id;
		this.score = score;
		this.cmt = cmt;
		this.regdate = regdate;
	}
	
			
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public int getBno() {
		return bno;
	}
	public void setBno(int bno) {
		this.bno = bno;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getCmt() {
		return cmt;
	}
	public void setCmt(String cmt) {
		this.cmt = cmt;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "BookscoreVO [sno=" + sno + ", bno=" + bno + ", id=" + id + ", score=" + score + ", cmt=" + cmt
				+ ", regdate=" + regdate + "]";
	}
	
	
	
}
