package com.bitcamp.home.board;

import java.util.List;

public interface BoardReplyDAOImpl {

   //댓글 쓰기
   public int replyInsert(BoardReplyVO vo);
   //댓글 목록 불러오기
   public List<BoardReplyVO> replyList(int no);
   //댓글 삭제
   public int replyDel(int no, String userid);
   //댓글 수정
   public int replyUpdate(BoardReplyVO vo);
   
}