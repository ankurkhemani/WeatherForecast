package com.example.ankur.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.weather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ResultActivity extends Activity {
    String jsonStr, unit, city, state; //Extras

    //TextViews
    TextView tempTV, summaryTV;

    // parsed from JSON
    String summary, temp, dewpoint, humidity, windspeed, visibility, precipProbability, precipitation, sunsetTime, sunriseTime;
    int precipIntensity;
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
        tempTV = (TextView) findViewById(R.id.temp);
        summaryTV = (TextView) findViewById(R.id.summary);

        Log.d("Response: ", "> " + jsonStr);

        // Parse the JSon String
        if (jsonStr != null && !jsonStr.isEmpty()) {
            parseJSON(jsonStr, unit);
        } else {
            Log.e("ServiceHandler", "Couldn't get any data from the url");
            Toast.makeText(ResultActivity.this, "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
        }



    }

    public void initializeObjects(){

    }

    public void parseJSON(String jsonStr, String unit){


            try {
                JSONObject jsonObj = new JSONObject(jsonStr);
                // Getting JSON Array node
                summary= jsonObj.getJSONObject("currently").getString("summary");
                // display result
                displayResult();
                temp = jsonObj.getJSONObject("currently").getString("temperature");
                dewpoint= jsonObj.getJSONObject("currently").getString("dewpoint");
                humidity = jsonObj.getJSONObject("currently").getString("humidity");
                windspeed= jsonObj.getJSONObject("currently").getString("windspeed");
                visibility = jsonObj.getJSONObject("currently").getString("visibility");
                precipProbability = jsonObj.getJSONObject("currently").getString("precipProbability");
                precipIntensity = Integer.parseInt(jsonObj.getJSONObject("currently").getString("precipIntensity"));
                sunsetTime = jsonObj.getJSONObject("currently").getString("sunsetTime");
                sunriseTime = jsonObj.getJSONObject("currently").getString("sunriseTime");




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



            }
            catch (JSONException e) {
                e.printStackTrace();
            }


    }

    public void displayResult(){

        summaryTV.setText(summary + " in " + city + ", " + state);
        tempTV.setText(Html.fromHtml(temp+ "<sup><small>" + (char) 0x00B0 + unit + "<small></sup>"));

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
