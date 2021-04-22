package com.bitcamp.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bitcamp.myapp.service.BoardService;
import com.bitcamp.myapp.vo.BoardVO;

@Controller
public class BoardController {
	@Inject
	BoardService service;
	
	@RequestMapping("/list")
	public ModelAndView boardList(HttpServletRequest req, String searchKey, String searchWord) {
		ModelAndView mav =  new ModelAndView();
		
		if(searchKey==null && searchWord==null) {
			mav.addObject("list",  service.allList());
		}else {
			mav.addObject("list", service.searchList(searchKey, searchWord));
			System.out.println(searchKey+", "+searchWord+"---back");
		}
		mav.setViewName("board/list");
		return mav;
	}
	
	@RequestMapping("/view")
	public String boardView(int no, Model model) {//return을 string으로 하고 보낼 데이터가 있으면 model을 이용한다
		model.addAttribute("vo", service.boardSelect(no));
		return "board/view";
	}
	
	@RequestMapping("/write")
	public String boardWrite() {
		return "board/write";
	}
	
	@RequestMapping(value="/writeOk", method=RequestMethod.POST)
	public ModelAndView boardWriteOk(BoardVO vo, HttpServletRequest req) {
		vo.setIp(req.getRemoteAddr());
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		if(service.boardInsert(vo)>0) {//글쓰기 성공
			mav.setViewName("redirect:list");
		}else {//실패
			mav.setViewName("redirect:write");
		}
		return mav;
	}
	
	// 글 수정
	@RequestMapping("/boardEdit")
	public ModelAndView boardEdit(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", service.boardEditSelect(no));
		mav.setViewName("board/edit");
		return mav;
	}
	// 글 수정 업데이트
	@RequestMapping(value="/editOk", method=RequestMethod.POST)
	public ModelAndView boardEditOk(BoardVO vo, HttpSession session) {
		vo.setUserid((String)session.getAttribute("logId"));

		ModelAndView mav = new ModelAndView();
		mav.addObject("no", vo.getNo());
		if(service.boardUpdate(vo)>0) {//성공
			mav.setViewName("redirect:view");
		}else {//실패
			mav.setViewName("redirect:boardEdit");
		}
		return mav;
	}
	//글 삭제
	@RequestMapping("/boardDel")
	public ModelAndView boardDel(BoardVO vo, HttpSession session) {
		vo.setUserid((String)session.getAttribute("logId"));
		ModelAndView mav = new ModelAndView();
		
		if(service.boardDelete(vo)>0) {
			mav.setViewName("redirect:list");
			System.out.println("ASDF");
		}else {
			mav.addObject("no", vo.getNo());
			mav.setViewName("redirect:view");
			System.out.println("A1234");
		}
		return mav;
	}
	
	//레코드 여러개를 한번에 삭제하기
	@RequestMapping("/multiDel")
	public ModelAndView boardMultiDel(BoardVO vo) {
		for(int no : vo.getNoList()) {
			System.out.println("no="+no);
		}

		ModelAndView mav = new ModelAndView();
		int result = service.boardMultiDelete(vo.getNoList());
		System.out.println(result+"개 삭제");
		mav.setViewName("redirect:list");
		return mav;
	}
}
