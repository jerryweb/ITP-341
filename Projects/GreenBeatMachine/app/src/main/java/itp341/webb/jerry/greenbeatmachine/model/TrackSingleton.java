package itp341.webb.jerry.greenbeatmachine.model;

import android.content.Context;
import android.nfc.Tag;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.MainActivity;

/**
 * Created by jerrywebb on 11/28/15.
 */
public class TrackSingleton {
    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<Track> mTracks;
    private static TrackSingleton sTrackSingleton;
    private Context mAppContext;

    private TrackSingleton(Context context){
        mTracks = new ArrayList<Track>();
        this.mAppContext = context;
    }


    public static TrackSingleton get(Context c) {
        if (sTrackSingleton == null) {
            sTrackSingleton = new TrackSingleton(c.getApplicationContext());
        }
        return sTrackSingleton;
    }
}
