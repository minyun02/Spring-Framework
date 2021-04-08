package com.bitcamp.home.comment;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitcamp.home.board.BoardVO;

@Controller
public class CommentController {
	
	@RequestMapping("/ajaxComment")
	@ResponseBody
	public String ajaxComment(BoardVO vo, HttpServletRequest req){
		String result = "1234";
		//댓글이 달리는 글 번호는 vo에 있고, 댓글을 다는 작성자 아이디를 vo에 세팅한다.
		try {
			System.out.println(vo.getNo()+"글번호");
			vo.setNo(Integer.parseInt(req.getParameter("no")));
			vo.setUserid((String)req.getSession().getAttribute("logId"));
			System.out.println("댓글작성자=>"+vo.getUserid());
			String contentC = req.getParameter("contentC");
			System.out.println("param?????--->"+contentC);
			CommentDAO dao = new CommentDAO();
			dao.commentInsert(vo, contentC);
			dao.commentAllRecord(vo.getNo());
			
		}catch(Exception e) {
			
		}
		return result;
	}
}
