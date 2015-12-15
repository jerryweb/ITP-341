package itp341.webb.jerry.greenbeatmachine.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.R;
import itp341.webb.jerry.greenbeatmachine.SoundAdapter;
import itp341.webb.jerry.greenbeatmachine.database.SoundsDbHelper;
import itp341.webb.jerry.greenbeatmachine.database.SoundDbSchema.TABLE_SOUNDS;

/**
 * Created by jerrywebb on 11/28/15.
 */
public class SoundsSingleton {
    public static final String TAG = "itp341.finalProject.tag";
    private static final String SD_PATH = "/sdcard/";
    private ArrayList<String> songs;
    private ArrayList<Sound> sounds;
    private SQLiteDatabase mDatabase;

    private static SoundsSingleton sSounds;
    private Context mAppContext;


//    SoundPool track1SamplePool;
    private SoundsSingleton(Context appContext) {
        mAppContext = appContext;
        mDatabase = new SoundsDbHelper(mAppContext).getWritableDatabase();
        songs = new ArrayList<>();
        sounds = new ArrayList<>();
    }




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
        cv.put(TABLE_SOUNDS.KEY_RESOURCE_ARRAY_POSTION, s.getResourceArrayPosition());


        mDatabase.insert(TABLE_SOUNDS.NAME, null, cv);
            sounds.add(s);
        return 0;
    }

    public ArrayList<Sound> getSoundList(){
        return sounds;
    }



    public Cursor getSound(String name) {
        Log.d(TAG, "getSound: id = " + name);

        String [] projection =  {
                TABLE_SOUNDS.KEY_ID,
                TABLE_SOUNDS.KEY_NAME,
                TABLE_SOUNDS.KEY_SOUNDID,
                TABLE_SOUNDS.KEY_TYPE,
                TABLE_SOUNDS.KEY_RESOURCE_ARRAY_POSTION
        };

        String sortOrder = TABLE_SOUNDS.KEY_NAME + " asc";
        String selection = TABLE_SOUNDS.KEY_NAME + " = ?";  //_id = ?
        Log.d(TAG, "key id = "+ TABLE_SOUNDS.KEY_NAME);
        String [] selectionArgs = {name};



        Cursor c = mDatabase.query(TABLE_SOUNDS.NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
        return c;
    }


}
