package com.example.g.myapplication;

import android.app.AlertDialog;
import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import org.json.JSONObject;

import bbdd.WeatherDataHelper;
import conectionWeatherData.RemoteFetch;
import fragment.TempHumidity;
import fragment.Weather;
import sharePreferences.Preferences;
import weatherData.WeatherData;
import weatherData.WeatherModel;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost TabHost;
    private Handler handler = new Handler();
    private WeatherModel weatherModel;
    private WeatherDataHelper weatherDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        weatherDataHelper = new WeatherDataHelper(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                showInputDialog();
            }
        });
        weatherModel.setPressure((float)11.1);
        weatherModel.setHumidity((float)22.2);
        weatherModel.setTemp((float)22.2);
        weatherDataHelper.insertWeatherDataHelper(weatherModel);
        TabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        TabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        /*TabHost.addTab(
                TabHost.newTabSpec("tab1").setIndicator("Tiempo", null),
                Weather.class, null);*/
        TabHost.addTab(
                TabHost.newTabSpec("tab2").setIndicator("Temp y humedad", null),
                TempHumidity.class, null);

    }

    public  void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cambia ciudad");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                changeCity(input.getText().toString());
                updateWeatherData(input.getText().toString());

            }
        });
        builder.show();
    }

    public void changeCity(String city){
        new Preferences(this).setCity(city);

    }


    public void updateWeatherData(final String city){
        new Thread(){
            public void run(){
                final JSONObject json = RemoteFetch.getJSON(city);
                if(json == null){
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplication(),
                                    getApplication().getString(R.string.place_not_found),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            getValues(json);

                        }
                    });
                }
            }
        }.start();
    }

    public final void getValues(JSONObject jsonObject){
        try{

            JSONObject main = jsonObject.getJSONObject("main");
            weatherModel.setHumidity(Float.parseFloat(main.getString("humidity")));
            weatherModel.setPressure(Float.parseFloat(main.getString("pressure")));
            weatherModel.setTemp(Float.parseFloat(main.getString("temp")));
            JSONObject details = jsonObject.getJSONArray("weather").getJSONObject(0);
            weatherModel.setWeather(details.getInt("id")+ "," + jsonObject.getJSONObject("sys").getLong("sunrise") * 1000 + "," + jsonObject.getJSONObject("sys").getLong("sunset") * 1000);
            weatherModel.setWeathername(details.getString("description"));
            weatherDataHelper.insertWeatherDataHelper(weatherModel);
        }catch (Exception e){

        }

    }


}
