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
	
    FB.init({
      appId      : CRYSTAL_APP_ID, // App ID
      channelUrl : '//www.crystars.com/channel.html', // Channel File
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true,  // parse XFBML
	  fileUpload : true
    });

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
    console.log('Welcome!  Fetching your information.... ');
    fb_checkSession(id,token,checkSessionEnd);
		
		
	}
	
	function checkSessionEnd(raw){
		if(raw.id!=null && raw.id!=CRYSTAL_EMPTY)
		{
			console.log('recieve user data 22');
			successInitUser(raw);
		}else{
			console.log('creat User data');
			successGetUserData(raw.token);
		}
	}
	
	
	function successGetUserData(token){
		
		FB.api('/me', function(response) {
        	console.log('Good to see you, ' + response.name + '.'+response.id + '.');
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
		console.log('Good to see you,');
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
			$('#logf>a>img').show();
			$('#logf>a>img').attr('src',userData.portrait);
			$('#logf>a>span').text(userData.name);
			$('#logf>a').attr("data-toggle","dropdown");
			//var el = document.getElementById("portrait");
			//el.scr = userData.portait;
			
			
		}else if(sts == CRYSTAR_STATUS_DAUTH){
			$('#logf>a>img').hide();
			$('#logf>a>img').attr('src',"");
			$('#logf>a>span').text('login');
			$('#logf>a').attr("data-toggle","");
			$('.fb-login-button').hide();
			//$('#topSearch #fb-root').show();
			//$('#topSearch #fb-root').attr("display","inline");
			//$('#topSearch #search').hide();
			//$('#topSearch #search').attr("display","none");
		}else{
			$('#logf>a>img').hide();
			$('#logf>a>img').attr('src',"");
			$('#logf>a>span').text('login');
			$('#logf>a').attr("data-toggle","");
			$('.fb-login-button').hide();
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
	
	var ROW_COUNT=2;
	var PAGE_ITME_COUNT = 12;
	function createItemList(data){
		
		cst_clearNodeById( 'mainItemList' );
		
		var ul ;
		ul= getDynamicDOM("ul",'thumbnails');
		
		var i =0;
		$(data).each(function(index, element) {
			if(i>ROW_COUNT)
			{
				$('.row-fluid').append(ul);
				ul= getDynamicDOM("ul",'thumbnails');
				//ul.setAttribute("class",'thumbnails');
				i=0;
			}
			ul.appendChild( createListItem(element) );
			
			i++;
        });
			
		$('.row-fluid').append(ul);
		
		
	}
	
	function createListItem(itemDesc){

		var li = getDynamicDOM("li",'span4');
		//li.setAttribute("class",'span4');
		
		var div= getDynamicDOM("div",'thumbnail');
		//div.setAttribute("class",'thumbnail');
		
		var img= getDynamicDOMImg(itemDesc.image,"");
		//img.setAttribute('src',itemDesc.image);
		
		var div2= getDynamicDOM("div",'caption');
		//div2.setAttribute("class",'caption');
		
		var h3= getDynamicDOM("h3");
		var h3text = document.createTextNode(itemDesc.title);
		h3.appendChild(h3text);
		var p1= getDynamicDOM("p");
		var p1text = document.createTextNode(itemDesc.desc);
		h3.appendChild(p1text);
		var p2=getDynamicDOM("p");
		var a1= getDynamicDOM("a","btn btn-primary");
		//a1.setAttribute("class","btn btn-primary");
		var a1text = document.createTextNode("찜하기");
		a1.appendChild(a1text);
		var a2= getDynamicDOM("a","btn");
		//a2.setAttribute("class","btn");
		var a2text = document.createTextNode("공유");
		a2.appendChild(a2text);
		
		p2.appendChild(a1);
		p2.appendChild(a2);
		div2.appendChild(h3);
		div2.appendChild(p1);
		div2.appendChild(p2);
		div.appendChild(img);
		div.appendChild(div2);
		li.appendChild(div);
		
		return li;
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
			getdb_Item(CRYSTAR_ITEM_DATE ,ppage,null,recieve_Item);
		}
	}
	
	
	function recieve_Item(data){
		
		currentPage = data.page;
		if(data.total!=-1)
		{
		pageTotal = Math.ceil(data.total/PAGE_ITME_COUNT);
		
		currentGrandPage = 1;
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
		var totalbatch = pageTotal>=PAGE_NAV_COUNT ?  PAGE_NAV_COUNT : pageTotal;
	
			a = getDynamicDOMLink("a",null,null,"#");
			var atext = document.createTextNode('<');
			a.appendChild(atext);
			li= getDynamicDOM("li");
			li.appendChild(a);
			ul.append( li );
		
		for(i=0;i<totalbatch;i++){
			a = getDynamicDOMLink("a",null,null,"#");
			var atext = document.createTextNode(startPoint+1);
			a.appendChild(atext);
			if((i+1)==boldPoint)
			li= getDynamicDOM("li","active");
			else
			li= getDynamicDOM("li");
			
			li.appendChild(a);
			ul.append( li );
			startPoint++;
        }
			a = getDynamicDOMLink("a",null,null,"#");
			var atext = document.createTextNode('>');
			a.appendChild(atext);
			li= getDynamicDOM("li");
			li.appendChild(a);
			ul.append( li );
			
		
		
	}
	
	var presetDom;
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
			//var data2 = e.target.innerHTML;
			if(data==">&gt;")
			{
				if(currentGrandPage>=grandTotalPage)
					return;
					
				currentGrandPage = currentGrandPage+1;	
				get_Item( ((currentGrandPage+1) * PAGE_NAV_COUNT)+1 );
			}
			else if(data=="<&lt;")
			{
				if(currentGrandPage==1)
					return;
					
				currentGrandPage = currentGrandPage-1;
				get_Item( ((currentGrandPage) * PAGE_NAV_COUNT)+1 );
			}else{
				get_Item(data);
			}
			cst_clearNodeById( 'pageNav' );
        });
		
		$('#mainItemList').mouseover(function(e) {
        	    console.log('over');
        });
		
		$('#mainItemList').mouseout(function(e) {
            	console.log('out');
        });
	
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