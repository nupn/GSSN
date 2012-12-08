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
 		<script src='lib/crystal.js'></script>
 		<script src="http://code.highcharts.com/highcharts.js"></script>
		<script src="http://code.highcharts.com/modules/exporting.js"></script>
		<script language="javascript" type="text/javascript" src="./bootstrap/js/bootstrap-modal.js"></script>
		<script language="javascript" type="text/javascript" src='./fb-init.js'></script>
		<script language="javascript" type="text/javascript" src='./lib/jquery.js'></script>
	    <script language="javascript" type="text/javascript" src='./lib/json2.js'></script>
	 	<script language="javascript" type="text/javascript" src='./lib/translator.js'></script>
	    <script language="javascript" type="text/javascript" src="./bootstrap/js/bootstrap.js"></script>
	    <script language="javascript" type="text/javascript" src="./bootstrap/js/bootstrap-typeahead.js"></script>
	    <script language="javascript" type="text/javascript" src='./lib/crystal.js'></script>
	    <script language="javascript" type="text/javascript" src='./fbtool.js'></script>
	    <script language="javascript" type="text/javascript" src='./garret.js'></script>
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
 							<c:out value="${user.nickname}"/>'s 다락방 
 						</p>
 					</div><!--lay1-0-->

 					<br/>
 					<!-- 인증된 SNS의 프로필 사진을 넣자-->
 					<div id="lay1-1">
 					<c:set var="profileimg" value="${user.facebookprofileimg}"/>
 					
 					<img src="${profileimg}" id="profileimg" alt="프로필사진" >
 					
 					<br/><br/>
 					</div><!--lay1-1-->

 					<div id="lay1-2">
 					<table class= "table table-hover">
 						<tbody class="sidetable">
 							<tr>
 								<th>신뢰도</th>
 							</tr>
 							<tr>
 								<th>
 								<c:choose>
 								<c:when test="${mineralC < 1 || mineralB < 1 }">
	 								<c:forEach begin="1" end="5">
	 									<img class="mineralimg" src="img/mineralB.jpg" alt="미네랄B">
	 								</c:forEach>
	 							</c:when>
		 							<c:otherwise> 								
	 								<c:forEach begin="1" end="${mineralC}" var="i">
		 								<img class="mineralimg" src="img/mineralC.jpg" alt="미네랄C">
		 							</c:forEach>
		 							<c:forEach begin="1" end="${mineralB}" var="i">
		 								<img class="mineralimg" src="img/mineralB.jpg" alt="미네랄B">
		 							</c:forEach>
	 							</c:otherwise>
	 							</c:choose>
 								</th>
 								<!--별점이 이미지형식으로 들어갈예정-->
 							</tr>
 							<tr>
 								<th><a href="${user.home}"><input type="button" class="btn btn-primary" value="판매자 페이스북가기"></a></th>
 								<!--인증 SNS아이콘? 들어갈자리-->
 							</tr>
 							<c:if test="${identity eq 'guest'}"> 	
 							<tr >
 								<th><a href="#" onclick="javascipt:newWindowmsgsend(${g_num},${o_num})"><input id="tomsgbtn" type="button" class="btn btn-danger" value="판매자에게 쪽지보내기"/></a></th>
 							</tr>
 							</c:if>
 					</table>
 					</div> <!--lay1-2-->
 				</div>
 				
 				<c:set var="item" value="${item}"/>
 				
 				<div class="span9" >
 					<!--body / Layout2-->
 					<ul class="nav nav-tabs" id="myTab">
 						<li  class="active"><a  href="#garret" data-toggle="tab"  >다락방</a></li>
 						<li> <a href="#shop"  data-toggle="tab" >판매중</a></li>
 						<li><a href="#transaction" data-toggle="tab" >거래 내역1</a></li>
 						<c:if test="${identity eq 'owner'}">
 							<li><a href="#message" data-toggle="tab" >메시지 수신함</a></li>
 						</c:if>
 						<li><a href="#account" data-toggle="tab">기본 정보1</a></li>
 					</ul>
					
					<!-- 다락방 -->
 					<div class="tab-content">
 						<div class="tab-pane fade in active" id="garret">
 							<h4>거래가 진행중인 물품</h4>
 							<div id="lay2-1-1">
								
 								<div id="s_item_list">
 								
 								<table class="table table-striped" id="t_list_table">
									<thead>
										<tr>
											<th id="t_num">거래번호</th>
											<th id="t_bookname">책이름</th>
											<th id="t_price">가격</th>
											<th id="t_type">거래종류</th>
										</tr>
									</thead>
									<tbody>
									<c:choose>
										<c:when test="${tradesize == 0 }">
											<tr>
											<td colspan="4"><c:out value="거래가 진행중인 물품이 없습니다."/></td>
											</tr>
										</c:when>
										<c:otherwise>
										<c:forEach begin="0" end="${tradesize-1}" var="i">
										<c:if test="${trade[i].trade_status eq 'true'}">
										<c:if test="${trade[i].tsales_memnum ==  user.mem_num}">										
										<tr>
											<td><c:out value="${trade[i].trade_num}"/></td>
											<td><c:out value="${trade[i].trade_itemtitle}"/></td>
											<td><c:out value="${trade[i].trade_price}"/></td>											
											<td>판매중</td>
										</tr>
										</c:if>									
										</c:if>
										</c:forEach>
										
											<c:forEach begin="0" end="${tradesize-1}" var="i">
											<c:if test="${trade[i].trade_status eq 'true'}">
												<c:if test="${trade[i].tpurchase_memnum ==  user.mem_num}">
												
													<tr>
														<td><c:out value="${trade[i].trade_num}"/></td>
														<td><c:out value="${trade[i].trade_itemtitle}"/></td>
														<td><c:out value="${trade[i].trade_price}"/></td>
														<td>구매중</td>
													</tr>
												</c:if>		
											</c:if>
											</c:forEach>		
										
										
										</c:otherwise>
										</c:choose>																					
									</tbody>

								</table>	
 								</div>
 							</div>
 							<!-- lay2-1-1-->
 							<br/>
 							<div id="lay2-1-2">
 								<table class="table table-striped">
 									<thead>
 									<tr>
 										<th colspan="2">확인하지 않은 메시지</th>
 									</tr>
 								</thead>
 								<tbody>
 								<c:choose>
 									<c:when test="${messagesize == 0}">
 										<tr>
 											<td colspan="2">받은 메시지가 없습니다.</td>
 										</tr>
 									</c:when>
 									<c:when test="${notconfirmmsgsize == 0 }">
 										<tr>
 											<td colspan="2">확인하지 않은 메시지가 없습니다.</td>
 										</tr>
 									</c:when>
 									<c:otherwise>
	 									<c:forEach begin="0" end="${messagesize-1}" var="i" varStatus="status">
	 											<c:if test ="${message[i].yesno eq false}">
		 											<tr>
		 												<c:if test="${identity eq 'owner'}">
		 													<!-- <td><a class="hm_set" href="messageServlet.do?sendtype=0&msg_num=${message[i].msg_num}">${message[i].title}</a></td> -->
		 													<td><a id="messagelink" href="#" onclick="javascipt:newWindowmsgread(${message[status.index].msg_num})">${message[i].title}</a></td> 	
		 													
		 																									
		 												</c:if>
		 												<c:if test="${identity eq 'guest'}"> 												
		 													<td  class="hm_set" >${message[i].title}</td>
		 												</c:if>
		 												<td>${message[i].otherNickname}</td>
		 											</tr>
	 											</c:if>
	 									</c:forEach> 
 									</c:otherwise>	
 									</c:choose>								
 								</tbody>
 								</table>
 	 						</div><!--lay2-1-2-->
 	 					
 	 						<div id="lay2-1-3">
 	 							<table class="table table-striped">
 									<thead>
 									<tr>
 										<th>최근 등록된 후기</th>
 									</tr>
 								</thead>
 								<tbody>
 								<c:choose>
 									<c:when test="${reviewsize == 0}">
 										<tr>
 											<td colspan="2">입력된 후기가 없습니다.</td>
 										</tr>
 									</c:when>
 									<c:otherwise>
	 									<c:forEach begin="0" end="${reviewsize-1}" var="i">
	 										<tr id="hrv_set">
					 							<td id="hrv_set_post">${review[i].evaluation_post}</td>											
					 							<td id="hrv_set_rating">${review[i].evaluation_rating} 점</td>
		 									</tr>
	 									</c:forEach> 
 									</c:otherwise>	
 									</c:choose>								
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
 						
 						<!-- 거래내역 -->
 						<div class="tab-pane fade in " id="transaction">
 							<div id="lay5-1">
 								<table class= "table table-hover" > 
 									<thead>
	 									<tr >
	 										<td >거래번호</td>
	 										<td class="tr_set">거래물품</td>
	 										<td>거래일자</td>
	 										<td>거래여부</td>
	 									</tr>
 									</thead>
 									<tbody>
 										<c:choose>
	 										<c:when test="${tradesize == 0 }">
	 											<tr>
	 												<td colspan="4">거래 기록이 없습니다.</td>
	 											</tr>
	 										</c:when>
	 										<c:otherwise>
	 											<c:forEach begin="0" end="${tradesize-1}" var="i">
		 											<c:if test="${trade[i].trade_status eq 'false'}">			 										
				 											<tr  class="success">
					 											<td>${trade[i].trade_num}</td>
					 											<td>${trade[i].trade_itemtitle}</td>
					 											<td>${trade[i].trade_date}</td>
					 											<td>거래완료</td>
					 										</tr>			 										
			 										</c:if>
		 										</c:forEach>
		 										<c:forEach begin="0" end="${tradesize-1}" var="i">
			 										<c:if test="${trade[i].trade_status eq 'true'}">			 											
					 										<tr class="warning">
																<td>${trade[i].trade_num}</td>
						 										<td>${trade[i].trade_itemtitle}</td>
						 										<td>${trade[i].trade_date}</td>
																<td>거래중</td>
															</tr>
													</c:if>
	 											</c:forEach>
											</c:otherwise>
										</c:choose>
 									</tbody>
	 							</table>
 							</div>
 							<br/><br/>
 							<div id="lay5-2">
 								<table class= "table table-hover" > 
 									<thead>
	 									<tr>
	 										<td class="rv_set">후기</td>
	 										<td>신뢰도</td>
	 										<td>작성일자</td>
	 										<td>작성자</td>
	 									</tr>
 									</thead>
 									<tbody>
 									<c:choose>
	 									<c:when test="${reviewsize==0}">
	 										<tr>
	 											<td colspan="4">거래 후기가 없습니다.</td>
	 										</tr>
	 									</c:when>
	 									<c:otherwise>
		 									<c:forEach begin="0" end="${reviewsize}" var="i">
		 										<tr>
		 											<td>${review[i].evaluation_post}</td>
		 											<td>${review[i].evaluation_rating}</td>
		 											<td>${review[i].date}</td>
		 											<td>${review[i].name}</td>								
		 										</tr>
		 									</c:forEach>
	 									</c:otherwise>
 									</c:choose>
 									</tbody>
 					
	 							</table>
 							</div>
 							<!-- 거래한적이 있는 고객일 경우 탭이 보임-->

 							<br/><br/>
 							<div id="lay5-3">
 								
	 								<c:if test="${identity eq 'guest'}">
	 									"${user.nickname}" 님과 거래기록이 있는 분 만 후기를 작성하실수 있습니다.
	 								</c:if>
	 								
	 								<br/><br/>
	 							
	 								<c:if test="${bool_trade eq true }">
	 								<form class="form-horizontal" action="garretServlet.do" method="POST">
	 								<input type="hidden" name="_method" value="review"/>
	
		 								<input type="text" id="review" name="review" placeholder="후기를 적어주세요"/>
			 							<select id="rating" name="rating">
			 								<option value="1">1 점</option>
			 								<option value="1.5">1.5 점</option>
			 								<option value="2">2 점</option>
			 								<option value="2.5">2.5 점</option>
			 								<option value="3">3 점</option>
											<option value="3.5">3.5 점</option>
		 									<option value="4">4 점</option>
		 									<option value="4.5">4.5 점</option>	
		 									<option value="5">5 점</option>
			 							</select>
			 							
		 								<input type="hidden" name="rvname" value="${g_name}"/>
		 								<input type="hidden" name="o_num" value="${o_num}"/>
		 								<input type="hidden" name="g_num" value="${g_num}"/>
		 								
			 							<div id="tr_btn_set">
			 								<input type="submit" id="tr_btn" class="btn btn-success" value="등록"/>
			 							</div>
			 						</form>
	 								</c:if>

 							</div>
 						</div>
 						
 	 					
 						<!-- 메시지 -->
 						<c:if test="${identity eq 'owner'}">
 						<div class="tab-pane fade in " id="message">
 							<div id="lay2-2">
 								<div id="table1">
 								<table class= "table table-bordered" id="test"> 
 									<thead>
 									<tr>
 										<th>보낸사람</th>
 										<th id="t_width_1">제목</th>
 										<th>받은날짜</th>
 										<th>확인여부</th>
 									</tr>
 									<tbody>
 										<c:choose>
 											<c:when test="${messagesize == 0}">
 											<tr>
 												<td colspan="4">받은 메시지가 없습니다.</td>
 											</tr>
 											</c:when>
 											<c:otherwise>	
		 										<c:forEach begin="0" end="${messagesize-1}" var="i" varStatus="status">
		 											<c:if test ="${message[i].yesno eq false}">
		 												<tr class="success">		 													
		 													<td>${message[i].from_memnum}</td>
		 													<td>
		 														<a id="messagelink" href="#" onclick="javascipt:newWindowmsgread(${message[status.index].msg_num})">${message[i].title}</a>
		 													</td>		 															 																																			
		 													<td>${message[i].to_date}</td>
		 													<td>N</td>
		 												</tr>
		 											</c:if>	
		 											<c:if test ="${message[i].yesno eq true}">
		 												<tr class="warning">
		 													<td>${message[i].from_memnum}</td>
			 												<td>
		 														<a id="messagelink" href="#" onclick="javascipt:newWindowmsgread(${message[status.index].msg_num})">${message[i].title}</a>
															</td>
			 												<td>${message[i].to_date}</td>
			 												<td>Y</td>
			 											</tr>
		 											</c:if>		 												 																															
		 										</c:forEach>
		 									</c:otherwise>
 										</c:choose>
 										<!--리스트로들어가나..?-->
 									</tbody>
 								</table>
 								</div>
 								<br/>
 									<a id="messagelink" href="#" onclick="javascipt:newWindowmsgsend(${o_num})">
 									<input type="button" class="btn btn-primary" value="메시지보내기" id="msgbtn"></a>
 								
 						
 							</div>
 						</div>
 						</c:if>
 						
 						<!-- 계정관리 -->
 						<div class="tab-pane fade in" id="account">
 							<div id="lay4-1">
								<form class="form-horizontal">
									<div class="control-group">
										<label class="control-label" >회원번호</label>
										<div class="controls">
											<input id="disabledInput" type="text" value="${user.mem_num}" disabled>
											<!--기본닉네임은 수정못함  -> 수정못하게-->
										</div>
									</div>
									<div class="control-group">
										<label class="control-label" >닉네임</label>
										<div class="controls">
											<input type="text" value="${user.nickname}" disabled> 
										</div>

									</div>
									
									<div class="control-group">
										<label class="control-label" >이메일주소</label>
										<div class="controls">
											<input type="text" value="${user.email}" disabled> 
										</div>

									</div>
								</form>
 							</div>
 							
 						</div>
 						
 						
 						<!-- 물품정보 -->
 						<div class="tab-pane fade in" id="shop">
 							<form class="form-horizontal" action="garretServlet.do" method="POST">
 							<input type="hidden" name="_method" value="goods"/>
 							<div> 							
 							<div id="lay3-1">
								<table class="table table-striped" >
									<thead>
										<tr>
											<c:if test="${identity eq 'owner'}">
												<th id="g_check">Check</th>
											</c:if>
											<th id="g_image">이미지</th>
											<th id="g_contents">내용</th>
											<th id="g_status">상태</th>
										</tr>
									</thead>								
									<tbody>
									<c:choose>
										<c:when test="${itemsize==0}">
										<tr>
											<c:if test="${identity eq 'owner'}">
												<td colspan="4"><c:out value="등록된 물품이 없습니다."/></td>
											</c:if>
											<c:if test="${identity eq 'guest'}">
												<td colspan="3"><c:out value="등록된 물품이 없습니다."/></td>
											</c:if>
										</tr>										
										</c:when>
										<c:otherwise>							
										<c:forEach begin="0" end="${itemsize-1}" var="i" varStatus="status">
										<c:if test="${item[i].status != 4 }">
										<tr class ="td_set">
											<c:if test="${identity eq 'owner'}">
												<td>	
													<input type="checkbox" name="choice" value="${item[i].goods_num}">
 												</td>
 											</c:if>
 											<td>
 												<img class="media-object" src="${item[i].image}">
 											</td>
 											<td class="contenthegiht">										
 												<div class="media-body">
 												<h4 class="media-heading">
	 												<a href="#" onclick="javascipt:newWindowshowitem(${item[status.index].goods_num})">
	 												${item[i].book_name}
	 												</a>
 												</h4>
 												${item[i].content}
 												</div>
 											</td>
 											<td>
 												<c:choose>
 											 		<c:when test="${item[i].status == 1 }">
	 													신규
													</c:when>												
	 												<c:when test="${item[i].status == 2 }">
	 													거래중
	 												</c:when>
	 													
	 												<c:when test="${item[i].status == 3 }">
	 													거래완료
	 												</c:when>
	 												<c:otherwise>
	 													Error
	 												</c:otherwise>
 												</c:choose> 												
 											</td>
										</tr>
										</c:if>
										
										</c:forEach>
										</c:otherwise>
										</c:choose>
									</tbody>
								</table>
							</div>
						
							<div class="print_sum">총 개수 = <span id="sum" class="count"></span>${itemsize}</div>
								<br/>
							<c:if test="${identity eq 'owner'}">
								<div class="right">
									<a href="#modal" data-toggle="modal"><input type="button" class="btn btn-primary" value="거래" ></a>
									<input type="submit" class="btn btn-success" name="status" value="거래완료" id="finish">
									<input type="submit" class="btn btn-danger" name="status" value="물품삭제" id="del">
								</div>
								
								<div id="modal" class="modal hide fade">
								  <div class="modal-header">
								    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								    
								    <h3>구매자의 회원번호를 입력해주세요</h3>
								  </div>
								  <div class="modal-body">
								  	<input type="text" name="purchase_memnum">
								  </div>
								  <div class="modal-footer">
								  	<input type="submit" class="btn btn-primary" name="status" value="거래">
								    <a href="garretServlet.do" class="btn">Close</a>								    
								  </div>
								</div>
							</c:if>	
							</div>
							</form> 	<!-- 체크박스와 아이템 넘버를 가져감 -->
						
						
						</div>
						
					
 						
 						</div>
 
 	 				</div>
 	 				</div>

 				</div>
 				<div class="navbar navbar-inverse navbar-fixed-bottom">
  <div class="navbar-inner">
    <!--<a class="brand" href="#">Title</a>-->
    <ul class="nav">
      	<li>
                <a href="http://www.crystars.com">NewItme</a>
       </li>
      <li><a id="wishList" href="#">Favorite</a></li>
      <li class="dropdown" id="logf">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <img id="portrait"  width="20" height="20" src="" alt="">
                <span></span>
                </a>
                <ul class="dropdown-menu">
                  <li><a class="logout" href="#">logo out</a></li>
                  <li><a href="#myModal" data-toggle="modal">sell Item</a></li>
                  <li><a href="#"id="garretLink">다락방</a></li>
                  <li class="divider"></li>
                  <li class="nav-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li>
                </ul>
                 <li><a href="http://www.crystars.com/BoardServlet.do?page=1">게시판</a></li>
    </ul>
  </div>
</div>
			
          <div class="row-fluid" id="mainItemList">
          </div>
          
          <div >
          	<ul class="nav nav-pills" id="pageNav">
            </ul>
          </div>
      
                
          <div id="sectItemSell">
          	
          </div>
          
          <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h3>Search Item</h3>
              </div>
              <div class="modal-body">
                <input type="text" id="modalSearchIput" width="200"  data-provide="typeahead" />
                <button type="button" class="btn btn-warning" id="newItemSearchBtn">검색</button>
                
                <nav style="margin-top:3px">
	  			<a class="select">제목</a>
                <a>저자</a>
	  			</nav>
                
                <div id="modalSearchOption"></div>
                
                <div id="searchResultContainer"></div>
              	</div>
              
              <div class="modal-footer">
                <a href="#" id="searchCancel" class="btn">close</a>
                <a href="#" id="searchNextBtn" class="btn btn-primary">pass</a>
              </div>
            </div>
          
    	 
             	 
         <div id="regBookModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">Regist</h3>
              </div>
              <div class="modal-body">
                    <div class="row-fluid">
                        <ul class="thumbnails">
                          <li class="span3">
                            <a href="#" class="thumbnail">
                              <img class="imgsock" src="http://www.crystars.com/upload/defaultSmall.jpg" alt="default1">
                            </a>
                          </li>
                          <li class="span3">
                            <a href="#" class="thumbnail">
                              <img class="imgsock" src="http://www.crystars.com/upload/defaultSmall.jpg" alt="default1">
                            </a>
                          </li>
                          <li class="span3">
                            <a href="#" class="thumbnail">
                              <img class="imgsock" src="http://www.crystars.com/upload/defaultSmall.jpg" alt="default1">
                            </a>
                          </li>
                          <li class="span3">
                            <a href="#" class="thumbnail">
                              <img class="imgsock" src="http://www.crystars.com/upload/defaultSmall.jpg" alt="default1">
                            </a>
                          </li>
                        </ul>
         			 </div>
                  
              
                  <div>
                   <legend>상세정보</legend>
                  	<div style="float:left;">
                  	<label>price</label>
                    <input name="price" type="text" id="submitPrice"><br/>
                    <label>title</label>
                    <input name="title" type="text" id="submitTitle"><br/>
                    <label>상태</label>
                        <input id="rdo1" type="radio" name="quality" value="1">매우좋음
                        <input id="rdo2" type="radio" name="quality" value="2" checked="checked">중고
                        <input id="rdo3" type="radio" name="quality" value="3">손상됨
                    </div>
                    <div style="float:left;  margin-left:50px;">
                    <textarea name="subscritp" cols="10" rows="6" id="submitText">
                    </textarea>
					</div>
                  </div>
                	
                    
              </div>
              
              <div class="modal-footer">
                <a href="#" class="btn" id="regBookCancel">Close</a>
            	<a href="#" class="btn btn-primary" id="submitIme">change Images</a>
              </div>
            </div>
            
            
            
            
            <!-- 아이템 상세보기 Modal@@@@@@@@@@@@-->
            
            <div id="viewItem" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                
                    <div class="media" style="padding:3px;">
                          <a class="pull-left" href="#">
                            <img  id="viewItemPortriat" class="media-object" style="margin:1px;" src="http://placehold.it/64x64" width="32"height="32">
                          </a>
                          <div class="media-body">
                            <div class="media" style="font-size:11px; margin-top:0;line-height:15px">
                              <a id="viewItemName" href=""></a><br/>
                              <span id="viewItemDate"></span>
                            </div>
                          </div>
                        </div>
                 
              </div>
              <div class="modal-body">
              
              
			  <div style="margin:auto; text-align:center;  line-height:1.8;">              
                 
                  <div>
                   <div style="float:left;">
                    <div>
                  	 <img id ="viewItemImg1" src="http://www.crystars.com/upload/defaultSmall.jpg" alt=""></div>
                    </div>
                    <div style="float:left;  margin-left:0px; text-align:left;">
                    
                    <ul style="list-style-type:none; line-height:15px;">
                    <li><b><span id="viewItemBookTitle"></span></b></li>
                    <li><span id="viewItemBookPrice"></span>원</li>
                   	<li>출판사 : </li>
                    <li><span id="viewItemBookPublisher"></span></li>
                    <li><span id="viewItemBookContent"></span></li>
					</ul>

                   </div>
                  </div>
                  
                  <div style="clear:both"></div>
					<p></p>
                        <div class="row-fluid" >
                            <ul class="thumbnails">
                              <li class="span3">
                                <a id ="viewItemImg2" href="#" class="thumbnail">
                                  <img src="http://www.crystars.com/upload/defaultSmall.jpg" alt="">
                                </a>
                              </li>
                              <li class="span3">
                                <a id ="viewItemImg3" href="#" class="thumbnail">
                                  <img src="http://www.crystars.com/upload/defaultSmall.jpg" alt="">
                                </a>
                              </li>
                              <li class="span3">
                                <a id ="viewItemImg4" href="#" class="thumbnail">
                                  <img src="http://www.crystars.com/upload/defaultSmall.jpg" alt="">
                                </a>
                              </li>
                              <li class="span3">
                                <a id ="viewItemImg5" href="#" class="thumbnail">
                                  <img src="http://www.crystars.com/upload/defaultSmall.jpg" alt="">
                                </a>
                              </li>
                            </ul>
                         </div>
                  </div>
                  
              </div>
              
              <div class="modal-footer">
                <a id="closeViewItem" class="btn">Close</a>
              </div>
            </div>
         
         	<!-- 아이템 상세보기 Modal@@@@@@@@@@@@-->
          
<br/><br/>
<div class="fb-root"></div>
<div class="fb-login-button leftAlign" data-show-faces="true" data-width="300" data-max-rows="1" registration-url="http://www.crystars.com">
</div> 
<br/><br/><br/><br/><br/>


			<div style="visibility:hidden">
				<div id="imgFramePreset">
                  <div style="position:absolute; z-index:15; float:right; margin-top:5px; margin-left:-3px; padding-left:0px;">
                  <button type="button" class="btn btn-info quickItem" id="shareitem">공유</button>
                  <button type="button" class="btn quickItem" id="wishitem">wishit</button>
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
		//setItemDB($(this).val(),doGet);
		$(this).tab('show');
	});

	$('#garretLink').click(function(e) {
		
        getData(function(usrdata){
			var data=[];
			data[0] = {"name":"userid","value":usrdata.pid};
			
			cst_movePage( "/garretServlet.do",data,CRYSTAL_GET);
			});
   });
	$('#wishList').click(function(e) {

       getData(function(usrdata){
			var send= {};
			send.user = userData;
			location.href = CST_HOME + "/mycart.html?json="+JSON.stringify(send);
		
	 		});
	 });
});

function newWindowmsgread(msg_num){
	location.reload();
	newchromeLess("messageServlet.do?sendtype=0&msg_num="+msg_num,900,730,100,100, "메시지보기");
}
function newWindowmsgsend(fromid){
	location.reload();
	newchromeLess("messageServlet.do?sendtype=1&fromid="+fromid,900,730,100,100, "메시지보내기");
}
function newWindowmsgsend(fromid,toid){
	location.reload();
	newchromeLess("messageServlet.do?sendtype=2&fromid="+fromid+"&toid="+toid,900,730,100,100, "메시지보내기");
}
function newWindowshowitem(goods_num){
	newchromeLess("messageServlet.do?sendtype=3&goods_num="+goods_num,900,730,100,100, "아이템정보");
}
function setItemDB(raw,callback){
	
 	callJsonP( "/good",GET,raw,callback);
}


function chart(){
	
    $(document).ready(function() {
    	var sales = Array();
    	var purchase = Array();
    	<c:forEach items="${salescount}" var="item"> 
        	sales.push("${item}"); 
        </c:forEach>
        <c:forEach items="${purchasecount}" var="item">
        	purchase.push("${item}");
        </c:forEach>
        
        for(var i=0;i<12;i++){
        	sales[i] = 1*sales[i];
        	purchase[i] = 1*purchase[i];
        }
    	
    	
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'container',
                type: 'line',
                marginRight: 130,
                marginBottom: 25
            },
            title: {
                text: '월별 거래 횟수',
                x: -20 //center
            },
            subtitle: {
                x: -20
            },
            xAxis: {
                categories: ['1','2','3','4','5','6','7','8','9','10','11','12']
            },
            yAxis: {
                title: {
                    text: ''
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
                        this.y +'개';
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
                name: '구매',
                data: [purchase[0], purchase[1], purchase[2], purchase[3], purchase[4], purchase[5], purchase[6], purchase[7], purchase[8], purchase[9], purchase[10], purchase[11]],
            },{
                name: '판매',
                data: [1*sales[0], sales[1], sales[2], sales[3], sales[4], sales[5], sales[6], sales[7], sales[8], sales[9], sales[10], sales[11]]
            }]
        });
    });

}

</script>

