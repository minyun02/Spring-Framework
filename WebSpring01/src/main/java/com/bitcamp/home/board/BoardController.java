package com.bitcamp.home.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class BoardController {
	//게시판 전체 목록 불러오기
	@RequestMapping("/boardList")
	public ModelAndView boardList() {
		BoardDAO dao = new BoardDAO();
		List<BoardVO> list = dao.boardAllRecord();
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("list", list);
		mav.setViewName("board/boardList");
		
		return mav;
	}
	//글쓰기
	@RequestMapping("/boardWrite")
	public String boardWrite(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		// 로그인 안된경우 로그인페이지, 로그인 된 경우 글쓰기
		String logStatus = (String)ses.getAttribute("logStatus");
		if(logStatus!=null && logStatus.equals("Y")) { //&&연산자에서는 첫번째 조건이 false면 뒤에있는거는 검사를 안하고 스킵한다.
			return "board/boardWrite";
		}else {
			return "member/loginForm";
		}
	}
	//글등록하기
	@RequestMapping(value="/boardWriteOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(HttpServletRequest req, BoardVO vo) {
		ModelAndView mav = new ModelAndView();
		BoardDAO dao = new BoardDAO();
		
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		vo.setIp(req.getRemoteAddr());
		
		int result = dao.boardInsert(vo);
		if(result>0) {
			mav.setViewName("redirect:boardList");
		}else {
			mav.setViewName("board/boardWriteOk");
		}
		return mav;
	}
	//글 내용 보기
		@RequestMapping("/boardView")
		public ModelAndView boardView(BoardVO vo) {
			BoardDAO dao = new BoardDAO();
			dao.boardSelect(vo);
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("vo", vo);
			mav.setViewName("board/boardView");
			
			return mav;
		}
	//글 수정 폼
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(BoardVO vo) {
		BoardDAO dao = new BoardDAO();
		dao.boardSelect(vo);
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("/board/boardEdit");
		return mav;
	}
	//글 수정하기
	@RequestMapping(value="/boardEditOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo, HttpServletRequest req) {
		HttpSession ses = req.getSession();
		vo.setUserid((String)ses.getAttribute("logId"));
		
		BoardDAO dao = new BoardDAO();
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", vo.getNo());
		
		int result = dao.boardUpdate(vo);
		
		if(result>0) {//업데이트 성공
			mav.setViewName("redirect:boardView");
		}else {//글 수정 실패
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	//글 삭제
	@RequestMapping("/boardDel")
	public ModelAndView boardDel(BoardVO vo, HttpServletRequest req) {
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		
		BoardDAO dao = new BoardDAO();
		int result = dao.boardDelete(vo);
		
		ModelAndView mav = new ModelAndView();
		if(result>0) {
			mav.setViewName("redirect:boardList");
		}else {
			mav.addObject("no", vo.getNo());
			mav.setViewName("redirect:boardView");
		}
		return mav;
	}
	
}







