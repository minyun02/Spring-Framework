package com.bitcamp.jdbc.board;

import java.util.List;

public interface BoardReplyDAOImpl {
	//댓글 쓰기
	public int replyInsert(BoardReplyVO vo);
	//댓글 수정
	public int replyUpdate(BoardReplyVO vo);
	//댓글 삭제
	public int replyDelete(int num, String userid);
	//댓글 해당글의 전체목록
	public List<BoardReplyVO> replyAllRecord(int no);
}
