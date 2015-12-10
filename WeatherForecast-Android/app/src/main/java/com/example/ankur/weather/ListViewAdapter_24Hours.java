package com.example.ankur.weather;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ListViewAdapter_24Hours extends BaseAdapter {

    private Activity activity;
    private String jsonStr;
    private int count;
    private static LayoutInflater inflater=null;
    private int imageResourceID;


    public ListViewAdapter_24Hours(Activity a, String json, int count) {
        activity = a;
        jsonStr=json;
        this.count = count;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return count;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.listitem_24hours, null);

        if(getItemId(position)%2==0)
            vi.setBackgroundColor(Color.parseColor("#CBCBCB"));

        TextView timeTV = (TextView)vi.findViewById(R.id.time);
        TextView tempTV = (TextView)vi.findViewById(R.id.temp);
        ImageView iconIV = (ImageView)vi.findViewById(R.id.icon);

        try {
        JSONObject jsonObj = new JSONObject(jsonStr);;
        String time = jsonObj.getJSONObject("hourly").getJSONArray("data").getJSONObject(position).getString("time");
        String temp = jsonObj.getJSONObject("hourly").getJSONArray("data").getJSONObject(position).getString("temperature");
        String icon = jsonObj.getJSONObject("hourly").getJSONArray("data").getJSONObject(position).getString("icon");


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


        timeTV.setText(time);
        tempTV.setText(temp);
        iconIV.setImageResource(imageResourceID);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return vi;
    }
}
