<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<link type="text/css" rel="stylesheet" href="css/common.css"/>
<link type="text/css" rel="stylesheet" href="css/login.css"/>
<link type="text/css" rel="stylesheet" href="css/bbs.css"/>
<link rel="stylesheet" href="css/summernote-lite.min.css"/>


</head>
<body>
<div id="wrap">
	<!-- 상단 영역 -->
	<div id="header">
		<h1>SK Together</h1>
		<ul class="gnb">
			<li><a href=""><span class="menu m01">기브유</span></a></li>
			<li><a href=""><span class="menu m02">위드유</span></a></li>
			<li><a href=""><span class="menu m03">스마트 전통시장</span></a></li>
			<li><a href=""><span class="menu m04">BRAVO!</span></a></li>
			<li><a href=""><span class="menu m05">SKT와 사회공헌</span></a></li>
		</ul>
	</div>
	<!-- 상단 영역 끝 -->

	<!-- 콘텐츠 영역 -->
	<div id="contents_sub">
	
		<h1 style="font-size: 30px; color: #000; margin-bottom: 20px;">공헌쓰기</h1>
		
		<div class="bbs_area" id="bbs">
			<!--             ↱ WriteController로 가서 @RequestMapping 찾아감 -->
			<form action="write_ok" method="post" encType="multipart/form-data" name="frm">
			
				<!-- <input type="hidden" name="type" value="write"/> -->
				<!--   ↳위에 input hidden은 multipart라 request.getParameter으로 못받음 Controller로 전달 하지 못하므로 의미가 없음 -->
				
				<input type="hidden" name="bname" value="${param.bname }"/>
				<table summary="게시판 글쓰기">
					<caption>게시판 글쓰기</caption>
					<tbody>
						<tr>
							<th>제목:</th>           <!-- ↱ BbsVO의 변수 이름이랑 같아야 함 -->
							<td><input type="text" name="subject" size="45"/></td>
						</tr>
					<!-- 	<tr>
							<th>이름:</th>
							<td><input type="text" name="writer" size="12"/></td>
						</tr> -->
						<tr>
							<th>내용:</th>
							<td><textarea name="content" id="content" cols="50" rows="8"></textarea></td>
						</tr>
						
<!-- 												
 					 	<tr>
							<th>첨부파일:</th>
							<td><input id="m_img" type="file" name="file"/></td>
						</tr>
						
					
 						<tr>
							<th>첨부된 이미지:</th>
							<td class="select_img"><img src=""></td>
						</tr>
 -->
 
						<tr>
							<td colspan="2">
								<input type="button" value="보내기" onclick="sendData()"/>
								<input type="button" value="다시"/>
								<input type="button" value="목록"/>
							</td>
						</tr>
					</tbody>
				</table>
				<input type="text" id="file_name" name="file_name" value=""/>
			</form>
		</div> <!-- class="bbs_area" 끝-->
		
	</div>
	<!-- 콘텐츠 영역 끝-->

	<!-- 하단 영역 -->
	<div id="footer">
		<div class="footer_area">
			<ul class="foot_guide">
				<li><a href="">개인정보취급방침</a></li>
				<li><a href="">웹회원 이용약관</a></li>
				<li><a href="">책임한계와 법적고지</a></li>
				<li><a href="">이메일 무단수집 거부</a></li>
			</ul>
			<address>
			 서울시 중구 을지로 몇가 번지
			 대표이사: 마루치 
			 고객상담: 국번없이 114 혹은 
			 02-1234-1234 (평일 09:00~17:00)
			</address> 
			<p class="copyright">
				Copyright (c) 2012 SK Teleccom.
				All rights reserved.
			</p>
		</div>
	</div>
	<!-- 하단 영역 끝 -->
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>

<script src="js/summernote-lite.min.js"></script>
<script src="js/lang/summernote-ko-KR.js"></script>


<script type="text/javascript">

	function sendData(){
/* 		for(var i=0 ; i<document.forms[0].elements.length ; i++){
			if(document.forms[0].elements[i].value == ""){
				alert(document.forms[0].elements[i].name+"를 입력하세요");
				document.forms[0].elements[i].focus();
				return;//수행 중단
			}
		}
	 */
		document.forms[0].submit();
	}
	
/*  	
	// ↱ 파일 첨부할 경우 이미지 바로 보이게 하기.
  	$("#m_img").change(function(){
		if(this.files && this.files[0]) {
			var reader = new FileReader;
			reader.onload = function(data) {
				$(".select_img img").attr("src", data.target.result).width(500);
			}
			reader.readAsDataURL(this.files[0]);
		}
	});
 */
	  
  	
	$(function () {
		console.log("SSSSS");
		$("#content").summernote({
			height:300,
			maxHeight:600,  //최대크기지정
			minHeight:200,
			tabSize:10,
			width: 800,
			focus: true,
			lang:"ko-KR",
			
			callbacks: {
				onImageUpload: function (files, editor) {
					console.log('yyyyyyy');
					for (var i = 0; i < files.length; i++) {
						sendFile(files[i], editor);
					}
				}
			}
		});
	});
	
	function sendFile(file, editor) {
		var frm = new FormData();
		
		frm.append("file", file)
		
		// ┌>비동기식 통신하기!!
		$.ajax({
			url:"write_summer",
			type:"post",
			data: frm,
			// ┌> contentType, processData를 지정해 줘야 일반적인 문자열을 보내는 것이 아닌 -> 파일을 보내는 것이라는 것을 알려줄 수 있다.
			contentType: false,
			processData: false,
			dataType:"json",
			
		}).done(function(res) { //성공시 (이미지 주소를 content에 넣어주기)
			console.log(res.img_url);
		     //┌> jQuery로 img 태그 만듦
			var image = $("<img>").attr("src", res.img_url);  // <-img태그 생성됨
		                           //└>속성 부여
		     //┌>html id가 content인 내용안에 넣어주기 
		    //$("#content").summernote("insertNode", image[0]);
		                               //└>(insertNode를 적으면<textarea rows="12" cols="50" id="content" name="content">!!insertNode!!열로 들어감</textarea>)
		    
		    
		    //                            ┌>img 태그를 알아서 만들어서 넣어줌
			$("#content").summernote("editor.insertImage", res.img_url);
			$("#file_name").val(res.f_name);
		}).fail(function(err) { //실패시
			console.log(err);
		});
	} 
 
	 
	
</script>
</body>
</html>



