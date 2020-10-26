package mybatis.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.MemVO;

@Component
public class MemDAO {
	
	@Autowired
	private SqlSessionTemplate sst;
	
	
	public MemDAO() {
		System.out.println("DAO생성!~~~");
	}
	
	
	// 로그인을 수행하는 기능 - MemberController
	public MemVO login(String m_id, String m_pw) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("m_id", m_id);
		map.put("m_pw", m_pw);
		
		MemVO vo = sst.selectOne("mem.login", map);
		
		return vo;
		
//		return sst.selectOne("mem.login", map);
	}
	
	
	
}
