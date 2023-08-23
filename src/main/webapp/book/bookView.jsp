<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>  
<link rel="stylesheet" href="${pageContext.request.contextPath}/star-rating/css/star-rating.min.css" media="all" type="text/css"/>
<link rel="stylesheet" href="${pageContext.request.contextPath}/star-rating/themes/krajee-svg/theme.min.css" media="all" type="text/css"/>
<script src="${pageContext.request.contextPath}/star-rating/js/star-rating.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/star-rating/themes/krajee-svg/theme.min.js" type="text/javascript"></script>   
<style>
	.btn_rud{
		display:flex;
		gap:5px;
		justify-content: center;
	}
	th{
		width:100px;	
	}
	td{
		text-align: left;
	}
</style>

<main>
<h2>도서 상세</h2>
<form action="bEdit" method="post"  enctype="multipart/form-data" id="uploadForm" name="uploadForm" >
	<input type="hidden" value="${vo.bno}" name="bno" id="bno">
	<table class="table table-sm table-bordered">
		<tr>
			<th>도서제목</th>
			<td class="disp" >${vo.title}</td>
			<td class="edit" style="display:none;">
				<input type="text" size="120" maxlength="50" name="title" id="title" 
			    value="${vo.title}" placeholder="도서명입력" required>
			</td>
		</tr>
        <tr>
			<th>저자</th>
			<td  class="disp" >${vo.writer}</td>
			<td class="edit" style="display:none;">
				<input type="text" size="120"  maxlength="30" name="writer" 
				value="${vo.writer}" id="writer" placeholder="저자명입력">
			</td>
		</tr>
        <tr>
			<th>출판사</th>
			<td class="disp" >${vo.publisher}</td>
			<td class="edit" style="display:none;">
			 <input type="text" size="120"  maxlength="30" name="publisher" 
			 value="${vo.publisher}" id="publisher" placeholder="출판사명입력">
			</td>
		</tr>
        <tr>
			<th>가격</th>
			<td class="disp" ><fmt:formatNumber value="${vo.price}" type="currency" currencySymbol="\\"> </fmt:formatNumber></td>
			<td class="edit" style="display:none;">
				<input type="text" size="120"  maxlength="7" name="price" id="price" 
				  value="${vo.price}" onkeydown="inputNum(this)" placeholder="가격입력" required>
			</td>
		</tr>
        <tr>
			<th>도서내용</th>
			<td class="disp" >
			<% 
				BookVO vo=(BookVO)request.getAttribute("vo");
				if(vo!=null) {
					String content=vo.getContent();
					if(content!=null)
						out.write(content.replaceAll("\r\n", "<br>"));
				}
				
			%>
			</td>
			<td class="edit" style="display:none;">
				<textarea name="content" id="content" cols="119" rows="10" 
					maxlength="1000">${vo.content}</textarea>
			</td>
		</tr>
		<tr>
			<th>도서 이미지</th>
			<td class="disp" >
				<c:if test="${vo.savFilename!=null}">
					<img src="imgDown?upload=${vo.savePath}&saveFname=${vo.savFilename}&originFname=${vo.srcFilename}" alt="" height="300px">
				</c:if>				
			</td>
			<td class="edit" style="display:none;">
					<div>
						<c:if test="${vo.savFilename!=null}">
							기존 파일명 : ${vo.srcFilename}
						</c:if>
					</div><br>
					<div class="form-group row">
						<label for="file" class="col-sm-2 col-form-label">파일첨부</label>
						<div class="col-sm-10">
							<input type="file" name="file" id="file">
							<small class="text-muted">(파일크기 : 2MB / 이미지 파일만 가능)</small>
							<small id="file" class="text-info"></small>
						</div>
					</div>						
				</td>
		</tr>
		<!-- web-star -->
		<tr id="tbl_star2">
			<th>평균 별점</th>
			<td>  
			<input id="avgScore" name="avgScore" value="${avgScore1}" class="rating rating-loading" data-size="sm" readonly="readonly">
			&nbsp; 평가자 인원 수 : <span id="spanCnt">${avgCnt1}</span>
			</td>
		</tr>
		
	</table>
	
	<!-- 별점 저장 시작 -->
	<c:if test="${ sessionScope.mvo!=null }">
	<div>
		<table>
			<tr>
				<td width="300px">
					<input id="score" name="score" class="rating rating-loading" data-size="sm" data-min="0" data-max="5" data-step="0.5" required="required">
					
				 </td>
				 <td>
				 	 감상평 : <input type="text" maxlength="100" id="cmt" name="cmt" size="50" required="required" >
				 	<button type="button" onclick="saveStar()">저장하기</button>
				 	
				 </td>
			</tr>
		</table>	
	</div>
	</c:if>
	<!-- 별점 저장 끝 -->
	<br><br><br>
	<div class="btn_rud">
		<button type="button" id="btnList" onclick="location.href='bList?page=${page}&searchword=${searchword}&searchtype=${searchtype}'" class="btn btn-success" >도서목록</button>
		<c:if test="${sessionScope.mvo.grade=='a'}">
			<button type="button" id="btnEdit" onclick="bookEdit()" class="btn btn-warning" >도서수정</button>
	 		<button type="button" id="btnDelete" onclick="bookDelete()" class="btn btn-danger" >도서삭제</button> 
	 		<button type="submit" id="btnSave" class="btn btn-primary" style="display:none;">도서저장</button> 
	 		<button type="reset" id="btnCancle" onclick="bookCancle()" class="btn btn-info" style="display:none;">수정취소</button>
 		</c:if> 
    </div>
	</form>
	<br><input type="text" name="page" id="page" value="0">
	<table id="tbl_star">

	</table>
	<button type="button" id="btn_next" style="display:none" onclick="getStar()">더보기</button>
</main>


<script type="text/javascript">
/* ===========================================
ready function 호출
===========================================
$(document).ready(function() {
	 if(ratingStar.length){ //별점 댓글이 하나 이상 있으면 출력
		 ratingStar.removeClass(".rating-loading").addClass(".rating-loading").rating();
	 });
	 } */
 $(document).ready(function() {
	 $("page").val(0);
	 getStar();
	});
	 	 
 /* ===========================================
 	newStar() 호출
 =========================================== */	 
function newStar() {
	let ratingStar=$(".rating-loading");
	 if(ratingStar.length){ //별점 댓글이 하나 이상 있으면 출력
		 ratingStar.removeClass(".rating-loading").addClass(".rating-loading").rating();
	 }
	 $(".rating-container").ramoveClass("rating-md rating-sm");
	 $(".rating-container").addClass("rating-sm");
	}

/*===========================================
 *  댓글을 ajax로 가져오기 - doAjax(url, param, callback)
	- 보내는 데이터 : bno, page
	- 받아오는 데이터 : json(다음페이지 버튼 활성화 여부, 출력할 데이터)
  ===========================================*/
function getStar() { // Ajax에서 데이터를 가져오기. Ajax가 데이터를 비동기로 가져오는 것임.
	let url="scoreAjaxlist";
	let param={"bno":$("#bno").val(),
			   "page":$("#page").val()*1+1
		       };
	//증가한 페이지를 적용하기 위한 구문
	$("#page").val($("#page").val()*1+1);
	console.log(param);
	doAjax(url, param, getStarAfter);
}
 /*===========================================
  *  댓글을 ajax로 가져온 후 결과 값을 출력하는 함수
 	- 받아오는 데이터 : json를 table태그 내에 출력하는 일을 해줘야 한다.
   ===========================================*/
 function getStarAfter(data) { // getStar에서 받아온 데이터로 웹페이지에 출력하기.
	 console.log("getStarAfter");
	 if(data=="err" || data==null){
		 console.log("데이터가 없어서 출력하지 않습니다.");
	 } else {
		 console.log(data);
		 // data 배열에 있는 값을 tbl_star 에 html 태그로 조립해서 출력
		 let starList=data.arr;
		 console.log(starList);
		 let html="";
		 for(let vo of starList){ // js에서 쓰이는 foreach문 
			 html+='<tr>';
			 html+='<td>';
			 html+='<input id="score" name="score" value='+vo.score+' class="rating rating-loading" data-size="sm" readonly="readonly">';
			 html+='</td>';
			 html+='<td>'+vo.id+'님 : '+vo.cmt+'</td>';
			 html+='</tr>';
		 }
		 $("#tbl_star").append(html);
		 // let next=data.next;
		 // console.log(next);
		 if(data.next) {
			 $("#btn_next").css("display","block");
		 } else $("#btn_next").css("display","none");
		 //loading 중인 별점을 보여주는 작업
		let ratingStar=$(".rating-loading");
		 if(ratingStar.length){ //별점 댓글이 하나 이상 있으면 출력
			 ratingStar.removeClass(".rating-loading").addClass(".rating-loading").rating();
		 }
	 }
	
 	}

/*===========================================
 *  별점과 comment 저장하는 기능이 있는 함수
 	doAjaxHtml(url, param, callback) 을 호출
 	json 데이터 타입 :
 	{"bno":47,"id":"dontuch1","score":4.5,"cmt":"재밌었다."}
  ===========================================*/
function saveStar() {
	let cmt=$("#cmt").val().trim();
	if(cmt=="") {
		alert("빈 칸에 감상평을 적어주세요.")
		$("#cmt").focus();
	}
    let url="scoreAjaxsave";
	let param={"id":"${sessionScope.mvo.id}",
			   "bno":document.getElementById("bno").value,
			   "score":$("#score").val(), //jQuery사용
			   "cmt":cmt
			   };
	console.log(param);
	doAjaxHtml(url, param, saveStarAfter);
  }
/*===========================================
 *  별점과 comment 저장완료 후 수행 될 함수
 	callback에 의해서 호출
 
  ===========================================*/
  function saveStarAfter(data){
	// 페이지에 설정된 데이터를 초기화 시켜주는 명령어
	console.log("saveStarAfter");
	console.log(data);
	let retData=JSON.parse(data); // String -> json data변환
	//가져온 데이터(수정된 평균 값, 작성자 총 인원수) 설정
	/* $("#avgscore").val(data.avgscore);
	$("#cntscore").html(data.cntscore); */
	$("#avgScore").rating("destory");
	$("#avgScore").val(retData.avgScore1);
	$("#avgScore").rating("create"); //newStar 함수를 만듦으로서 create rating을 쓸 필요가 없다.
	
	let span_Cnt=document.getElementById("spanCnt");
	span_Cnt.innerHTML=retData.avgCnt1;
	// 별점을 등록하면 별점 목록을 다시 1페이지를 보여줘야 하기 때문에 함수를 바꾼다.
	$("#tbl_star").html("");
	$("#page").val(0);
	getStar(); // 새로운 정보를 받아서 웹페이지에 출력해주는 것.

	// 별점을 등록한 후 value가 초기화 되는 함수
	$("#score").rating("destory");
	$("#score").val(0);
	$("#score").rating("create");
	
	$("#cmt").val("");
	$("#cmt").focus();
	
	newStar();
}

/*===========================================
 도서수정버튼은 클릭했을때 호출되는 함수
===========================================*/
	//$(".disp") : 제이쿼리
	function bookEdit(){
		$(".disp").css("display","none");
		$(".edit").css("display","block");
		//버튼
		$("#btnEdit").css("display","none");
		$("#btnDelete").css("display","none");
		$("#btnSave").css("display","block");
		$("#btnCancle").css("display","block");
		//데이터 값 설정하기
		//document.getElementById("title").value="${vo.title}";
		//document.querySelector("#writer").value="${vo.writer}";
		//document.querySelector("#publisher").value="${vo.publisher}";
		//document.querySelector("#price").value="${vo.price}";
					
	}
	//도서 삭제
	function bookDelete(){
		if(confirm("도서삭제를 수행 하시겠습니까?")){
			location.href="bDelete?bno=${vo.bno}";
		}
	}
	//도서저장
	//function bookSave(){
		//document.querySelector("#uploadForm") 폼태그의 요소가져오기 
	//	document.querySelector("#uploadForm").submit();
	//}
	function bookCancle(){
		$(".disp").css("display","block");
		$(".edit").css("display","none");
		//버튼
		$("#btnEdit").css("display","block");
		$("#btnDelete").css("display","block");
		$("#btnSave").css("display","none");
		$("#btnCancle").css("display","none");
	}
	
</script>
<%@ include file="../include/footer.jsp" %>