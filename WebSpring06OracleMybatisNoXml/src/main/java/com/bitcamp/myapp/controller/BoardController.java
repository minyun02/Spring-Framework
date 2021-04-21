package com.bitcamp.myapp.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
		
		//String searchKey = req.getParameter("searchKey");
		//String searchWord = req.getParameter("searchWord");
		
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
}
