package conectionWeatherData;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.g.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import weatherData.WeatherModel;

public class WeatherConnectionApi extends AsyncTask<WeatherModel, Void, WeatherModel> {
    private static String cityacum = "";
    private static String openWeatherMapApi;
    private  WeatherModel weatherModel = new WeatherModel();
    private Context context;

    public WeatherConnectionApi(Context context){
        this.context = context;
    }

    @Override
    protected WeatherModel doInBackground(WeatherModel... params) {
        cityacum = "baeza";
        openWeatherMapApi = "http://api.openweathermap.org/data/2.5/weather?q="+cityacum+"&appid=619e16aa1572618094ce0e897e987d53";


        URL url = null;
        JSONObject data = null;
        StringBuffer json = null;

        try {

            url = new URL(openWeatherMapApi);
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            json = new StringBuffer(1024);
            String tmp="";

            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            data = new JSONObject(json.toString());
            convertDataToWeatherModel(data);

        } catch (JSONException | IOException e) {
            Log.d("error22", e.getMessage());
        }

        return weatherModel;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    public void convertDataToWeatherModel(JSONObject json){

        try {
            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            weatherModel.setHumidity(Float.parseFloat(main.getString("humidity")));
            weatherModel.setPressure(Float.parseFloat(main.getString("pressure")));
            weatherModel.setTemp(Float.parseFloat(main.getDouble("temp") + ""));
            weatherModel.setWeathername(details.getString("description"));
            setWeatherIcon(details.getInt("id"),
                    json.getJSONObject("sys").getLong("sunrise") * 1000,
                    json.getJSONObject("sys").getLong("sunset") * 1000);

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }

    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = this.context.getString(R.string.weather_sunny);
            } else {
                icon = this.context.getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = this.context.getString(R.string.weather_thunder);
                    break;
                case 3 : icon = this.context.getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = this.context.getString(R.string.weather_foggy);
                    break;
                case 8 : icon = this.context.getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = this.context.getString(R.string.weather_snowy);
                    break;
                case 5 : icon = this.context.getString(R.string.weather_rainy);
                    break;
            }
        }
        weatherModel.setWeather(icon);
    }


}
