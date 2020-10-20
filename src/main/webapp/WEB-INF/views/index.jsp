<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- JSTL에서 함수 사용하기 -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main html</title>

<!-- 외부 css파일 연결 -->
<link rel="stylesheet" type="text/css" href="css/common.css"/>
<link rel="stylesheet" type="text/css" href="css/main.css"/>

</head>
<body>
	<div id="wrap">
		<!-- ┌상단 영역 -->
		<div id="header">
			<div class="txt_right">
			 
			<c:if test="${!login_chk }">
			                 <!-- ↱IndexController.java로 이동함 -->			
				<span><a href="login">로그인</a></span>
			</c:if>
			<c:if test="${login_chk }">
			                    <!-- ↱post방식으로 하기!! -->
				<span><a href="javascript:logout()">로그아웃</a></span>
			</c:if>
			</div>
			<h1>SK Together</h1> <!-- img src로 할 수 있지만 그러면 시각장애인들이 읽지를 못한다. -->
			<ul class="gnb">
				<li><a href=""><span class="menu m01">기브유</span></a></li>
				                        <!-- └>클래스를 2개 지정한 것임 -->
				<li><a href=""><span class="menu m02">위드유</span></a></li>
				<li><a href=""><span class="menu m03">스마트 전통시장</span></a></li>
				<li><a href=""><span class="menu m04">BRAVO!</span></a></li>
				<li><a href=""><span class="menu m05">SKT와 사회공헌</span></a></li>
				<li class="end"></li>
			</ul>
		</div>
		<!-- └상단 영역 끝 -->
		
		
		<!-- 콘텐츠 영역 -->
		<div id="contents">
			<div class="main_img">
				<a href="">
					<img src="img/@img00.png" />
				</a>
			</div>
			
			<div class="main_news"> <!-- 바탕임 /뉴스들(3개)을 하나로 묶어 놓는 영역이다.-->
				<div class="news_type01 fl">  <!-- 클래스 2개 선언한거다. -->
					<p class="title">기브유 후원참여</p>
					<a href="" class="news_src">  <!-- 클릭하면 연결될 수 있게 -->
						<span class="thum_img">
							<img src="img/@img01.png" alt="기사사진"/>
						</span>
						<span class="ellip subject">  <!-- 글이 너무 길어서 ...으로 줄일거임 -->
							난청이지만 피아니스트가 되고 싶은
							한별이의 이야기 입니다.
						</span>
						<span class="writer">by Ttogether</span>
						<span class="more_view">자세히 보기</span>
						<span class="fclear"></span>  <!-- float를 종료하기 위해 만듦 -->
					</a>
				</div>
				
				<div class="news_type01 cen">  <!-- 클래스 2개 선언한거다. -->
					<p class="title">기브유 후원금 쓰임현황</p>
					<a href="" class="news_src">  <!-- 클릭하면 연결될 수 있게 -->
						<span class="thum_img">
							<img src="img/@img02.png" alt="기사사진"/>
						</span>
						<span class="ellip subject">  <!-- 글이 너무 길어서 ...으로 줄일거임 -->
							레티하씨 가정에 희망의 집 선물
						</span>
						<span class="writer">by 한국해비타트</span>
						<span class="more_view">자세히 보기</span>
						<span class="fclear"></span>  <!-- float를 종료하기 위해 만듦 -->
					</a>
				</div>
				
				<div class="news_type01 fr">  <!-- 클래스 2개 선언한거다. -->
					<p class="title">기브유 나눔영상</p>
					<a href="" class="news_src">  <!-- 클릭하면 연결될 수 있게 -->
						<span class="thum_img">
							<img src="img/@img03.png" alt="기사사진"/>
							<span class="btn_play"title="동영상 재생">  <!-- 플레이 버튼을 넣기 위해 만 -->
								<!-- <a href=""></a> --> <!-- 클릭했을 때 이동할 수 있 -->
							</span>
						</span>
						<span class="ellip subject">  <!-- 글이 너무 길어서 ...으로 줄일거임 -->
							1리터의 생명을 선물해주세요.
						</span>
						<span class="writer">by hungersaver</span>
						<span class="more_view">자세히 보기</span>
						<span class="fclear"></span>  <!-- float를 종료하기 위해 만듦 -->
					</a>
				</div>
				
			</div> <!-- class="news_type01 fl" -->

			<div class="main_board">
				<!-- 공지사항 -->
				<div class="board_type01 fl">
					<p class="title">공지사항</p>
					<span class="more_view"><a href="Controller?type=notice">더보기</a></span>
					<ul class="notice">
					<c:if test="${ar != null }">
						<c:forEach var="mm" items="${ar }">
						<li>
							<a href="" class="ellip">${mm.content }</a>
							                 <!-- ↱ JSTL 함수 사용하기 -->
							<span class="date">${fn:substring(mm.reg_date, 0, 10) }</span>
						</li>
						</c:forEach>
					</c:if>
					
					<c:if test="${ar == null }">
						<li>등록된 공지가 없습니다.</li>
					</c:if>
					</ul>
				</div>
				<!-- 공지사항 끝-->
				
				<!-- 트위터 -->
				<div class="board_type01 cen">
					<p class="title">T-Together트위터</p>
					<span class="more_view"><a href="">더보기</a></span>
					<div class="article">
						<span class="thum_img">
							<img src="img/@img04.png" />
						</span>
						<span class="src">
							[캠페인] 햇살이 따스한 가을날이 다가옵니다.
							그리고 겨울이 온다 [존 스노우]
						</span>
						<div class="fclear"></div>  <!-- float종료하는 fclear 미리 만들어 놓은거 사용 -->
					</div>
				</div>
				<!-- 트위터 끝 -->
		
				<!-- 배너 -->
				<div class="board_type01 fr">
					<span class="banner b01">
						<a href="">
							T-Together와 함께할 기관/단체를 모십니다.
						</a>
					</span>
					<span class="banner b02">
						<a href="">
							T-Together 이제는 모바일로 함께해요.
						</a>
					</span>
				</div>
				<!-- 배너 끝 -->
		
			</div>  <!-- main_board -->

		</div> <!-- class="main_board" -->
		<!-- 콘텐츠 영역 끝 -->

		<!-- 하단 영역(footer) -->
		<div id = "footer">
			<div class="footer_area">
				<ul class="foot_guide">
					<li><a href="">개인정보취급방침</a></li>
					<li><a href="">회원이용약관</a></li>
					<li><a href="">책임한계와 법적고지</a></li>
					<li><a href="">이메일 무단수집 거부</a></li>
				</ul>
				<address>
					서울특별시 강남구 강남대로 1번지
					대표이사: 박승완
					고객상담: 국번없이 001 혹은 02-1234-1234 (평일 9:30 ~ 17:00)
				</address>
				<p class="copyright">
					copyright (c) 2020 SK Telecom. All rights reserved.
				</p>
			</div>
		</div>
		<!-- 하단 영역 끝 (footer) -->
	</div>
	<!-- ===================================================== -->
	
	<!-- ↱로그아웃을 post방식으로 보내기 위해 만듦 -->
	<form action="Controller" method="post" name="frm">
		<input type="hidden" name="type">
	</form>
	
	<script type="text/javascript">
		function logout() {
			//현재 문서에서 이름이 frm인 폼을 알아내어 -> 그안에 있는 요소들 중 
			// 이름이 type인 요소(value)의 값을 "logout"으로 지정하기
			document.frm.type.value = "logout";
			document.frm.submit();
		}
	</script>
</body>
</html>