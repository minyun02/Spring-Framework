package com.bitcamp.home.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReplyController {
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping(value="/commentWriteOk", method=RequestMethod.POST)
	@ResponseBody
	public String commentWriteOk(ReplyVO vo, HttpServletRequest req) {
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		
		ReplyDAOImp dao = sqlSession.getMapper(ReplyDAOImp.class);
		
		return dao.replyInsert(vo)+"개 입력완료!";
	}
	
	@RequestMapping("/replyList")
	@ResponseBody
	public ModelAndView replyList(int no){
		ReplyDAOImp dao = sqlSession.getMapper(ReplyDAOImp.class);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", dao.replyAllRecord(no));
		
		return mav;
	}
}
