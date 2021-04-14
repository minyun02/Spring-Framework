<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
   <meta charset="UTF-8">
   <title>Insert title here</title>
   <meta name="viewport" content="width=device-width, initial-scale=1"/>
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
   <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

</head>
<script>
   function boardDel(){
      if(confirm("삭제하시겠습니까?????")){
         location.href="boardDel?no=${vo.no}";
      }
   }
    $(function(){  
    	//ajax 요청 등록하기
    	$(document).ajaxSend(function(event, request, settings){
    		request.setRequestHeader("AJAX","true");
    	});
    	
    	//ajax 요청 에러받기
    	$(document).ajaxError(function(event, request, settings, thrownError){
    		if(request.status == 1000 || request.status == 0){
    			location.href= "<%=request.getContextPath()%>/loginForm";
    		}else{
    			alert("AJAX요청 에러받기 실패~")
    		}
    	});
    	    	
       function replyList(){//1.댓글 리스트
       var url = "/home/replyList";
       var params = "no=${vo.no}" 
       
       $.ajax({
          url:url,
          data:params,
          success:function(result){
             
             var $result = $(result);
             var tag ="<ul>";
             
             $result.each(function(idx, obj){
                  tag+= "<li id=liStyle'><div>"+obj.userid+"("+obj.replydate+")";
                  
                  if(obj.userid =="${logId}"){
                     tag+= "<input type='button' value='수정' />";
                     tag+= "<input type='button' value='삭제' title='"+obj.num+"' />";   
                  }
                  
                  tag+= "<br/>"+ obj.content+"</div>";
                  
                  //수정폼 보여주기
                  if(obj.userid=="${logId}"){
                     tag += "<div style='display:none;'>";
                     tag += "<form method ='post'>";
                     tag += "<textarea name='content'style='width:500px;height:80px;'>"+obj.content+"</textarea>";
                     tag += "<input type='submit' value='수정하기'/>" ;
                     tag += "<input type='hidden' name='num' value='"+obj.num+"'/>";
                     tag+="</form></div>";
                  }
                  
                  
                  tag+= "</li>";
               });
               tag+= "</ul>";
               $("#replyList").html(tag);
               
            },error:function(){
               console.log('댓글 가져오기 에러..');
            }
         })
         
      }
    
         
        //댓글쓰기
         $("#replyBtn").click(function(){
            if($("#content").val()!=''){
               var url = "/home/replyWriteOk";
               var params = $("#replyFrm").serialize();// no=181&content=내용
               
               $.ajax({
                  url : url,
                  data : params,
                  success : function(result){
                     replyList();
                     $("#content").val("");
                     console.log('댓글등록성공' + result);   
                  },error:function(e){
                     console.log("댓글등록 실패....")
                  }
               });
            }else{
               alert("댓글을 입력후 저장하세요."); 
            }
         });
        
        //댓글 삭제
        $(document).on('click','#replyList input[value=삭제]',function(){
           var url = "/home/replyDel";
           var params = "num="+$(this).attr("title");
           
           $.ajax({
              url: url,
              data : params,
              success:function(result){
                 replyList();
              },error : function(e){
                 console.log("댓글 삭제 실패");
              }
              
           });
        })
        
        //댓글 수정
        $(document).on('submit','#replyList form',function(){
           var url="/home/replyEdit";
           var params = $(this).serialize();
           
           $.ajax({
              url:url,
              data: params,
              type: "post",
              success:function(reslut){
                   replyList();
              },error:function(e){
                 console.log("댓글수정실패");
              }
           })
           
        })
        
        
         replyList(); //글내용보기 클릭시 실행
         
        $(document).on('click','#replyList input[value=수정]',function(){
            
            $("#replyList>ul>li>div:nth-child(1)").css("display", "block"); 
             $("#replyList>ul>li>div:nth-child(2)").css("display", "none"); 
            
             $(this).parent().css("display", "none");
            $(this).parent().next().css("display", "block");
         });
      });
</script>
<body>
   <div class="container">
      <h1>글 내용보기</h1>
      번호: ${vo.no } <br/>
      작성자: ${vo.userid }<br/>
      작성일 : ${vo.writedate } , 조회수 ${vo.hit }<br/>
      제목 : ${vo.subject } <br/>
      글내용 ${vo.content }<br/>
      
      <c:if test="${logId==vo.userid }">
         <a href="boardEdit?no=${vo.no }">수정</a>
         <a href="javascript:boardDel()">삭제</a>
      </c:if>
      
      <div>
         <h3>댓글쓰기</h3>
         <form method="post" id ="replyFrm">
            <input type="hidden" name="no" value="${vo.no}"/>
            <textarea name="content" id ="content" style="width:50%;"></textarea><br>
    		<input type="submit" value="댓글 쓰기" id="replyBtn"/>
         </form>
      </div>
      
      <div id="replyList"></div>
   </div>
   
</body>
</html>