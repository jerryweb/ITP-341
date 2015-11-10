package itp341.exercises.week11_singleton_sqlite.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLData;

import itp341.exercises.week11_singleton_sqlite.app.database.CoffeeShopDbHelper;
import itp341.exercises.week11_singleton_sqlite.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;

public class CoffeeShopSingleton {
    private static final String TAG = CoffeeShopSingleton.class.getSimpleName();


    //TODO DB - member

    private SQLiteDatabase mDatabase;


    private static CoffeeShopSingleton sCoffeeShops;
    private Context mAppContext;

    //TODO DB - modify
    private CoffeeShopSingleton(Context appContext) {
        mAppContext = appContext;
        mDatabase = new CoffeeShopDbHelper(mAppContext).getWritableDatabase();
    }

    public static CoffeeShopSingleton get(Context c) {
        if (sCoffeeShops == null) {
            sCoffeeShops = new CoffeeShopSingleton(c.getApplicationContext());
        }
        return sCoffeeShops;
    }




    public long addCoffeeShop(CoffeeShop cs) {
        Log.d(TAG, "addCoffeeShop: " + cs.toString());

        ContentValues cv = new ContentValues();
        //cv.put(CoffeeShopDbSchema.TABLE_COFFEE_SHOPS.KEY_NAME, cs.getName());
        cv.put(TABLE_COFFEE_SHOPS.KEY_NAME, cs.getName());
//      cv.put(CoffeeShopDbSchema.TABLE_COFFEE_SHOPS.KEY_RATING, cs.getRating());
        cv.put(TABLE_COFFEE_SHOPS.KEY_RATING, cs.getRating());

        mDatabase.insert(TABLE_COFFEE_SHOPS.NAME, null, cv);
        return 0;
    }

    //TODO DB
    public int removeCoffeeShop(long id) {
        Log.d(TAG, "removeCoffeeShop: id = " + id);

        String selection = TABLE_COFFEE_SHOPS.KEY_ID + " = ?";  //_id = ?
        String[] selectionArgs = {Long.toString(id)};


        return mDatabase.delete(TABLE_COFFEE_SHOPS.NAME, selection, selectionArgs);
    }

    public Cursor getCoffeeShops() {
        Log.d(TAG, "getCoffeeShops");
        String [] projection =  {
                TABLE_COFFEE_SHOPS.KEY_ID,
                TABLE_COFFEE_SHOPS.KEY_NAME,
                TABLE_COFFEE_SHOPS.KEY_RATING
        };

        String sortOrder = TABLE_COFFEE_SHOPS.KEY_NAME + " asc";

        Cursor c = mDatabase.query(TABLE_COFFEE_SHOPS.NAME,projection,
                null, null, null, null, sortOrder);
        return c;
    }


    //TODO DB
    public Cursor getCoffeeShop(long id) {
        Log.d(TAG, "getCoffeeShop: id = " + id);

        String [] projection =  {
            TABLE_COFFEE_SHOPS.KEY_ID,
            TABLE_COFFEE_SHOPS.KEY_NAME,
            TABLE_COFFEE_SHOPS.KEY_RATING
        };

        String sortOrder = TABLE_COFFEE_SHOPS.KEY_NAME + " asc";
        String selection = TABLE_COFFEE_SHOPS.KEY_ID + " = ?";  //_id = ?
        String [] selectionArgs = {Long.toString(id)};



        Cursor c = mDatabase.query(TABLE_COFFEE_SHOPS.NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    //TODO DB
    public int updateCoffeeShop(long id, CoffeeShop cs) {
        Log.d(TAG, "updateCoffeeShop: id = " + id);
        Log.d(TAG, "coffeeShop info = " + cs.toString());

        ContentValues cv = new ContentValues();
        cv.put(TABLE_COFFEE_SHOPS.KEY_NAME, cs.getName());
        cv.put(TABLE_COFFEE_SHOPS.KEY_RATING, cs.getRating());

        String selection = TABLE_COFFEE_SHOPS.KEY_ID + " = ?";  //_id = ?
        String [] selectionArgs = {Long.toString(id)};

        return mDatabase.update(TABLE_COFFEE_SHOPS.NAME, cv, selection, selectionArgs);

    }
}
