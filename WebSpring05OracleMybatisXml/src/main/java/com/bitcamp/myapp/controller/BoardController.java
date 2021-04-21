package com.bitcamp.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bitcamp.myapp.service.BoardService;
import com.bitcamp.myapp.vo.BoardVO;
import com.bitcamp.myapp.vo.MemberVO;

@Controller
public class BoardController {
	
	@Inject
	BoardService boardService;

	@RequestMapping("/boardList")
	public ModelAndView boardList() {
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("list", boardService.boardAllRecored());
		mav.setViewName("board/boardList");
		
		return mav;
	}
	@RequestMapping("/boardWrite")
	public String boardWrite() {
		return "/board/boardWrite";
	}
	
	@RequestMapping(value="/boardWriteOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo, HttpServletRequest req, HttpSession session) {
		//MemberVO mVo = (MemberVO)req.getSession().getAttribute("logVo");
		//vo.setUserid(mVo.getUserid());
		vo.setUserid(((MemberVO)session.getAttribute("logVo")).getUserid());
		
		vo.setIp(req.getRemoteAddr()); // ip 세팅
		
		ModelAndView mav = new ModelAndView();
		//메소드 호출
		int result = boardService.boardInsert(vo);
		if(result>0) {//insert성공
			mav.setViewName("redirect:boardList");
		}else {//실패
			mav.setViewName("redirect:boardWrite");
		}
		return mav;
	}
	
	@RequestMapping("/boardView")
	public ModelAndView boardView(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", boardService.boardSelect(no));
		mav.setViewName("board/boardView");
		return mav; 
	}
	
	//글 수정
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", boardService.boardSelect(no));
		mav.setViewName("board/boardEdit");
		return mav;
	}
	//글수정오케이
	@RequestMapping(value="/boardEditOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo, HttpSession session) {
		vo.setUserid(((MemberVO)session.getAttribute("logVo")).getUserid());
		
		ModelAndView mav = new ModelAndView();
		int result = boardService.boardUpdate(vo);
		mav.addObject("no", vo.getNo());
		if(result>0) {//성공
			mav.setViewName("redirect:boardView");
		}else {//실패
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	//글삭제
	@RequestMapping("/boardDelete")
	public ModelAndView boardDelete(int no, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		int result = boardService.boardDelete(no, ((MemberVO)session.getAttribute("logVo")).getUserid());
		if(result>0) {//성공
			mav.setViewName("redirect:boardList");
		}else {
			mav.addObject("no", no);
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
}
