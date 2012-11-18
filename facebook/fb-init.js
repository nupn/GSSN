document.write('<scr'+'ipt type="text/javascript" src="../lib/jquery.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="../lib/crystal.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./fbtool.js"><\/scr'+'ipt>');
document.write('<scr'+'ipt type="text/javascript" src="./facebook-js-sdk/src/meta/all.js"><\/scr'+'ipt>');
	
  // Additional JS functions here
  window.fbAsyncInit = function() {
	initial=true;
	
    FB.init({
      appId      : CRYSTAL_APP_ID, // App ID
      channelUrl : '//www.crystars.com/channel.html', // Channel File
      status     : true, // check login status
      cookie     : true, // enable cookies to allow the server to access the session
      xfbml      : true  // parse XFBML
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
	 
	 
	
  };

  // Load the SDK Asynchronously
  (function(d){
     var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement('script'); js.id = id; js.async = true;
     js.src = "//connect.facebook.net/en_US/all.js";
     ref.parentNode.insertBefore(js, ref);
   }(document));
   
   function login() {
    FB.login(function(response) {
        if (response.authResponse) {
            // connected
			authSuccess(response.authResponse.userID,response.authResponse.accessToken);
        } else {
            // cancelled
        }
    }, {scope: 'email,publish_actions'});
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
			$('#logf>a>img').attr('src',userData.portrait);
			$('#logf>a>span').text(userData.name);
			//var el = document.getElementById("portrait");
			//el.scr = userData.portait;
			
		}else if(sts == CRYSTAR_STATUS_DAUTH){
			$('#logf>a>img').attr('src',"");
			$('#logf>a>span').text('login');
			$('#logf>ul').hide();
		}else{
			$('#logf>a>img').attr('src',"");
			$('#logf>a>span').text('login');
			$('#logf>ul').hide();
		}
	}
	
	function logIconDown(){
	}
	
