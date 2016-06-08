package com.example.mur07114879.weatherapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.ExecutorDelivery;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String place;
        //defining strings 
        final TextView tempreture = (TextView) findViewById(R.id.current_temperature_field);
        final TextView state = (TextView) findViewById(R.id.conditions_field);
        final ImageView img = (ImageView) findViewById(R.id.image);

        //get shared preferences
        SharedPreferences prefs = getSharedPreferences("details", MODE_PRIVATE);
        //get city value, if this doesn't exist set to null and put into shared preferences
        String city = prefs.getString("city", null);
        EditText weather = (EditText) findViewById(R.id.current_city);
        weather.setText(city);
        try {

            place = URLEncoder.encode(city, "UTF-8");
        } catch (Exception z) {
            place = "Kingston%20Upon%20Hull";

        }
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + place + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        RequestQueue queue = Volley.newRequestQueue(this);
        // JSON Object Request - bare in mind you may not be able to parse a JSON Array, make sure your source is a proper formatted object!
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //retrieving json objects from api
                            JSONObject query = response.getJSONObject("query");
                            JSONObject results = query.getJSONObject("results");
                            JSONObject channel = results.getJSONObject("channel");
                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            String temp = condition.getString("temp");
                            tempreture.setText(temp);
                            //this is pulling the text from the yahoo api and displaying the image
                            //which matches the text e.g. if the text equals showers it will display the
                            //image rain.
                            String weather = condition.getString("text");
                            state.setText(weather);
                            if (weather.equals("Mostly Cloudy")) {
                                img.setImageResource(R.drawable.cloudy);
                            } else if (weather.equals("Showers")) {
                                img.setImageResource(R.drawable.rain);
                            } else if (weather.equals("Snow")) {
                                img.setImageResource(R.drawable.snow);
                            } else if (weather.equals("Sunny")) {
                                img.setImageResource(R.drawable.sunny);
                            } else if (weather.equals("Windy")) {
                                img.setImageResource(R.drawable.windy);
                            } else if (weather.equals("Thunderstorms")) {
                                img.setImageResource(R.drawable.thunder);
                            }

                            Log.w("received", response.toString());
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // On Volley error (usually no resposnse or not the correct JSON format)
                Log.w("json", error);
            }
        });

        queue.add(req);

    }

    public void update(View view) {
        EditText weather = (EditText) findViewById(R.id.current_city);

        final TextView tempreture = (TextView) findViewById(R.id.current_temperature_field);
        final TextView state = (TextView) findViewById(R.id.conditions_field);
        final ImageView img = (ImageView) findViewById(R.id.image);


        SharedPreferences.Editor editor = getSharedPreferences("details", MODE_PRIVATE).edit();
        //put the values in city in shared preferences
        editor.putString("city", weather.getText().toString());
        //applies changes
        editor.apply();


        String place;
        try {
            String placeEntered = weather.getText().toString();
            place = URLEncoder.encode(placeEntered, "UTF-8");
        } catch (Exception z) {
            place = "Kingston%20Upon%20Hull";

        }
        //defining url where json objects will be retrieved from
        String url = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + place + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
        RequestQueue queue = Volley.newRequestQueue(this);
        // JSON Object Request - bare in mind you may not be able to parse a JSON Array, make sure your source is a proper formatted object!
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //retrieving json objects from api
                            JSONObject query = response.getJSONObject("query");
                            JSONObject results = query.getJSONObject("results");
                            JSONObject channel = results.getJSONObject("channel");
                            JSONObject item = channel.getJSONObject("item");
                            JSONObject condition = item.getJSONObject("condition");
                            String temp = condition.getString("temp");
                            tempreture.setText(temp);
                            //this is pulling the text from the yahoo api and displaying the image
                            //which matches the text e.g. if the text equals showers it will display the
                            //image rain.
                            String weather = condition.getString("text");
                            state.setText(weather);
                            if (weather.equals("Mostly Cloudy")) {
                                img.setImageResource(R.drawable.cloudy);
                            } else if (weather.equals("Showers")) {
                                img.setImageResource(R.drawable.rain);
                            } else if (weather.equals("Snow")) {
                                img.setImageResource(R.drawable.snow);
                            } else if (weather.equals("Sunny")) {
                                img.setImageResource(R.drawable.sunny);
                            } else if (weather.equals("Windy")) {
                                img.setImageResource(R.drawable.windy);
                            } else if (weather.equals("Thunderstorms")) {
                                img.setImageResource(R.drawable.thunder);
                            }

                            Log.w("received", response.toString());
                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // On Volley error (usually no resposnse or not the correct JSON format)
                Log.w("json", error);
            }
        });

        queue.add(req);

    }
    //saving objects
    public void save(View view) {




    }
}