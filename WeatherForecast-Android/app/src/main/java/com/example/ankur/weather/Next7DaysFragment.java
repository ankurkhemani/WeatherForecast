package com.example.ankur.weather;

/**
 * Created by Ankur on 08/12/15.
 */

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.maps.AerisMapView;
import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.MapViewFragment;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

import org.w3c.dom.NodeList;

import java.util.HashMap;

// import the AerisMapView & components

public class Next7DaysFragment extends Fragment
{

    ListViewAdapter_7Days adapter;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_7days, container, false);



        final String jsonStr = getArguments().getString("jsonStr");
        String unit = getArguments().getString("unit");

        list=(ListView)view.findViewById(R.id.list);

        // Getting adapter
        adapter = new ListViewAdapter_7Days(getActivity(), jsonStr, 7, unit);
        list.setAdapter(adapter);

        return view;
    }
}
