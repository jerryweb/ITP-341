package itp341.webb.jerry.greenbeatmachine.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import android.widget.Toast;

import itp341.webb.jerry.greenbeatmachine.R;
import itp341.webb.jerry.greenbeatmachine.database.SoundsDbHelper;
import itp341.webb.jerry.greenbeatmachine.database.SoundDbSchema.TABLE_SOUNDS;

/**
 * Created by jerrywebb on 11/28/15.
 */
public class SoundsSingleton {
    public static final String TAG = "itp341.finalProject.tag";

    private SQLiteDatabase mDatabase;

    private static SoundsSingleton sSounds;
    private Context mAppContext;


//    SoundPool track1SamplePool;
    private SoundsSingleton(Context appContext) {
        mAppContext = appContext;
        mDatabase = new SoundsDbHelper(mAppContext).getWritableDatabase();


    }


//    public void playSound(int id){
//        track1SamplePool.play(id+1, (float) 0.8, (float) 0.8, 1, 0, 1);
//    }

    public static SoundsSingleton get(Context c) {
        if (sSounds == null) {
            sSounds = new SoundsSingleton(c.getApplicationContext());
        }
        return sSounds;
    }
    //add a sound to the database
    public long addSound(Sound s) {
        Log.d(TAG, "addSound: " + s.toString());

        ContentValues cv = new ContentValues();
        cv.put(TABLE_SOUNDS.KEY_NAME, s.getName());
        Log.d(TAG, "sound add id: " + s.getSoundId());

        cv.put(TABLE_SOUNDS.KEY_SOUNDID, s.getSoundId());
        cv.put(TABLE_SOUNDS.KEY_TYPE, s.getType());

        mDatabase.insert(TABLE_SOUNDS.NAME, null, cv);
        return 0;
    }

    public int removeSound(long id){
        Log.d(TAG, "removesound: id = " + id);

        String selection = TABLE_SOUNDS.KEY_ID + " = ?";  //_id = ?
        String[] selectionArgs = {Long.toString(id)};

        return mDatabase.delete(TABLE_SOUNDS.NAME, selection, selectionArgs);
    }

    public Cursor getSounds(){
        Log.d(TAG, "getSounds");
        String [] projection =  {
                TABLE_SOUNDS.KEY_ID,
                TABLE_SOUNDS.KEY_NAME,
                TABLE_SOUNDS.KEY_SOUNDID,
                TABLE_SOUNDS.KEY_TYPE
        };

        String sortOrder = TABLE_SOUNDS.KEY_NAME + " asc";

        Cursor c = mDatabase.query(TABLE_SOUNDS.NAME,projection,
                null, null, null, null, sortOrder);
        return c;
    }

    public Cursor getSound(String name) {
        Log.d(TAG, "getSound: id = " + name);

        String [] projection =  {
                TABLE_SOUNDS.KEY_ID,
                TABLE_SOUNDS.KEY_NAME,
                TABLE_SOUNDS.KEY_SOUNDID,
                TABLE_SOUNDS.KEY_TYPE
        };

        String sortOrder = TABLE_SOUNDS.KEY_NAME + " asc";
        String selection = TABLE_SOUNDS.KEY_NAME + " = ?";  //_id = ?
        Log.d(TAG, "key id = "+ TABLE_SOUNDS.KEY_NAME);
        String [] selectionArgs = {name};



        Cursor c = mDatabase.query(TABLE_SOUNDS.NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    public int updateSound(long id, Sound s) {
        Log.d(TAG, "updateSound: id = " + id);
        Log.d(TAG, "updateSound info = " + s.toString());

        ContentValues cv = new ContentValues();
        cv.put(TABLE_SOUNDS.KEY_NAME, s.getName());
        cv.put(TABLE_SOUNDS.KEY_SOUNDID, s.getSoundId());
        cv.put(TABLE_SOUNDS.KEY_TYPE, s.getType());

        String selection = TABLE_SOUNDS.KEY_ID + " = ?";  //_id = ?
        String [] selectionArgs = {Long.toString(id)};

        return mDatabase.update(TABLE_SOUNDS.NAME, cv, selection, selectionArgs);

    }
}
