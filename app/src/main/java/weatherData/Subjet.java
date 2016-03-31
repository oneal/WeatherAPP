package weatherData;

/**
 * Created by g on 29/01/2016.
 */
interface Subjet {
   public void addObserver(Observer observer);
   public void removeObserver(Observer observer);
   public void  notifyObserver();
}
