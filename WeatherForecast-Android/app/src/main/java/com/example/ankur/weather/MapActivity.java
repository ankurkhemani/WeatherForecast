package com.example.ankur.weather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

/**
 * Created by Ankur on 08/12/15.
 */
public class MapActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        Bundle bundle = new Bundle();
        bundle.putString("lat", getIntent().getStringExtra("lat"));
        bundle.putString("lon", getIntent().getStringExtra("lon"));


        MapFragment myf = new MapFragment();
        myf.setArguments(bundle);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment, myf);
        transaction.commit();
    }
}
