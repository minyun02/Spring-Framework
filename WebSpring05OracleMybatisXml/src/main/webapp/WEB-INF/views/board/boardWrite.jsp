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
<script src="https://cdn.ckeditor.com/4.16.0/standard/ckeditor.js"></script>
<script>
	$(function(){
		CKEDITOR.replace("content");
		
		$("#boardFrm").on('submit',function(){
			// document.getElementById("subject").value
			// document.boardFrm.subject.value
			// $("#subject").val()
			if($("#subject").val()==""){
				return false;
			}
			if(CKEDITOR.instances.content.getData()==''){
				return false;
			}
			return true;
		});
	});
</script>
<style>
	ul, li{margin: 0;padding: 0;list-style:none;}
	#subject{width:100%;}
</style>
</head>
<body>
<div class="container">
	<h1>글쓰기</h1>
	<form method="post" name="boardFrm" action="boardWriteOk" id="boardFrm">
		<ul>
			<li><input type="hidden" name="userid" value="${logVo.userid}">
			<li>제목 : <input type="text" name="subject" id="subject"></li>
			<li><textarea name="content" id="content"></textarea></li>
			<li><input type="submit" value="글등록">
				<input type="reset" value="다시쓰기"></li>
		</ul>
	</form>
</div>
</body>
</html>