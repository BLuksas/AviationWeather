package com.example.weather;

import android.app.DownloadManager;
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

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

public class Weather {
    private static double temp1;
    private static double winds1;
    private static double windd1;
    private static double barPress1;

    public double[] Weather(Context context, String stationname) {

        final DecimalFormat df = new DecimalFormat("####0.00");
        //Weather request
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        try {
            URL url = new URL("https", "api.weather.gov", "/stations/"+stationname+"/observations");

            System.out.println(url.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                //formats response JSON
                                JSONArray features = response.getJSONArray("features");
                                JSONObject entry = features.getJSONObject(0);
                                //temperature
                                JSONObject properties = entry.getJSONObject("properties");
                                JSONObject temperature = properties.getJSONObject("temperature");
                                Double temp = temperature.getDouble("value");
                                Double temp_rounded = Double.valueOf(df.format(temp));

                                Weather.temp1 = temp_rounded;
                                System.out.println("Fine");
                                //wind speed
                                JSONObject windSpeed = properties.getJSONObject("windSpeed");
                                Double wind_s = windSpeed.getDouble("value");

                                Weather.winds1 = wind_s;

                                //wind direction
                                JSONObject windDirection = properties.getJSONObject("windDirection");
                                Double wind_d = windDirection.getDouble("value");

                                Weather.windd1 = wind_d;

                                //barometric pressure
                                JSONObject barometricPressure = properties.getJSONObject("barometricPressure");
                                Integer barPress = barometricPressure.getInt("value");
                                Double barPressD = Double.valueOf(barPress);

                                Weather.barPress1 = barPress;

                            } catch (JSONException e) {
                                System.out.println("Error");
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

            return new double[]{temp1, winds1, windd1, barPress1};

        } catch (MalformedURLException url){
            System.out.println("Error");
        } return new double[]{temp1, winds1, windd1, barPress1};}
    }


