package com.bitcamp.jdbc.data;

import java.util.List;

public interface DataDAOImpl {
	//글목록
	public List<DataVO> allList();
	//
	public int dataInsert1(DataVO vo);
}	
