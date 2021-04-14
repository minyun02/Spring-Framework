package com.bitcamp.home.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardReplyController {
   @Autowired
   SqlSession sqlSession;
   
   @RequestMapping("/replyList")
   @ResponseBody
   public List<BoardReplyVO> boardList(int no) {
   
      BoardReplyDAOImpl dao = sqlSession.getMapper(BoardReplyDAOImpl.class);
      return dao.replyList(no);
      
   }
   @RequestMapping("/replyWriteOk")
   @ResponseBody
   public String boardInsert(BoardReplyVO vo, HttpServletRequest req) {
      String result="";
      vo.setUserid((String)req.getSession().getAttribute("logId"));
      vo.setIp(req.getRemoteAddr());
      
      BoardReplyDAOImpl dao = sqlSession.getMapper(BoardReplyDAOImpl.class);
   
      int cnt = dao.replyInsert(vo);
      result = cnt+"개 insert 성공";
      return result;
   }
   
   @RequestMapping("/replyEdit")
   @ResponseBody
   public String replyEdit (HttpServletRequest req, BoardReplyVO vo) {
      vo.setUserid((String)req.getSession().getAttribute("logId"));
      
      BoardReplyDAOImpl dao =sqlSession.getMapper(BoardReplyDAOImpl.class);
   return dao.replyUpdate(vo)+"개 edit 성공";
   }
   
   @RequestMapping("/replyDel")
   @ResponseBody
   public String replyDel(int num, HttpServletRequest req) {
      
      BoardReplyDAOImpl dao =sqlSession.getMapper(BoardReplyDAOImpl.class);
      
      return dao.replyDel(num, (String)req.getSession().getAttribute("logId"))+"del 성공";
   }
   
}