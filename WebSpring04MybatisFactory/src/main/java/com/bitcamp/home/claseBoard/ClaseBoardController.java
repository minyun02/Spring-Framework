package com.bitcamp.home.claseBoard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClaseBoardController {
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	private DataSourceTransactionManager transactionManager;
	
	//글목록
	@RequestMapping("/claseList")
	public ModelAndView claseList() {
		ModelAndView mav = new ModelAndView();
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		
		mav.addObject("totalRecord", dao.getTotalRecord()); //총 레코드 수 구하기 ->
		mav.addObject("list", dao.claseAllRecord());
		
		mav.setViewName("claseBoard/claseList");
		return mav;
	}
	//글쓰기 폼
	@RequestMapping("/claseWrite")
	public String claseWrite() {
		return "claseBoard/claseWrite";
	}
	//글쓰기 - DB
	@RequestMapping(value="/claseWriteOk", method=RequestMethod.POST)
	public ModelAndView claseWriteOk(ClaseBoardDTO vo, HttpServletRequest req) {
		vo.setIp(req.getRemoteAddr());
		vo.setUserid((String)req.getSession().getAttribute("logId"));
		
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		int cnt = dao.claseInsert(vo);
		
		ModelAndView mav = new ModelAndView();
		if(cnt>0) {
			mav.setViewName("redirect:claseList");
		}else {
			mav.setViewName("redirect:claseWrite");
		}
		return mav;
	}
	//글 내용보기
	@RequestMapping("/claseView")
	public ModelAndView claseView(int no) {
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		
		ModelAndView mav = new ModelAndView();

		dao.hitCount(no); //게시물을 눌려서 들어가면 조회수를 올려줘야지!
		
		mav.addObject("dto", dao.claseSelect(no));
		//mav.addObject("nextPrev", dao.nextPrev(no)); //이전글 다음글 제목
		//mav.addObject("num", dao.nextPrevNum(no)); //이전글 다음글 번호
		mav.setViewName("claseBoard/claseView");
		
		//이전글 다음글
		PrevNextVO vo = dao.lagLeadSelect(no);
		mav.addObject("vo", vo);
		return mav;
	}
	//답글쓰기 폼으로 이동
	@RequestMapping("/claseWriteForm")
	public ModelAndView claseWriteForm(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("no", no);
		mav.setViewName("claseBoard/claseWriteForm");
		return mav;
	}
	//답글쓰기
	@RequestMapping(value="/claseWriteFormOk", method=RequestMethod.POST)
	@Transactional(rollbackFor= {Exception.class, RuntimeException.class})
	public ModelAndView claseWriteFormOk(ClaseBoardDTO dto, HttpSession session, HttpServletRequest req) {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED);
		
		TransactionStatus status = transactionManager.getTransaction(def);
		
		
		dto.setIp(req.getRemoteAddr());
		dto.setUserid((String)session.getAttribute("logId"));
		
		ModelAndView mav = new ModelAndView();
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		try {
			//1.원글번호의 ref, step, lvl를 가져온다(select)
			ClaseBoardDTO orgDto = dao.origInfor(dto.getNo());
			
			//2.lvl증가 : 원글번호가 같고 lvl이 원글번호의 lvl보다 크면 1증가 한다
			int lvlCnt = dao.lvlcount(orgDto);
			System.out.println(lvlCnt+"개 lvl 업데이트 완료");
			
			//3. 답글추가
			dto.setRef(orgDto.getRef());
			dto.setStep(orgDto.getStep()+1);
			dto.setLvl(orgDto.getLvl()+1);
			
			int cnt = dao.claseDataInsert(dto);
			mav.addObject("no", dto.getNo());
			if(cnt>0) {//정상 실행
				//원글보기
				mav.setViewName("redirect:claseView");
				transactionManager.commit(status);
			}else {//something went wrong~
				mav.setViewName("redirect:claseWriteForm");
				transactionManager.rollback(status);
			}
		}catch(Exception e) {
			mav.addObject("no", dto.getNo());
			mav.setViewName("redirect:claseWriteForm");
			System.out.println("답글쓰기 에러발생--> 롤백!!!");
		}
		return mav;
	}
	//글 수정 폼으로 이동
	@RequestMapping("/claseEdit")
	public ModelAndView claseEdit(int no) {
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		ModelAndView mav = new ModelAndView();
		mav.addObject("dto", dao.claseSelect(no));
		mav.setViewName("claseBoard/claseEdit");
		return mav;
	}
	//글 수정하기
	@RequestMapping(value="/claseEditOk", method=RequestMethod.POST)
	public ModelAndView claseEditOk(ClaseBoardDTO dto, HttpSession session) {
		dto.setUserid((String)session.getAttribute("logId"));
		
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		ModelAndView mav = new ModelAndView();
		int result = dao.claseUpdate(dto);
		mav.addObject("no", dto.getNo());
		if(result>0) {//수정성공 -> 글 내용보기로
			mav.setViewName("redirect:claseView");
		}else {//실패 -> 수정페이지로
			mav.setViewName("redirect:claseEdit");
		}
		return mav;
	}
	//글 삭제하기
	@RequestMapping("/claseDel")
	public ModelAndView claseDel(int no, HttpSession session) {
		ClaseBoardDAOImp dao = sqlSession.getMapper(ClaseBoardDAOImp.class);
		String userid = (String)session.getAttribute("logId");
		//월글삭제가 가능하고 답글이 있는 경우 답글까지 지운다. -> delete
		//답글은 제목, 글내용을 "삭제된 글입니다"로 바꾼다. -> update
		
		//원글인지 확인용 정보 -> step가져오기, no, ref가 같은지
		ClaseBoardDTO orgData = dao.getStep(no); //step과 userid가 들어있다.
		
		int result = 0;
		if(orgData.getStep()==0 && orgData.getUserid().equals(userid)) {//원글이다.
			result = dao.claseDelete(no);
		}else if(orgData.getStep()>0 && orgData.getUserid().equals(userid)){//답글이다.
			System.out.println(no+"+"+userid);
			result = dao.claseDeleteUpdate(no, userid);
		}
		
		ModelAndView mav = new ModelAndView();
		/*if(result>0) {//삭제 성공
			mav.setViewName("redirect:claseList"); 
		}else {//삭제 실패
			mav.addObject("no", no);
			mav.setViewName("redirect:claseView");
		}*/
		mav.addObject("result", result);
		mav.addObject("no", no);
		mav.setViewName("claseBoard/delCheck");
		return mav;
	}
}
