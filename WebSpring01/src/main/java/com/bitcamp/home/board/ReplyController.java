package com.bitcamp.home.board;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ReplyController {
	
	@RequestMapping("/commentWriteOk")
	@ResponseBody
	public String commentWriteOk(BoardVO vo, HttpServletRequest req) {
		//vo랑 request가 필요하다
		return "working fine?";
	}
}
