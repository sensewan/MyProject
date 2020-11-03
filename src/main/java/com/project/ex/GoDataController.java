package com.project.ex;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import kr.go.vo.DataVO;

@Controller
public class GoDataController {
	
	// ↱ 멤버변수로 이용해야만 만들어 놓은 ar가지고 viewData가서 사용할 수 있음
	private DataVO[] ar;

	@RequestMapping("/goData")
	public ModelAndView data() throws Exception {   //public String data(Model model){ 이렇게도 가능하긴 함

		// ↱REST API 서버의 URL을 객체로 만들어 놓을거임 (즉 공공데이터의 주소임) (아직 연결은 안됨. 연결할 통로를 만들어 놓은것임)
		URL url = new URL("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey=a6I2uPlzirpaL6hi4R8DpR3mgATg8SnvgxXFA6tUxSPXCrpz5DdGamLumDL1nNMHxMW73W%2BXoFzUPhp3IyAZXA%3D%3D&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&eventStartDate=20201101&numOfRows=7");
		
		// ↱자바에서는 외부 자원을 읽거나 쓰기를 할 때 반드시 -> 스트림 작업을 해야한다.
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				                 // ↳ url.opeConnetion()하면 urlConnection이 나옴 근데 우리는 웹을 사용하므로
				                 // ↳ HttpURLConnection(자식임)을 사용할 거임 그러므로 캐스팅 해줘야함

		//                                        ↱ xml로 인식
		conn.setRequestProperty("Content-Type", "application/xml");  // MIME Type 임
		
		conn.connect();  // 연결 및 요청!!!
		
		// ↱ xml 읽을 준비하기
		SAXBuilder builder = new SAXBuilder();
		
		Document doc = builder.build(conn.getInputStream()); 
		             // ↳ 이렇게 하면 conn과 saxbuilder의 조합으로 xml 문서를 읽어서 doc에 갖다놓음
		
		Element root = doc.getRootElement(); // 루트인 <respone> 얻음
		
		// response안에 body/items/item 들이 필요하므로 얻어내자 (즉 body를 얻어서 그안에 있는 items를 얻고 items 안에 있는 item들을 얻을 거임)
		Element body = root.getChild("body");
		Element items = body.getChild("items");
		
		// ↱ items안에 있는 여러 개의 item들을 얻어낸다.
		List<Element> e_list = items.getChildren("item");
		
		
		// ↱ e_list에 있는 것은 Element이므로 이것을 List<DataVO>로 또는 DataVO[]로 바꿔야 -> JSP에서 표현할 수 있다.	
		ar = new DataVO[e_list.size()];
		int i = 0;
		for (Element e : e_list) {
			String addr1 = e.getChildText("addr1"); 
			String addr2 = e.getChildText("addr2"); 
			String eventenddate = e.getChildText("eventenddate"); 
			String eventstartdate = e.getChildText("eventstartdate"); 
			String firstimage = e.getChildText("firstimage"); 
			String firstimage2 = e.getChildText("firstimage2"); 
			String mapx= e.getChildText("mapx"); 
			String mapy = e.getChildText("mapy"); 
			String tel = e.getChildText("tel"); 
			String title = e.getChildText("title"); 
			
			// ↱ vo에 생성자를 만들어 놓았기에 편하게 vo에 다 넣어주기
			DataVO vo = new DataVO(addr1, addr2, eventenddate, eventstartdate, firstimage, firstimage2, mapx, mapy, tel, title);
			
			// ↱ ar에 넣어주기
			ar[i++] = vo;
			
		}// for의 끝
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", ar);
		
		mv.setViewName("tour/goData");
	
		return mv;
	}
	
	@RequestMapping("/viewData")
	public ModelAndView viewData(int idx){
		ModelAndView mv = new ModelAndView();
		
		System.out.println(idx);
		
//		DataVO vo = ar[idx]; 이렇게도 가능..
		
		mv.addObject("vo", ar[idx]);
		
		mv.setViewName("tour/viewData");
		
		
		return mv;
	}
	
}




