<?php
   require 'facebook-facebook-php-sdk-1270f0d/src/facebook.php';

   $app_id = '101009976715320';
   $app_secret = '6caf2bfb1f8245543f9be215eea384a3';
   $app_namespace = 'dumdum_smog';
   $app_url = 'http://apps.facebook.com/' . $app_namespace . '/';
   $scope = 'email,publish_actions';

   // Init the Facebook SDK
   $facebook = new Facebook(array(
     'appId'  => $app_id,
     'secret' => $app_secret,
   ));

   // Get the current user
   $user = $facebook->getUser();

   // If the user has not installed the app, redirect them to the Auth Dialog
   
    $signed_request = $_REQUEST["signed_request"];

     list($encoded_sig, $payload) = explode('.', $signed_request, 2); 

     $data = json_decode(base64_decode(strtr($payload, '-_', '+/')), true);

	 
   if (!$user) {
     $loginUrl = $facebook->getLoginUrl(array(
       'scope' => $scope,
       'redirect_uri' => $app_url,
     ));
	 print('<script> top.location.href=\'' . $loginUrl . '\'</script>');
	}else{
	
	 print('<script> top.location.href=\'http://apps.facebook.com/'.$app_namespace.'/NupnS2w.html?user='.$user.'&key='.$data["oauth_token"].'\'</script>');
	}
	//	 print('<script> top.location.href=\'http://www.nupn.kr/faceBookTest.html&result='.$user . '\'</script>');
     //print('<script> top.location.href=\'' . $loginUrl . '\'</script>');
	/* $data = $facebook->api('/me');
	print("\n1:");
	print($data);
	print("\n11:");
	print($data['name']);
	
	print($user);
	print($loginUrl);
	
	*/
	
   
 ?>