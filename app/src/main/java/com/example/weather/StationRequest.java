package com.example.weather;

import android.content.Context;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


public class StationRequest {

    private static String stationid;
    private static String stationname;

    public String[] StationRequest(Context context, String strlat, String strlong) {

        final DecimalFormat df = new DecimalFormat("####0.00");
        //Weather request
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        String url = "https://api.weather.gov/points/"+strlat+","+strlong+"/stations";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //formats response JSON
                            JSONArray features = response.getJSONArray("features");
                            JSONObject entry = features.getJSONObject(0);
                            //station request
                            JSONObject properties = entry.getJSONObject("properties");
                            String id = properties.getString("stationIdentifier");
                            StationRequest.stationid = id;
                            System.out.println(stationid);
                            //station name
                            String name = properties.getString("name");
                            StationRequest.stationname = name;



                        } catch (JSONException e) {
                            StationRequest.stationid = "Error";
                            return;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(jsonObjectRequest);

        return new String[]{stationid, stationname};
    }
}
