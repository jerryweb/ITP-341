package itp341.webb.jerry.greenbeatmachine.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import itp341.webb.jerry.greenbeatmachine.database. SavedSessionsDbSchema.TABLE_SAVED_SESSIONS;

/**
 * Created by jerrywebb on 12/14/15.
 */
public class SavedSessionsDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sessions_sqlite.db";
    private static final int DATABASE_VERSION = 1;


    //SQL statement to create table
    private static final String TABLE_SAVED_SESSIONS = "CREATE TABLE " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.NAME + " (" +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_ID + " integer primary key autoincrement, " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_NAME + " TEXT, " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_SEQUENCER_STEPS + " BLOB, " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_MASTER_VOLUME + " REAL " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_TRACK_VOLUMES + " REAL " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_TRACK_PANS + " REAL " +
            SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.KEY_TRACK_MUTES + " REAL "
            + ");";

    public SavedSessionsDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_SAVED_SESSIONS);
    }

    //Implement this to address changes to database schema
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.w("Example",
                "Upgrading database, this will drop ALL tables and recreate.");
        db.execSQL("DROP TABLE IF EXISTS " + SavedSessionsDbSchema.TABLE_SAVED_SESSIONS.NAME);
        onCreate(db);
    }
}
