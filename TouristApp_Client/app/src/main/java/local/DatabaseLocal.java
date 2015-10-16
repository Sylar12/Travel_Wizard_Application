package local;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import model.City;

/**
 * Created by vincent on 15/4/10.
 */
public class DatabaseLocal {

    private SQLiteDatabase db;

    public DatabaseLocal(Activity act) {
        this.db = act.openOrCreateDatabase("StudentDB", Context.MODE_APPEND, null);
        // User table recording the recently used userIDs
        db.execSQL("CREATE TABLE IF NOT EXISTS `recent_user` (\n" +
                "  id text PRIMARY KEY\n" +
                ");");
        // The recently searched cities
        db.execSQL("CREATE TABLE IF NOT EXISTS `recent_city` (\n" +
                "  id INTEGER PRIMARY KEY,\n" +
                "  cityName text,\n" +
                "  score double,\n" +
                "  location text,\n" +
                "  introduction text\n" +
                ");");
    }

    public ArrayList<String> getRecentUserLocalDB() {
        String selectQuery = "SELECT * FROM `recent_user`";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> userIDs = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                userIDs.add(id);
            } while (cursor.moveToNext());
        }
        return userIDs;
    }

    public ArrayList<City> getCitiesLocalDB() {
        String selectQuery = "SELECT * FROM `recent_city`";
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<City> cities = new ArrayList<City>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String cityName = cursor.getString(1);
                double score = cursor.getDouble(2);
                String location = cursor.getString(3);
                String introduction = cursor.getString(4);
                City city = new City(id, cityName, location, introduction, score);
                cities.add(city);
            } while (cursor.moveToNext());
        }
        return cities;
    }



}
