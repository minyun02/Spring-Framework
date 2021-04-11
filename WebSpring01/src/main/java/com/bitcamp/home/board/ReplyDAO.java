package com.bitcamp.home.board;

import java.util.ArrayList;
import java.util.List;

import com.bitcamp.home.DBCPConnection;

public class ReplyDAO extends DBCPConnection implements ReplyDAOImpl {

	@Override
	public int replyInsert(ReplyVO vo) {
		int result = 0;
		try {
			getConn();
			sql = "insert into reply(num, no, content, userid) values(memsq.nextval, ?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getContent());
			pstmt.setString(3, vo.getUserid());
			
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("댓글등록 에러 발생");
			e.printStackTrace();
		}finally {
			setClose();
		}
		return result;
	}

	@Override
	public int replyUpdate(ReplyVO vo) {
		int result = 0;
		try {
			getConn();
			sql = "update reply set content=? where num=? and userid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getContent());
			pstmt.setInt(2, vo.getNum());
			pstmt.setString(3, vo.getUserid());
			result = pstmt.executeUpdate();
			System.out.println("update-->"+result);
		}catch(Exception e) {
			System.out.println("comments updating failed from dao");
			e.printStackTrace();
		}finally {
			setClose();
		}
		return result;
	}

	@Override
	public int replyDelete(int num, String userid) {
		int result = 0;
		try {
			getConn();
			sql = "delete from reply where num=? and userid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, userid);
			result = pstmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("failed to delete a comment from dao");
			e.printStackTrace();
		}finally {
			setClose();
		}
		return result;
	}

	@Override
	public List<ReplyVO> replyAllRecord(int no) {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		try {
			getConn();
			sql = "select num, content, userid, writedate from reply where no=? order by num";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ReplyVO vo = new ReplyVO();
				vo.setNum(rs.getInt(1));
				vo.setContent(rs.getString(2));
				vo.setUserid(rs.getString(3));
				vo.setWritedate(rs.getString(4));
				list.add(vo);
			}
		}catch(Exception e) {
			System.out.println("댓글목록 불러오기 에러 발생-----");
			e.printStackTrace();
		}finally {
			setClose();
		}
		return list;
	}

}
