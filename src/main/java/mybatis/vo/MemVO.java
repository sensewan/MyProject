package mybatis.vo;

public class MemVO {
	
	// 넘어올 파라미터 name과 같은 이름으로 선언을 해줘야함  (만약에 m_name이 안 넘어오면 그냥 null값이 저장됨)

	private String m_id, m_pw, m_name;

	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_pw() {
		return m_pw;
	}

	public void setM_pw(String m_pw) {
		this.m_pw = m_pw;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}
	
	
	
}
