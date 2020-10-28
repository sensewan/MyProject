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
			<li><a href=""><span class="menu m03">스마트 전통시장</span></a></li>
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
						<th class="no">번호</th>
						<th class="subject">제목</th>
						<th class="writer">글쓴이</th>
						<th class="reg">날짜</th>
						<th class="hit">조회수</th>
					</tr>
				</thead>
				
				<tbody>
				
				<c:if test="${ar != null }">
					<c:forEach var ="aa" items="${requestScope.ar }" varStatus="st">
					<tr>
						<td>${rowTotal - st.index -(blockList*(nowPage - 1)) }</td>
						<td style="text-align: left">
						                         <!-- ↱나중에 뒤로 가기 눌렀을 때 필요-->
							<a href="view?cPage=${nowPage }&b_idx=${aa.b_idx}">
						${aa.subject }                            <!-- ↳ 클릭한 게시물을 보여주기 위해 필요 -->
						</a></td>
						<td>${aa.writer }</td>
						<td>
						<c:if test="${aa.write_date ne null }">
							${fn:substring(aa.write_date, 0, 10) }
						</c:if>
						</td>					
						<td>${aa.hit }</td>
					</tr>
					</c:forEach>
				</c:if>
				
				<c:if test="${ar == null }">
					<tr>
						<td colspan="5" class="empty">등록된 게시물이 없습니다.</td>
					</tr>
				</c:if>
	
				</tbody>

				<tfoot>
					<tr>
						<td colspan="4">
							<!-- <ol class="paging"> --> 
							${p_code }
						</td>


						<!--                  ↱작다는 표시 (아래것을 StringBuffer에 저장해 놓을거임) -->
						<!-- <li><a href="#">&lt;</a></li>

							<li class="now">1</li>
							        
							<li><a href="#">2</a></li>
							
							<li><a href="#">&gt;</a></li>	
	                        	</ol> -->

						<td>
							<!-- ↱ 비동기통신 하기 --> 
							<input type="button" value="글쓰기" id="write_btn" />
						</td>
					</tr>
				</tfoot>

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


<script type="text/javascript">

$(function () {

	$("#write_btn").bind("click", function () {

		$.ajax({
			url: "write",
			dataType:"JSON"
			
		}).done(function(data) {
			//console.log(data.chk);
		 	if (data.chk == "0") {
				alert("로그인 하셔야 합니다.");
				//location.href="login";
			}else if (data.chk =="1") {
				location.href=data.url;
			}
		
		});
		
	});
});

</script>
</body>
</html>



