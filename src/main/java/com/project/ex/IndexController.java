package com.project.ex;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import mybatis.vo.MemVO;

@Controller
public class IndexController {

	//               ↱뒤에 아무것도 입력하지 않은 상태임 즉 프로젝트 실행하면 바로 실행됨
	@RequestMapping("/")
	public String index() {
		
		return "index";   // views/index.jsp를 의미
	}
	
	// ↱브라우저에서 /login이 입력될경우 실행됨
	@RequestMapping("/login")
	public String login() {
		return "login";  // views/login.jsp를 의
	}
	
	
	// 생략하면 기본으로 get방식임 /   ↱ post방식으로 받아오는 방법 (위에랑 같은 "/login"이지만 구별이 됨)
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ModelAndView login(MemVO vo) {
		ModelAndView mv = new ModelAndView();
		
		System.out.println(vo.getM_id() +"/"+ vo.getM_pw());
		return mv;
	}
	
	
}
