package bbdd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        db.execSQL("INSERT INTO "+QuoteDataSource.WEATHER_TABLE_NAME+
                " VALUES ("+weatherModel.getWeather()+", "+weatherModel.getWeathername()+
                ", "+weatherModel.getPressure()+", "+weatherModel.getTemp()+
                ", "+weatherModel.getHumidity()+")");

        db.close();
    }

    public WeatherModel selectWeatherDataHelper(){
        SQLiteDatabase db = getWritableDatabase();

        WeatherModel weatherModel = null;

        Cursor cursor = db.rawQuery("SELECT codigo,nombre FROM weather_data", null);
        if (cursor.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                weatherModel = new WeatherModel();
                weatherModel.setWeather(cursor.getString(2));
                weatherModel.setWeathername(cursor.getString(3));
                weatherModel.setPressure(cursor.getFloat(4));
                weatherModel.setTemp(cursor.getFloat(5));
                weatherModel.setHumidity(cursor.getFloat(6));
            } while(cursor.moveToNext());
        }

        return weatherModel;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
