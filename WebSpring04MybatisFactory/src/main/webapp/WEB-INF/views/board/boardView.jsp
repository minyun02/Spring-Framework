<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function boardDel(){
		if(confirm("삭제?")){
			location.href="boardDel?no=${vo.no}";
		}
	}
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글내용보기~</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" integrity="sha384-+YQ4JLhjyBLPDQt//I+STsc9iw4uQqACwlvpslubQzn4u2UU2UFM80nGisd026JF" crossorigin="anonymous"></script>
<script type="text/javascript">
	$(function(){
		//1댓글등록
		$("#replyWriteBtn").click(function(){
			var url = "/home/commentWriteOk";
			var params = $("#replyWriteFrm").serialize();
		
			$.ajax({
				url : url,
				data : params,
				type : "POST",
				success : function(result){
					replyList();
					console.log("댓글등록 성공..")
				}, error : function(){
					console.log("댓글 등록 실패...")
				}
			});//1 ajax end
		});//1end
		
		//2댓글 목록 불러오기
		function replyList(){
			var url = "/home/replyList";
			var params = "no=${vo.no}";
			
			$.ajax({
				url : url,
				data : params,
				success : function(result){
					var $result = $(result);
					
					var tag = "<ul>";
					$result.each(function(idx, obj){
						tag += "<li style='border-bottom: 1px sold #ddd'><div>"+obj.userid+"("+obj.writedate+")";
						
						if(obj.userid=='${logId}'){
							tag += "<input type='button' value='수정'>";
							tag += "<input type='button' value='삭제' title='"+obj.num+"'>";
						}
						tag += "<br>"+obj.content+"</div>";
						
						if(obj.userid=='${logId}'){
							//수정폼
							tag += "<div style='display:none;'>";
							tag += "<form method='post'>";
							tag += "<textarea name='content' style='width:500px;height:80px;'>"+obj.content+"</textarea>";
							tag += "<input type='submit' value='수정하기'>";
							tag += "<input type='hidden' name='num' value='"+obj.num+"'>";
							tag += "</form></div>"; 
						}
						
						tag += "</li>";
					});
					tag += "</ul>";
					$("#replyList").html(tag);
				},error : function(){
					console.log("댓글 불러오기 실패....");
				}
			});//ajax end
		}//2end
		//replyList();//boardView에 들어오면 댓글 목록 불러오
	});
</script>
</head>
<body>
<div class="container">
	<h1>글내용보기</h1>
	번호 : ${vo.no }<br>
	작성자 : ${vo.userid }<br>
	작성일 : ${vo.writedate}, 조회수 : ${vo.hit}<br>
	제목 : ${vo.subject}<br>
	글내용 : ${vo.content}<br>
	<c:if test="${logId==vo.userid}">
		<a href="boardEdit?no=${vo.no}">수정</a>
		<a href="javascript:boardDel()">삭제</a>
	</c:if>
</div>
<hr>
<!-- 댓글폼 -->
<div class="container">
	<c:if test="${logId!=null || logId!=''}">
		<form method="post" id="replyWriteFrm">
			<input type="hidden" name="no" value="${vo.no}">
			<textarea id="content" name="content" style="width:500px; height:100px;margin:0px"></textarea><br>
			<input type="button" value="댓글등록" id="replyWriteBtn">
		</form>
	</c:if>
</div>
<!-- 댓글 리스트 -->
<div class="container" id="replyList">
	<c:forEach var="v" items="${list}">
		1. ${v.no}
		2. ${vo.content}
	</c:forEach>
</div>
</body>
</html>