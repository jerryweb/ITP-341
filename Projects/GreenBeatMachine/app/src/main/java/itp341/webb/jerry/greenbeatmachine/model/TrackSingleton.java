package itp341.webb.jerry.greenbeatmachine.model;

import android.content.Context;
import android.nfc.Tag;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.MainActivity;

/**
 * Created by jerrywebb on 11/28/15.
 */
public class TrackSingleton {
//    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<Track> mTracks;
    private static TrackSingleton sTrackSingleton;
    private Context mAppContext;
    private double masterVolume;

    private TrackSingleton(Context context){
        mTracks = new ArrayList<Track>();
        this.mAppContext = context;
        masterVolume = 0.8;

        for(int i = 0; i<8;i++){
            Track t = new Track("track " + i, 0.8, 0.5);
            mTracks.add(t);
        }
    }


    public static TrackSingleton get(Context c) {
        if (sTrackSingleton == null) {
            sTrackSingleton = new TrackSingleton(c.getApplicationContext());
        }
        return sTrackSingleton;
    }

    public ArrayList<Track> getmTracks(){
        return mTracks;
    }

    public Track getTrack(int position){
        return mTracks.get(position);
    }

    public void addTrack(Track t){
        mTracks.add(t);
    }

    public void updateTrack(int position, Track n){
        if(position >= 0 && position < mTracks.size()){
            mTracks.set(position, n);
        }
    }

    public double getMasterVolume() {
        return masterVolume;
    }

    public void setMasterVolume(double masterVolume) {
        this.masterVolume = masterVolume;
    }
}
