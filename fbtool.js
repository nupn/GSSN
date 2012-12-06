// JavaScript Document
/*
document.write('<scr'+'ipt type="text/javascript" src="./lib/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./lib/crystal.js"><\/scr'+'ipt>');
*/
function fb_checkSession(id,token,callback){
	var data={};
	data.id = id;
	data.token = token;
 	callJsonP( "/CheckSession.do",CRYSTAL_GET,data,callback);
}
function fb_setUserInDB(raw,callback){
	
 	callJsonP( "/User.do",CRYSTAL_GET,raw,callback);
}

function getdb_Item(type,page,needTotal,callback){
	var data={};
	data.itemType = type;
	data.page=page;
	data.needTotal = (needTotal!=null) ? "total":"none";
 	callJsonP( "/Item.do",CRYSTAL_GET,data,callback);
}


var DAUM_SEARCH_ALL = 'all';
var DAUM_SEARCH_TITLE = 'title';
var DAUM_SEARCH_WRITER = 'writer ';

function getBookBYDaum(query,type,callback){
	var data={};
		data.apikey = '056bdbabcaa675a7383729e2da0f9e42405eadf2';
		data.q = query;
		data.result = '20';
		data.output = 'json';
		data.searchType = type;
		data.sort ='accu';
		 
 	callJson( "http://apis.daum.net/search/book",CRYSTAL_GET,data,callback);
}

function getdb_ItemBYcate(type,page,cate,cateval,needTotal,callback){
	
}


function getItemRegPage(data)
{
	
		var div;
		div = getDynamicDOM("div","media hverBG","searchContainerMain");
		
		var a=getDynamicDOMLink("span","pull-left");
		var img = getDynamicDOMImg(data.cover_s_url);
		a.appendChild(img);
		
		var divBody = getDynamicDOM("div","media-body");
		var bodyh4 =  getDynamicDOM("h4","media-heading");
		bodyh4.innerHTML=htmlEntityDecode(data.title);
		
		var nav = getDynamicDOM("nav");
		var a1;
		a1=getDynamicDOM("a");
		a1.innerHTML =htmlEntityDecode(data.author); 
		nav.appendChild(a1);
		
		a1=getDynamicDOM("a");
		a1.innerHTML =htmlEntityDecode(data.sale_price); 
		nav.appendChild(a1);
		
		
		a1=getDynamicDOM("a");
		a1.innerHTML =htmlEntityDecode(data.pub_nm); 
		nav.appendChild(a1);
		
		a1=getDynamicDOM("a");
		a1.innerHTML =htmlEntityDecode(data.translator); 
		nav.appendChild(a1);
		
		
		var span = getDynamicDOM("span");
		span.innerHTML = htmlEntityDecode(data.description.substr(0,75)+"...");
		
		var hid = getDynamicDOMExtend("input" , [{"name":"name","value":"searchBookIsbn"},{"name":"type","value":"hidden"},{"name":"value","value":data.isbn}]);

		
		divBody.appendChild(bodyh4);
		divBody.appendChild(nav);
		divBody.appendChild(span);
		divBody.appendChild(hid);
		
		div.appendChild(a);
		div.appendChild(divBody);
		
		
		return div;
}
/*
<nav>
	  <a href="http://tranbot.net/index.php?tag=CSS">최신순</a><a href="http://tranbot.net/index.php?tag=레이아웃">판매자등급순</a>
      <a href="http://tranbot.net/index.php?tag=레이아웃">판매자인기순</a>
	  </nav>

<div class="media">
  <a class="pull-left" href="#">
    <img class="media-object" src="http://placehold.it/64x64">
  </a>
  <div class="media-body">
    <h4 class="media-heading">Media heading</h4>
    
  </div>
</div>


 <div class="modal hide fade">
	  <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		<h3>Modal header</h3>
	  </div>
	  <div class="modal-body">
		<p>One fine body…</p>
	  </div>
	  <div class="modal-footer">
		<a href="#" class="btn">Close</a>
		<a href="#" class="btn btn-primary">Save changes</a>
	  </div>
	</div>
*/