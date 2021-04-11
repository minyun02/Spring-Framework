<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
	function delCheck(recordNo){
		if(confirm("삭제할까요?")){
			location.href = "/home/boardDel?no="+recordNo;
		}
	}
	$(function(){
		//1. 댓글 등록하기
		//commentBtn이 눌리면 이벤트
		$("#commentBtn").click(function(){
			var url = "/home/commentWriteOk"
			var params = $("#commentFrm").serialize();
			
			$.ajax({
				url : url,
				data : params,
				type : "POST",
				success : function(result){
					replyList();
					console.log(result)
					$("#comment").val("");
				},error : function(){
					console.log("댓글등록 실패...");
				}
			});
		}); //1end
		
		//2. 댓글 목록 불러오기
		function replyList(){
			var url = "/home/replyList";
			var params = "no=${vo.no}";
			
			$.ajax ({
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
					console.log("댓글 불러오기 실패...");
				}
			});
		}//2end
		
		//3.댓글 수정하기
		$(document).on('submit','#replyList form',function(){
			var url = "/home/replyEditOk";
			var params = $(this).serialize();
			console.log("????")
			$.ajax({
				url : url,
				data : params,
				type : "POST",
				success : function(result){
					replyList();
				},error : function(result){
					console.log("comment editing failed...")
				}
			});//ajax
		});//3end
		
		//4.댓글 삭제하기
		$(document).on('click','#replyList input[value=삭제]', function(){
			if(confirm("delete this comment?")){
				var url = "/home/replyDel";
				var params = "num="+$(this).attr("title");
				
				$.ajax({
					url : url,
					data : params,
					success : function(result){
						replyList();
					},error : function(){
						console.log("failed to delete a comment...")
					}
				});//ajax end
			}
		});//4end
		replyList();
		//수정 폼 보여주기
		$(document).on('click','#replyList input[value=수정]',function(){
			$("#replyList>ul>li>div:nth-child(1)").css("display","block");
			$("#replyList>ul>li>div:nth-child(2)").css("display","none");
			
			$(this).parent().css("display","none");
			$(this).parent().next().css("display","block");
		});//댓글 수정창 열기 end
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
	<!-- 댓글 목록 -->
	<div id="replyList">
	
	</div>
</div>
</body>
</html>