package com.bitcamp.jdbc.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardReplyController {
	
	@RequestMapping("/replyWriteOk")
	@ResponseBody						//원글번호, 댓글
	public String replyWriteOk(BoardReplyVO vo, HttpServletRequest req) {
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		vo.setIp(req.getRemoteAddr());
		
		BoardReplyDAO dao = new BoardReplyDAO();
		return dao.replyInsert(vo)+"개 추가됨";
	}
	//해당 댓글 목록 불러오기
	@RequestMapping("/replyList")
	@ResponseBody
	public List<BoardReplyVO> replyList(int no) {
		System.out.println(no);
		
		BoardReplyDAO dao = new BoardReplyDAO();
		return dao.replyAllRecord(no);
	}
	//댓글 수정하기
	@RequestMapping("/replyEditOk")
	@ResponseBody
	public String replyEditOk(BoardReplyVO vo, HttpServletRequest req) {
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		BoardReplyDAO dao = new BoardReplyDAO();
		return dao.replyUpdate(vo)+"개 업데이트 성공~";
	}
	//댓글 삭제하기
	@RequestMapping("/replyDel")
	@ResponseBody	
	public String replyDel(int num, HttpServletRequest req) {
		BoardReplyDAO dao = new BoardReplyDAO();
		return dao.replyDelete(num, (String)req.getSession().getAttribute("logId"))+"개 삭제 성공!";
	}
}
