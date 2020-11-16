package com.project.ex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mybatis.dao.BbsDAO;
import mybatis.vo.BbsVO;
import spring.util.FileUploadUtil;

@Controller
public class ViewController {

	@Autowired
	private BbsDAO b_dao;
	
	@Autowired
	private  HttpSession session;  // 조회수 올리는 거 방지도 가능
	
	@Autowired
	private HttpServletRequest request;
	
	// 절대경로 얻기
	@Autowired
	private ServletContext application;
	
	// ↱ 첨부파일이 저장될 위치 만들기
	private String uploadPath = "/resources/upload";
	
	
	
	@RequestMapping("/view")
	public ModelAndView view(String cPage, String b_idx) {
        						// ↳ 나중에 뒤로가기 or 목록 눌렀을 때 클릭하기 전 페이지로 돌아가기 위해 필요
								//   list.jsp에서 파라미터 값으로 넘어옴
		
		// ★★조회수★★ 만들기!!~
		// 한 번 본 게시물(BbsVO)인 경우에는 -> "List<BbsVO> v_list" 를 만들어 여기에 저장해 놓기.
		// 그리고 session에 "view_list"이름으로 저장해 놓고 -> 처음본 게시물인지 아닌지 확인하자.
		Object obj = session.getAttribute("view_list");  // 게시물을 보지 않았을 경우에는 obj는 null 값임
		List<BbsVO> v_list = null;
		
		if (obj == null) {
			// ↳↴ 게시물을 한번도 보지 않은 경우임 -> 이제 클릭을 했으므로 v_list를 만들고 -> session에 저장하자.
			v_list = new ArrayList<BbsVO>();
			
			// ↱현재 세션에 없는 경우이므로 세션에 v_list를 저장하자!!
			session.setAttribute("view_list", v_list);
			
		}else {  // sesseion에 "view_list"가 저장된 경우 임. 즉 게시물을 1번 이상 본경우임
			v_list = (List<BbsVO>) obj;  
           			// ↳즉 처음 게시물을 클릭했을 때 만들어져 seission에 저장해놓 은 vo(클릭한 게시물)를 다시 넣어줌
		}
		
		
		boolean chk = false;  // <- 반복문 돌릴때 b_idx가 있을 경우 chk를 true로 변경하기 위해서 만듦
		// v_list에 저장된 요소들 중에 b_idx가 -> 인자로 넘어온 b_idx와 같은 것이 있을 경우 예전에 한 번 본적이 있는 경우임
		// ↱ 처음 게시물을 클릭한 경우에는 v_list에 아무것도 없기 때문에 for문 돌지 않음.
		for(BbsVO bvo : v_list) { 
			if (bvo.getB_idx().equals(b_idx)) {
				// ↳같은 것이 있는 경우 (즉 한번 봤던 경우임)
				chk = true;
				break;
			}
		}
			
		// ↱ 클릭한 게시글 불러오기
		BbsVO vo = b_dao.getBbs(b_idx);
		
		// ↱ 게시물을 한번도 보지 않았을 경우 hit수 증가하기 (즉 chk가 false인 경우 느낌표로 인해 true로 작동하겠지)
		if (!chk) {
			b_dao.updateHit(b_idx);
			vo.setHit(String.valueOf(Integer.parseInt(vo.getHit())+1));  // vo에 hit가 String으로 되어있어서 이렇게 해야함..
			
			// ↱ 위에서 만든 ArrayList에 게시글 추가해 놓기 (b_idx 비교해서 hit수 계속 증가하는거 막기 위해)
			v_list.add(vo);
		}
		
		
		ModelAndView mv = new ModelAndView();
		// ↱ 클릭한 게시글 저장
		mv.addObject("vo", vo);
		
		// ↱ ★포워드★로 이동하는 것으로 cPage변수를 view에서 사용가능 or mv.addObject로 추가해도 가능함
		if (vo.getBname().equals("BBS")) {
			mv.setViewName("bbs/view");			
		}else if (vo.getBname().equals("market")) {
			mv.setViewName("market/m_view");			
		}
		
		// ↱ 내가 임의로 추가한 코드임.. view.jsp에서 img src에서 사용가능하다고 생각함...
//		String path = application.getRealPath(uploadPath);
//		mv.addObject("path", path);
		
		// ↱ ★★수정을 눌렀을 때 edit.jsp에서 표현하기 위해 session에 저장함!!!~ (cf. ModelAndView는 리퀘스트에 저장됨...)
		session.setAttribute("bvo", vo);
		
		return mv;
	}
	
	
	@RequestMapping("/edit")
	public ModelAndView edit(BbsVO vo) throws Exception {
		ModelAndView mv = new ModelAndView();
		
		String c_type = request.getContentType();
		
		if (c_type != null && c_type.startsWith("multipart")) {
			
			MultipartFile mf = vo.getFile();
			if (mf != null && mf.getSize() > 0) {
				String path = application.getRealPath(uploadPath);
				
				String f_name = mf.getOriginalFilename();
				
				f_name = FileUploadUtil.checkSameFileName(f_name, path);
				
				mf.transferTo(new File(path, f_name));
				
				vo.setFile_name(f_name);
			}
			vo.setIp(request.getRemoteAddr());
			
			boolean res = b_dao.editBbs(vo);
			
			if (res) {
				mv.setViewName("redirect:/view?b_idx="+vo.getB_idx()+"&cPage="+vo.getcPage());
			}
		}else {
			mv.setViewName("bbs/edit");
		}
		
		return mv;
	}
		
}






