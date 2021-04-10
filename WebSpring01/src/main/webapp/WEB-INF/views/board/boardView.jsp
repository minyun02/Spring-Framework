<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function delCheck(recordNo){
		if(confirm("삭제할까요?")){
			location.href = "/home/boardDel?no="+recordNo;
		}
	}
	//댓글 등록하기
	$(function(){
		//commentBtn이 눌리면 이벤트
		$("#commentBtn").click(function(){
			var url = "/home/commentWriteOk"
			var params = $("#commentFrm").serialize();
			
			$.ajax({
				url : url,
				data : params,
				success : function(result){
					console.log(result)
					$("#comment").val("");
				},error : function(){
					console.log("댓글등록 실패...");
				}
			});
		});
	});
</script>
<div class="container">
	<h1>글내용보기</h1>
	<ul>
		<li>글번호 : ${vo.no}</li>
		<li>작성자 : ${vo.userid}</li>
		<li>등록일 : ${vo.writedate}, 조회수 : ${vo.hit}</li>
		<li>제목 : ${vo.subject}</li>
		<li>${vo.content}</li>
	</ul>
	<div>
		<c:if test="${logId==vo.userid}"> 
			<a href="/home/boardEdit?no=${vo.no}">수정</a>
			<a href="javascript:delCheck(${vo.no})">삭제</a>
		</c:if>	
	</div>
	<!-- 댓들 입력 창 -->
	<div>
		<!-- 로그인을 해야지 보인다. -->
		<c:if test="${logStatus=='Y'}">
			<form method="post" id="commentFrm"> <!-- action not needed when using ajax -->
				댓글내용 : <br>
				<!-- 댓글의 원글 번호도 받아가야한다 -->
				<input type="hidden" name="no" value="${vo.no}">				
				<textarea name="content" id="comment" style="width:500px; height:100px;"></textarea><br>
				<input type="button" value="댓글달기" id="commentBtn">
			</form>
		</c:if>
	</div>
</div>
</body>
</html>