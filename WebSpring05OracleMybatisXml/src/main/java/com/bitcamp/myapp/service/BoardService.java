package com.bitcamp.myapp.service;

import java.util.List;

import com.bitcamp.myapp.dao.BoardDAO;
import com.bitcamp.myapp.vo.BoardVO;

public interface BoardService {
	public List<BoardDAO> boardAllRecored();
	//글등록
	public int boardInsert(BoardVO vo);
	public BoardVO boardSelect(int no);
	public int boardUpdate(BoardVO vo);
}
