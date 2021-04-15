package com.bitcamp.home.claseBoard;

import java.util.List;

public interface ClaseBoardDAOImp {
	
	public int claseInsert(ClaseBoardDTO dto);
	//전체레코드 선택
	public List<ClaseBoardDTO> claseAllRecord();
	//글내용 보기(글 1개 선택)
	public ClaseBoardDTO claseSelect(int no);
	//조회수 올리기
	public void hitCount(int no);
	
	//답글
	//원글의 ref, step, lvl을 선택
	public ClaseBoardDTO origInfor(int no);
	//lvl 증가하기
	public int lvlcount(ClaseBoardDTO dto);
	//새로운답글 넣기
	public int claseDataInsert(ClaseBoardDTO dto);
}
