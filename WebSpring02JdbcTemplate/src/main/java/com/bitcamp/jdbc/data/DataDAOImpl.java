package com.bitcamp.jdbc.data;

import java.util.List;

public interface DataDAOImpl {
	//글목록
	public List<DataVO> allList();
	//레코드 추가
	public int dataInsert1(DataVO vo);
	//레코드 선택
	public DataVO dataSelect(int no);
	//파일명 선택
	public DataVO getSelectFilename(int no);
	//레코드 수정
	public int dataUpdate(DataVO vo);
	//레코드 삭제
	public int dataDelete(int no, String userid);
}	
