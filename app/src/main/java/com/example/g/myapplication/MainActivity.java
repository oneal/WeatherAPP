package com.example.g.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import bbdd.QuoteDataSource;
import conectionWeatherData.RemoteFetch;
import conectionWeatherData.WeatherConnectionApi;
import fragment.TempHumidity;
import fragment.Weather;
import sharePreferences.Preferences;
import weatherData.WeatherModel;

public class MainActivity extends FragmentActivity {

    private FragmentTabHost TabHost;
    private Handler handler = new Handler();
    private WeatherModel weatherModel;
    private SQLiteDatabase database;
    private QuoteDataSource quoteDataSource;
    private WeatherConnectionApi weatherConnectionApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                showInputDialog();
            }
        });

        quoteDataSource = new QuoteDataSource(this);

        TabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        TabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        /*TabHost.addTab(
                TabHost.newTabSpec("tab1").setIndicator("Tiempo", null),
                Weather.class, null);*/
        TabHost.addTab(
                TabHost.newTabSpec("tab2").setIndicator("Temp y humedad", null),
                TempHumidity.class, null);

        weatherConnectionApi = new WeatherConnectionApi(MainActivity.this);
        weatherConnectionApi.execute();

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
            }
        });
        builder.show();
    }

    public void changeCity(String city){
        new Preferences(this).setCity(city);

    }




}
