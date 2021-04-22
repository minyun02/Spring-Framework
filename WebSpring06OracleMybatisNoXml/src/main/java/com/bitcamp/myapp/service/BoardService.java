package com.bitcamp.myapp.service;

import java.util.List;

import com.bitcamp.myapp.vo.BoardVO;

public interface BoardService {
	public List<BoardVO> allList();
	public BoardVO boardSelect(int no);
	public int boardInsert(BoardVO vo);
	public List<BoardVO> searchList(String searchKey, String searchWord);
	public BoardVO boardEditSelect(int no);
	public int boardUpdate(BoardVO vo);
	public int boardDelete(BoardVO vo);
	public int boardMultiDelete(int[] noList);
}
