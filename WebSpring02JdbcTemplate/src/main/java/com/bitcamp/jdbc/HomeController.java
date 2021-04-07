package com.bitcamp.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	public JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	//homecontroller가 실행될때 메소드가 자동실행 : servlet-context.xml에 있는 template객체를 jdbcTemplate와 Constants.jdbcTemplae에도 세팅한다. 
	//web.xml -> servlet-context.xml로가서 bean name중 같은 걸 찾아서 실행한다.
	@Autowired
	public void setJdbcTemplate(JdbcTemplate template) {
		this.jdbcTemplate = template;
		Constants.jdbcTemplate = template;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
}
