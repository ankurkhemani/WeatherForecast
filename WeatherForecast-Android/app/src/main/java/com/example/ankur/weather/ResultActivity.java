package com.example.ankur.weather;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.weather.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.xml.transform.Result;

public class ResultActivity extends Activity {
    private String jsonStr, unit, city, state; //Extras

    //TextViews
    private TextView tempTV, summaryTV, lowHighTV, precipitationTV, cofrainTV, windSpeedTV, dewPointTV, humidityTV, visibilityTV,
    sunriseTV, sunsetTV;
    private ImageView iconIV;

    // parsed from JSON
    private String icon, summary, temp, dewpoint, humidity, windspeed, visibility,
            precipProbability, precipitation, sunsetTime, sunriseTime, tempMax, tempMin, chancesOfRain;
    private double precipIntensity;
    private int imageResourceID;
    private Button moreDetails, viewMap, fbShare;
    private String lat, lon;

    CallbackManager callbackManager;
    ShareDialog shareDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        jsonStr = getIntent().getStringExtra("jsonStr");
        unit = getIntent().getStringExtra("unit");
        city = getIntent().getStringExtra("city");
        state = getIntent().getStringExtra("state");

        // initialize all widgets and other variables
        initializeObjects();

        Log.d("Response: ", "> " + jsonStr);

        // Parse the JSon String
        if (jsonStr != null && !jsonStr.isEmpty()) {
            parseJSON(jsonStr, unit);
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            Toast.makeText(ResultActivity.this, "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
        }


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, shareCallBack);

        fbShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + city + ", " + state)
                            .setContentDescription(summary + ", " + Html.fromHtml(Math.round(Double.valueOf(temp)) + "<sup><small>" + (char) 0x00B0 + unit + "</small></sup>"))
                            .setContentUrl(Uri.parse("http://forecast.io"))
                            .setImageUrl(Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/rain.png"))
                            .build();

                    shareDialog.show(linkContent);
                }
            }
        });

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ResultActivity.this, MapActivity.class);
                myIntent.putExtra("lat", lat);
                myIntent.putExtra("lon", lon);
                ResultActivity.this.startActivity(myIntent);
            }
        });

        moreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ResultActivity.this, DetailsActivity.class);
                myIntent.putExtra("jsonStr", jsonStr);
                myIntent.putExtra("unit", unit);
                myIntent.putExtra("city", city);
                myIntent.putExtra("state", state);
                ResultActivity.this.startActivity(myIntent);
            }
        });
    }

    public FacebookCallback<Sharer.Result> shareCallBack = new FacebookCallback<Sharer.Result>() {

        @Override
        public void onSuccess(Sharer.Result result) {
            Toast.makeText(ResultActivity.this, "Facebook Post Successful", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCancel() {
            Toast.makeText(ResultActivity.this, "Post Cancelled", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onError(FacebookException error) {
            Toast.makeText(ResultActivity.this, "Failed To Post", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void initializeObjects(){

        //TextViews
        tempTV = (TextView) findViewById(R.id.temp);
        summaryTV = (TextView) findViewById(R.id.summary);
        lowHighTV = (TextView) findViewById(R.id.lowHigh);
        precipitationTV = (TextView) findViewById(R.id.precipitation);
        cofrainTV = (TextView) findViewById(R.id.cofrain);
        windSpeedTV = (TextView) findViewById(R.id.windSpeed);
        dewPointTV = (TextView) findViewById(R.id.dewPoint);
        humidityTV = (TextView) findViewById(R.id.humidity);
        visibilityTV = (TextView) findViewById(R.id.visibility);
        sunriseTV = (TextView) findViewById(R.id.sunrise);
        sunsetTV = (TextView) findViewById(R.id.sunset);

        //Imageview
        iconIV = (ImageView) findViewById(R.id.icon);

        //Buttons
        fbShare = (Button) findViewById(R.id.fb);
        viewMap = (Button) findViewById(R.id.viewMap);
        moreDetails = (Button) findViewById(R.id.moreDetails);

    }

    public void parseJSON(String jsonStr, String unit){


            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                icon= jsonObj.getJSONObject("currently").getString("icon");

                summary= jsonObj.getJSONObject("currently").getString("summary");
                temp = jsonObj.getJSONObject("currently").getString("temperature");
                dewpoint= jsonObj.getJSONObject("currently").getString("dewPoint");
                humidity = jsonObj.getJSONObject("currently").getString("humidity");
                windspeed= jsonObj.getJSONObject("currently").getString("windSpeed");
                visibility = jsonObj.getJSONObject("currently").getString("visibility");
                precipProbability = jsonObj.getJSONObject("currently").getString("precipProbability");
                precipIntensity = Double.parseDouble(jsonObj.getJSONObject("currently").getString("precipIntensity"));
                sunsetTime = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("sunsetTime");
                sunriseTime = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("sunriseTime");
                tempMax = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMax");
                tempMin = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMin");

                lat= jsonObj.getString("latitude");
                lon= jsonObj.getString("longitude");


                chancesOfRain=(precipIntensity*100)+"%";

                if( precipIntensity >=0 && precipIntensity < 0.002)
                {
                    precipitation="None";
                }
                else  if(0.002 <= precipIntensity && precipIntensity < 0.017)
                {
                    precipitation="Very Light";
                }

                else  if(0.017 <= precipIntensity && precipIntensity < 0.1)
                {
                    precipitation="Light";
                }
                else  if(0.1 <= precipIntensity && precipIntensity < 0.4)
                {
                    precipitation="Moderate";
                }
                else  if(0.4 <= precipIntensity)
                {
                    precipitation="Heavy";
                }

                if(unit.equals("C"))
                {
                        windspeed+=" m/s";
                        visibility+=" Km";
                }
                else
                {
                        windspeed+=" mph";
                        visibility+=" mi";
                }


                switch(icon){
                    case "clear-day": imageResourceID = R.drawable.clear; break;
                    case "clear-night": imageResourceID = R.drawable.clear_night; break;
                    case "rain": imageResourceID = R.drawable.rain; break;
                    case "snow": imageResourceID = R.drawable.snow; break;
                    case "sleet": imageResourceID = R.drawable.sleet; break;
                    case "wind": imageResourceID = R.drawable.wind; break;
                    case "fog": imageResourceID = R.drawable.fog; break;
                    case "cloudy": imageResourceID = R.drawable.cloudy; break;
                    case "partly-cloudy-day": imageResourceID = R.drawable.cloud_day; break;
                    case "partly-cloudy-night": imageResourceID = R.drawable.cloud_night; break;
                }


                // display result
                displayResult();

            }
            catch (JSONException e) {
                e.printStackTrace();
            }


    }

    public void displayResult(){

        summaryTV.setText(summary + " in " + city + ", " + state);
        tempTV.setText(Html.fromHtml(Math.round(Double.valueOf(temp)) + "<sup><small>" + (char) 0x00B0 + unit + "</small></sup>"));
        iconIV.setImageResource(imageResourceID);
        lowHighTV.setText(Html.fromHtml("L:" + tempMin + "<sup><small>" + (char) 0x00B0 + "</small></sup>" + " | " +
                "H:" + tempMax + "<sup><small>" + (char) 0x00B0 + "</small></sup>"));
        precipitationTV.setText(precipitation);
        cofrainTV.setText(chancesOfRain);
        windSpeedTV.setText(windspeed);
        dewPointTV.setText(Html.fromHtml(dewpoint + " <sup><small>" + (char) 0x00B0 + "</small></sup>" + unit));
        humidityTV.setText(humidity + "%");
        visibilityTV.setText(visibility);
        sunriseTV.setText(sunriseTime);
        sunsetTV.setText(sunsetTime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
