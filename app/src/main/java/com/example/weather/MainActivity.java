package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    Weather mainWeather = new Weather();
    StationRequest mainStationRequest = new StationRequest();
    Forecast mainForecast = new Forecast();

    final DecimalFormat df = new DecimalFormat("####0.00");
    public static String strlat;
    public static String strlong;
    public static String stationname;
    public static String stationnamelong;
    public static Integer count = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView = findViewById(R.id.textView);
        final TextView textView4 = findViewById(R.id.textView4);
        final TextView textView2 = findViewById(R.id.textView2);
        final EditText search = findViewById(R.id.editText);
        search.setText("");

        //Location Display//////////////////////////////////////////////////////////////////////////////////////

        final LocationManager locationmanager = (LocationManager) getSystemService(LOCATION_SERVICE);
        final LocationListener locationlistener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                textView.setText("Your location is:\n" + location.getLatitude() + "°,\n" + location.getLongitude() + "°");
                String latitude = Double.toString(location.getLatitude());
                String longitude = Double.toString(location.getLongitude());
                MainActivity.strlat = latitude;
                MainActivity.strlong = longitude;
                call();

                String[] id_name = mainStationRequest.StationRequest(getBaseContext(), strlat, strlong);
                MainActivity.stationname = id_name[0];
                MainActivity.stationnamelong = id_name[1];
                textView2.setText("Station Identifier: " + stationname + "\n Station Name: " + stationnamelong);
                //temp format
                double[] returnedWeather = mainWeather.Weather(getBaseContext(), stationname);
                Double returnedTemp = returnedWeather[0];
                Double tempF = ((returnedTemp)) * 9 / 5 + 32;
                Double tempFRounded = Double.valueOf(df.format(tempF));
                String tempFString = tempFRounded.toString();

                //wind speed
                Double returnedWindSpeed = returnedWeather[1];
                Double windSpeedKts = returnedWindSpeed * 1.94384;
                Double windSpeedKtsRounded = Double.valueOf(df.format(windSpeedKts));

                //wind direction
                Double returnedWindDireciton = Double.valueOf(returnedWeather[2]);
                Double windDtirectionsRounded = Double.valueOf(df.format(returnedWindDireciton));

                //Bar Pressure
                Double returnedBarPress = returnedWeather[3];
                Double BarPressHG = returnedBarPress * 0.0002953;
                Double BarPressRounded = Double.valueOf(df.format(BarPressHG));

                textView4.setText("Temperature: " + tempFString + " °F/ " + returnedTemp + " °C\n Wind: " + windDtirectionsRounded + "° at " + windSpeedKtsRounded + " Kts\n Altimeter: "
                        + BarPressRounded + " inHg/ \n" + returnedBarPress + " Pa");



            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
            }

        };

        /////////////////////////////////////////////////////////////////////////////////////////////////////

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                locationmanager.requestLocationUpdates("gps", 10, 0, locationlistener);

            }
        });
        /////////////Search Function////////////////////////////////////////////////////////

        button2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                if(search.getText().length() == 0){

                } else {
                    locationmanager.removeUpdates(locationlistener);
                    stationname = search.getText().toString();
                    search.setCursorVisible(false);
                    search.setActivated(false);
                    textView2.setText("Station Identifier: " + stationname);

                    //temp format
                    double[] returnedWeather = mainWeather.Weather(getBaseContext(), stationname);
                    Double returnedTemp = returnedWeather[0];
                    Double tempF = ((returnedTemp)) * 9 / 5 + 32;
                    Double tempFRounded = Double.valueOf(df.format(tempF));
                    String tempFString = tempFRounded.toString();


                    //wind speed
                    Double returnedWindSpeed = returnedWeather[1];
                    Double windSpeedKts = returnedWindSpeed * 1.94384;
                    Double windSpeedKtsRounded = Double.valueOf(df.format(windSpeedKts));

                    //wind direction
                    Double returnedWindDireciton = Double.valueOf(returnedWeather[2]);
                    Double windDtirectionsRounded = Double.valueOf(df.format(returnedWindDireciton));

                    //Bar Pressure
                    Double returnedBarPress = returnedWeather[3];
                    Double BarPressHG = returnedBarPress * 0.0002953;
                    Double BarPressRounded = Double.valueOf(df.format(BarPressHG));

                    textView4.setText("Temperature: " + tempFString + " °F/ " + returnedTemp + " °C\n Wind: " + windDtirectionsRounded + "° at " + windSpeedKtsRounded + " Kts\n Altimeter: "
                            + BarPressRounded + " inHg/ \n" + returnedBarPress + " Pa");
                    count++;
                    if (count % 2 != 1) {
                        search.setText("");
                        count = 2;
                    }
                }



            }

        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET
                }, 2);
                return;
            }

        }

        locationmanager.requestLocationUpdates("gps", 10, 0, locationlistener);

            }


public void call() {
        StationRequest mainStationRequest = new StationRequest();

        String[] id_name = mainStationRequest.StationRequest(getBaseContext(), strlat, strlong);
        MainActivity.stationname = id_name[0];
        MainActivity.stationnamelong = id_name[1];
        mainWeather.Weather(getBaseContext(), stationname);
}
    }







