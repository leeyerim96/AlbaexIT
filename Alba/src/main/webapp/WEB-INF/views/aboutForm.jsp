<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>about 폼</title>
<style>
 h3 { animation-name:textColorAnimation;
         animation-duration:2s;
         animation-iteration-count:infinite;
}

@keyframes textColorAnimation {
   0% { color:pink;}
   30% { color:orange;}
   100% { color: coral;}
}
</style>
</head>
<script src="resources/jsLib/jquery-3.2.1.min.js"></script>   
<script src="resources/jsLib/axlogin.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">

<body>
<div class="w3-main w3-content w3-padding" style="max-width:1200px;height:1000px;margin-top:100px">

<h1><b>About us</b></h1>
<hr>
<div class="w3-row-padding w3-padding-16 w3-center">
    <div class="w3-third">
          <img src="resources/image/hyejin.jpg" alt="Hyejin" style="width:100%">
         <h3>Hyejin Kim</h3>
          <p>shflxpf701@naver.com</p>
    
    </div>
    <div class="w3-third">
           <img src="resources/image/yerim.jpg" alt="Yerim" style="width:100%">
          <h3>Yerim lee</h3>
            <p>leeyerim96@naver.com</p>
    </div>
    <div class="w3-third">
          <img src="resources/image/gayoung.jpg" alt="Gayoung" style="width:100%">
         <h3>Gayoung Hwang</h3>
          <p>hi2729@naver.com</p>
    </div>
    
    </div> 
     </div>
     <div align="center">
<h1><b>찾아오시는 길</b></h1>
<div id="map" style="width:50%;height:50%; height:500px; width:1200px;"></div>
<script type="text/javascript" 
	    src="//dapi.kakao.com/v2/maps/sdk.js?appkey=9c84bf675a46e4c56ca0414a7f76db2d&libraries=services">
</script>

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
//var description = 'AlbaexIT' ; // description: 설명,묘사

geocoder.addressSearch(address, function(result, status) { 
	// 정상적으로 검색이 완료됐으면
	if (status === daum.maps.services.Status.OK) { 
		
		var coords = new daum.maps.LatLng(result[0].y, result[0].x);  
		// 결과값으로 받은 위치를 마커로 표시합니다
		var marker = new daum.maps.Marker({ map: map, position: coords }); 
		// 인포윈도우로 장소에 대한 설명을 표시합니다 
		var infowindow = new daum.maps.InfoWindow({ 
			//content: description });
		    content: '<div style="width:150px;text-align:center;padding:6px 0;">AlbaexIT</div>' }); 
		infowindow.open(map, marker); 
		// 지도의 중심을 결과값으로 받은 위치로 이동시킵니다 
		map.setCenter(coords); 
	} // if
	}); // addressSearch

</script>
</div>
<br>
<div align="center">
주소 : 성남시 분당구 구미동 7-2(돌마로 46) 5층<br>
미금역 6번출구 직진 100m 앞 광천플라자  빌딩<br>
TEL : 000-0000-0000 <br>
</div>
</body>
</html>