package conectionWeatherData;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.g.myapplication.R;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteFetch {
    private static String cityacum = "";
    private static String openWeatherMapApi;

    public static JSONObject getJSON(String city){
        try {
            cityacum = city;
            openWeatherMapApi = "http://api.openweathermap.org/data/2.5/weather?q="+cityacum+"&appid=44db6a862fba0b067b1930da0d769e98";
            URL url = new URL(openWeatherMapApi);
            HttpURLConnection connection =
                    (HttpURLConnection)url.openConnection();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            StringBuffer json = new StringBuffer(1024);
            String tmp="";

            while((tmp=reader.readLine())!=null)
                json.append(tmp).append("\n");
            reader.close();

            JSONObject data = new JSONObject(json.toString());

            // This value will be 404 if the request was not
            // successful
            if(data.getInt("cod") != 200){
                return null;
            }

            return data;
        }catch(Exception e){
            return null;
        }
    }
}