package spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// ↱ 로그인 체크하기!~                     ↱ 로그인된 session이 있으면 그대로 주고 session이 없으면 새로 만들어 주세요
		HttpSession session = request.getSession(true);   // false라고 하면 -> session이 없을 때 null값 들어감
		
		// ↱ 로그인시 저장했던 객체를 얻어낸다.
		Object obj = session.getAttribute("mvo");  // obj가 있으면 true를 반환한다.(즉 true면 계속 쭉 진행하라는 뜻임)
		
		if(obj == null) {
			response.sendRedirect("login");  //로그인 화면으로 이동
			return false;
		}
		
		// ↱ if문 안거치고 여기 온거면 로그인 된 상태이므로 true 반환해주면 된다.
		return true;
	}
	
}
