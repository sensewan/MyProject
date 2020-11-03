<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터활용</title>
<link type="text/css" rel="stylesheet" href="css/common.css"/>
<link type="text/css" rel="stylesheet" href="css/login.css"/>

<style type="text/css">

		
</style>

</head>
<body>
<div id="wrap">
	<!-- 상단 영역 -->
	<div id="header">
		<h1>SK Together</h1>
		<ul class="gnb">
			<li><a href=""><span class="menu m01">기브유</span></a></li>
			<li><a href=""><span class="menu m02">위드유</span></a></li>
			<li><a href="market"><span class="menu m03">스마트 전통시장</span></a></li>
			<li><a href=""><span class="menu m04">BRAVO!</span></a></li>
			<li><a href=""><span class="menu m05">SKT와 사회공헌</span></a></li>
		</ul>
	</div>
	<!-- 상단 영역 끝 -->

	<!-- 콘텐츠 영역 -->
	<div id="contents_sub">
	
		<h1 style="font-size: 30px; color: #000; margin-bottom: 20px;">SKT와 사회공헌</h1>
		
		<div class="bbs_area" id="bbs">
			<table summary="게시판 목록">
				<caption>게시판 목록</caption>
				<thead>
					<tr class="title">
						<th class="no">이미지</th>
						<th class="subject">제목</th>
						<th class="writer">위치</th>
						<th class="reg">날짜</th>
						<th class="hit">전화번호</th>
					</tr>
				</thead>
				
				<tbody>
					<tr>
						<td>제목: ${vo.title }</td>
					</tr>
					<tr>
						<td>
							<img src="${vo.firstimage }"/>
						</td>
					</tr>
					<tr>
						<td>
							${vo.addr1 }, ${vo.addr2 }
						</td>
					</tr>
					
					<!-- 카카오맵 적용 -->
					<tr>
						<td>
							<div id="map" style="width:800px;height:400px;"></div>
						</td>
					
					</tr>
					

	
				</tbody>


				</table>
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
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=cdb0e08bc78164637f460112638cb43d"></script>

<script type="text/javascript">

	// ========카카카오 맵==================
	var container = document.getElementById('map');
	var options = {
		center : new kakao.maps.LatLng(${vo.mapy}, ${vo.mapx}),
		level : 3
	};

	var map = new kakao.maps.Map(container, options);
	
	// 마커가 표시될 위치입니다 
	var markerPosition  = new kakao.maps.LatLng(${vo.mapy}, ${vo.mapx}); 

	// 마커를 생성합니다
	var marker = new kakao.maps.Marker({
	    position: markerPosition
	});

	// 마커가 지도 위에 표시되도록 설정합니다
	marker.setMap(map);
	var iwContent = '<div style="padding:5px;">${vo.title} <br><a href="https://map.kakao.com/link/map/${vo.title},${vo.mapy},${vo.mapx}" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/${vo.title},${vo.mapy},${vo.mapx}" style="color:blue" target="_blank">길찾기</a></div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new kakao.maps.LatLng(${vo.mapy}, ${vo.mapx}); //인포윈도우 표시 위치입니다
	
	// 인포윈도우를 생성합니다
	var infowindow = new kakao.maps.InfoWindow({
	    position : iwPosition, 
	    content : iwContent 
	});
	  
	// 마커 위에 인포윈도우를 표시합니다. 두번째 파라미터인 marker를 넣어주지 않으면 지도 위에 표시됩니다
	infowindow.open(map, marker); 
	
	// ========카카카오 맵==================

	$(function() {

		$("#write_btn").bind("click", function() {

			$.ajax({
				url : "write",
				dataType : "JSON"

			}).done(function(data) {
				//console.log(data.chk);
				if (data.chk == "0") {
					alert("로그인 하셔야 합니다.");
					location.href = "login";
				} else if (data.chk == "1") {
					location.href = data.url + "?bname=${bname}";
				}

			});

		});
	});
</script>
</body>
</html>



