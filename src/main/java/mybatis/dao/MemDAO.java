package mybatis.dao;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import mybatis.vo.MemVO;

public class MemDAO {
	
	@Autowired
	private SqlSessionTemplate sst;
	
	public MemVO login(String m_id, String m_pw) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("m_id", m_id);
		map.put("m_pw", m_pw);
		
		MemVO vo = sst.selectOne("mem.login", map);
		
		return vo;
	}
	
	
	
}
