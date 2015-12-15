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


    //These are used to filter through the audio files on the sd card
    class wavFileFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String filename) {
            return (filename.endsWith(".wav"));
        }
    }

    class mp3FileFilter implements FilenameFilter{

        @Override
        public boolean accept(File dir, String filename) {
            return (filename.endsWith(".mp3"));
        }
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


        if(sounds.size() < 12) {
            sounds.add(s);
        }
        return 0;
    }

    public ArrayList<Sound> getSoundList(){
        return sounds;
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
                TABLE_SOUNDS.KEY_TYPE,
                TABLE_SOUNDS.KEY_RESOURCE_ARRAY_POSTION
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

    public Cursor getSoundWithLong(long id) {
        Log.d(TAG, "getSound: id = " + id);

        String [] projection =  {
                TABLE_SOUNDS.KEY_ID,
                TABLE_SOUNDS.KEY_NAME,
                TABLE_SOUNDS.KEY_SOUNDID,
                TABLE_SOUNDS.KEY_TYPE,
                TABLE_SOUNDS.KEY_RESOURCE_ARRAY_POSTION
        };

        String sortOrder = TABLE_SOUNDS.KEY_NAME + " asc";
        String selection = TABLE_SOUNDS.KEY_ID + " = ?";  //_id = ?
        Log.d(TAG, "key id = "+ TABLE_SOUNDS.KEY_ID);
        String [] selectionArgs = {String.valueOf(id)};



        Cursor c = mDatabase.query(TABLE_SOUNDS.NAME, projection,
                selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    public int updateSound(String name, Sound s) {
        Log.d(TAG, "updateSound: id = " + name);
        Log.d(TAG, "updateSound info = " + s.toString());

        ContentValues cv = new ContentValues();
        cv.put(TABLE_SOUNDS.KEY_NAME, s.getName());
        cv.put(TABLE_SOUNDS.KEY_SOUNDID, s.getSoundId());
        cv.put(TABLE_SOUNDS.KEY_TYPE, s.getType());
        cv.put(TABLE_SOUNDS.KEY_RESOURCE_ARRAY_POSTION, s.getResourceArrayPosition());

        String selection = TABLE_SOUNDS.KEY_NAME + " = ?";  //_id = ?
        String [] selectionArgs = {name};

        return mDatabase.update(TABLE_SOUNDS.NAME, cv, selection, selectionArgs);

    }

//    public void updateSounds(){
//        File home = new File(SD_PATH);
//        if(home.listFiles( new mp3FileFilter()).length > 0){
//            for (File file : home.listFiles(new mp3FileFilter())) {
//                songs.add(file.getName());
//            }
//
//            SoundAdapter<String> songList = new SoundAdapter(mAppContext, R.layout.item_sound,songs);
////            setListAdapter(songList);
//        }
//    }
}
