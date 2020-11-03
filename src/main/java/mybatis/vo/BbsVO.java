package mybatis.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class BbsVO {
	// 만약 vo로 파라미터를 받을 거면 jsp의 name과 같아야 한다.!!
	
	// 원글 게시판이 저장되는 bbs테이블의 각 레코들을 객체화시키는 클래스다.
	// bname은 어떠한 게시판인지 알기 위해서
	private String b_idx,      // 글 고유번호
					subject,
					writer,
					content,
					file_name,   // 파일 첨부할 경우이름
					write_date,
					ip,
					hit,          // 조회수
					status,       // 회원이 게시글을 삭제할경우 실제로 지우지는 않고 삭제 되었는지 안 되었는지 확인 하기 위해만듦
					bname;        // 게시판의 종류 (Q&A, 1:1게시판, 공지사항 등)
	
	// ↱ 파일 첨부한 것을 받을 수 있다.(★★servlet-context.xml★★ 에 추가해 놓았기 때문에 가능한 것이다!!)
	private MultipartFile file; 
	
	private int cPage;
	
	// ↱ 썸네일 이미지 저장. (내가추가..사용가능한지 모름)
	private String thum_img;
	
	

	// ↱ 원글에 댓글이 여러게 작성 할수 있으므로 list로 만들어 놓는다. (1:N 관계) 댓글을 보는 것이므로 댓글vo인 CommVO를 사용
	// ⎥ (bbs.xml의 commList에서 올거임) (mybatis에서 list로 주므로 반환형을 List로 해야함)
	private List<CommVO> c_list;  // mapper에서 resultmap을 사용해서 갖고올거임
	
	
	

	public String getThum_img() {
		return thum_img;
	}
	
	public void setThum_img(String thum_img) {
		this.thum_img = thum_img;
	}

	public int getcPage() {
		return cPage;
	}

	public void setcPage(int cPage) {
		this.cPage = cPage;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getB_idx() {
		return b_idx;
	}

	public void setB_idx(String b_idx) {
		this.b_idx = b_idx;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}

	public String getWrite_date() {
		return write_date;
	}

	public void setWrite_date(String write_date) {
		this.write_date = write_date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getHit() {
		return hit;
	}

	public void setHit(String hit) {
		this.hit = hit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public List<CommVO> getC_list() {
		return c_list;
	}

	public void setC_list(List<CommVO> c_list) {
		this.c_list = c_list;
	}
	
	

}
