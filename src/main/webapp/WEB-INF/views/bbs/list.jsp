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
				
				<tfoot>
	                      <tr>
	                          <td colspan="4">
	                          	<!-- <ol class="paging"> -->
	                          	${p_code }
	                          </td>
	                                  
	               
	<!--             작다는 표시 (아래것을 StringBuffer에 저장해 놓을거임) -->
	<!-- <li><a href="#">&lt;</a></li>
	
		<li class="now">1</li>
	         
		<li><a href="#">2</a></li>
	 
			<li><a href="#">&gt;</a></li>	
	                            </ol> -->
	                              
							  <td> 
								<input type="button" value="글쓰기" onclick="javascript:location.href='Controller?type=write'"/>
							  </td>
	                      </tr>
	                  </tfoot>
				<tbody>
				
				<c:if test="${ar != null }">
					<c:forEach var ="aa" items="${requestScope.ar }" varStatus="st">
					<tr>
						<td>${rowTotal - st.index -(blockList*(nowPage - 1)) }</td>
						<td style="text-align: left">
						                                      <!-- ↱나중에 뒤로 가기 눌렀을 때 필요-->
							<a href="Controller?type=view&cPage=${nowPage }&b_idx=${aa.b_idx}">
						${aa.subject }                                           <!-- ↳ 클릭한 게시물을 보여주기 위해 필요 -->
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


	$(function() {
		// ↱ id가 login_btn인 요소 클릭 했을때 이벤트 발생
		$("#login_btn").bind("click", function() {
			var id = $("#s_id").val();
			var pw = $("#s_pw").val();
			
			// ↱하나도 입력하지 않은 경우 및 공백만 입력한 경우.
			if (id.trim().length < 1) {
				alert("아이디 입력하시오.");
				$("#s_id").val(""); //초기화
				$("#s_id").focus();
				return;
			}
			
			if (pw.trim().length < 1) {
				alert("아이디 입력하시오.");
				$("#s_pw").val(""); //초기화
				$("#s_pw").focus();
				return;
			}
			
			//console.log(id+"/"+pw);
		
			 $.ajax({
				url: "login",
				type: "post",
				data: "m_id="+encodeURIComponent(id)+"&m_pw="+encodeURIComponent(pw),
				dataType: "JSON"
			}).done(function(data) {
				if (data.res == "0") {
					alert("아아디 비번 제대로 입력하시오!~");
					//location.reload();
				}else {
					alert(data.mvo.m_name+"님 환영합니다. 처음화면으로 이동합니다.")
					location.href="index";
				}
				
			}).fail(function(err) {
				console.log(err.statusText);
			}); 
		});
	});  
	
	
/* 	$(document).ready(function() {
		$("#login_btn").on('click', function() {
			loginChk();
		})
	});
	
	function loginChk() {
		$.ajax({
			url: "loginChk",
			type: "post",
			data: {m_id:$("#s_id").val(), m_pw:$("#s_pw").val()},
			dataType: "JSON"
		}).done(function(data) {
			if (data.fa == "fail") {
				alert(data.fa)
				//location.reload();
			}else {			
				location.href = "index";
			}
		}).fail(function(err) {
			console.log(err.statusText);
		});

	} */
	
	
	
/* 	function exe() {
		//var id = document.forms[0].id.value();
		var id = document.getElementById("s_id").value;
		var pw = document.getElementById("s_pw").value;
		
		if(id.length < 4){
			alert("아이디를 4자리이상 입력하시오");
			return;
		}
		
		if (pw.length < 2) {
			alert("비밀번호를 제대로 입력하시오");
			return;
		}
		document.forms[0].submit();
		
		
	} */


	
	
</script>
</body>
</html>



