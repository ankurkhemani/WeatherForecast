package com.example.ankur.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ankur.weather.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class UserProfile extends Activity {
    private TextView info;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        FacebookSdk.sdkInitialize(getApplicationContext());
//
//        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.user_profile);
//        info = (TextView)findViewById(R.id.info);
//        loginButton = (LoginButton)findViewById(R.id.login_button);
//
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                info.setText(
//                        "User ID: "
//                                + loginResult.getAccessToken().getUserId()
//                                + "\n" +
//                                "Auth Token: "
//                                + loginResult.getAccessToken().getToken()
//                );
//            }
//
//            @Override
//            public void onCancel() {
//                info.setText("Login attempt canceled.");
//            }
//
//            @Override
//            public void onError(FacebookException e) {
//                info.setText("Login attempt failed.");
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
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
