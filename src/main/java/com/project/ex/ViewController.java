package com.project.ex;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class ViewController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private  HttpSession session;
	
	@RequestMapping("/view")
	public String view(String cPage, String b_idx) {
		BbsVO vo = b_dao.getBbs(b_idx);
		
		session.setAttribute("vo", vo);
		
		return "bbs/view";
		
	}
	
}
