package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.example.g.myapplication.R;
import org.json.JSONObject;

import clases.Utils;
import conectionWeatherData.RemoteFetch;
import sharePreferences.Preferences;
import weatherData.CurrentConditionDisplay;
import weatherData.WeatherData;

public class TempHumidity extends Fragment implements DisplayView {
    private CurrentConditionDisplay currentConditionDisplay;
    private WeatherData weatherData;
    private TextView temp;
    private TextView humidity;
    private TextView pressure;
    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        weatherData = new WeatherData();
        currentConditionDisplay = new CurrentConditionDisplay(weatherData);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tem_humidity_layout, container, false);
        temp = (TextView) v.findViewById(R.id.temp);
        humidity = (TextView) v.findViewById(R.id.humedad);
        pressure = (TextView) v.findViewById(R.id.press);
        temp.setText("0 ºC");
        humidity.setText("0%");
        pressure.setText("0");
        renderView();
        return v;
    }

    @Override
    public void renderView() {
        temp.setText((int)(currentConditionDisplay.displayTemp()-273.15)+"ºC");
        humidity.setText((int)currentConditionDisplay.displayHumidity()+"%");
        pressure.setText(currentConditionDisplay.displayPressure()+" hPa");
    }


}
