package com.example.ankur.weather;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {

    EditText streetET, cityET;
    Button search, clear, about;
    RadioGroup degreeRG;
    TextView errorMessage;
    Spinner spinner;
    ImageView logo;
    String unitShort, city, state;

    private ProgressDialog pDialog;

    // URL to get contacts JSON
    private static String url = "http://weatherforecast.elasticbeanstalk.com";

    String[] states={"Select", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // state spinner code
        spinner = (Spinner) findViewById(R.id.stateSpinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, states);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //logo click
        logo = (ImageView)findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://forecast.io"));
                startActivity(intent);
            }
        });

        //radiogroup and fahrenheit radiobutton
        degreeRG = (RadioGroup) findViewById(R.id.degreeRG);

        //Edittexts
        streetET = (EditText) findViewById(R.id.streetEditText);
        cityET = (EditText) findViewById(R.id.cityEditText);

        //Buttons
        search = (Button) findViewById(R.id.search);
        clear = (Button) findViewById(R.id.clear);
        about = (Button) findViewById(R.id.about);

        //ErrorMessage Textview
        errorMessage = (TextView) findViewById(R.id.error);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String street = streetET.getText().toString();
                city = cityET.getText().toString();
                state = spinner.getSelectedItem().toString();

                if(validate(street, city, state)) {



                    // get selected radio button from radioGroup
                    int selectedId = degreeRG.getCheckedRadioButtonId();
                    // find the radiobutton by returned id
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    String degree = radioButton.getText().toString();
                    String unit = "";

                    if(degree.equals("Fahrenheit")){
                        unit = "us";
                        unitShort = "F";
                    }
                    else {
                        unit = "si";
                        unitShort = "C";
                    }

                    Toast.makeText(MainActivity.this, street + " - " + city + " - " + state + " - " + unit
                            , Toast.LENGTH_SHORT).show();

                    List<NameValuePair> params=new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("address1", street));
                    params.add(new BasicNameValuePair("city1", city));
                    params.add(new BasicNameValuePair("state1", state));
                    params.add(new BasicNameValuePair("unit1", unit));

                    // Calling async task to get json
                    new GetWeatherDataFromAWS(params).execute();
                }

            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                streetET.setText("");
                cityET.setText("");
                spinner.setSelection(0);
                degreeRG.check(R.id.fahrenheit);

            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(MainActivity.this, UserProfile.class);
                MainActivity.this.startActivity(myIntent);
            }
        });



    }

    public boolean validate(String street, String city, String state){

        boolean flag = true;

        if(street.matches("")){
            errorMessage.setText("Please enter a Street.");
            flag = false;
        }
        else if (city.matches("")) {
            errorMessage.setText("Please enter a City.");
            flag = false;
        }
        else if (state == "Select") {
            errorMessage.setText("Please select a State.");
            flag = false;
        }

        if(flag==true)
            errorMessage.setText("");

        return flag;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    /**
     * Async task class to get json by making HTTP call
     * */
    private class GetWeatherDataFromAWS extends AsyncTask<Void, Void, String> {
        List<NameValuePair> params;
        public GetWeatherDataFromAWS(List<NameValuePair> params) {
            // do stuff
            this.params = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Fetching weather ...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected String doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET, params);

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            if (result != null && !result.isEmpty()) {

                Intent myIntent = new Intent(MainActivity.this, ResultActivity.class);
                myIntent.putExtra("jsonStr", result);
                myIntent.putExtra("unit", unitShort);
                myIntent.putExtra("city", city);
                myIntent.putExtra("state", state);
                MainActivity.this.startActivity(myIntent);

            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
                Toast.makeText(MainActivity.this, "Couldn't get any data from the url", Toast.LENGTH_SHORT).show();
            }


        }

    }
}
