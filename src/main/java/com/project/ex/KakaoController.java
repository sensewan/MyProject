package com.project.ex;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import mybatis.vo.MemVO;

@Controller
public class KakaoController {
	
	@Autowired
	private HttpSession session;
	

	@RequestMapping("/kakao_login")
	public ModelAndView kakaoLogin(String code) {
		// 카카오 서버에서 인증 코드를 전달해 주는 곳임.
		
		ModelAndView mv = new ModelAndView();
		
		
		// 1.인증코드를 인자(String code)로 받는다.
		mv.addObject("code", code);
		
		//System.out.println("카카오인증코드-> "+code);
		
		// 2.받은 인증 코드를 다시 카카오에 보내 -> 이번에는 토큰을 받아야 한다. (POST방식)
		String access_Token  = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";
		
		try {
			URL url = new URL(reqURL);  // 토큰 받기 위한 주소 연결하기
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // 연결함
			
			// ↱POST 방식으로 요청하기 위해 setDoOut을 true로 지정해야 한다.
			conn.setRequestMethod("POST");  // 요청 방식 설정하기!
			conn.setDoOutput(true);   // POST방식으로 보내겠다는 뜻임
			
			// ↱카카오가 원하는 인자4개(grant_type, client_id, redirect_uri, code)를 만들어서 서버로 보내야함
			StringBuffer sb= new StringBuffer();
			sb.append("grant_type=authorization_code&client_id=ff8e46244db2f93a41ed12dafcdd41e5");
			sb.append("&redirect_uri=http://localhost:8080/kakao_login");
			sb.append("&code="+code);
			
			// ↱전달해야하는 파라미터들을 보내기위해 스트림 작업하기 (무조건 문자열만 보내기 때문에 BufferedWriter 됨)
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream())); // conn과 연결된 BufferedWriter가 만들어짐
			bw.write(sb.toString());  // 인자4개를 POST방식으로 보냄!!(즉 토큰 받기 위해 https://kauth.kakao.com/oauth/token 으로 인자 4개보냄)
			bw.flush(); // 보냈으므로 스트림 비우기
			
			
			// ↱결과 코드가 200번이면 성공이다.
			int res_code = conn.getResponseCode();
			//System.out.println("res_code즉 코드성공확인-> "+res_code);
			
			// ↱요청이 성공일 경우에 수행
			if (res_code == 200) {
				// 요청을 통해 얻은 JSON타입의 결과 메세지를 읽어올거임(즉 토큰을 얻어낼거임)
				BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				// ↳ conn이 InputStream만 갖고 있음 즉 BufferedReader는 크기가 2개임 근데 InputStream은 크기가1임 즉 크기가 서로 다르기 때문에 
			    //   서로 크기를 이어주는 InputStreamReader를 생성해서 2개를 이어주는 것이다.) (위에 BufferedWriter 적용한것도 마찬가지임)
				
				String line = "";  // 한줄씩 읽을거임?
				StringBuffer result = new StringBuffer();  //json을 result에 저장할거임
				
				//     ↱ line에 한 줄씩 주세요. 더이상 줄게 없으면 null이 됨
				while((line = br.readLine()) != null) {
					result.append(line);  // line에 한 줄씩 받은 것을 result 스트링버퍼에 추가하기
				} // while문 끝
				
				br.close();
				bw.close();
				
				System.out.println("받은 json확인-> "+result.toString());
				
				// ↱JSON파싱 처리하기!!~(라이브러리 이용)
				//  "access_token"과 "refresh_token"을 잡아내어 ModelAndView에 저장한 후  -> result.jsp로 이동하여 결과를 표현할거임
				JSONParser j_par = new JSONParser();
				Object obj = j_par.parse(result.toString());
				
				// ↱자바에서 편하게 사용할 수 있도록 JSON객체로 변환할거임
				JSONObject j_obj = (JSONObject) obj;
				
				access_Token = (String) j_obj.get("access_token");
				refresh_Token = (String) j_obj.get("refresh_token");
				
				
				// 3. 사용자 정보를 얻기 위해 -> 얻은 토큰을 이용하여 다시 한번 서버에 요청하기
				String header = "Bearer "+access_Token;
				String apiURL = "https://kapi.kakao.com/v2/user/me";
				
				URL url2 = new URL(apiURL); // 주소 연결 설정
				HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection(); // 연결
				
				// POST방식 설정
				conn2.setRequestMethod("POST");
				conn2.setDoOutput(true);
				
				conn2.setRequestProperty("Authorization", header); // header가지고 통신함
				
				// ↱ 통신 성공코드 200 들어감
				res_code = conn2.getResponseCode(); // ↱200쓴거랑 같은 거임
				
				System.out.println("로그인확인!!-> "+res_code);
				if (res_code == HttpURLConnection.HTTP_OK) {
					// 정상적으로 사용자 정보를 요청한경우임
					
					// 사용자 정보를 읽어오기
					BufferedReader brd = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
					
					StringBuffer sb2 = new StringBuffer();
					String str = null;
					while ((str=brd.readLine())!=null) {
						sb2.append(str);  //카카오 서버에서 전달되어온 모든 값들이 sb2에 저장된다 (JSON형태임)
					} //while문 
					
					    // JSON을 파싱하여 인식시킴 (현재 obj로만 얻어 낸거임 -> json object로 바꿔줘야함)
					obj = j_par.parse(sb2.toString());
					
					// JSON으로 인식된 정보를 다시 JSON객체로 형변한 한다.
					j_obj = (JSONObject) obj;
					
					//properties 정보만 갖고옴
					JSONObject n = (JSONObject) j_obj.get("properties"); 
					
					String name = (String) n.get("nickname");
					String p_img = (String) n.get("profile_image");
					
					
					// 로그인 정보 세션에 저장하기
					MemVO mvo = new MemVO();
					mvo.setM_name(name);
					
					session.setAttribute("mvo", mvo); // 로그인 정보 저장!!
					
					
					mv.addObject("nickname", name);
					mv.addObject("pic", p_img);
					
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		mv.setViewName("kakao_result");
		
		return mv;
	}
}





