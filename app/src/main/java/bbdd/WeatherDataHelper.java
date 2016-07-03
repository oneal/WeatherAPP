package bbdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import fragment.Weather;
import weatherData.WeatherModel;

public class WeatherDataHelper extends SQLiteOpenHelper {

    public WeatherDataHelper(Context context){
        super(context,QuoteDataSource.WEATHER_BBDD,null,QuoteDataSource.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QuoteDataSource.TABLE_WEATHER_DATA);
    }

    public void insertWeatherDataHelper(WeatherModel weatherModel){
        SQLiteDatabase db = getWritableDatabase();

        Log.d("hola",weatherModel.toString());

        db.execSQL("INSERT INTO "+QuoteDataSource.WEATHER_TABLE_NAME+
                " VALUES (NULL,'"+weatherModel.getWeather()+"', '"+weatherModel.getWeathername()+
                "', "+weatherModel.getPressure()+", "+weatherModel.getTemp()+
                ", "+weatherModel.getHumidity()+")");

        db.close();
    }

    public WeatherModel selectWeatherDataHelper(){
        SQLiteDatabase db = getWritableDatabase();

        WeatherModel weatherModel = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + QuoteDataSource.WEATHER_TABLE_NAME + " WHERE id = (SELECT max(id) FROM " + QuoteDataSource.WEATHER_TABLE_NAME +")", null);
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                weatherModel = new WeatherModel();
                weatherModel.setWeather(cursor.getString(1));
                weatherModel.setWeathername(cursor.getString(2));
                weatherModel.setPressure(cursor.getFloat(3));
                weatherModel.setTemp(cursor.getFloat(4));
                weatherModel.setHumidity(cursor.getFloat(5));
            } while(cursor.moveToNext());
        }

        return weatherModel;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
