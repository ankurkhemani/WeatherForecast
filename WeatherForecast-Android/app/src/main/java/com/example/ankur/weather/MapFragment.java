package com.example.ankur.weather;

/**
 * Created by Ankur on 08/12/15.
 */

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.hamweather.aeris.communication.AerisCallback;
import com.hamweather.aeris.communication.AerisEngine;
import com.hamweather.aeris.communication.EndpointType;
import com.hamweather.aeris.communication.parameter.PlaceParameter;
import com.hamweather.aeris.maps.MapViewFragment;


// import the AerisMapView & components
        import com.hamweather.aeris.maps.AerisMapView;
        import com.hamweather.aeris.maps.AerisMapView.AerisMapType;
import com.hamweather.aeris.maps.interfaces.OnAerisMapLongClickListener;
import com.hamweather.aeris.model.AerisResponse;
import com.hamweather.aeris.tiles.AerisTile;

public class MapFragment extends MapViewFragment implements OnAerisMapLongClickListener, AerisCallback
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aeris, container, false);

        // setting up secret key and client id for oauth to aeris
        AerisEngine.initWithKeys(this.getString(R.string.aeris_client_id), this.getString(R.string.aeris_client_secret), "com.example.ankur.weather");

        mapView = (AerisMapView)view.findViewById(R.id.aerisfragment_map);
        mapView.init(savedInstanceState, AerisMapType.GOOGLE);

        String lat = getArguments().getString("lat");
        String lon = getArguments().getString("lon");
        Toast.makeText(getActivity(), lat + "  " + lon, Toast.LENGTH_LONG).show();
//        Location location = new Location("");
//        location.setLatitude(Double.valueOf(lat));
//        location.setLongitude(Double.valueOf(lon));
        mapView.moveToLocation(new LatLng(Double.valueOf(lat), Double.valueOf(lon)), 12);
        // show the radar tile overlay
        mapView.addLayer(AerisTile.RADSAT);
        mapView.setOnAerisMapLongClickListener(this);


        return view;
    }


    @Override
    public void onResult(EndpointType endpointType, AerisResponse aerisResponse) {

    }

    @Override
    public void onMapLongClick(double v, double v1) {

    }
}
