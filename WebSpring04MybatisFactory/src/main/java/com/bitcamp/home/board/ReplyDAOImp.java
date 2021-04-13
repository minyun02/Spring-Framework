package com.bitcamp.home.board;

import java.util.List;

public interface ReplyDAOImp {
	//댓글 쓰기
	public int replyInsert(ReplyVO vo);
	//댓글 수정
	public int replyUpdate(ReplyVO vo);
	//댓글 삭제
	public int replyDelete(int num, String userid);
	//해당 글 댓글 전체목록
	public List<ReplyVO> replyAllRecord(int no);
}
