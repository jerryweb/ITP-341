package itp341.exercises.week11_singleton_sqlite.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import itp341.exercises.week11_singleton_sqlite.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;

/**
 * Created by R on 11/2/2015.
 */
public class CoffeeShopDbHelper extends SQLiteOpenHelper {
    //similar to defining a JSON file
    private static final String DATABASE_NAME = "week11_sqlite.db";
    //Only changes when the database changes in structure
    private static final int DATABASE_VERSION = 1;

    //SQL statement to create table
    private static final String CREATE_TABLE_COFFEE_SHOPS =
            "CREATE TABLE " +
                    TABLE_COFFEE_SHOPS.NAME + " (" +
                    TABLE_COFFEE_SHOPS.KEY_ID + " integer primary key autoincrement, " +
                    TABLE_COFFEE_SHOPS.KEY_NAME + " TEXT, " +
                    TABLE_COFFEE_SHOPS.KEY_RATING + " REAL" +
                    ");";

    public CoffeeShopDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Called only first time database is created
    //Create the schema for the new table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_COFFEE_SHOPS);

    }

    //Implement this to address changes to database schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("Example",
                "Upgrading database, this will drop ALL tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COFFEE_SHOPS.NAME);


        onCreate(db);
    }

}

