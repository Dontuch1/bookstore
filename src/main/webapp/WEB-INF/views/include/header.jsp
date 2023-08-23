<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="com.ezen.book.BookVO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>도서관리 시스템</title>
<link rel="shortcut icon" href="#"> <!-- favicon.ico 에러방지 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">

<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/jquery.validate.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-validation@1.19.0/dist/additional-methods.js"></script>
<script src="${pageContext.request.contextPath}/js/fileupload_validate.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery-3.6.3.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/common.js"></script>
</head>
<body>
<div class="btn_log">
	<c:choose>
		<c:when test="${sessionScope.mvo==null}">
			<button type="button" id="login" name="login" onclick="location.href='member?cmd=login'" class="btn btn-success" >로 그 인</button>
			<button type="button" id="logout" name="logout" onclick="" class="btn btn-warning" >회원가입</button>		
		</c:when>
		<c:otherwise>
			${sessionScope.mvo.name}님 
			<button type="button" id="logout" name="logout" onclick="location.href='member?cmd=logout'" class="btn btn-success" >로그아웃</button>
		</c:otherwise>
	</c:choose>	
</div>


