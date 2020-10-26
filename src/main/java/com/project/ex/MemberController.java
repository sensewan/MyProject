package com.project.ex;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.dao.MemDAO;
import mybatis.vo.MemVO;

@Controller
public class MemberController {
	
	
	// ↱ 세션에 저장하기 위해
	@Autowired
	private HttpSession session;

	@Autowired
	private MemDAO m_dao;
	
	@RequestMapping("/login")  // Get방식 호출
	public String login() {
		return "login";
	}
	
	
	// post방식으로 왔을 때 작동하게 하기
	@RequestMapping(value = "/login", method =RequestMethod.POST )
	@ResponseBody
	public Map<String, Object> login(String m_id, String m_pw) {
		// 인자 두개를 MemDAO의 login함수를 호출하면서 전달하면 정확한 정보일 때만 
		// MemVO한개를 받게 된다.
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MemVO mvo = m_dao.login(m_id, m_pw);
		
		// mvo가 null이면 아이디 및 비번을 잘 못 입력한 경우임.
		if (mvo != null) {
			// ↱ 세션에 mvo라는 이름으로 mvo를 저장하자.
			session.setAttribute("mvo", mvo);
			map.put("res", "1");
			map.put("mvo", mvo);
		}else {
			map.put("res", "0");
		}
		
		return map;
		
	}
	
	@RequestMapping("/logout")
	@ResponseBody
	public Map<String, String> logout() {
		
		session.removeAttribute("mvo");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("res", "0");
		
		return map;
	}
	
	
}
