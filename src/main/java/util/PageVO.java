package util;
public class PageVO {
   //page 나누기 정보를 담을 객체
    private int page; //현재 페이지 (get)
    private int totalCount; //row 전체의 수 (get)
    private int displayRow;  //한 페이지에 몇 개의 로우 (선택 set)
    private int displayPage;  //한 페이지에 몇 개의 페이지 (선택 set)
    
    // 검색어 저장
    private String searchword;
    private String searchtype;
    
    //계산에 의해서 값을 설정. paging();
    private int beginPage;  //출력 시작
	private int endPage;    //출력 끝
    boolean prev; //prev 버튼이 보일건지 안보일건지
    boolean next; //next 버튼이 보일건지 안보일건지
    
    public PageVO(int page, int totalCount, int displayRow, int displayPage) {
		super();
		this.page = page;
		this.totalCount = totalCount;
		this.displayRow = displayRow;
		this.displayPage = displayPage;
		paging(); // 생성자에 있는 4개의 변수를 설정해준 값. 
	}
    
    public String getSearchtype() {
  		return searchtype;
  	}
  	public void setSearchtype(String searchtype) {
  		this.searchtype = searchtype;
  	}
	public String getSearchword() {
		return searchword;
	}
	public void setSearchword(String searchword) {
		this.searchword = searchword;
	}
	public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotalCount() {
        return totalCount;
    }
    public void setTotalCount(int totalCount) {
        //setTotalCount()를 꼭 호출해야 paging이 되기 때문에
        //paging()함수를 setTotalCount()를 호출했을 때 자동으로 호출되게 한다.
        this.totalCount = totalCount;
        paging();
    }
    public int getDisplayRow() {
        return displayRow;
    }
    public void setDisplayRow(int displayRow) {
        this.displayRow = displayRow;
    }
    public int getDisplayPage() {
        return displayPage;
    }
    public void setDisplayPage(int displayPage) {
        this.displayPage = displayPage;
    }
    public int getBeginPage() {
        return beginPage;
    }
    public int getEndPage() {
        return endPage;
    }
    public boolean isPrev() {
        return prev;
    }
    public boolean isNext() {
        return next;
    }
    private void paging(){
        // prev, next, beginPage, endPage를 계산해서 만든다.
        // 2+9 = 11, 11/10 = 1, 1*10 = 10
        // 10+9 = 19, 19/10 = 1, 1*10 = 10
        // 11+9 = 20, 20/10 = 2, 2*10 = 20
        // 20+9 = 29, 29/10 = 2, 2*10 = 20
        // 111+9 = 120 120/10 = 12, 12*10 = 120
        
        // (2+9)/10 * 10 (1번 방법)
        //endPage = ((page+(displayPage-1))/displayPage)*displayPage;
        
        // 1/10 0.1(올림) 1 (2번 방법)
    	
    	//-----------예시
    	// page(1), totalCount(39), displayRow(10), displayPage(5)
        endPage = ((int)Math.ceil(page/(double)displayPage))*displayPage;
        System.out.println("endPage : " + endPage);
        
        beginPage = endPage - (displayPage - 1);
        System.out.println("beginPage : " + beginPage);
        
        // 글 32개
        // 32/10 = 3.2 (올림) 4페이지
        // 2=?  2/10
        int totalPage = (int)Math.ceil(totalCount/(double)displayRow);
        
        if(totalPage<endPage){
            endPage = totalPage;
            next = false;
        }else{
            next = true;
        }
        prev = (beginPage==1)?false:true;//page가 11이상에만 나온다.
        System.out.println("endPage : " + endPage);
        System.out.println("totalPage : " + totalPage);       
    }
    public boolean nextPageScore() {
    	boolean next=false;
    	int totalPage = (int)Math.ceil(totalCount/(double)displayRow);//4
        
        if(totalPage<page+1){
            next = false;//다음페이지 보이지않도록
        }else{
            next = true;
        }
    	return next;
    }
}
