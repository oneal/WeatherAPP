package conectionWeatherData;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import bbdd.QuoteDataSource;
import weatherData.WeatherModel;

public class WeatherConnectionApi extends AsyncTask {
    private static String cityacum = "";
    private static String openWeatherMapApi;
    private static WeatherModel weatherModel = new WeatherModel();

    @Override
    protected Object doInBackground(Object[] params) {
        cityacum = "baeza";
        openWeatherMapApi = "http://api.openweathermap.org/data/2.5/weather?q="+cityacum+"&appid=b1b15e88fa797225412429c1c50c122a";
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
            convertDataWeatherModel(data);

            QuoteDataSource.getWeatherDataHelper().insertWeatherDataHelper(weatherModel);

        } catch (JSONException | IOException e) {
            Log.d("error22", e.getMessage());
        }

        return null;
    }

    public void convertDataWeatherModel(JSONObject json){

        try {

            JSONObject details = json.getJSONArray("weather").getJSONObject(0);
            JSONObject main = json.getJSONObject("main");
            weatherModel.setHumidity(Float.parseFloat(main.getString("humidity")));
            weatherModel.setPressure(Float.parseFloat(main.getString("pressure")));
            weatherModel.setTemp(Float.parseFloat(String.format("%.2f", main.getDouble("temp"))));

            /*DateFormat df = DateFormat.getDateTimeInstance();
            String updatedOn = df.format(new Date(json.getLong("dt")*1000));
            updatedField.setText("Last update: " + updatedOn);*/

        }catch(Exception e){
            Log.e("SimpleWeather", "One or more fields not found in the JSON data");
        }
    }


}
