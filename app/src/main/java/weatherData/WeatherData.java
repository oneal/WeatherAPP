package weatherData;

import android.content.Context;

import org.json.JSONObject;
import java.util.ArrayList;

import bbdd.WeatherDataHelper;
import fragment.Weather;

public class WeatherData implements Subjet {

    private ArrayList observers;
    private WeatherModel weatherModel;
    private WeatherDataHelper weatherDataHelper;

    public WeatherData() {
        observers = new ArrayList();
    }


    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        int pos = observers.indexOf(observer);
        if(pos >= 0){
            observers.remove(pos);
        }
    }

    @Override
    public void notifyObserver() {
        for(int i = 0; i<observers.size(); i++){
            Observer observer = (Observer)observers.get(i);
            observer.update(this.weatherModel);
        }
    }

    public void setMeasurements(){
        this.weatherModel.setTemp(weatherModel.getTemp());
        this.weatherModel.setPressure(weatherModel.getPressure());
        this.weatherModel.setHumidity(weatherModel.getHumidity());
        measurementChanged();

    }

    public void measurementChanged(){
        notifyObserver();
    }


    public ArrayList<Observer> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<Observer> observers) {
        this.observers = observers;
    }
}
