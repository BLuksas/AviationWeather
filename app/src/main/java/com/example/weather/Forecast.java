package com.example.weather;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weather.StationRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Forecast {
    public String[] Forecast(Context context, String strlat, String strlong){
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        String url = "https://api.weather.gov/points/40.45,-86.91/forecast";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            //formats response JSON
                            JSONObject properties = response.getJSONObject("properties");
                            JSONArray periods = properties.getJSONArray("Periods");
                            //First Weather
                            JSONObject weather1 = periods.getJSONObject(0);
                            JSONObject time = weather1.getJSONObject("name");
                            System.out.println(time);



                        } catch (JSONException e) {
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

        return new String[]{};
    }
}
