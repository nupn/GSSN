// JavaScript Document
/*
document.write('<scr'+'ipt type="text/javascript" src="./lib/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./lib/crystal.js"><\/scr'+'ipt>');
*/

function fc_sendQuery(str){
	
	//www.crystars.com:9090/search/json?cn=myfc&fl=data&se={data:OR(dragun):100:15}&sn=1&ln=50
	var data={};
	data.cn = 'myfc';
	data.fl = 'data';
	data.se = '{data:OR(dragun):100:15}';
	data.sn = 1;
	data.ln=50;
 	callJson( "/Proxy.do",CRYSTAL_GET,data,fc_receiveResult);
}


function fc_receiveResult(data){
	
	var datas= data;
	alert(datas);
	
}


function fb_sendFeedQuick(itemData){
				var variables={};
				//variables.access_token = primaryKey;
				variables.message = itemData.title;
				variables.picture = itemData.image;
				variables.name = itemData.title;
				variables.caption = itemData.name;
				variables.description = itemData.content;
				variables.link = "http://www.crystal.com/view?id="+itemData.id;
				variables.actions="{name:\"찜하기\",link:\"http://www.crystal.com/favorite?id="+itemData.id+"\"}";
					
	
FB.api('/me/feed',variables, function(response) {
 	 
});
}

function fb_sendFeed(itemData){
  FB.ui(
  {
    method: 'feed',
    name: itemData.title,
    link: "http://www.crystal.com/view?id="+itemData.id,
    picture: itemData.image,
    caption: itemData.name,
    description: itemData.content,
	actions : "{name:\"찜하기\",link:\"http://www.crystal.com/favorite?id="+itemData.id+"\"}"
					
  },
  function(response) {
    if (response && response.post_id) {
      alert('Post was published.');
    } else {
      alert('Post was not published.');
    }
  }
);	
}

function fb_checkSession(id,token,callback){
	var data={};
	data.id = id;
	data.token = token;
 	callJsonP( "/CheckSession.do",CRYSTAL_GET,data,callback);
}
function fb_setUserInDB(raw,callback){
	
 	callJsonP( "/User.do",CRYSTAL_GET,raw,callback);
}
function deleteImge(src,callback){
	var data={};
	data.filename = src;
	data.delframe = "";//desprecae
 	callJsonP( "/Upload.do",CRYSTAL_GET,data,callback);
}

function getdb_Item(type,page,needTotal,callback){
	var data={};
	data.itemType = type;
	data.page=page;
	data.needTotal = (needTotal!=null) ? "total":"none";
 	callJsonP( "/Item.do",CRYSTAL_GET,data,callback);
}

function regist_Item(datas,callback){
	
 	callJsonP( "/RegistItem.do",CRYSTAL_GET,datas,callback);
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
		 
 	callJsonP( "http://apis.daum.net/search/book",CRYSTAL_GET,data,callback);
}

function getdb_ItemBYcate(type,page,cate,cateval,needTotal,callback){
	
}

function getFooterPortrait(data,desc){
	var div= getDynamicDOM("div",'media mediaBoder');
	
	var hom="";
	if(data.home!=null)
	hom =data.home;
	
	var almg =  getDynamicDOMExtend("a",[{"name":"class","value":"pull-left"},{"name":"href","value":hom}]);
	
	var img = getDynamicDOMExtend("img" , [{"name":"src","value":data.portrait},{"name":"class","value":"media-object"},{"name":"alt","value":"portrait"},{"name":"width","value":"32px"},{"name":"height","value":"32px"}]);
	
	var div1 = getDynamicDOM("div","media-body");
	
	var div2 = getDynamicDOM("div","media inner-media");
	
	
	var a1 = getDynamicDOMExtend("a" , [{"name":"href","value":data.home}]);
	a1.appendChild(document.createTextNode(data.name));
	var a2 = getDynamicDOM("span");
	a2.appendChild(document.createTextNode(desc.price));
	
	
	
	div2.appendChild(a1);
	div2.appendChild(getDynamicDOM("br"));
	div2.appendChild(a2);
	
	almg.appendChild(img);
	div1.appendChild(div2);
	
	div.appendChild(almg);
	div.appendChild(div1);
	
	var hid = getDynamicDOMExtend("input",[{"name":"id","value":"hoverItemId"},{"name":"type","value":"hidden"}]);
	div.appendChild(hid);
	
	return div;
	
}

function createListItem(itemDesc){
		

		var li = getDynamicDOM("li",'span3');
		var div= getDynamicDOM("div",'thumbnail');
		
		var divkk= getDynamicDOM("div",'imgFrame');
		
		var diva= getDynamicDOM("div",'aaa');
		var img= getDynamicDOMImg(itemDesc.image,"");
		//img.setAttribute('src',itemDesc.image);
		
		var div2= getDynamicDOM("div",'caption');
		//div2.setAttribute("class",'caption');
		
		var h3= getDynamicDOM("h5");
		var h3text = document.createTextNode(itemDesc.title);
		h3.appendChild(h3text);
		var p1= getDynamicDOM("p");
		var p1text = document.createTextNode(itemDesc.content);
		h3.appendChild(p1text);
		var p2=getDynamicDOM("p");
		
		var div3 = getFooterPortrait(itemDesc.user,itemDesc);
		
		diva.appendChild(img);
		div2.appendChild(h3);
		div2.appendChild(p1);
		div2.appendChild(p2);
		div.appendChild(diva);
		div.appendChild(divkk);
		div.appendChild(div2);
		div.appendChild(div3);
		li.appendChild(div);
		
		return li;
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
		
		var hid = getDynamicDOMExtend("input" , [{"name":"class","value":"searchBookIsbn"},{"name":"type","value":"hidden"},{"name":"value","value":data.isbn}]);

		
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