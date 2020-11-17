package com.project.ex;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import mybatis.vo.MemVO;
import spring.util.FileUploadUtil;

@Controller
public class WriteController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private HttpSession session;
	
	// ↱ip얻기
	@Autowired
	private HttpServletRequest request;
		
	// ↱절대 경로 얻기
	@Autowired
	private ServletContext application;
	
		
	// ↱ 첨부파일이 저장될 위치 만들기
	private String uploadPath = "/resources/upload";
	
	
	
	@RequestMapping("/write")
	@ResponseBody
	public Map<String, String> write() {
		Map<String, String> map = new HashMap<String, String>();
		
		// 로그인 되었을 경우에만 글쓰는게 가능하게 만들기.
		// 로그인 안 되어있을 경우 로그인 하라고 알림창 띄우기.
		
		MemVO mvo = (MemVO)session.getAttribute("mvo");
		
		if (mvo != null) {
			map.put("chk", "1");
			map.put("url", "writeForm");
		}else {
			map.put("chk", "0");
		}
		
		return map;
	}
	
	
	@RequestMapping("/writeForm")
	public ModelAndView writeForm(String bname) {
		ModelAndView mv = new ModelAndView();
		
		MemVO mvo = (MemVO)session.getAttribute("mvo");
		//System.out.println("제발나와라 "+bname);
		
		if (mvo != null) {
			mv.addObject("bname", bname);
			mv.setViewName("bbs/write");
		}else {
			mv.setViewName("login");
		}
		
		return mv;
	}
	
	
	
	@RequestMapping("/write_ok")          // ↱ new File() 때문에 하는것임 (io이용이므로 예외처리 해야함)
	public ModelAndView writeOK(BbsVO vo) throws Exception {
								// ↳ ★★wirte.jsp에서 전달되는 폼의 값들 -> (bname, subject, content, file) 을
						        // 멤버변수로 가지고 있는 ★★BbsVO★★로 모두 받는다.(스프링이라 자동으로 들어감) 
						        // (즉 넘어오는 이름이랑 VO변수 이름이 같아야함) (단! 파일 첨부인 경우에는 VO에 MultipartFile 로 선언해줘야함)
		ModelAndView mv = new ModelAndView();



		// ↱ 로그인한 정보를 얻어내서 writer를 알아내자 (왜냐하면 mapper에서 writer를 입력하도록 해놓았기 때문임)
		MemVO mvo = (MemVO)session.getAttribute("mvo");
		
		// ↱ ip 저장하기
		vo.setIp(request.getRemoteAddr());
		vo.setWriter(mvo.getM_name());
		
		b_dao.add2(vo);
		
		
		// ↱ 저장하고 bname에 맞는 Controller로 가서 리스트페이지로 간다.
		mv.setViewName("redirect:/bbs?bname="+vo.getBname());  // reDirect로 가는 이유는 게시판 글 저장하고 새로운 글 목록을 보여주기 위함.
		
		return mv;	
	}
	
	
	@RequestMapping(value = "/write_summer", method = RequestMethod.POST)          // ↱ new File() 때문에 하는것임 (io이용이므로 예외처리 해야함)
	@ResponseBody
	public Map<String, String> writeSummer(MultipartFile file) throws Exception {
								// ↳ ★★wirte.jsp에서 전달되는 폼의 값들 -> (bname, subject, content, file) 을
						        // 멤버변수로 가지고 있는 ★★BbsVO★★로 모두 받는다.(스프링이라 자동으로 들어감) 
						        // (즉 넘어오는 이름이랑 VO변수 이름이 같아야함) (단! 파일 첨부인 경우에는 VO에 MultipartFile 로 선언해줘야함)
		
		Map<String, String> map = new HashMap<String, String>();
		
		
		// ↱ 첨부된 파일이 있는지? 없는지? 확인하기.
		if (file != null && file.getSize() > 0) { // null은 안해도 되지만 다른 경로로 들어오는 경우에는 null이 들어올 수 있음
			// 첨부파일이 있는경우임.  ↱ 저장될 위치를 절대경로로 만든다.
			String path = application.getRealPath(uploadPath);
			
			System.out.println("경로확인-> "+path);
			
			// ↱파일명 얻기
			String f_name = file.getOriginalFilename();
			
			// ↱ ★★동일한 파일명이★★ 있을 수 있으므로 변경하기!~ 
			f_name = FileUploadUtil.checkSameFileName(f_name, path);
			
			
			// ↱ 업로드 진행!!~    ↱경로, ↱파일이름
			file.transferTo(new File(path, f_name));  // 예외처리 필요!!~
			
			//                   ↱"http://localhost:8080/ex" 이렇게 들어간다. 
			map.put("img_url", request.getContextPath()+"/upload/"+f_name);
			//                                             ↳ uploadPath를 적을경우 /resources/upload 로 들어가게 된다.
			//                                               현재 servlet-context에서 resources를(맵핑) 지워 놓았기에 resources가 들어가면 안된다.
			
			map.put("f_name", f_name);
			
		}

		return map;	
	}
	
//	
//	@RequestMapping("/write_ok")          // ↱ new File() 때문에 하는것임 (io이용이므로 예외처리 해야함)
//	public ModelAndView writeOK(BbsVO vo) throws Exception {
//								// ↳ ★★wirte.jsp에서 전달되는 폼의 값들 -> (bname, subject, content, file) 을
//						        // 멤버변수로 가지고 있는 ★★BbsVO★★로 모두 받는다.(스프링이라 자동으로 들어감) 
//						        // (즉 넘어오는 이름이랑 VO변수 이름이 같아야함) (단! 파일 첨부인 경우에는 VO에 MultipartFile 로 선언해줘야함)
//		ModelAndView mv = new ModelAndView();
//
//		// ↱ 첨부된 파일이 있는지? 없는지? 확인하기.
//		MultipartFile mf = vo.getFile();
//		if (mf != null && mf.getSize() > 0) { // null은 안해도 되지만 다른 경로로 들어오는 경우에는 null이 들어올 수 있음
//			// 첨부파일이 있는경우임.  ↱ 저장될 위치를 절대경로로 만든다.
//			String path = application.getRealPath(uploadPath);
//			
//			// ↱파일명 얻기
//			String f_name = mf.getOriginalFilename();
//			
//			// ↱ ★★동일한 파일명이★★ 있을 수 있으므로 변경하기!~ 
//			f_name = FileUploadUtil.checkSameFileName(f_name, path);
//			
//			
//			// ↱ 업로드 진행!!~    ↱경로, ↱파일이름
//			mf.transferTo(new File(path, f_name));  // 예외처리 필요!!~
//			
//			// ↱ DB작업을 위해 파일명을 vo객체에 저장한다.
//			vo.setFile_name(f_name);
//			
//		}
//
//		// ↱ 로그인한 정보를 얻어내서 writer를 알아내자 (왜냐하면 mapper에서 writer를 입력하도록 해놓았기 때문임)
//		MemVO mvo = (MemVO)session.getAttribute("mvo");
//		
//		// ↱ ip 저장하기
//		vo.setIp(request.getRemoteAddr());
//		vo.setWriter(mvo.getM_name());
//		
//		b_dao.add2(vo);
//		
//		
//		// ↱ 저장하고 bname에 맞는 Controller로 가서 리스트페이지로 간다.
//		mv.setViewName("redirect:/bbs?bname="+vo.getBname());  // reDirect로 가는 이유는 게시판 글 저장하고 새로운 글 목록을 보여주기 위함.
//		
//		return mv;	
//	}
//	
	
	
}
