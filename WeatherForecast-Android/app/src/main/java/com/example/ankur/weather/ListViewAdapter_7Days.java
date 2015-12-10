package com.example.ankur.weather;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ListViewAdapter_7Days extends BaseAdapter {

    private Activity activity;
    private String jsonStr;
    private int count;
    private static LayoutInflater inflater=null;
    private int imageResourceID;
    private String unit;

    public ListViewAdapter_7Days(Activity a, String json, int count, String unit) {
        activity = a;
        jsonStr=json;
        this.count = count;
        this.unit = unit;
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
            vi = inflater.inflate(R.layout.listitem_7days, null);

        switch(position){
            case 0: vi.setBackgroundColor(Color.parseColor("#FFDB6A")); break;
            case 1: vi.setBackgroundColor(Color.parseColor("#A0E7FF")); break;
            case 2: vi.setBackgroundColor(Color.parseColor("#FFC4EA")); break;
            case 3: vi.setBackgroundColor(Color.parseColor("#C4FFA5")); break;
            case 4: vi.setBackgroundColor(Color.parseColor("#FFBDB7")); break;
            case 5: vi.setBackgroundColor(Color.parseColor("#EFFFB5")); break;
            case 6: vi.setBackgroundColor(Color.parseColor("#BCBEFF")); break;


        }
        TextView dateTV = (TextView)vi.findViewById(R.id.date);
        TextView minMaxTV = (TextView)vi.findViewById(R.id.minMax);
        ImageView iconIV = (ImageView)vi.findViewById(R.id.icon);

        try {
        JSONObject jsonObj = new JSONObject(jsonStr);;
        String date = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(position+1).getString("day")
                +", " +
                jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(position+1).getString("today");

        String min = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(position+1).getString("temperatureMin");
        String max = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(position+1).getString("temperatureMax");

        String icon = jsonObj.getJSONObject("daily").getJSONArray("data").getJSONObject(position+1).getString("icon");


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
        minMaxTV.setText(Html.fromHtml("Min: " + min + (char) 0x00B0 + unit +
                " | Max: " + max + (char) 0x00B0 + unit ));
        dateTV.setText(date);
        iconIV.setImageResource(imageResourceID);

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return vi;
    }
}
