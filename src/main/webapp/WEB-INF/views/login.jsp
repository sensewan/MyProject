<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<link type="text/css" rel="stylesheet" 
href="css/common.css"/>
<link type="text/css" rel="stylesheet" 
href="css/login.css"/>
</head>
<body>
<div id="wrap">
	<!-- 상단 영역 -->
	<div id="header">
		<h1>SK Together</h1>
		<ul class="gnb">
			<li><a href=""><span class="menu m01">기브유</span></a></li>
			<li><a href="goData"><span class="menu m02">위드유</span></a></li>
			<li><a href="bbs?bname=market"><span class="menu m03">스마트 전통시장</span></a></li>
			<li><a href=""><span class="menu m04">BRAVO!</span></a></li>
			<li><a href="bbs"><span class="menu m05">SKT와 사회공헌</span></a></li>
		</ul>
	</div>
	<!-- 상단 영역 끝 -->

	<!-- 콘텐츠 영역 -->
	<div id="contents_sub">
		<h1 class="sub_title tit02">회원 로그인</h1>
		
		<div class="login_area">

			<!-- 일반개인회원 -->
			<div class="person_login">
				<h2 class="sub_title title01">일반 개인회원</h2>
				<div class="login">
				  <form action="login" method="post" id="aa">
   				  <!-- ↱ 위에가 post 방식으로 보내므로 hidden으로 보내는 거임 -->
				  	<input type="hidden" name="type" value="login" />
					<div class="input_area">
						<p>
						 <label for="s_id">아이디</label>
						 <input type="text" name="m_id" id="s_id"/>
						</p>
						<p>
						 <label for="s_pw">비밀번호</label>
						 <input type="password" name="m_pw" id="s_pw"/>
						</p>
					</div>
					<div class="btnArea_right">
						<span class="btn b_login">
						<!-- ↱ href="" 이렇게 있을 경우 페이지가 없는게 아니라 현재 페이지를 새로고침한다. -->
						 <a id="login_btn">로그인</a>
						</span>
					</div>
					<div class="fclear"></div>
					<p class="login_search">
						<input type="checkbox" name="chk" 
						 id="ch01"/><label for="ch01">
						 아이디저장</label>
						<span class="btn b_search">
						  <a href="">아이디/비밀번호찾기</a>
						</span>
					</p>
				  </form>						
				</div>
			</div>
			<!-- 일반개인회원 끝-->

			<!-- 기관단체회원 -->
			<div class="group_login">
				<h2 class="sub_title title02">일반 개인회원</h2>
				<div class="login">
				  <form action="" method="post">
					<div class="input_area">
						<p>
						 <label for="s_id2">아이디</label>
						 <input type="text" name="id" id="s_id2"/>
						</p>
						<p>
						 <label for="s_pw2">비밀번호</label>
						 <input type="password" name="pw" id="s_pw2"/>
						</p>
					</div>
					<div class="btnArea_right">
						<span class="btn b_login">
						 <a href="">로그인</a>
						</span>
					</div>
					<div class="fclear"></div>
					<p class="login_search">
						<input type="checkbox" name="chk" 
						 id="ch02"/><label for="ch02">
						 아이디저장</label>
						<span class="btn b_search">
						  <a href="">아이디/비밀번호찾기</a>
						</span>
					</p>
				  </form>						
				</div>
			</div>
			<!-- 기관단체회원 끝-->
			<div>
				<a href="https://kauth.kakao.com/oauth/authorize?client_id=ff8e46244db2f93a41ed12dafcdd41e5&redirect_uri=http://localhost:8080/kakao_login&response_type=code">
					<img src="img/kakao_login.png"/>				
				</a>
			</div>
		</div> <!-- class="login_area" 끝-->
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



