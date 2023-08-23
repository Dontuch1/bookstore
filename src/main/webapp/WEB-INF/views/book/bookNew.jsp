<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../include/header.jsp" %>    

<style>
	.btn{
		display:flex;
		justify-content: center;
	}
</style>
<main>

	<h2>도서 등록</h2>
	<!--===  도서 등록  ===-->
	<form action="book?cmd=new" method="post" id="uploadForm" enctype="multipart/form-data" >
		<table class="table table-sm table-bordered">
			<tr>
				<th>도서제목</th>
				<td><input type="text" size="120" maxlength="50" name="title" id="title" placeholder="도서명입력" required></td>
			</tr>
	        <tr>
				<th>저자</th>
				<td><input type="text" size="120"  maxlength="30" name="writer" id="writer" placeholder="저자명입력"></td>
			</tr>
	        <tr>
				<th>출판사</th>
				<td><input type="text" size="120"  maxlength="30" name="publisher" id="publisher" placeholder="출판사명입력"></td>
			</tr>
	        <tr>
				<th>가격</th>
				<td><input type="text" size="120"  maxlength="7" name="price" id="price" 
				  onkeydown="inputNum(this)" placeholder="가격입력" required></td>
			</tr>
	        <tr>
				<th>도서내용</th>
				<td><textarea name="content" id="content" cols="119" rows="10" maxlength="1000"></textarea></td>
			</tr>
			<tr>
				<th>도서 이미지</th>
				<td>
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
		</table>
		
		<!--===  도서 등록 버튼  ===-->
		<c:if test="${sessionScope.mvo.grade=='a'}">
			<div class="btn">
				<button type="submit" class="btn btn-success">도서등록</button> &nbsp; 
			    <button type="reset" class="btn btn-secondary">다시입력</button>
		    </div>
	    </c:if>
	</form>
</main>
<%@ include file="../include/footer.jsp" %>