package com.bitcamp.home.comment;

import java.util.ArrayList;
import java.util.List;

import com.bitcamp.home.DBCPConnection;
import com.bitcamp.home.board.BoardVO;

public class CommentDAO extends DBCPConnection {
	//댓글 등록
	public void commentInsert(BoardVO vo, String contentC) {
		try {
			getConn();
			sql = "insert into ajaxComment(no, useridC, cntentC) values(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getUserid());
			pstmt.setString(3, contentC);
			
			pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("댓글등록 에러발생....");
			e.printStackTrace();
		}finally {
			setClose();
		}
	}
	//댓글 불러오기
	public List<CommentVO> commentAllRecord(int no) {
		List<CommentVO> list = new ArrayList<CommentVO>(); 
		try {
			getConn();
			sql = "select useridC, cntentC, to_char(writedateC, 'MM-DD HH:MI') writedate from ajaxComment where no=? "
					+ "order by rowNum desc";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				CommentVO vo = new CommentVO();
				vo.setUseridC(rs.getString(1));
				vo.setCntentC(rs.getString(2));
				vo.setWritedateC(rs.getString(3));
				list.add(vo);
			}
		}catch(Exception e) {
			System.out.println("댓글 불러오기 에러 발생---");
			e.printStackTrace();
		}finally {
			setClose();
		}
		return list;
	}
}
