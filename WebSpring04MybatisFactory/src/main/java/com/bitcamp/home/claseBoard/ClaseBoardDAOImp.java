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
	//총 레코드 수 구하기
	public int getTotalRecord();
	//답글 수정하기
	public int claseUpdate(ClaseBoardDTO dto);
	//삭제 1.원글 정보 가져오기
	public ClaseBoardDTO getStep(int no); //step과 userid를 가져와야한다.
	//원글 삭제
	public int claseDelete(int no);
	//월글의 답글들 지우기 update
	public int claseDeleteUpdate(int no, String userid);
	//이전글 다음글 불러오기
	public PrevNextVO lagLeadSelect(int no);
	//public ClaseBoardDTO nextPrev(int no);
	//public ClaseBoardDTO nextPrevNum(int no);
	//쿼리문
	/*<select id="nextPrev" resultType="com.bitcamp.home.claseBoard.ClaseBoardDTO">
	select * from (select no, subject, (lead(subject,1,'다음글없음') over (order by no)) nextSubject,
	(lag(subject,1,'이전글없음') over (order by no)) prevSubject from claseboard where step=0) where no=#{param1}
	</select>
	<select id="nextPrevNum" resultType="com.bitcamp.home.claseBoard.ClaseBoardDTO">
		select * from (select no, subject, (lead(no,1) over (order by no)) nextNum,
		(lag(no,1) over (order by no)) prevNum from claseboard where step=0) where no=#{param1}
	</select>*/
}
