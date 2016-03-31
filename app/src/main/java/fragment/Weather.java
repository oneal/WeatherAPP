package fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.g.myapplication.R;

import org.json.JSONObject;

import java.util.Date;

import clases.Utils;
import conectionWeatherData.RemoteFetch;
import sharePreferences.Preferences;
import weatherData.WeatherConditionDisplay;
import weatherData.WeatherData;

public class Weather extends Fragment implements DisplayView {

    Typeface weatherFont;

    private WeatherConditionDisplay weatherConditionDisplay;
    private TextView weather;
    private TextView weatherName;
    Handler handler = new Handler();
    private WeatherData weatherData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weather.ttf");
        weatherData = new WeatherData();
        this.weatherConditionDisplay = new WeatherConditionDisplay(weatherData);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_layout, container, false);
        weather = (TextView) v.findViewById(R.id.weather);
        weather.setTypeface(weatherFont);
        weatherName = (TextView) v.findViewById(R.id.weatherName);
        weather.setText("");
        weatherName.setText("");
        renderView();
        return v;
    }

    @Override
    public void renderView() {
        String[] weather = weatherConditionDisplay.displayWeather().split(",");
        setWeatherIcon(Integer.parseInt(weather[0]), Long.parseLong(weather[1]), Long.parseLong(weather[2]));
        weatherName.setText(weatherConditionDisplay.displayWeatherName() + "");
    }



    private void setWeatherIcon(int actualId, long sunrise, long sunset){
        int id = actualId / 100;
        String icon = "";
        if(actualId == 800){
            long currentTime = new Date().getTime();
            if(currentTime>=sunrise && currentTime<sunset) {
                icon = getActivity().getString(R.string.weather_sunny);
            } else {
                icon = getActivity().getString(R.string.weather_clear_night);
            }
        } else {
            switch(id) {
                case 2 : icon = getActivity().getString(R.string.weather_thunder);
                    break;
                case 3 : icon = getActivity().getString(R.string.weather_drizzle);
                    break;
                case 7 : icon = getActivity().getString(R.string.weather_foggy);
                    break;
                case 8 : icon = getActivity().getString(R.string.weather_cloudy);
                    break;
                case 6 : icon = getActivity().getString(R.string.weather_snowy);
                    break;
                case 5 : icon = getActivity().getString(R.string.weather_rainy);
                    break;
            }
        }
        weather.setText(icon);
    }
}