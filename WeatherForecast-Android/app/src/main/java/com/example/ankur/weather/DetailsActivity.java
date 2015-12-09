package com.example.ankur.weather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;


public class DetailsActivity extends FragmentActivity {

    // Fragment TabHost as mTabHost
    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle bundle = new Bundle();
        bundle.putString("jsonStr", getIntent().getStringExtra("jsonStr"));
        bundle.putString("unit", getIntent().getStringExtra("unit"));
        bundle.putString("city", getIntent().getStringExtra("city"));
        bundle.putString("state", getIntent().getStringExtra("state"));




        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        mTabHost.addTab(
                mTabHost.newTabSpec("Next 24 Hours").setIndicator("Next 24 Hours", null),
                Next24HoursFragment.class, bundle);
        mTabHost.addTab(
                mTabHost.newTabSpec("Next 7 Days").setIndicator("Next 7 Days", null),
                Next7DaysFragment.class, bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);

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
