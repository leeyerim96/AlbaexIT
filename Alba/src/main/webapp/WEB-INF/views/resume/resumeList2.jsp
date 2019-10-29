<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Karma">
<script src="resources/jsLib/jquery-3.2.1.min.js"></script>
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<script>
   function searchword(){
     
      $.ajax({
         type:'Post',
         url:'searchresume',
         data: {
            search: $("#search").val().replace(',' , '|')
         },
         success:function(result){
            $('#resultArea').html(result);
         }
      }); //ajax
   }   

</script>
<style>
  
    #container {
 
      width: 30%;
      margin: 0 auto;     /* 가로로 중앙에 배치 */
      padding-top: 10%;   /* 테두리와 내용 사이의 패딩 여백 */
    }

  #custom-search-input{
  
    padding: 3px;
    border: solid 1px #E4E4E4;
    border-radius: 6px;
    background-color: #fff;
}

#custom-search-input input{
   
    border: 0;
    box-shadow: none;
    font-size: 13px;
}

#custom-search-input button{
   
    margin: 2px 0 0 0;
    background: none;
    box-shadow: none;
    border: 0;
    color: #666666;
    padding: 0 8px 0 10px;
    border-left: solid 1px #ccc;
}

#custom-search-input button:hover{
    border: 0;
    box-shadow: none;
    border-left: solid 1px #ccc;
}

#custom-search-input .glyphicon-search{

    font-size: 15px;
}

    /* Bootstrap 수정 */
    .table > thead {
      background-color:#FFE5C2;
      height:5px;
    }
     .table > thead > tr > th {
      text-align: center;
       color: black;
    }
    
      .table > tbody > tr > td {
      text-align: center;
      color: black;
    }
    .table-hover > tbody > tr:hover {
      background-color: #FDF5E6;
    }
   
    .table > tbody > tr > #title {
      text-align: left;
    }
     
    div > #paging {
      text-align: center;
    }
  
  </style>
<meta charset="UTF-8">
<title>내 이력서 목록</title>
</head>
<body>
<div id="container" class="w3-main w3-content w3-padding" style="max-width:1200px;margin-top:100px;width:1000px;">
 <!-- 검색박스 -->

<div  align="center" >
   <div class="row">
        <div class="col-md-5" >
       
            <div id="custom-search-input" >
                <div class="input-group col-md-11">
                    <input type="text" class="form-control input-lg" placeholder="ex)학력,경력,직종,기간,요일,시간,희망근무지역 " id="search" name="search" />
                    <span class="input-group-btn" >
                        <button class="btn btn-info btn-lg" type="button" onclick="searchword()">
                            <i class="glyphicon glyphicon-search"></i>
                        </button>
                    </span>
                </div>
            </div>
        </div>
   </div>
</div>

<br><br>

<div id="resultArea">
<table class="table table-striped table-bordered table-hover">
  <thead>
          <tr>
          
            <th width="15%">제목</th>
            <th width="7%">이름</th>
            <th width="5%">나이</th>
            <th width="5%">성별</th>
             <th width="10%">연락처</th>
              <th width="21%">주소</th>
               <th width="5%">학력</th>
                <th width="5%">경력</th>
                 <th width="5%">직종</th>
                  <th width="5%">기간</th>
                   <th width="5%">요일</th>
                    <th width="5%">시간</th>
                     <th width="19%">희망근무지역</th>
                  
          </tr>
        </thead>
        <tbody>
<c:forEach var="rr" items="${Apple}">
   
    <c:if test="${loginID!=null}" >
   <tr onclick="location.href='rdetail?userno=${rr.userno}'">
   </c:if>
   
   <c:if test="${kloginID!=null}" >
   <tr onclick="location.href='krdetail?userno=${rr.userno}'">
   </c:if>
   
   <td>${rr.title}</td>
   <td>${rr.pname}</td>  
   <td>${rr.age}</td>
   <td>${rr.gender}</td>
   <td>${rr.phone}</td>
   <td>${rr.addr}</td>
   <td>${rr.academic}</td>
   <td>${rr.career}</td>
   <td>${rr.typeoccup}</td>
   <td>${rr.period}</td>
   <td>${rr.dow}</td>
   <td>${rr.timetotime}</td>
   <td>${rr.region}</td>
   </tr>
 
</c:forEach>
</tbody>
</table><br>
 <div id="paging" align="center">
 <c:choose>
       <c:when test="${sPage > perPageNo}">
          <a href="rlist2?currPage=1"> 〈〈  </a>&nbsp;
          <a href="rlist2?currPage=${sPage-1}">〈 </a>&nbsp;
       </c:when>
       <c:otherwise>
          〈〈&nbsp;〈 &nbsp;&nbsp;
       </c:otherwise>
    </c:choose>
    
    <c:if test="${totalPage < ePage}">
       <c:set var="ePage" value="${totalPage}"></c:set>
    </c:if>
    <c:forEach var="i" begin="${sPage}" end="${ePage}">
       <c:choose>
          <c:when test="${i==currPage}">
             <font size="5" color="Orange">${i}</font>&nbsp;
          </c:when>
          <c:otherwise>
             <a href="rlist2?currPage=${i}">${i}</a>&nbsp;
          </c:otherwise>
       </c:choose>
    </c:forEach>
    
    <c:choose>
       <c:when test="${totalPage==ePage}">
            〉&nbsp; 〉〉&nbsp;&nbsp;
       </c:when>
       <c:otherwise>
          <a href="rlist2?currPage=${ePage+1}">  〉</a>&nbsp;
          <a href="rlist2?currPage=${totalPage}"> 〉〉</a>&nbsp;
       </c:otherwise>
    </c:choose>
 </div>
<a href="#" onclick="history.back()"><button type=button >뒤로가기</button></a> <br><br>
</div>
</div>

</body>
</html>