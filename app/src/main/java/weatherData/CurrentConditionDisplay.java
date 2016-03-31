package weatherData;

import bbdd.WeatherDataHelper;

/**
 * Created by g on 29/01/2016.
 */
public class CurrentConditionDisplay implements Observer {

    private Subjet weatherData;
    private float temp;
    private float humidity;
    private float pressure;

    public CurrentConditionDisplay(Subjet weatherData) {
        this.weatherData = weatherData;
        this.weatherData.addObserver(this);

    }

    @Override
    public void update(WeatherModel weatherModel) {
        this.temp = weatherModel.getTemp();
        this.humidity = weatherModel.getHumidity();
        this.pressure = weatherModel.getPressure();
        displayTemp();
        displayHumidity();
    }

    public float displayTemp() {
        return  this.temp;
    }

    public float displayHumidity(){
        return this.humidity;
    }

    public float displayPressure() {
        return this.pressure;
    }


}
