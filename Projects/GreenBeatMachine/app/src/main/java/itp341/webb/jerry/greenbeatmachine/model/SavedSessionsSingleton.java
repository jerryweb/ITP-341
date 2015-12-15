package itp341.webb.jerry.greenbeatmachine.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.Controller.Sequencer;
import itp341.webb.jerry.greenbeatmachine.database.SavedSessionsDbHelper;
import itp341.webb.jerry.greenbeatmachine.database.SavedSessionsDbSchema.TABLE_SAVED_SESSIONS;
import itp341.webb.jerry.greenbeatmachine.database.SoundDbSchema;

/**
 * Created by jerrywebb on 12/14/15.
 */
public class SavedSessionsSingleton {
    public static final String TAG = "itp341.finalProject.tag";
    private SQLiteDatabase mDatabase;
    private static SavedSessionsSingleton savedSessionsSingleton;
    private Context mAppContext;

    private SavedSessionsSingleton (Context context){
        mAppContext = context;
        mDatabase = new SavedSessionsDbHelper(mAppContext).getWritableDatabase();
    }

    public static SavedSessionsSingleton get(Context c) {
        if (savedSessionsSingleton == null) {
            savedSessionsSingleton = new SavedSessionsSingleton(c.getApplicationContext());
        }
        return savedSessionsSingleton;
    }

    public void saveMasterVolume(float volume){
        int [][] midiSteps = new int[8][16];
        double [] trackVolumes = new double[8];
        double [] trackPans = new double[8];
        int [] trackMutes = new int[8];
        String trackVolumeString = null;

        ArrayList<Track> tracks = TrackSingleton.get(mAppContext).getmTracks();

        boolean [][] steps = TrackSingleton.getSequencer().getMidiSteps();
        for (int i = 0; i<8; i++) {
            for (int j = 0; j < 16; j++) {
                midiSteps[i][j] = (steps[i][j]) ? 1 : 0;
            }
        }
            for (int i = 0; i<8; i++) {
                trackVolumes[i] = tracks.get(i).getTrackVolume();
                trackPans[i] = tracks.get(i).getTrackPan();
                trackMutes[i] = (tracks.get(i).isMuted()) ? 1 :0;
            }

//        trackVolumeString.toString(trackVolumes);

        ContentValues cv= new ContentValues();
        cv.put(TABLE_SAVED_SESSIONS.KEY_NAME, volume);
//        cv.put(TABLE_SAVED_SESSIONS.KEY_SEQUENCER_STEPS, );
//            cv.put(TABLE_SAVED_SESSIONS.KEY_TRACK_VOLUMES, )
    }

}
