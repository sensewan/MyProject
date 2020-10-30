package com.project.ex;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;

@Controller
public class DellController {

	@Autowired
	private BbsDAO b_dao;
	
	
	@RequestMapping("/dell")
	@ResponseBody
	public Map<String, String> dell(BbsVO vo) {
//		ModelAndView mv = new ModelAndView();
		Map<String, String> map = new HashMap<String, String>();
		
		int cnt = b_dao.delBbs(vo.getB_idx());
		
		if (cnt > 0) {
			map.put("res", "삭제성공");
			map.put("url", "bbs");
		}else {
			map.put("res", "삭제실패");
		}
		
		
//		mv.setViewName("redirect:/bbs");
		return map;
	}
}
