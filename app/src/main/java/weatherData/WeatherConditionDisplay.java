package weatherData;

/**
 * Created by g on 09/02/2016.
 */
public class WeatherConditionDisplay implements Observer {

    private Subjet weatherData;
    private String weather;
    private String weatherName;

    public WeatherConditionDisplay(Subjet weatherData){
        this.weatherData = weatherData;
        this.weatherData.addObserver(this);
    }

    @Override
    public void update(WeatherModel weatherModel) {
        this.weather = weatherModel.getWeather();
        this.weatherName = weatherModel.getWeathername();
        displayWeather();
        displayWeatherName();
    }

    public String displayWeather(){
        return weather;
    }

    public String displayWeatherName(){
        return weatherName;
    }
}
