<?php
    header('Access-Control-Allow-Origin: *');
	
		 if (is_ajax()) {
                 test_function();
                     }
                                                                          
 
//Function to check if the request is an AJAX request
function is_ajax() {
  return isset($_SERVER['HTTP_X_REQUESTED_WITH']) && strtolower($_SERVER['HTTP_X_REQUESTED_WITH']) == 'xmlhttprequest';
}


function test_function(){
    
    if($_GET["unit1"] == "si")
     $unit="si";
     else
     $unit="us";
     
        $urlA = rawurlencode("https://maps.googleapis.com/maps/api/geocode/xml?");
        $urlB = urlencode("address=".$_GET["address1"]. "," .  $_GET["city1"] . "," . $_GET["state1"] . "&key=AIzaSyAjBE8Khra6i01lddq7_PmsTPqa6qWla-0");
        $url = $urlA . $urlB;                           
        $xml = simplexml_load_file($url) or die("url not loading"); 
          if($xml->result)
          {
         $lat = (string) $xml->result->geometry->location->lat;
         $long = (string) $xml->result->geometry->location->lng;
         }
         else{
         $lat=NULL;
         $long=NULL;
         }

         if($lat && $long)
         {
         $urlFa="https://api.forecast.io/forecast/01f5252f70291f9066491f595975ab0b/$lat,$long?";
         $urlFb="units=$unit&exclude=flags";
         $foreCastApiUrl=$urlFa.$urlFb;
         $json = file_get_contents($foreCastApiUrl);
         echo $json;    
        }
    
}
?>
