package com.example.g.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import conectionWeatherData.WeatherConnectionApi;

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
        mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle("Reproducciendo")
                        .setContentText("Hello World!");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        mBuilder.setContentIntent(pendingIntent);
        mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        weatherConnectionApi = new WeatherConnectionApi(getApplicationContext());
        weatherConnectionApi.execute();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        try {
            finalize();
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
