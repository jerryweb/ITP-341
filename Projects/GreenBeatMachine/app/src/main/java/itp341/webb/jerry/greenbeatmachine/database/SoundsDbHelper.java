package itp341.webb.jerry.greenbeatmachine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jerrywebb on 12/6/15.
 */
public class SoundsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "final_sqlite.db";
    private static final int DATABASE_VERSION = 3;

    public SoundsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    //Implement this to address changes to database schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("Example",
                "Upgrading database, this will drop ALL tables and recreate.");
    }
}
