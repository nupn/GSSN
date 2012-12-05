/*
document.write('<scr'+'ipt type="text/javascript" src="./lib/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./lib/crystal.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./fbtool.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./facebook-js-sdk/src/meta/all.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./UI.js"><\/scr'+'ipt>');
*/
// plug in


  (function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/ko_KR/all.js#xfbml=1&appId="+CRYSTAL_APP_ID;
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

/*
(function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/en_US/all.js";
     ref.parentNode.insertBefore(js, ref);
   }(document));
	*/

// initial info stream

  window.fbAsyncInit = function() {
	initial=true;
	if($.browser.msie==false)
	{
    FB.init({
      appId      : CRYSTAL_APP_ID, // App ID
      channelUrl : '//www.crystars.com/channel.html', // Channel File
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true,  // parse XFBML
	  fileUpload : true
    });
	}
    // Additional init code here
	
	 FB.getLoginStatus(function(response) {
	  if (response.status === 'connected') {
		//getToken( receivetoken );
		
			authSuccess(response.authResponse.userID,response.authResponse.accessToken);
	  } else if (response.status === 'not_authorized') {
			updateStatus(CRYSTAR_STATUS_DAUTH);
			//login();
			initial=false;
	  } else {
		  updateStatus(CRYSTAR_STATUS_LOGOUT);
		  initial=false;
	  }
	 });
	 
	get_Item(-1);
	addOverEvent();
  };

  // Load the SDK Asynchronously
  /*
    (function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/en_US/all.js";
     ref.parentNode.insertBefore(js, ref);
   }(document));
   //*/
   
   function login() {
    FB.login(function(response) {
        if (response.authResponse) {
            // connected
			authSuccess(response.authResponse.userID,response.authResponse.accessToken);
        } else {
            // cancelled
        }
    }, {scope: 'email,publish_actions,publish_stream,user_photos'});
	}
  
   function authSuccess(id,token) {
    //console.log('Welcome!  Fetching your information.... ');
    fb_checkSession(id,token,checkSessionEnd);
		
		
	}
	
	function checkSessionEnd(raw){
		if(raw.id!=null && raw.id!=CRYSTAL_EMPTY)
		{
			//console.log('recieve user data 22');
			successInitUser(raw);
		}else{
			//console.log('creat User data');
			successGetUserData(raw.token);
		}
	}
	
	
	function successGetUserData(token){
		
		FB.api('/me', function(response) {
        	//console.log('Good to see you, ' + response.name + '.'+response.id + '.');
			var obj ={};
			obj.name = encodeURIComponent(response.name);
			obj.id = response.id;
			obj.email = response.email;
			obj.link = response.link;
			obj.token = token;
			obj.portrait = 'http://graph.facebook.com/'+response.id+'/picture';
			fb_setUserInDB(obj,successInitUser);
    	});
		
		
	}
	
	function successInitUser(data){
		//console.log('Good to see you,');
		initial=false;
		userData = data;
		userData.name = decodeURIComponent(userData.name);
		if(completeFuc!=null)
			completeFuc(data);
		
		updateStatus(CRYSTAR_STATUS_LOGIN);
		
	}
	
	var initial=false;
	var completeFuc;
	var userData;
	var currentState;
	function getData(funct){
		
		if(userData ==null && initial==false)
		{
			completeFuc = funct;
			
			 FB.getLoginStatus(function(response) {
	  			if (response.status === 'connected') {
				initial=true;
				authSuccess(response.authResponse.userID,response.authResponse.accessToken);
	  			} else if (response.status === 'not_authorized') {
				updateStatus(CRYSTAR_STATUS_DAUTH);
	  			} else {
		  		updateStatus(CRYSTAR_STATUS_LOGOUT);
	  			}
			 });
		}else if(userData !=null){
			funct(userData);
		}
	}
	
	
	function updateStatus(sts){
		if(currentState==sts)
				return;
				
		currentState = sts;
		if(sts == CRYSTAR_STATUS_LOGIN)
		{
			cst_show('#logf>a>img');
			$('#logf>a>img').attr('src',userData.portrait);
			$('#logf>a>span').text(userData.name);
			$('#logf>a').attr("data-toggle","dropdown");
			//var el = document.getElementById("portrait");
			//el.scr = userData.portait;
			
			
		}else if(sts == CRYSTAR_STATUS_DAUTH){
			$('#logf>a>img').attr('src',"");
			$('#logf>a>span').text('login');
			$('#logf>a').attr("data-toggle","");
			cst_hide('.fb-login-button');
			cst_hide('#logf>a>img');
			//$('#topSearch #fb-root').show();
			//$('#topSearch #fb-root').attr("display","inline");
			//$('#topSearch #search').hide();
			//$('#topSearch #search').attr("display","none");
		}else{
			$('#logf>a>img').attr('src',"");
			$('#logf>a>span').text('login');
			$('#logf>a').attr("data-toggle","");
			
			cst_hide('.fb-login-button');
			cst_hide('#logf>a>img');
			//$('#topSearch #fb-root').show();
			//$('#topSearch #fb-root').attr("display","inline");
			//$('#topSearch #search').hide();
			//$('#topSearch #search').attr("display","none");
		}
	}
	
	$('#logf>a').click(logIconDown);
	function logIconDown(e){
		
		if((currentState == CRYSTAR_STATUS_DAUTH || currentState == CRYSTAR_STATUS_LOGOUT )&& initial==false)
		{
			login();
		}
	}
	
	$('.logout').click(logOut);
	function logOut(){
		FB.logout(function(response) {
  		location.reload();
  		});
	}
	
	
	
	/*
	$('#test1').click(createAlbum);
	function createAlbum(){
		FB.api('/me/albums', 'post', { 'access_token': userData.token ,'name':'crystars album'   }, function(response) {
		  if (!response || response.error) {
			//alert('Error occured');
		  } else {
			
			//addImg( response.id );
			
		  }
		});
	
	}
	
	function addImg(albumid){
		
		FB.api('/'+albumid+'/photos', 'post', { 'access_token': userData.token  }, function(response) {
		  if (!response || response.error) {
			//alert('Error occured');
		  } else {
			
			//alert( response.id );
			
		  }
		});
	}*/
	
	function getItemDescById(id){
		
	var result=null;
	 $(currentPageItemData).each(function(index, element) {
        if(element.id == id){
			result =  element
			return false;	
		}
    });
	return result;
	}
	
	var currentPageItemData;
	var hoverItem;
	var ROW_COUNT=3;
	var PAGE_ITME_COUNT = 12;
	function createItemList(data){
		
		currentPageItemData = data;
		cst_clearNodeById( 'mainItemList' );
		
		var ul ;
		ul= getDynamicDOM("ul",'thumbnails');
		var items;
		var i =0;
		$(data).each(function(index, element) {
			if(i>ROW_COUNT)
			{
				$('#mainItemList').append(ul);
				ul= getDynamicDOM("ul",'thumbnails');
				//ul.setAttribute("class",'thumbnails');
				i=0;
			}
			items = createListItem(element);
			
			$(items).hover(function(e){
			console.log( "hoverin");
			var target=$(this).find('.imgFrame');
			hoverItem = this;
				target.append(preset);
				$(this).find("#hoverItemId").attr("value",element.id);
				$('#shareitem').click(function(e) {
					if(hoverItem!=null)
						itemShare( $(hoverItem).find("#hoverItemId").attr("value") );
				});
			},function(e){
				$(this).find('.imgFrame').empty();
				
			});
			
			
			ul.appendChild( items );
			
			i++;
        });
		
		
		
			
		$('#mainItemList').append(ul);
		
		if(preset==null)
		{	
		preset=$('#imgFramePreset');
		$('body').remove('#imgFramePreset');		
		}
	}
	
	function itemShare(id){

	fb_sendFeed( getItemDescById(id) );
	
	}
	
	var PAGE_NAV_COUNT=9;
	var currentPage;
	var pageTotal;
	
	var grandPageTotal;
	var currentGrandPage;
	function get_Item(ppage){
		if(ppage==-1)
		{
			getdb_Item(CRYSTAR_ITEM_DATE ,1,"total",recieve_Item);
		}else{
			getdb_Item(CRYSTAR_ITEM_DATE ,ppage,"total",recieve_Item);
		}
	}
	
	
	function recieve_Item(data){
		currentGrandPage = 1;
		currentPage = data.page;
		if(data.total!=-1)
		{
		pageTotal = Math.ceil(data.total/PAGE_ITME_COUNT);
		
		currentGrandPage = Math.floor(currentPage/PAGE_NAV_COUNT);
		grandPageTotal = Math.ceil(pageTotal/PAGE_NAV_COUNT);
		
		
		}
		
		createPageNav();
		createItemList(data.result);
		
	}
	
	function createPageNav(){
		
		var ul ;
		ul= $('#pageNav');
		
		var li;
		var a;
		var atext;
		var i=0;
		var boldPoint = currentPage%PAGE_NAV_COUNT;
		var startPoint = Math.floor(currentPage/PAGE_NAV_COUNT)*PAGE_NAV_COUNT;
		var totalbatch = (pageTotal-(currentGrandPage*PAGE_NAV_COUNT))<=PAGE_NAV_COUNT ? (pageTotal-(currentGrandPage*PAGE_NAV_COUNT)): PAGE_NAV_COUNT;

		//(int)((pageTotal-(currentGrandPage*PAGE_NAV_COUNT))<=PAGE_NAV_COUNT ? (pageTotal-(currentGrandPage*PAGE_NAV_COUNT)): PAGE_NAV_COUNT);
		
			a = getDynamicDOMLink("a",null,null,"#");
			var atext = document.createTextNode('<');
			a.appendChild(atext);
			li= getDynamicDOM("li");
			li.appendChild(a);
			ul.append( li );
		
		for(i=1;i<totalbatch;i++){
			startPoint++;
			
			a = getDynamicDOMLink("a",null,null,"#");
			var atext = document.createTextNode(startPoint);
			a.appendChild(atext);
			if(i==boldPoint)
			li= getDynamicDOM("li","active");
			else
			li= getDynamicDOM("li");
			
			li.appendChild(a);
			ul.append( li );
			
        }
			a = getDynamicDOMLink("a",null,null,"#");
			var atext = document.createTextNode('>');
			a.appendChild(atext);
			li= getDynamicDOM("li");
			li.appendChild(a);
			ul.append( li );
			
		
		
	}
	
	var preset;
	var presetDom;
	var krDiv="#";
	var mainStr="";
	var typeAheadResult="";
	function addOverEvent(){

		
		var div = getDynamicDOM("div");
		var ul = getDynamicDOM("ul","nav nav-pills");
		
		var li = getDynamicDOM("li");
		var litext = document.createTextNode('pick');
		li.appendChild(litext);
		ul.appendChild(li);
		
		li = getDynamicDOM("li");
		litext = document.createTextNode('share');
		li.appendChild(litext);
		ul.appendChild(li);
		
		div.appendChild(ul);
		presetDom = div;
		
		$('#pageNav').click(function(e) {
            var data = e.target.text;
			//var data2 = e.target.innerHTML; >&gt; <&lt;
			if(data==">")
			{
				if(currentGrandPage+1>=grandPageTotal)
					return;
					
				currentGrandPage = currentGrandPage+1;	
				get_Item( ((currentGrandPage+1) * PAGE_NAV_COUNT)+1 );
			}
			else if(data=="<")
			{
				if(currentGrandPage==0)
					return;
					
				currentGrandPage = currentGrandPage-1;
				get_Item( ((currentGrandPage) * PAGE_NAV_COUNT)+1 );
			}else{
				get_Item(data);
			}
			cst_clearNodeById( 'pageNav' );
        });
		
		/*$('.sellitem').click(function(e) {
        	    $('#sectItemSell').append(getItemRegPage());
        });*/
		
		if($.browser.mozilla )
		{
			autoCompleteMozila();
			
			$('#modalSearchIput').css({"ime-mode":"Disabled"});

		}else{
			autoCompleteElse();
		}
		//----------------
		
		//------------------
		
		$('.form-search>.btn').click(function(e) {
            fc_sendQuery("dragun");
			e.preventDefault();
			e.stopPropagation();
        });
		
		$('#mainItemList').mouseover(function(e) {
        	    //console.log('over');
        });
		
		$('#mainItemList').mouseout(function(e) {
            	//console.log('out');
        });
		
		
		
		$('#newItemSearchBtn').click(newItemSearch);
		$('#searchCancel').click(function(e) {
        		    hideSearchBook();
        });
		$('#regBookCancel').click(function(e) {
        		    hideRegBook();
        });
		$('#searchNextBtn').click(function(e) {
            gotoBookRegis("");
        });
		$('#searchResultContainer').click(function(e) {
			//need modf
			var data = e.target.parentNode;
			var count=0;
			while( data.className != "media hverBG" && count<3)
			{
						data = data.parentNode;
						count++;
			}
			if( data.className == "media hverBG")
			{
            var node = data.childNodes;
			var temp;
				$(node).each(function(index, element) {
					if(element.nodeName=="DIV")
					{
							 temp = element;
					}
				});
				temp =temp.childNodes
				$(temp).each(function(index, element) {
					if(element.nodeName=="INPUT")
							 gotoBookRegis(element.value);
				});
			}
			
			
			//alert($(this).find('.searchBookIsbn').attr("value"));;
        });
		
		
		
		$('.imgsock').click(function(e){
					showPopup( this);
				});
				
		$('#submitIme').click(submitNewItem);
	}
	function hideSearchBook(){
		searchData=null;
		cst_clearNodeById('searchResultContainer');
		$('#myModal').modal('hide');
	}
	
	
	function autoCompleteElse(){
		$('#modalSearchIput').typeahead({
				minLength: 1
				 , exlistener : exlisteners
			  //, highlighter: function (item) {return item}
			  , source: function (query, process) {
					if(finished==false)
						return;
					finished=false;
					
					setAjaxFilter();
						
					var dat={};
					dat.apikey = '056bdbabcaa675a7383729e2da0f9e42405eadf2';
					dat.q = query;
					dat.result = '5';
					dat.output = 'json';
					dat.searchType = DAUM_SEARCH_ALL;
					dat.sort ='popular';	
					
					return $.ajax({
							url: "http://apis.daum.net/search/book"
							, crossDomain: true
							, dataType: 'json'
							, type: CRYSTAL_GET
							, data: dat
							, success: function( data, textStatus, jqXHR )
							{
								var rdata = new Array(); 
								$(data.channel.item).each(function(index, element) {
										var str =element.title.replace(/&lt;\/b&gt;/gi, "").replace(/&lt;b&gt;/gi, "");
									if(rdata.isItem(str)==-1)
										rdata.push(str);
									});
									
									finished = true;
									return process(rdata);
								
							}
							, error: function( jqXHR, textStatus, errorThrown )
							{
							}
						});
					
					
					}
		});
	}
	
	function exlisteners(e){
		if(e.keyCode==13)
		{
			newItemSearch();
			return false
		}
		return true;
		//cls.lookup();
	}
	
	function autoCompleteMozila(){
		
		$('#modalSearchIput').keydown(function(e){
			//console.log("keyUP"+e.keyCode);
			
			if((e.keyCode < 41 && e.keyCode>36) || e.keyCode==13)
			{
				
			}else{
				
			if(e.keyCode==21){
				mainStr+=krDiv;
			}else if(e.keyCode==8)
			{
				/* drag나 커서위치에따른 지우기 지원 아직안함
				var selectT = document.getSelection();
				if(selectT==null || selectT==""){
				}else{
				}*/
				
					var d=0;
					var pre="";
					var post="";
					for(d=mainStr.length;d>=0;d--)
					{
						if(mainStr.charAt(d)!=krDiv)
						{
							if(d!=0);
							pre = mainStr.substr(0 ,d-1);
							
							if(d!=mainStr.length)
							post = mainStr.substr(d+1 ,mainStr.length-d);
							mainStr = pre + post;
							break;
						}
					}
				
			}else{
			    mainStr += String.fromCharCode(e.keyCode);   
			}
			typeAheadResult="";
			mainStr = mainStr.toLowerCase();
			var arra = mainStr.split(krDiv);
			var i=0;
				for(i=0;i<arra.length;i++)
				{
					if(i%2==0)
					typeAheadResult += arra[i];
					else
					 typeAheadResult += translate( arra[i] );
				}
			
			$('#modalSearchIput').val(typeAheadResult);
			
			e.preventDefault();
      		e.stopPropagation();
			}
		});
		
		$('#modalSearchIput').typeahead({
				minLength: 1
			  , exlistener : exlistenersFF
			  //, highlighter: function (item) {return item}
			  , source: function (query, process) {
					if(finished==false)
						return;
					finished=false;
					
					setAjaxFilter();
						
					var dat={};
					dat.apikey = '056bdbabcaa675a7383729e2da0f9e42405eadf2';
					dat.q = query;
					dat.result = '5';
					dat.output = 'json';
					dat.searchType = DAUM_SEARCH_ALL;
					dat.sort ='popular';	
					return $.ajax({
							url: "http://apis.daum.net/search/book"
							, crossDomain: true
							, dataType: 'json'
							, type: CRYSTAL_GET
							, data: dat
							,highlighter:function(){}
							, success: function( data, textStatus, jqXHR )
							{
								var rdata = new Array(); 
								$(data.channel.item).each(function(index, element) {
									var str =element.title.replace(/&lt;\/b&gt;/gi, "").replace(/&lt;b&gt;/gi, "");
									if(rdata.isItem(str)==-1)
										rdata.push(str);
									});
									
									finished = true;
									return process(rdata);
								
							}
							, error: function( jqXHR, textStatus, errorThrown )
							{
							}
						});
					
					
					}
		});
	}
	var acs_prevValue="";
	function exlistenersFF(e){
		if(e.keyCode==13)
		{
			newItemSearch();
			return false
		}else{
			$('#modalSearchIput').val(typeAheadResult);
		}
		return true;
		//cls.lookup();
	}
	
	var finished=true;
	
	
	function newItemSearch(e){
		typeAheadResult="";
		var str = $('#modalSearchIput').val();
		 if(  acs_prevValue != str && str!=""  && str!=null)
		 {
			acs_prevValue = str;
			getBookBYDaum( str,DAUM_SEARCH_ALL,searchBookUpdate);
		 }
	}

	var searchData;
	function searchBookUpdate(data){
		
		var container = $('#searchResultContainer');
		container.empty();
		searchData = data.channel.item;
		$(searchData).each(function(index, element) {
        	container.append(getItemRegPage(element));
        });
		
	}
	function hideRegBook(){
		clearNewItme();
		$('#regBookModal').modal('hide');
	}
	
	
	var selectedBookItem;
	function gotoBookRegis(isbn){
		mainStr="";
		$('#modalSearchIput').val("");
		typeAheadResult="";
		acs_prevValue="";
		var container = $('#searchResultContainer');
		container.empty();
		
		clearNewItme();
		
		var collecting=null;
		if(searchData!=null && isbn!=null)
		{
			$(searchData).each(function(index, element) {
				if(element.isbn ==isbn)
				{
					collecting=element;
					return false;
				}
				
				return true;
			});
			
			if(collecting!=null)
			{
				initNewItme(collecting);
			}
			
		}else{
			
		}
		selectedBookItem =collecting;
		
		hideSearchBook();
		$('#regBookModal').modal('show');
		
		
	}
	var popup;
	function showPopup(target){
		
		if(popup!=null && popup.closed==false)
					return;
					
					$('.imgsock').each(function(index, element) {
					  $(this).attr("id", "");
					});
				
						
						var img =$(target).attr("src");
						if(img!="http://www.crystars.com/upload/defaultSmall.jpg")
						{
							if(confirm("이미지를 삭제하시겠습니까?")){
							$(target).attr("src","http://www.crystars.com/upload/defaultSmall.jpg");

							deleteImge(img,function(data){});
							}
							//popup = newchromeLess("./popup/imgdelete.html?img="+img+"&delframe="+id,300,150,200,200, "regImg");
						}else{
							$(target).attr("id", "img1");
							popup= newchromeLess("./popup/imgupload.html",300,150,200,200, "regImg");
							
						}
	}
	
	
	function clearNewItme(){
		selectedBookItem=null;
		$('.imgsock').each(function(index, element) {
			  $(this).attr("src", "http://www.crystars.com/upload/defaultSmall.jpg");
			});
		$('#submitPrice').attr("value","");
		$('#submitTitle').attr("value","");
		$('#submitText').text("");
	}
	
	
	function initNewItme(receivedata){
		if(receivedata.cover_l_url!=null &&receivedata.cover_l_url!="")
		{
			$('.imgsock').each(function(index, element) {
			  $(this).attr("src", receivedata.cover_l_url);
			   return false;
			});
		}
		$('#submitPrice').attr("value",receivedata.sale_price);
		$('#submitTitle').attr("value",removeTag(htmlEntityDecode(receivedata.title)));
	}

	function submitNewItem(e){
		var receivedata=selectedBookItem;
		//getData(function(userdata){
				var data={};
				data.pid=userData.pid;
				data.owner=userData.id;
				data.image=$('.imgsock').first().attr("src");
				var imgbundl="";
				$('.imgsock').each(function(index, element) {
				  imgbundl =$(this).attr("src") +"&";
				   
				});
				
				data.imgDetail = imgbundl;
				data.price=$('#submitPrice').attr("value");
				data.booktitle = encodeURIComponent(removeTag(htmlEntityDecode(receivedata.title  ? receivedata.title : "")));
				data.title= encodeURIComponent($('#submitTitle').attr("value"));
				data.isbn=receivedata.isbn ? receivedata.isbn : "";
				data.quality= $(":input:radio[name=quality]:checked").val();
				data.publisher =encodeURIComponent(removeTag(htmlEntityDecode(receivedata.pub_nm? receivedata.pub_nm : "")));
				data.desc =encodeURIComponent($('#submitText').text());
				data.callback=function(){};
				regist_Item(data,function(data){hideRegBook();});
				//});
	}
	
	/*
		
	
	
	<div >
          	<ul class="nav nav-pills" id="pageNav">
            </ul>
          </div>
		 <div class="centerAlign" >
          	<ul class="nav nav-pills centerUL" id="pageNav">
            </ul>
          </div>
	
		<div class="centerAlign" >
          <ul class="nav nav-pills centerUL">
              <li><a href="#">&#60;</a></li>
			  
              <li class="active">
                <a href="#">1</a>
              </li>
              <li><a href="#">2</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">2</a></li>
              <li><a href="#">2</a></li>
			  
              <li><a href="#">&#62;</a></li>
              
			</ul>
          </div>
	
	FB.api('/'+userData.id+'?fields=albums.fields(can_upload,id,link)&access_token='+userData.token, 'get', function(response) {
		  if (!response || response.error) {
			alert('Error occured');
		  } else {
			
			
			
		  }
		});
	
	function postImg(){
		FB.api('/me/feed', 'post', { message: body }, function(response) {
		  if (!response || response.error) {
			alert('Error occured');
		  } else {
			alert('Post ID: ' + response.id);
		  }
		});
	
	}*/