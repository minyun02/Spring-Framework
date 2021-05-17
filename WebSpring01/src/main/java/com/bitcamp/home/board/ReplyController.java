package com.bitcamp.home.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReplyController {

	@RequestMapping(value="/commentWriteOk", method=RequestMethod.POST)
	@ResponseBody
	public String commentWriteOk(ReplyVO vo, HttpServletRequest req) {
		//vo랑 request가 필요하다
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		
		ReplyDAO dao = new ReplyDAO();
		return dao.replyInsert(vo)+"-> inserted";
	}
	@RequestMapping("/replyList")
	@ResponseBody
	public List<ReplyVO> replyList(int no){
		//System.out.println("원글번호===>"+no);

		ReplyDAO dao = new ReplyDAO();
		return dao.replyAllRecord(no);
	}
	//comment editing
	@RequestMapping("/replyEditOk")
	@ResponseBody
	public String replyEditOk(ReplyVO vo, HttpServletRequest req) {
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		ReplyDAO dao = new ReplyDAO();
		return dao.replyUpdate(vo)+"update(s) succeed";
	}
	//4 deleting
	@RequestMapping("/replyDel")
	@ResponseBody
	public String replyDel(int num, HttpServletRequest req) {
		ReplyDAO dao = new ReplyDAO();
		return dao.replyDelete(num, (String)req.getSession().getAttribute("logId"))+"개 삭제 성공";
	}
}
