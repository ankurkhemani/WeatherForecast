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

public class Next24HoursFragment extends Fragment
{

    ListViewAdapter_24Hours adapter;
    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_24hours, container, false);



        final String jsonStr = getArguments().getString("jsonStr");
        String unit = getArguments().getString("unit");

        list=(ListView)view.findViewById(R.id.list);

        final RelativeLayout footerLayout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.footerview, null);
        ImageButton btnLoadMore = (ImageButton) footerLayout.findViewById(R.id.more);
        list.addFooterView(footerLayout);


        RelativeLayout headerLayout = (RelativeLayout) getActivity().getLayoutInflater().inflate(R.layout.headerview, null);
        TextView temp = (TextView) headerLayout.findViewById(R.id.temp);
        temp.setText(Html.fromHtml("Temp(" + (char) 0x00B0  + unit + ")"));
        list.addHeaderView(headerLayout);

        // Getting adapter
        adapter = new ListViewAdapter_24Hours(getActivity(), jsonStr, 24);
        list.setAdapter(adapter);

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // get listview current position - used to maintain scroll position
                int currentPosition = list.getFirstVisiblePosition();

                // Appending new data to menuItems ArrayList
                adapter = new ListViewAdapter_24Hours(
                        getActivity(),
                        jsonStr, 48);

                list.setAdapter(adapter);

                // Setting new scroll position
                list.setSelectionFromTop(currentPosition + 1, 0);
                list.removeFooterView(footerLayout);
            }
        });

        return view;
    }
}
