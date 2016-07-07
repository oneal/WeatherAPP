package com.example.g.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import bbdd.QuoteDataSource;
import conectionWeatherData.WeatherConnectionApi;
import fragment.Weather;
import weatherData.WeatherModel;

public class WeatherService extends Service {

    private NotificationCompat.Builder mBuilder;
    private int notification_id = 001;
    private NotificationManager mNotifyMgr;
    private WeatherConnectionApi weatherConnectionApi;
    private Context context;

    public WeatherService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        weatherConnectionApi = new WeatherConnectionApi(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            WeatherModel weatherModel = weatherConnectionApi.execute().get();
            QuoteDataSource.getWeatherDataHelper().insertWeatherDataHelper(weatherModel);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            weatherConnectionApi.cancel(true);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
