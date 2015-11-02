<!doctype html>
<html>

<head>

	<title>Weather Forecast</title>
	<meta charset="utf-8" />
    
    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    
    
    <style>
        
        body{
            
            background: url("http://cs-server.usc.edu:45678/hw/hw8/images/bg.jpg") no-repeat center center fixed; 
            background-size:cover;
            -moz-background-size: cover;
        }
        
        .main{
            background: rgba(0,0,0,0.5);
            color: white;
            padding-top: 10px;
        }
        
        .result{
            padding-top: 10px;
        }
        
        h2#title{
            padding-top: 0;
            margin-top: 0;
            color: black;
        }
        
        .required{
            color: red;
            
        }
        
        .requiredField{
            color: red;
            margin:3px;
            padding: 0;
            display: none;
            font-size:85%;   
        }
        
        .nav-tabs li a{
            background-color:#EEEEEE;
            margin-right: 2px;
        }
        .tab-content{
            background-color:#303136;
            color:#fff;
            padding:2px
        }
        
        .nav-tabs > li.active > a,
            .nav-tabs > li.active > a:focus,
            .nav-tabs > li.active > a:hover{
            background-color: #3071A9 !important;
            color: white;
        }
    </style>
    
    
    <script type="text/javascript">
        var states=["Select your state...", "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "District Of Columbia", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"];

        var value=["Select your state...", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"];

        function initializeSelect(){
            var select = document.getElementById("state");
            for(var i = 0; i < states.length; i++) {
                 var opt = document.createElement("option");
                   opt.innerHTML = states[i];
                     opt.value = value[i];
                       select.appendChild(opt);
                                   }
            select.selectedIndex=0;
        }
        
        function validateData()
            {
        
                  var x = document.getElementById("address").value;
                  var y = document.getElementById("city").value;
                  var selectList=document.getElementById("state");
                  var re = /^\s*$/;
                  var flag=0;
                   if( x == null || x == "" || re.test(x)){
                          
                          document.getElementById("addressMissing").style.display="block";
                          flag=1;
                          
                                                          }
                
                   if( y == null || y == "" || re.test(y)){
                          document.getElementById("cityMissing").style.display="block"
                         flag=1;
                                                          }
                                                               
                 if( selectList.options[selectList.selectedIndex].value == "Select your state..." )
                       {
                         document.getElementById("stateMissing").style.display="block";
                         flag=1;
       
                        }
                
                if(flag==1)
                    return false;
                  else
                 return true;
             }
        
           function clearForm()
             {
                 document.getElementById("address").value="";
                 document.getElementById("addressMissing").style.display="none";
                 document.getElementById("city").value="";
                 document.getElementById("cityMissing").style.display="none";
                var select= document.getElementById("state");
                 select.selectedIndex=0;
                 document.getElementById("stateMissing").style.display="none";
                 document.getElementById("Fahrenheit").checked=true; 
                  document.getElementById("Celsius").checked=false; 
                
             }
       
        
    </script>
	
</head>

<body>
    
    <div class="container label">
        
        <h2 id="title"> Forecast Search</h2>
    
    </div>
            
    <div class="container main">
        <div class="row">
            
            <form role="form" method="post" action="index.php" onsubmit="return validateData();">
                
            <div class="col-sm-2">
                Street:<span class="required">*</span>
                <div class="form-group">
                    <input type="text" class="form-control input-sm" placeholder="Enter street address" id="address" name="StreetAddress">
                </div>
                <p class="requiredField" id="addressMissing">Please enter the street address.</p>
            </div>
            
            <div class="col-md-2">
                City:<span class="required">*</span>
                <div class="form-group">
                    <input type="text" class="form-control input-sm" placeholder="Enter the city" id="city" name="City">
                </div>
                <p class="requiredField" id="cityMissing">Please enter the city.</p>
            </div>
            
            <div class="col-md-2">
                State:<span class="required">*</span>
                <div class="form-group">
                    <select class="form-control input-sm" id="state" name="State">
                    </select>
                </div>
                <p class="requiredField" id="stateMissing">Please select a state.</p>
            </div>
            
            <script type="text/javascript">
                initializeSelect();
            </script>
            
            <div class="col-md-3">
                Degree:<span class="required">*</span>
                <div class="radio">
                   <label class="radio-inline input-sm"><input type="radio" name="unit" value="Fahrenheit" id="Fahrenheit" checked>Fahrenheit</label>
                   <label class="radio-inline input-sm"><input type="radio" name="unit" value="Celsius" id="Celsius" >Degree</label>
    
                </div>
            </div>
            
         
            <div class="col-md-3"><span class="pull-right">
                <div class="form-group">
                    <button type="submit" name="submit" class="btn btn-primary btn-sm"  style="float:left;" aria-label="Left Align" >
                        <span class="glyphicon glyphicon-search"></span>
                        Search
                    </button>
                      &nbsp; &nbsp;
                    <button type="button" class="btn btn-default btn-sm" aria-label="Left Align" style="float:left;" onclick="clearForm()">
                        <span class="glyphicon glyphicon-refresh"></span>
                        Clear
                    </button>
                </div>
            </div>
                
            </form>
                
        </div>
    </div>
    
    <div class="container result">  
    <ul class="nav nav-tabs">
        <li><a data-toggle="tab" href="#home">Right Now</a></li>
        <li><a data-toggle="tab" href="#menu1">Next 24 Hours</a></li>
        <li><a data-toggle="tab" href="#menu2">Next 7 Days</a></li>
    </ul>

    <div class="tab-content">
        <div id="home" class="tab-pane fade in active">
            <h3>HOME</h3>
            <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.             </p>
        </div>
        <div id="menu1" class="tab-pane fade">
            <h3>Menu 1</h3>
            <p>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>
        </div>
        <div id="menu2" class="tab-pane fade">
            <h3>Menu 2</h3>
            <p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam.</p>
        </div>
    </div>
    
    </div>  
        
    <?php if(isset($_POST["submit"])): ?>
    
    <?php

        $urlA = rawurlencode("https://maps.googleapis.com/maps/api/geocode/xml?");
        $urlB = urlencode("address=".$_POST["StreetAddress"]. "," .  $_POST["City"] . "," . $_POST["State"] . "&key=AIzaSyBTsfwGZIxBYDk-SpEta0CLquAVlUkNRPg");
        $google_url = $urlA . $urlB;                  
                          
        $xml = simplexml_load_file($google_url) or die("url not loading"); 
        //echo "googleurl ->" . $google_url . "<br>"; 
        //print_r($xml);



        if($xml->result){
            $lat = (string) $xml->result->geometry->location->lat;
            $long = (string) $xml->result->geometry->location->lng; 
        }
        else{
            echo '<script language="javascript">';
            echo 'alert("Please enter a valid address")';
            echo '</script>';
            return;
        }
            
        //echo "lat". $lat;
        //echo "long" . $long;

        if($_POST["unit"] == "Fahrenheit"){
            $uv = "us";
            $unitOfTemperature = "F";
            $unitOfWindSpeed = "mph";
            $unitOfVisibilty = "mi";
        }
        else{
            $uv = "si";
            $unitOfTemperature = "C";
            $unitOfWindSpeed="m/s";
            $unitOfVisibilty ="km";

        }

        $forecastAPIKEY = "f936f6c35c37265a692691ccaacc8219";
        $urla = "https://api.forecast.io/forecast/$forecastAPIKEY/$lat,$long?";
            
        $urlb = "units=$uv&exclude=flags";        
        $forecast_url = $urla . $urlb;
        //echo "<br><br>URL: " . $forecast_url;
        $json = file_get_contents($forecast_url);

        //echo "<br><br>JSON: ";
        //var_dump($json);
        $obj = json_decode($json);
        
        echo "<br><br>";
        echo "<table border=1.5px rules=none align=center id=\"resultTable\">";
        
        $timeZone=$obj->{"timezone"};
        date_default_timezone_set($timeZone);

        $summary = $obj->currently->{"summary"};
        echo "<tr><td colspan=2 class=\"center\"><h2 style=\"padding:0; margin:0;\">$summary</h2></td><tr>";

        $temperature = $obj->currently->{"temperature"};
        echo "<tr><td colspan=2 class=\"center\"><h2 style=\"padding:0; margin:0;\">".round($temperature)."&deg; $unitOfTemperature</h2></td><tr>";

        $icon = $obj->currently->{"icon"};
        switch($icon){
            case "clear-day": $image = "clear";break;
            case "clear-night": $image = "clear_night";break;
            case "rain": $image = "rain"; break;
            case "snow": $image = "snow"; break;
            case "sleet": $image = "sleet"; break;
            case "wind": $image = "wind"; break;
            case "fog": $image = "fog"; break;
            case "cloudy": $image = "cloudy"; break;
            case "partly-cloudy-day": $image = "cloud_day"; break;
            case "partly-cloudy-night": $image = "cloud_night"; break;
        }

        echo "<tr><td colspan=2 class=\"center\"><img src=\"http://cs-server.usc.edu:45678/hw/hw6/images/$image.png\" alt=\"$icon\" title=\"$icon\" width=\"80px\" height=\"80px\" /></td></tr>";
        
        $precipitation = $obj->currently->{"precipIntensity"};
        if($uv = "si"){
            $precipitation=$precipitation/25.4;
        }

        
        if(0 <= $precipitation && $precipitation < 0.002)
        {
            $temp="None";
        }
        else  if(0.002 <= $precipitation && $precipitation < 0.017)
        {
            $temp="Very Light";
        }
        
        else  if(0.017 <= $precipitation && $precipitation < 0.1){
            $temp="Light";
        }
        else  if(0.1 <= $precipitation && $precipitation < 0.4){
            $temp="Moderate";
        }
        else  if(0.4 <= $precipitation){
            $temp="Heavy";
        }

        echo "<tr><td>Precipitation: </td><td>$temp</td><tr>";
        
        $chanceOfRain = intval($obj->currently->{"precipProbability"})*100 . "%";
        echo "<tr><td>Chance Of Rain: </td><td>$chanceOfRain</td><tr>";

        $windSpeed = $obj->currently->{"windSpeed"};
        echo "<tr><td>WindSpeed: </td><td>".round($windSpeed). " $unitOfWindSpeed</td><tr>";

        $dewPoint = $obj->currently->{"dewPoint"};
        echo "<tr><td>Dew Point: </td><td>".round($dewPoint)."&deg; $unitOfTemperature</td><tr>";
        
        $humidity = ($obj->currently->{"humidity"})*100 . "%";
        echo "<tr><td>Humidity: </td><td>$humidity</td><tr>";

        $visibility = $obj->currently->{"visibility"};
        echo "<tr><td>Visibility: </td><td>".round($visibility). " $unitOfVisibilty</td><tr>";

        $sunriseTime=$obj->{"daily"}->{"data"}[0]->{"sunriseTime"};
        echo "<tr><td>Sunrise: </td><td>".date("h:i A",$sunriseTime)."<br></td></tr>";
        
        $sunsetTime=$obj->{"daily"}->{"data"}[0]->{"sunsetTime"};
        echo "<tr><td>Sunset: </td><td>".date("h:i A",$sunsetTime)."<br></td></tr>";



        echo "</table>";
        
    ?>
    
    <?php endif; ?>

    
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

</body>

</html>
<!--
<div class="dropdown">
                    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">Select your state...
                        <span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" id="state"></ul>
                </div>-->
