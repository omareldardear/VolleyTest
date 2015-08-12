package com.omar.dardear.volleytest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {


    long city_id;
    String city_name;
    double cityLatitude ;
    double cityLongitude ;
    String city_country;
    int city_population;



    TextView city_id_text;
    TextView city_name_text;
    TextView city_lat_text;
    TextView city_lon_text;
    TextView city_country_text;
    TextView city_population_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText num=(EditText) findViewById(R.id.editText);
        Button mButton = (Button)findViewById(R.id.button);

          city_id_text=(TextView) findViewById(R.id.city_id);
          city_name_text=(TextView) findViewById(R.id.city_name);
          city_lat_text=(TextView) findViewById(R.id.city_lat);
          city_lon_text=(TextView) findViewById(R.id.city_lon);
          city_country_text=(TextView) findViewById(R.id.city_country);
          city_population_text=(TextView) findViewById(R.id.city_population);



        getJSONfile(Integer.parseInt(num.getText().toString()));

        mButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        getJSONfile(Integer.parseInt(num.getText().toString()));
                    }
                });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void getJSONfile(int num)
    {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+num+"&mode=json&units=metric&cnt=7";
        RequestQueue mRequestQueue;
        mRequestQueue = Volley.newRequestQueue(getApplicationContext());

       final JsonReqCache jsonRequest  =new JsonReqCache
                (Request.Method.GET, url, new Response.Listener<JSONObject>() {



                    @Override
                    public void onResponse(JSONObject forecastJson) {
                        // the response is already constructed as a JSONObject!
                        try {
                            JSONObject city =forecastJson.getJSONObject("city");


                             city_id=city.getLong("id");

                            JSONObject city_Coord = city.getJSONObject("coord");
                             cityLatitude = city_Coord.getDouble("lat");
                             cityLongitude = city_Coord.getDouble("lon");


                            city_name=city.getString("name");
                             city_country=city.getString("country");

                             city_population=city.getInt("population");
                            refresh();



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        error.printStackTrace();
                    }

                });




        jsonRequest.setShouldCache(Boolean.TRUE);
        jsonRequest.setTag("CityData");
        mRequestQueue.add(jsonRequest);



    }

    void refresh()
    {
          city_id_text.setText(Long.toString(city_id));
          city_name_text.setText(city_name);
          city_lat_text.setText(Double.toString(cityLatitude));
          city_lon_text.setText(Double.toString(cityLongitude));
          city_country_text.setText(city_country);
          city_population_text.setText(Integer.toString(city_population));

    }





}
