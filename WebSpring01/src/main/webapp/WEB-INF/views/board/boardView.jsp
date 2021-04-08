<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function delCheck(recordNo){
		if(confirm("삭제할까요?")){
			location.href = "/home/boardDel?no="+recordNo;
		}
	}
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
	<!-- 댓글부분 -->
	<div>
		댓글 : <br> 
		<textarea id="comment" style="width:500px; height:100px" maxlength="100"></textarea><br>
		<button id="commentGo">댓글달기</button>
		<ul>
			<c:forEach var="c" items="${comment}">
				<li>작성자 : ${c.useridC}</li>
				<li>등록일 : ${c.writedateC}</li>
				<li style="border-bottom:1px solid gray">${c.cntentC}</li>
			</c:forEach>
		</ul>
	</div>
	<script>
		//댓글이 달리는 글번호는 vo에 있고, 댓글 작성하는 사용자 아이디는 세션에 있다. 그럼 댓글 내용만 보내면된다?
		$(function(){
			$("#commentGo").click(function(){
				var params = "contentC="+$('#comment').val();
				var url = "/home/ajaxComment?no=${vo.no}";
				$.ajax({
					url : url,
					data : params,
					success : function(result){
						$.ajax({
							url : "/home/boardView?no=${vo.no}",
							success : function(){
								console.log("ㅊㅋㅊ")
							},error : function(){
								console.log("2실패")
							}
						});
					}, error : function(){
						console.log("failed......")
					}
				});
			});
		});
	</script>
</div>
</body>
</html>