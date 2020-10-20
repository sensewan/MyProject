package com.project.ex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	//               ↱뒤에 아무것도 입력하지 않은 상태임 즉 프로젝트 실행하면 바로 실행됨
	@RequestMapping("/")
	public String index() {
		
		return "index";   // views/index.jsp를 의미
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";  //views/login.jsp를 의
	}
}
