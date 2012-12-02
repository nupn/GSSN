<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
 	<head>
 		<meta charset="utf-8">
 		<title>물품 등록</title>
 	<link href="css/bootstrap.css" rel="stylesheet">
 		<link href="css/goodadd.css" rel="stylesheet">
 		<script src='js/jquery-1.8.2.js'></script>
 		<script src='js/bootstrap-tab.js'></script>
	</head>
 	<body >
 	<div id="g_lay1-1">
 		<br/>
 		<a href="#">
 		    <img class="image_set" src="image/300x300.jpg">
 		</a>
 		<div class="blank">
 		</div>
 		<div class="isbn_text">
 			ISBN으로 검색하기
 		</div>
 		
 		<div class="input-append">
 			<input class="span2" id="appendedInputButton" type="text">
 			<button class="btn" type="button">Search</button>
 		</div>
 		<div class="isbn_text2">
 			책 뒤편에 있는 ISBN 을 이용하시면 <br/>
 			책 제목, 표지그림, 출판사명이 <br/>
 			자동으로 입력됩니다.
 		</div>
 		<!--클릭하면 표지사진 첨부-->
 	</div>
 	<div id="g_lay1-2">
 		<br/>
 		<form class="form-horizontal" >
			<div class="control-group">
				<label class="control-label">책 제목</label>
				<div class="controls">
					<input type="text" id="bookname" class="input_set" placeholder="제목" >
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">출판사</label>
				<div class="controls">
					<input type="text" id="publisher" class="input_set" placeholder="출판사명">
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >상태</label>
				<div class="controls">
					<select id="grade">
						<option>S</option>
						<option>A</option>
						<option>B</option>
						<option>C</option>
						<option>D</option>
						<oprion>F</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >구입가격</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" id="purchaseprice" class="input_set2" placeholder="ex) 12000"> 
									
						<span class="add-on">원</span>

						<input type="text" id="per" placeholder="ex) 80">
						<span class="add-on">%</span>
					</div>


				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >판매가격</label>
				<div class="controls">
					<div class="input-append">
						<input type="text" id="sellprice"  class="input_set2" placeholder="ex) 8000" value="">
						<span class="add-on">원</span>
						<input type="button" id="price_cal" value="계산하기">
						<!-- 자바스크립트로 %정하면 가격 할당되게하자-->
					</div>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" >추가설명</label>
				<div class="controls">
					<textarea rows="7" id="contents"  class="input_set" placeholder="책 설명, 혹은 품질에 대한 추가적인 설명을 입력해주세요"></textarea>
				</div>
			</div>

		</form>
 	</div>
 	<div id="blank">

 	</div>
 	<br/>
 	<div id="g_lay2">

 		<div id="g_lay2_title">
 			추가이미지- 타이틀
 		</div>
 		<a href="#">
 		    <img class="image_set2" src="image/younha.jpg">
 		</a>
 		&nbsp;
 		<a href="#">
 		    <img class="image_set2" src="image/younha.jpg">
 		</a>
 		&nbsp;
 		<a  href="#">
 		    <img class="image_set2" src="image/younha.jpg">
 		</a> 
 	</div>		<!--추가사진 첨부-->
 	</div>
 	<br/>
 	<div id="g_lay3">
 		<input type="button" class="btn btn-success" value="물품추가" id="add">
 		<input type="button" class="btn btn-danger" value="물품삭제" id="del">
 		<!-- 버튼 자리-->
 	</div>
 	<div id="g_lay4">
 	</div>
 	</body>
 </html>
 <script type='text/javascript'>
$(function () {
	$('#price_cal').click(function(){
		var p_price =parseInt( $('#purchaseprice').val());
		var per = parseInt($('#per').val());
		if(!isNaN(p_price) && !isNaN(per)){
			var s_price = p_price * per * 0.01;
			$("#sellprice").val(s_price);	
		}
	});
});
</script>