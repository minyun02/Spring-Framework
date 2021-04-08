package com.bitcamp.myapp.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	@RequestMapping("/ajaxView")
	public String ajaxView() {
		return "ajax/ajaxView";
	}
	//															한글이 return되는 경우 인코딩
	@RequestMapping(value="/ajaxString", method=RequestMethod.GET, produces="application/text;charset=UTF-8")
	@ResponseBody //ajax 컨트롤러인 경우 return이 곧 데이터이다. return은 원래 view파일이나 modelandview를 보낼 수 있지만 데이터를 보낼때는 이걸 쓴다.
	public String ajaxString(String num, String name, String id) {
		return "오예~"+num+", "+name+", "+id;
	}
	/*public String ajaxString(HttpServletRequest req) {
		String num = req.getParameter("num");
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		
		String msg = "num="+num+", name="+name+", id="+id;
		System.out.println(msg);
		
		return "서버에서 받은 데이터---->"+msg;
	}*/
	@RequestMapping("/ajaxObject")
	@ResponseBody
	public TestVO ajaxObject(TestVO vo) {
		vo.setTel("010-1234-5678");
		vo.setAddr("서울시 마포구 백범로");
		
		return vo;
	}
	@RequestMapping("/ajaxList")
	@ResponseBody
	public List<TestVO> ajaxList(){
		List<TestVO> list = new ArrayList<TestVO>();
		list.add(new TestVO("1","홍길동","hong","010-1111-2222","서울시 마포구"));
		list.add(new TestVO("2","김길동","kim","010-3333-4444","서울시 성동구"));
		list.add(new TestVO("3","박길동","park","010-5555-6666","서울시 중구"));
		list.add(new TestVO("4","최길동","choi","010-7777-8888","서울시 서대문구"));
		list.add(new TestVO("5","이길동","lee","010-9999-1111","서울시 종로구"));
		return list;
	}
	@RequestMapping("/ajaxMap")
	@ResponseBody
	public HashMap<String, TestVO> ajaxMap(){
		HashMap<String, TestVO> map = new HashMap<String, TestVO>();
		
		map.put("p1", new TestVO("2","이순신","kim1","010-3333-1444","서울시 성동구1"));
		map.put("p2", new TestVO("3","김순신","kim2","010-3333-2444","서울시 성동구2"));
		map.put("p3", new TestVO("4","박순신","kim3","010-3333-3444","서울시 성동구3"));
		return map;
	}
	@RequestMapping(value="/ajaxJson", method=RequestMethod.GET, produces="application/text;charset=UTF-8")
	@ResponseBody
	public String ajaxJson() {
		int no = 1234;
		String name = "홍길동";
		String tel = "010-1234-5678";
		String addr = "서울시 마포구";
		String email = "abcd@nate.com";
		
		String jsonStr = "{\"no\":\""+no+"\",\"name\":\""+name+"\",\"tel\":\""+tel+"\",\"addr\":\""+addr+"\",\"email\":\""+email+"\"}";
		System.out.println(jsonStr);
		
		return jsonStr;
	}
	/*
	 
	 {"no":"1234", "name":"홍길동", "tel":"010-1234-5678","addr":"서울시 마포구"}
	 
	 */
}
