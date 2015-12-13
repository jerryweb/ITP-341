package itp341.webb.jerry.greenbeatmachine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import itp341.webb.jerry.greenbeatmachine.database.SoundDbSchema.TABLE_SOUNDS;


/**
 * Created by jerrywebb on 12/6/15.
 */
public class SoundsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sounds_sqlite.db";
    private static final int DATABASE_VERSION = 1;


    //SQL statement to create table
    private static final String CREATE_TABLE_SOUNDS = "CREATE TABLE " +
            TABLE_SOUNDS.NAME + " (" +
            TABLE_SOUNDS.KEY_ID + " integer primary key autoincrement, " +
            TABLE_SOUNDS.KEY_NAME + " TEXT, " +
            TABLE_SOUNDS.KEY_SOUNDID + " REAL, " +
            TABLE_SOUNDS.KEY_TYPE + " TEXT"
            + ");";

    public SoundsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SOUNDS);
    }

    //Implement this to address changes to database schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("Example",
                "Upgrading database, this will drop ALL tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOUNDS.NAME);
        onCreate(db);
    }
}
