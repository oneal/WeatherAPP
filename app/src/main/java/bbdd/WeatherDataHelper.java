package bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import weatherData.WeatherModel;

/**
 * Created by g on 18/02/2016.
 */
public class WeatherDataHelper extends SQLiteOpenHelper {

    private final static String WEATHER_BBDD = "weather_bbdd.db";
    public static final int DATABASE_VERSION = 1;
    public static final String WEATHER_TABLE_NAME = "weather_data";

    public static final String TABLE_WEATHER_DATA=
            "CREATE TABLE "+WEATHER_TABLE_NAME+"(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "ciudad TEXT," +
            "icon TEXT," +
            "descripcion TEXT," +
            "presion DECIMAL," +
            "temp DECIMAL," +
            "humedad DECIMAL," +
            "fechHora DATETIME"+
            ");";

    public WeatherDataHelper(Context context){
        super(context,WEATHER_BBDD,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_WEATHER_DATA);
    }

    public void insertWeatherDataHelper(WeatherModel weatherModel){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO "+WEATHER_TABLE_NAME+" VALUES ("+weatherModel.getWeather()+", "+weatherModel.getWeathername()+", "+weatherModel.getPressure()+", "+weatherModel.getTemp()+", "+weatherModel.getHumidity()+")");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
