<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
	<h1>답변형게시판 글내용보기</h1>
	<ul>
		<li>번호 : ${dto.no}</li>
		<li>작성자 : ${dto.userid}</li>
		<li>등록일 : ${dto.writedate}, 조회수 : ${dto.hit}</li>
		<li>제목 : ${dto.subject}</li>
		<li>글내용<br>${dto.content}</li>
	</ul>
	<div>
		수정
		삭제
		<a href="claseWriteForm?no=${dto.no}">답글</a>
	</div>
</div>
</body>
</html>