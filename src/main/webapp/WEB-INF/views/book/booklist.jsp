<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>
<style>
	#pagediv,#searchdiv{
		display:flex;
		justify-content: center;
	}
	#top-button{
		display:flex;
		justify-content: right;
		padding:10px;
	}
</style>
<main>
	<h2>도서 목록</h2>
	<div id="top-button">
		<c:if test="${sessionScope.mvo.grade=='a'}">
			<a href="bNew"><button type="button" class="btn btn-primary">도서등록</button></a>
		</c:if>
	</div>
	<table class="table table-sm table-bordered">
		<tr>
			<th>순서</th>
			<th>도서명</th>
			<th>저자</th>
			<th>출판사</th>
			<th>가격</th>
			<th>등록일자</th>
		</tr>
		<c:if test="${list==null}">
			<tr>
				<td colspan="6">검색된 도서가 없습니다.</td>
			</tr>
		</c:if>
		<c:forEach items="${list}" var="book" varStatus="sts">
			<tr>
				<td>${sts.count}</td>
				<td><a href="bView?bno=${book.bno}&page=${pVo.page}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}">${book.title}</a></td>
				<td>${book.writer}</td>
				<td>${book.publisher}</td>
				<td><fmt:formatNumber value="${book.price}" type="currency" currencySymbol="\\"> </fmt:formatNumber>  </td>
				<td>${book.regdate}</td>
			</tr>	
		</c:forEach>
	 </table>
	 <div id="pagediv">
		 <!-- 페이지 -->	 
	     <nav aria-label="Standard pagination example">
	          <ul class="pagination">
		         <c:if test="${pVo.prev}">
		            <li class="page-item">
		              <a class="page-link" href="bList?page=${pVo.beginPage-1}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}" aria-label="Previous">
		                <span aria-hidden="true">&laquo;</span>
		              </a>
		            </li>   
	            </c:if>        
	           <c:forEach begin="${pVo.beginPage}" end="${pVo.endPage}" var="i">
			 		<c:choose>
			 			<c:when test="${i!=pVo.page}"><li class="page-item"><a class="page-link" href="bList?page=${i}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}">${i}</a></li></c:when>
			 			<c:otherwise> <li class="page-item"><a class="page-link" style="font-weigth:bold;color:black">${i}</a></li></c:otherwise>	 		
			 		</c:choose>		 			 	
		 		</c:forEach> 
		 		<c:if test="${pVo.next}">
		           <li class="page-item">
		              <a class="page-link" href="bList?page=${pVo.endPage+1}&searchword=${pVo.searchword}&searchtype=${pVo.searchtype}" aria-label="Next">
		                <span aria-hidden="true">&raquo;</span>
		              </a>
		            </li>
	            </c:if>
	          </ul>
	    </nav><!-- paging end -->
    </div>
        <!--  검색 -->
    <div id="searchdiv">	 	
		<form action="bList" method="get">
	        <select name="searchtype" id="searchtype">
	            <option value="title" checked>도서명</option>
	            <option value="writer">저자명</option>
	            <option value="publisher">출판사</option>
	        </select>
	        <input type="text" size="20" name="searchword" id="searchword" >
	        <button onclick="return searchFun()">검 색</button> &nbsp;	        
	    </form>		
	    <!-- <a href="bList"><button>전체도서검색</button></a> -->
 	</div>		
 </main>
<script type="text/javascript">
	function searchFun() {
		//id searchword에 값을 가져온다
		let searchword=document.querySelector("#searchword");
		//값 trim 수행 빈것이면 alert 포커스 searchword return false
		if(searchword.value.trim()===""){
			//alert("검색어를 입력하세요.");
			searchword.value="";
			//searchword.focus();
			//return false;
			
		}
		//검색어가 있으면 return true
		return true;
	}
</script>
<%@ include file="../include/footer.jsp" %>