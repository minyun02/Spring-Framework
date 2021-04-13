package com.bitcamp.home.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BoardController {
	@Autowired
	SqlSession sqlSession;
	
	@RequestMapping("/boardList")
	public ModelAndView allList() {
		BoardDAOImp dao = sqlSession.getMapper(BoardDAOImp.class);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", dao.allList());
		mav.setViewName("board/boardList");
		
		return mav;	
	}
	@RequestMapping("/boardWrite")
	public String boardWrite() {
		return "board/boardWrite";
	}
	@RequestMapping(value="/boardWriteOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo, HttpSession session, HttpServletRequest req) {
		vo.setIp(req.getRemoteAddr());
		vo.setUserid((String)session.getAttribute("logId"));
		
		BoardDAOImp dao = sqlSession.getMapper(BoardDAOImp.class);
		int cnt = dao.boardInsert(vo);
		
		ModelAndView mav = new ModelAndView();
		if(cnt>0) {//등록
			mav.setViewName("redirect:boardList");
		}else {
			mav.setViewName("redirect:boardWrite");
		}
		return mav;
	}
	@RequestMapping("/boardView")
	public String boardView(int no, Model model) {
		BoardDAOImp dao = sqlSession.getMapper(BoardDAOImp.class);
		BoardVO vo = dao.boardSelect(no);
		model.addAttribute("vo", vo); // view페이지에서 사용한다.
		return "board/boardView";
	}
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		BoardDAOImp dao = sqlSession.getMapper(BoardDAOImp.class);
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", dao.boardSelect(no));
		mav.setViewName("board/boardEdit");
		return mav;
	}
	@RequestMapping(value="/boardEditOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo, HttpSession session) {
		BoardDAOImp dao = sqlSession.getMapper(BoardDAOImp.class);
		int cnt = dao.boardUpdate(vo.getNo(), vo.getSubject(), vo.getContent(), (String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", vo.getNo());
		if(cnt>0) {//수정--글내용보기 성공
			mav.setViewName("redirect:boardView");
		}else {//수정 -- 글 수정폼으로 실패
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	@RequestMapping("/boardDel")
	public ModelAndView boardDelete(int no, HttpSession session){
		BoardDAOImp dao = sqlSession.getMapper(BoardDAOImp.class);
		
		ModelAndView mav = new ModelAndView();
		if(dao.boardDelete(no, (String)session.getAttribute("logId"))>0) {//삭제 성공
			mav.setViewName("redirect:boardList");
		}else {//실패
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
}
