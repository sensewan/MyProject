package mybatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mybatis.vo.BbsVO;

@Component
public class BbsDAO {
	
	@Autowired
	private SqlSessionTemplate sst;
	
	// 페이징을 위한 목록 생성기능
	public BbsVO[] getList(int begin, int end, String bname) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		map.put("bname", bname);
		map.put("begin", String.valueOf(begin));
		map.put("end", String.valueOf(end));
		
		List<BbsVO> list = sst.selectList("bbs.list", map); 
		
		//받은 list를 배열로 전환하기
		BbsVO[] ar = null;
		
		if(list != null && list.size() > 0) {
			ar = new BbsVO[list.size()];
			
			list.toArray(ar);
		}

		return ar;	
	}
	
	
	// 총게시물 수 구하기
	public int totalCount(String bname) {
		
		int cnt = sst.selectOne("bbs.totalCount", bname);
		
		return cnt;
	}
	
	
	// 게시판 원글 저장하기
	public void add(String subject, String writer, String content, String fname, String ip, String bname) {
		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("subject", subject);
		map.put("writer", writer);
		map.put("content", content);
		map.put("file_name", fname);
		map.put("ip", ip);
		map.put("bname", bname);
		
		int cnt = sst.insert("bbs.add", map);
//		if (cnt > 0) {
//			sst.commit();
//		}else {
//			sst.rollback();
//		}
		
		
	}
	
	// 게시판 원글 저장하기 (vo로 받아서 하는 것임) ★★스프링이라 가능하다!!★★
	public void add2(BbsVO vo) {
		
		
		int cnt = sst.insert("bbs.add2", vo);
//		if (cnt > 0) {
//			sst.commit();
//		}else {
//			sst.rollback();
//		}
		
		
	}

	// 글 클릭했을 때 보기 기능
	public BbsVO getBbs(String b_idx) {
		
		BbsVO vo = sst.selectOne("bbs.getBbs", b_idx);
		
		
		return vo;
	}
	
	
	// 글 수정하기
	public boolean editBbs(BbsVO vo) {
		
		boolean value = false;
		
		// 파일첨부 되었을 때만 파일명을 DB에 저장할거임 / 만약 첨부된 파일이 없다면 -> 기존 파일을 유지할거임
		
		int cnt = sst.update("bbs.edit", vo);
		
		if (cnt > 0) {
			value = true;
		}
		
		return value;
		
	}
	
	
	// 삭제하기
	public int delBbs(String b_idx) {
		
		int cnt = sst.update("bbs.del", b_idx);
		
//		if (cnt > 0) {
//			sst.commit();
//		}else {
//			sst.rollback();
//		}
		return cnt;
	}
	
	
	// 조회수 증가하기
	public boolean updateHit(String b_idx) {
		int cnt = sst.update("bbs.update_hit", b_idx);
		
		if (cnt > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	
	
	
}






