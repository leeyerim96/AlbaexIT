<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<html>
<head>
	<title>Home_Mybatis</title>
	<script>
	function setClock() {
		var now = new Date() ;
		var t ='* Current Time: '
			+ now.getFullYear()+'년'+(now.getMonth()+1)+'월'+now.getDate()+'일'
		 	+'_'+now.getHours()+':'+now.getMinutes()+':'+now.getSeconds() ;
		document.getElementById("clock").innerHTML=t;
		setTimeout("setClock()",1000)  // 1/1000 초 단위
	}
	</script>
</head>
<body onload="setClock()">
<h2>Hello Spring MVC_Mybatis !!! </h2>
<b><P id="clock"></p></b>
<!-- <img src="resources/image/summersea.jpg" width="300" height="200"><br> -->
<div id="map" style="width:100%;height:100%;height:1000px"></div><br><br>

<script>
var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	mapOption = {
    	center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    	level: 3 // 지도의 확대 레벨
		};  

//지도를 생성합니다    
var map = new kakao.maps.Map(mapContainer, mapOption); 

//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

//주소로 좌표를 검색합니다
// 제주특별자치도 제주시 첨단로 242
var address = '경기 성남시 분당구 돌마로 46' ;
var description = '그린 컴퓨터아카데미' ; // description: 설명,묘사

geocoder.addressSearch(address, function(result, status) { 
	// 정상적으로 검색이 완료됐으면
	if (status === daum.maps.services.Status.OK) { 
		
		var coords = new daum.maps.LatLng(result[0].y, result[0].x);  
		// 결과값으로 받은 위치를 마커로 표시합니다
		var marker = new daum.maps.Marker({ map: map, position: coords }); 
		// 인포윈도우로 장소에 대한 설명을 표시합니다 
		var infowindow = new daum.maps.InfoWindow({ 
			content: description });
			/* content: '<div style="width:150px;text-align:center;padding:6px 0;">GreenComputer</div>' }); */
		infowindow.open(map, marker); 
		// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다 
		map.setCenter(coords); 
	} // if
	}); // addressSearch

</script>
</body>
</html>
