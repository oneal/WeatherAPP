package bbdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by g on 31/03/2016.
 */
public class QuoteDataSource {
    public static final String WEATHER_BBDD = "weather_bbdd.db";
    public static final int DATABASE_VERSION = 1;
    public static final String WEATHER_TABLE_NAME = "weather_data";

    public static final String TABLE_WEATHER_DATA=
            "CREATE TABLE "+WEATHER_TABLE_NAME+"(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "icon TEXT," +
                    "descripcion TEXT," +
                    "presion DECIMAL," +
                    "temp DECIMAL," +
                    "humedad DECIMAL," +
                    "fechHora DATETIME"+
                    ");";
    private static WeatherDataHelper weatherDataHelper;
    private static SQLiteDatabase sqLiteDatabase;

    public QuoteDataSource(Context context) {
        weatherDataHelper = new WeatherDataHelper(context);
        sqLiteDatabase = weatherDataHelper.getWritableDatabase();
    }

    public static WeatherDataHelper getWeatherDataHelper() {
        return weatherDataHelper;
    }

    public static SQLiteDatabase getSqLiteDatabase() {
        return sqLiteDatabase;
    }
}
