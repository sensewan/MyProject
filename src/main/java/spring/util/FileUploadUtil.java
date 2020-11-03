package spring.util;

import java.io.File;

// 일반적인 클래스를 선언한거임..
public class FileUploadUtil {
	
	public static String checkSameFileName(String filename, String path) {

		// ↱인자인 filename에서 확장자를 뺀 파일명을 알아내기 위해 -> 파일명에 있는 "."의 위치를 알아내자.
		int period = filename.lastIndexOf(".");
		
		// ↱파일명과 확장자를 분리하여 -> 파일명만 얻기	
		String f_name = filename.substring(0, period);  
		
		// ↱확장자 얻음 (예: .jpeg)
		String suffix = filename.substring(period);
		
		
		// ↱전체경로 + \파일명 넣기     ↱파일 구분자 넣어줌      이렇게 하는 이유는 같은이름의 파일네임이 있을경우 파일네임 변경할려고.
		String saveFile = path + System.getProperty("file.separator")+ filename;
		//System.out.println("파일경로!!!->"+saveFile);
		
		
		// ↱위의 경로(path + filename) 를 가지고 존재여부를 확인하기 (java.io.File이용!!)
		File f = new File(saveFile);  // 경로에 있는 파일을 가상적으로 만듦 (있을수도 있고 없을 수도 있지)
		
		// ↱파일이 존재할 경우 -> 이름을 변경시킬 때 사용할 숫자를 만들어야 함
		int idx = 1;
		// ↱while문으로 하는 이유는 같은 파일이름인지 계속 확인하기 위해?..
		while (f.exists()) { //같은 이름으로 파일이 존재하는 경우
			//파일명 뒤에 숫자를 붙여 파일명을 변경한다.
			StringBuffer sb = new StringBuffer();
			sb.append(f_name);
			sb.append("(");
			sb.append(idx++);
			sb.append(")");
			sb.append(suffix);
			
			filename=sb.toString(); // 변경한 파일이름
			
			saveFile = path + System.getProperty("file.separator")+ filename;
			
			// ↱ 파일명 바꾼 파일을 새로 만듦
			f = new File(saveFile);
			
		}// while문 끝
		
		return filename;  //중복되지 않은 파일명을 반환함
	}
	
	
}
