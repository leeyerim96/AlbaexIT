<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title> 자유게시판 </title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="resources/jsLib/jquery-3.2.1.min.js"></script>
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<style>
#container {
	width: 30%;
	margin: 0 auto;
	padding-top: 10%;
}

#custom-search-input {
	padding: 3px;
	border: solid 1px #E4E4E4;
	border-radius: 6px;
	background-color: #fff;
}

#custom-search-input input {
	border: 0;
	box-shadow: none;
}

#custom-search-input button {
	margin: 2px 0 0 0;
	background: none;
	box-shadow: none;
	border: 0;
	color: #666666;
	padding: 0 8px 0 10px;
	border-left: solid 1px #ccc;
}

#custom-search-input button:hover {
	border: 0;
	box-shadow: none;
	border-left: solid 1px #ccc;
}

#custom-search-input .glyphicon-search {
	font-size: 15px;
}

.table>thead {
	background-color: #FFE5C2;
}

.table>thead>tr>th {
	text-align: center;
	color: black;
}

.table-hover>tbody>tr:hover {
	background-color: #FDF5E6;
}

.table>tbody>tr>td {
	text-align: center;
	color: black;
}

.table>tbody>tr>#title {
	text-align: left;
}

div>#paging {
	text-align: center;
}

.hit {
	animation-name: blink;
	animation-duration: 1.5s;
	animation-timing-function: ease;
	animation-iteration-count: infinite;
}

@keyframes blink {
	from {color: white;
}

30%{
color:yellow;
}
to {
	color: red;
	font-weight: bold;
}
}
</style>
<script>
	function searchword(){
		$.ajax({
			type:'Post',
			url:'boardsearch',
			data: {
				search: $("#search").val().replace(',' , '|')
			},
			success:function(result){
				$('#resultArea').html(result);
			}
		});
	}	

</script>
</head>
<body>
	<div id="container" class="w3-main w3-content w3-padding"
		style="max-width: 1200px; margin-top: 100px; width: 1000px;">

		<!-- 검색박스 -->

		<div align="center">
			<div class="row">
				<div class="col-md-5">

					<div id="custom-search-input">
						<div class="input-group col-md-11">
							<input type="text" class="form-control input-lg"
								placeholder="제목과 내용을 입력하세요" id="search" name="search" /> <span
								class="input-group-btn">
								<button class="btn btn-info btn-lg" type="button"
									onclick="searchword()">
									<i class="glyphicon glyphicon-search"></i>
								</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>

		<br>
		<br>
		
		
		<div id="resultArea">
			<table class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th width="10%">번호</th>
						<th width="50%">제목</th>
						<th width="10%">아이디</th>
						<th width="20%">작성일</th>
						<th width="10%">조회</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bb" items="${hyejin}" varStatus="status">
						<tr>
							<td>${bb.seq}</td>

							<td id="title">
							<a href="bdetail?seq=${bb.seq}&pno=${bb.pno}&pid=${bb.pid}">${bb.title}</a>
							
							<c:if test="${bb.cnt >= 10}">
									<span class="hit">hit!</span>
						    </c:if> 
						    
						    <c:if test="${bb.mcnt > 0}">
									<span style="color: red;">[${bb.mcnt}]</span>
						    </c:if>
						    
						    </td>

							<td>${bb.pid}</td>
							<td>${bb.regdate}</td>
							<td>${bb.cnt}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			
			<!-- Paging 처리 -->
			<br>
			
			<div id="paging" align="center">
				<div align="right">
					<c:if test="${ploginID!=null}">
						<a href="binsertf"><input id=btn type=button value='글쓰기'></a>&nbsp;
					</c:if>
				</div>
				
				<c:choose>
				
					<c:when test="${sPage > perPageNo}">
						<a href="blist?currPage=1"> 〈〈 </a>&nbsp;
          				<a href="blist?currPage=${sPage-1}">〈 </a>&nbsp;
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
							<a href="blist?currPage=${i}">${i}</a>&nbsp;
          				</c:otherwise>
					</c:choose>
					
				</c:forEach>

				<c:choose>
					<c:when test="${totalPage==ePage}">
         				   〉&nbsp; 〉〉&nbsp;&nbsp;
      			    </c:when>
					<c:otherwise>
						<a href="blist?currPage=${ePage+1}"> 〉</a>&nbsp;
         			    <a href="blist?currPage=${totalPage}"> 〉〉</a>&nbsp;
      			    </c:otherwise>
				</c:choose>
				
			</div><!-- 페이지div -->
			<br>
			
			<hr>
			
			<br>

		</div><!-- resultArea div -->

	</div> <!-- 컨테이너div  -->
</body>
</html>