package com.bitcamp.myapp.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bitcamp.myapp.vo.BoardVO;

public interface BoardDAO {
	@Select("select no, subject, userid, hit, to_char(writedate, 'MM-DD HH:MI') writedate from board order by no desc")
	public List<BoardVO> allList();
	
	@Select("select no, subject, content, userid, hit, writedate from board where no=#{no}")
	public BoardVO boardSelect(int no);
	
	@Insert("insert into board(no, userid, subject, content, ip) values(boardsq.nextval, #{userid}, #{subject}, #{content}, #{ip})")
	public int boardInsert(BoardVO vo);
	
	@Select("select no, subject, userid, hit, to_char(writedate, 'MM-DD HH:MI') writedate from board "
			+ " where ${param1} like '%${param2}%' order by no desc")
	public List<BoardVO> searchList(String searchKey, String searchWord);
}
