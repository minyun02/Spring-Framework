package com.bitcamp.home.comment;

import org.springframework.stereotype.Controller;

@Controller
public class CommentController {
	
	@RequestMapping("/ajaxComment")
	@ResponseBody
	public List<CommentVO> ajaxComment(){
		
	}
}
