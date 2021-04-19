package com.bitcamp.myapp.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.bitcamp.myapp.dao.MemberDAO;
import com.bitcamp.myapp.vo.MemberVO;

@Service
public class MemberServiceImp implements MemberService{
	@Inject //dao를 객체로 자동으로 만들어주는 어노테이션 @autowired랑 같은 기능
	MemberDAO memberDAO;
	
	@Override
	public MemberVO loginCheck(MemberVO vo) {
		
		return memberDAO.loginCheck(vo);
	} 
}
