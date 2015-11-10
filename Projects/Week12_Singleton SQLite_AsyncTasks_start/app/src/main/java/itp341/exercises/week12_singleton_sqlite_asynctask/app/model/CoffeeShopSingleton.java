package itp341.exercises.week12_singleton_sqlite_asynctask.app.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import itp341.exercises.week12_singleton_sqlite_asynctask.app.database.CoffeeShopDbHelper;
import itp341.exercises.week12_singleton_sqlite_asynctask.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;

public class CoffeeShopSingleton {
    private static final String TAG = CoffeeShopSingleton.class.getSimpleName();


    private SQLiteDatabase mDatabase;

    private static CoffeeShopSingleton sCoffeeShops;
    private Context mAppContext;


    private CoffeeShopSingleton(Context appContext) {
        mAppContext = appContext;
        mDatabase = new CoffeeShopDbHelper(appContext)
                .getWritableDatabase();

    }

    public static CoffeeShopSingleton get(Context c) {
        if (sCoffeeShops == null) {
            sCoffeeShops = new CoffeeShopSingleton(c.getApplicationContext());
        }
        return sCoffeeShops;
    }


    public long addCoffeeShop(CoffeeShop cs) {
        Log.d(TAG, "insert: " + cs.toString());

        ContentValues cv = new ContentValues();
        cv.put(TABLE_COFFEE_SHOPS.KEY_NAME, cs.getName());
        cv.put(TABLE_COFFEE_SHOPS.KEY_RATING, cs.getRating());

        return mDatabase.insert(TABLE_COFFEE_SHOPS.NAME, null, cv);
    }

    public int removeCoffeeShop(long id) {
        Log.d(TAG, "deleteSingleId: id = " + id);

        String selection = TABLE_COFFEE_SHOPS.KEY_ID + "= ?";
        String[] selectionArgs = {Long.toString(id)};

        return mDatabase.delete(TABLE_COFFEE_SHOPS.NAME, selection, selectionArgs);

    }

    public Cursor getCoffeeShops() {
        Log.d(TAG, "selectAll");

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TABLE_COFFEE_SHOPS.KEY_ID,
                TABLE_COFFEE_SHOPS.KEY_NAME,
                TABLE_COFFEE_SHOPS.KEY_RATING
        };
        String sortOrder = TABLE_COFFEE_SHOPS.KEY_NAME + " asc";

        //NB it is important to include KEY_ID or Creating the SimpleCursorAdapter
        //later will crash because _id not present
        Cursor cursor = mDatabase.query(TABLE_COFFEE_SHOPS.NAME,
                projection, null, null, null, null, sortOrder);

        return cursor;
    }



    public Cursor getCoffeeShop(long id) {
        Log.d(TAG, "selectById: id = " + id);

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                TABLE_COFFEE_SHOPS.KEY_ID,
                TABLE_COFFEE_SHOPS.KEY_NAME,
                TABLE_COFFEE_SHOPS.KEY_RATING
        };
        String sortOrder = TABLE_COFFEE_SHOPS.KEY_NAME + " asc";
        String selection = TABLE_COFFEE_SHOPS.KEY_ID + "= ?";
        String[] selectionArgs = {Long.toString(id)};

        //NB it is important to include KEY_ID or Creating the SimpleCursorAdapter
        //later will crash because _id not present
        Cursor cursor = mDatabase.query(TABLE_COFFEE_SHOPS.NAME,
                projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;
    }

    public int updateCoffeeShop(long id, CoffeeShop cs) {
        Log.d(TAG, "UpdateSingleId: id = " + id);
        Log.d(TAG, "coffeeShop info = " + cs.toString());

        ContentValues cv = new ContentValues();
        cv.put(TABLE_COFFEE_SHOPS.KEY_NAME, cs.getName());
        cv.put(TABLE_COFFEE_SHOPS.KEY_RATING, cs.getRating());

        String selection = TABLE_COFFEE_SHOPS.KEY_ID + "= ?";
        String[] selectionArgs = {Long.toString(id)};

        return mDatabase.update(TABLE_COFFEE_SHOPS.NAME,
                cv,
                selection,
                selectionArgs
        );

    }

}
