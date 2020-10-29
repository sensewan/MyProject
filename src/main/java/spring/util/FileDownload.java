package spring.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownload
 얼래는 @WebServlet("FileDownload") 가 적혀 있어야함 -> 스프링이므로 -> web.xml에 servlet으로 등록해 줘야 한다.
 */
public class FileDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ↱파일 다운로드 하기 위한 파라미터 값들 받기 (dir, filename)
		String dir = request.getParameter("dir");
		String filename = request.getParameter("filename");
		
		// ↱dir을 절대경로로 만든다. 내장객체인 ServletContext application이 필요함. (servlet이라 자동으로 갖고 있음)
		String path = getServletContext().getRealPath(dir);
		
		// ↱앞에서 얻어낸 upload(dir)의 절대경로와 파일명을 연결하면 -> 전체경로가 된다.
		String fullPath = path + System.getProperty("file.separator")+ filename;
		                         // ↳ "/"이런걸 의미
		// ↱전체경로를 가지고 File객체를 생성한다.
		File f = new File(fullPath);
		
		
		// ↱바구니 역할 만들기
		byte[] buf = new byte[2048];  // cf. byte[] buf = new byte[(int)f.legtn()];  파일크기랑 아에 똑같이 만들기
		
		// ↱전송할 데이터가 Stream처리하기 위해서는 문자셋을 지정해야함 (서버입장에서 유저한테 보내기?)
		response.setContentType("application/octet-stream;charset=8859_1");
		
		
		// ↱다운로드 대화상자 처리하기                                     ↱파일네임이 한글인 경우 깨지지 않게 함(한글이름을 byte단위로 쪼갬)
		response.setHeader("Content-Disposition", "attachment;filename="+new String(filename.getBytes("utf-8"),"8859_1"));
		                                                                           // ↳java에서 utf-8로 변경해서 사용후 웹에다가는 8859_1로 다시 변경해서 보냄
		
		// ↱전송타입이 이진데이터 (binary)  (파일을 보낼 경우 다 쪼개서 보내야 함)
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		
		// ↱위에 설정 끝남 이제 진짜 파일 다운로드하기
		if (f.isFile()) {
			                                                    // ↱서버에 있는 파일을 읽을 준비!!가 되었음
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
			
			// ↱요청한 곳으로 보내기 위해 (즉 응답을 stream으로 줄거임) stream을 응답객체(response)로 부터 얻어내면 된다.
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			                                                     // ↳보내줄 곳 얻음
			
			int size = -1;
			try {
				// 파일을 읽어서 보내기
				while((size= bis.read(buf)) != -1) {
					bos.write(buf, 0, size); // 읽은 만큼 쓰기하라는 의미
					bos.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (bos != null) 
					bos.close();
				if (bis != null)
					bis.close();
			}
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
