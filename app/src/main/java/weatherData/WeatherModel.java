package weatherData;

/**
 * Created by g on 11/02/2016.
 */
public class WeatherModel {
    private float  temp = 0;
    private float humidity = 0;
    private float pressure = 0;
    private String weather = "";
    private String weathername = "";

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getWeathername() {
        return weathername;
    }

    public void setWeathername(String weathername) {
        this.weathername = weathername;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "temp=" + temp +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", weather='" + weather + '\'' +
                ", weathername='" + weathername + '\'' +
                '}';
    }
}
