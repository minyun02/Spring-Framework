package com.bitcamp.myapp.test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	
	@RequestMapping(value="/aLink", method = RequestMethod.GET)
	public String test(HttpServletRequest req, Model model) {
		//클라이언트에서 서버로 데이터 가져오기
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		//서버에 출력
		System.out.println(name+"/"+age);
		
		//Model객체에 데이터를 세팅하면 뷰에서 사용할 수 있다.
		model.addAttribute("username", name);
		model.addAttribute("age", age);
		model.addAttribute("msg", "서버에서 클라이언트에게 데이터 보내기");
		
		return "mappingTest/aLinkView";
	}
	//@RequestParam : 클라이언트측 데이터를 서버로 request한다.
	@RequestMapping("/aLink2")
	public String test2(Model model, @RequestParam("name") String username, @RequestParam("age") int age) { //""에는 불러올 parameter이름을 넣어주고 값을 담아줄 변수를 적어준다.
		System.out.println("aLink2-->"+username+"+"+age);
		
		model.addAttribute("username", username);
		model.addAttribute("age", age);
		model.addAttribute("msg", "@RequestParam을 이용한 데이터 처리");
		
		return "mappingTest/aLinkView";
	}
	@RequestMapping("/aLink3")	// vo에서 request해서 변수명이 같은 곳으로 값을 세팅한다.
	public String test3(TestVO vo, Model model) {
		System.out.println("TestVO--->"+vo.getUsername()+"+"+vo.getAge());
		
		vo.setMsg("vo를 이용한 request 테스트 중...");
		
		model.addAttribute("vo", vo);
		
		return "mappingTest/aLinkView2";
	}
	@RequestMapping("/aLink4")
	public ModelAndView test4(TestVO vo) {
		System.out.println("test4--->"+vo.getUsername()+", "+vo.getAge());
		
		vo.setMsg("ModelAndView테스트 중...");
		
		//데이터와 뷰파일명을 한번에 가지는 클래스
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo); //vo가 mav객체 세팅이 된다.
		mav.setViewName("mappingTest/aLinkView2"); //뷰이름을 mav객체에 세팅한다.
		
		return mav;
	}
	//폼으로 이동하기
	@RequestMapping("/formData")
	public String fromTest(){
		return "mappingTest/form";
	}
	@RequestMapping(value="/formDataOk", method=RequestMethod.POST)
	public ModelAndView formTestOk(TestVO vo){
		System.out.println("formData----?"+vo.getUsername()+", "+vo.getAge());
		
		vo.setMsg("폼데이터 전송 확인");
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("vo", vo);
		mav.setViewName("mappingTest/aLinkView2");
		return mav;
	}
}






