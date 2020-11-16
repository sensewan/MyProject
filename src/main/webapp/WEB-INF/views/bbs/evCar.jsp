<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전기차 현황</title>
<link type="text/css" rel="stylesheet" href="css/common.css"/>
<link type="text/css" rel="stylesheet" href="css/login.css"/>

<style type="text/css">
	body{ font-size: 12px }
	table{
		border-collapse: collapse;
		width: 500px;
	}
	table th, table tbody td{
		border: 1px solid black;
		padding: 4px;
	}
	table thead tr:first-child{ line-height: 40px; }
	table thead td:last-child{ text-align: right; }
	table caption { text-indent: -9999px; height: 0; }
	
	.h70{
		line-height: 70px;
		height: 70px;
		text-align: center;
	}
	#chart_div{
		height: 400px;
	}
</style>

</head>
<body>

<div id="wrap">
	<!-- 상단 영역 -->
	<jsp:include page = "../header.jsp" />
	<!-- 상단 영역 끝 -->	

	<!-- 콘텐츠 영역 -->
	<div id="contents_sub">
		<h1 class="sub_title tit02">전기차 충전소 현황</h1>
		
	<div id = "chart_div"></div>
		
	</div>
	<!-- 콘텐츠 영역 끝-->

	<!-- 하단 영역 -->
	<jsp:include page="footer.jsp"/>
	<!-- 하단 영역 끝 -->
</div>

	
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	
	<script src="https://cdn.amcharts.com/lib/4/core.js"></script>
	<script src="https://cdn.amcharts.com/lib/4/charts.js"></script>
	<script src="https://cdn.amcharts.com/lib/4/themes/animated.js"></script>
	
<script type="text/javascript">

	$(function () {
		
		$.ajax({
			url: "http://localhost:5000/evCar",
			type:"post",
			dataType: "JSON"
		}).done(function(data) {
			viewChart(data);
		});
		
	});
	
	function viewChart(json_data) {

		am4core.useTheme(am4themes_animated);
		
		//이제 차트 생성!~
		                            // 차트를 만들 id=값 넣어주면 된다.
		var chart =am4core.create("chart_div", am4charts.XYChart);
		
		// 차트에 데이터를 주겠다는 뜻 [json 값 주면 ]
		chart.data = json_data;
		
		// x축 만들기
		var categoryAxis =  chart.xAxes.push(new am4charts.CategoryAxis());
		categoryAxis.dataFields.category = "city";
		// x축 폰트사이즈 지정
		categoryAxis.renderer.labels.template.fontSize = 9;
		categoryAxis.renderer.minGridDistance = 40;
		
		// y축 만들기
		var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
		
		// Series 만들기
		var series = chart.series.push(new am4charts.ColumnSeries())
	    series.dataFields.valueY = "counts";
	    series.dataFields.categoryX = "city";
	    
	    series.columns.template.tooltipText = "[bold]{valueY}[/]";
	    series.columns.template.fill = am4core.color('#6e6eff');
	    series.columns.template.fillOpacity = 0.6;  // 그래프농도
	    series.columns.template.stroke = am4core.color('#ff0000'); // 테두리 설정
	    
	}

</script>

<script type="text/javascript">
	function search(frm) {
		frm.submit();
	}
</script>
</body>
</html>