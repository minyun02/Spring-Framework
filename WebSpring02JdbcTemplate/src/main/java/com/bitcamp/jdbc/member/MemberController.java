package com.bitcamp.jdbc.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MemberController {
	@RequestMapping("/login")
	public String login() {
		
		return "member/login";
	}
	@RequestMapping(value="/loginOk", method=RequestMethod.POST)
	public ModelAndView loginOk(MemberVO vo, HttpServletRequest req) {
		MemberDAO dao = new MemberDAO();//객체를 만들면 template이 설정됨
		MemberVO vo2 = dao.loginCheck(vo); //이름과 아이디가 들어있다. 없으면 null
		
		ModelAndView mav = new ModelAndView();
		if(vo2==null) {//로그인 실패
			mav.setViewName("redirect:login");
		}else {//로그인 성공
			HttpSession ses = req.getSession();
			ses.setAttribute("logId", vo2.getUserid());
			ses.setAttribute("logName", vo2.getUsername());
			ses.setAttribute("logStatus", "Y");
			
			mav.setViewName("redirect:/");
		}
		return mav;
	}
	@RequestMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession ses = req.getSession();
		ses.invalidate();
		return "home";
	}
}
