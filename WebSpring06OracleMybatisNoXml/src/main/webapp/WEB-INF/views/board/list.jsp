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
<style>
	ul, li{
		margin: 0;
		padding: 0;
		list-style-type: none;
	}
	li{
		float: left; width:10%; height: 40px; line-height:40px; border-bottom:1px solid lightblue;
	}
	
	li:nth-child(5n+2){
		width: 60%;
	}
	.wordcut{
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
</style>
<script>
	$(function(){
		$("#searchFrm").submit(function(){
			
			if($("#searchWord").val()==''){
				alert("검색어를 입력해주세요.")
				return false;
			}
			var url = "/myapp/list";
			var params = "searchKey="+$("#searchKey").val()+"&searchWord="+$("#searchWord").val();
			
			$.ajax({
				url : url,
				data : params,
				success : function(result){
						
				}, error : function(){
					
				}
			});
		});
		
		//전체 선택시 모든 체크박스 체크
		$("#listAllCheck").click(function(){
			$("#boardList input[type=checkbox]").prop('checked', $(this).prop('checked'));
		});
		//선택삭제가 클릭되면
		$("#delSelect").click(()=>{
			$("#delList").submit();
		});
	});
</script>
</head>
<body>
	<div class="container">
		<h1>Oracle Mybatis noXml 리스트</h1>
		<c:if test="${logStatus=='Y'}">
			<a href="write">글쓰기</a>
		</c:if>
		<br>
		<form id="searchFrm" method="post">
			<select id="searchKey" name="searchKey">
				<option value="subject">제목</option>
				<option value="userid">작성자</option>
				<option value="content">글내용</option>
			</select>
			<input type="text" id="searchWord" name="searchWord">
			<button>검색</button>
		</form>
		
			<input type="checkbox" id="listAllCheck">전체선택
			<input type="button" value="선택삭제" id="delSelect">
		
		<ul id="boardList">
			<li>번호</li>
			<li>제목</li>
			<li>글쓴이</li>
			<li>조회수</li>
			<li>작성일</li>

			<form method="post" id="delList" action="multiDel">	
				<c:forEach var="vo" items="${list}">
					<li>
						<c:if test="${logId==vo.userid}">
							<input type="checkbox" name="noList" value="${vo.no}">
						</c:if>			
						${vo.no}</li>
					<li class="wordcut"><a href="view?no=${vo.no}">${vo.subject}</a></li>
					<li>${vo.userid}</li>
					<li>${vo.hit}</li>
					<li>${vo.writedate}</li>
				</c:forEach>
			</form>
		</ul>
	</div>
</body>
</html>