<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"  import="java.sql.*" 
    import="org.apache.commons.lang3.StringUtils"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
 	<head>
 		<meta charset="utf-8">
 		<title>다락방</title>
 		<link href="css/bootstrap.css" rel="stylesheet">
 		<link href="css/garret.css" rel="stylesheet">
 		<script src='js/jquery-1.8.2.js'></script>
 		<script src='js/bootstrap-tab.js'></script>
 		<script src="http://code.highcharts.com/highcharts.js"></script>
		<script src="http://code.highcharts.com/modules/exporting.js"></script>

	</head>
 	<body >
 		 <!-- 전체 레이아웃-->
 		<div class="container-fluid">
 			<div class="row-fluid">
 				<div class="span3"> 
 					<!--side bar / Layout1-->
 					<br/>
 					<div id="lay1-0">
 						<!--닉네임을 넣자-->
 						<p id="nick">
 							 다락방 
 						</p>
 					</div><!--lay1-0-->

 					<br/>
 					<!-- 인증된 SNS의 프로필 사진을 넣자-->
 					<div id="lay1-1">
 					<img src="image/younha.jpg" alt="프로필사진" width="230px" >
 					<br/><br/><br/>
 					</div><!--lay1-1-->

 					<div id="lay1-2">
 					<table class= "table table-hover">
 						<tbody class="sidetable">
 							<tr>
 								<th>신뢰도</th>
 							</tr>
 							<tr>
 								<th>-> 별점 이미지</th>
 								<!--별점이 이미지형식으로 들어갈예정-->
 							</tr>
 							<tr>
 								<th>인증 SNS</th>
 							</tr>
 							<tr>
 								<th>-> 인증 SNS 아이콘</th>
 								<!--인증 SNS아이콘? 들어갈자리-->
 							</tr>
 						</tbody>
 					</table>
 					</div> <!--lay1-2-->
 				</div>
 				
 				<c:set var="item" value="${item}"/>
 				
 				<div class="span9" >
 					<!--body / Layout2-->
 					<ul class="nav nav-tabs" id="myTab">
 						<li  class="active"><a  href="#garret" data-toggle="tab"  > 다락방</a></li>
 						<li><a href="#message" data-toggle="tab" >메시지 수신함</a></li>
 						<li> <a href="#shop" value="show" data-toggle="tab" >가게 관리</a></li>
 						<li> <a href="#account" data-toggle="tab"> 계정 관리</a></li>
 					</ul>

 					<div class="tab-content">
 						<div class="tab-pane fade in active" id="garret">
 							<div id="lay2-1-1">

 								<div id="s_item_list">
 								<table class="table table-striped" id="t_list_table">
									<thead>
										<tr>
											<th id="t_num">거래번호</th>
											<th id="t_bookname">책이름</th>
											<th id="t_price">가격</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach begin="0" end="${itemsize-1}" var="i">
										<tr>
											<td><c:out value="${item[i].goods_num}"/></td>
											<td><c:out value="${item[i].book_name}"/></td>
											<td><c:out value="${item[i].price}"/></td>				
										</tr>
										</c:forEach>																					
									</tbody>

								</table>	
 								</div>
 							</div>
 							<!-- lay2-1-1-->
 							<br/>
 							
 							<div id="lay2-1-2">
 								<table class="table table-striped">
 									<thead>
 									<tr colspan="2">
 										<th>확인하지 않은 메시지</th>
 									</tr>
 								</thead>
 								<tbody>
 									<c:forEach begin="0" end="${messagesize-1}" var="i">
 											<c:if test ="${message[i].yesno eq false}">
	 											<tr>
	 												<td>${message[i].title}</td>
	 												<td>${message[i].otherNickname}</td>
	 											</tr>
 											</c:if>
 									</c:forEach>
 									
 								</tbody>
 								</table>
 	 						</div><!--lay2-1-2-->
 	 					
 	 						<div id="lay2-1-3">
 	 							<table class="table table-striped">
 									<thead>
 									<tr>
 										<th>후기</th>
 									</tr>
 								</thead>
 								<tbody>
 									<tr>
 										<td>정말 상태도 좋고 만족스럽네요!!!!!</td>
 									</tr>
 									<tr>
 										<td>다음에 또 이용하고 싶네요!!!ㅋ</td>
 									</tr>
 									<tr>
 										<td>시간약속이 장난입니까!!!!</td>
 									</tr>
 									
 								</tbody>
 								</table>
 							</div> <!--lay2-1-3-->
 							
 							
 							<div class="blank">
 							</div>
 							<br/>
 						 							
 							<div id="lay2-1-4">
 								<div id="container"></div>
 							</div><!--lay2-104-->
 							
 						</div>
 	 					
 							
 						
 						<div class="tab-pane fade in " id="message">
 							<div id="lay2-2">
 								<div id="table1">
 								<table class= "table table-bordered" id="test"> 
 									<thead>
 									<tr>
 										<th>보낸사람</th>
 										<th id="t_width_1">내용</th>
 										<th>확인여부</th>
 									</tr>
 									<tbody>
 										<c:forEach begin="0" end="${messagesize-1}" var="i">
 											<c:if test ="${message[i].yesno eq false}">
 												<tr class="success">
 													<td>${message[i].otherNickname}</td>
 													<td>${message[i].title}</td>
 													<td>N</td>
 												</tr>
 											</c:if>
	 											
 											<c:if test ="${message[i].yesno eq true}">
 												<tr class="warning">
 													<td>${message[i].otherNickname}</td>
	 												<td >${message[i].title}</td>
	 												<td>Y</td>
	 											</tr>
 											</c:if> 									
 										</c:forEach>
 										<!--리스트로들어가나..?-->
 									</tbody>
 									
 								</table>
 								</div>
 							</div>
 						</div>
 						<div class="tab-pane fade in" id="shop">
 							 							
 							<div id="lay3-1">
								<table class="table table-striped" >
									<thead>
										<tr>
											<th id="g_check">Check</th>
											<th id="g_image">이미지</th>
											<th id="g_contents">내용</th>
										</tr>
									</thead>								
									<tbody>
										<c:forEach begin="0" end="${itemsize-1}" var="i">
										<tr class ="td_set">
											<td>	
												<input type="checkbox" id="choice" value="first">
 											</td>
 											<td>
 												<img class="media-object" src="${item[i].image}">
 											</td>
 											<td>										
 												<div class="media-body">
 												<a href=""><h4 class="media-heading">${item[i].book_name}</h4></a>${item[i].content}
 												</div>
 											</td>
										</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						
						<div class="print_sum">총 개수 = <span id="sum" class="count"></span>${itemsize}</div>
							<br/>
						<div class="right">
							<input type="button" class="btn btn-info" value="물품추가" id="add">
							<input type="button" class="btn btn-infor" value="물품삭제" id="del">
						</div>		
						<div id="sample_row" style="display:none">
							<table>
								<tr>
								<td><a class="pull-left" href="#">
								<label class="checkbox inline">
								<input type="checkbox" id="choice" value="first">
 								</label>
 								<img class="media-object" src="http://placehold.it/64x64">
 										&nbsp;
 									</a>											
 									<div class="media-body">
 									<a href=""><h4 class="media-heading"> 제목1</h4></a>
										1번책임돠
 									</div></td>
								</tr>
							</table>
							</div>
									
 						</div> 
 						<div class="tab-pane fade in" id="account">
 							<div id="lay4-1">
								<form class="form-horizontal">
									<div class="control-group">
										<label class="control-label" >닉네임</label>
										<div class="controls">
											<input id="disabledInput" type="text" placeholder="Progr" disabled>
											<!--기본닉네임은 수정못함  -> 수정못하게-->
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" >핸드폰번호</label>
										<div class="controls">
											<input type="text"  placeholder="010-2530-1285">
											&nbsp; 인증됨
										</div>

									</div>
									<div>
										<textarea class="list" rows="5"  placeholder="자기소개">

										</textarea>
									</div>
									<br/>

									<div class="control-group">
										<div class="controls">
											<button id="mod_btn" type="submit" class="btn">수정하기</button>
										</div>
									</div>

								</form>
 							</div>
 						</div>
 						</div>
 
 	 				</div>
 	 				</div>

 				</div>
 
 			</div>

 		</div> 
 	</body>
</html>
<script type='text/javascript'>
$(function (){
	chart();
	$('#myTab a').click(function(e) {
		e.preventDefault();
		setItemDB($(this).value(),doGet);
		$(this).tab('show');
	});
	// 클릭할때마다 탭 show/hide
	
	$('#add').click(function(){
		window.open("goodadd.jsp",""," location=no, resizable=no, _blank, width=850, height=750");
	});

})
function setItemDB(raw,callback){
	
 	callJsonP( "/good",GET,raw,callback);
}
function recal(){
	

}
function chart(){
	 var chart;
    $(document).ready(function() {
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'line',
                marginRight: 130,
                marginBottom: 25
            },
            title: {
                text: 'Monthly Average Temperature',
                x: -20 //center
            },
            subtitle: {
                text: 'Source: WorldClimate.com',
                x: -20
            },
            xAxis: {
                categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
                    'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
            },
            yAxis: {
                title: {
                    text: 'Temperature (°C)'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            },
            tooltip: {
                formatter: function() {
                        return '<b>'+ this.series.name +'</b><br/>'+
                        this.x +': '+ this.y +'°C';
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -10,
                y: 100,
                borderWidth: 0
            },
            series: [{
                name: 'Tokyo',
                data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
            }, {
                name: 'New York',
                data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
            }, {
                name: 'Berlin',
                data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
            }, {
                name: 'London',
                data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
            }]
        });
    });

}
</script>

