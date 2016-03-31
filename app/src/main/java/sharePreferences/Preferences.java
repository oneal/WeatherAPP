package sharePreferences;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by g on 30/01/2016.
 */
public class Preferences {

    SharedPreferences preferences;

    public Preferences (Activity activity){
        this.preferences = activity.getPreferences(Context.MODE_APPEND);
    }

    public String getCity(){
        return preferences.getString("City", "Baeza");
    }

    public void setCity(String city){
        preferences.edit().putString("City", city).commit();
    }
}
