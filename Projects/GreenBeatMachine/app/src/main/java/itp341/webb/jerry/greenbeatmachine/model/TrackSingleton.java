package itp341.webb.jerry.greenbeatmachine.model;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.nfc.Tag;
import android.util.Log;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.Controller.Sequencer;
import itp341.webb.jerry.greenbeatmachine.MainActivity;
import itp341.webb.jerry.greenbeatmachine.R;

/**
 * Created by jerrywebb on 11/28/15.
 * This singleton holds the information for the 8 tracks and the set of sounds used during playback.
 * The sequencer controller is also located here as it is used for tempo playback.
 */
public class TrackSingleton {
    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<Track> mTracks;
    private static TrackSingleton sTrackSingleton;
    private Context mAppContext;
    private double masterVolume;
    private int bpm;
    int bmb_k_id;
    int phn_clp_id;
    int dry_ohh_cra_id;

    SoundPool samplePoolNew;   //used to play sounds simultaneously... best used for sound files < 1MB

    public static Sequencer sequencer;

    private TrackSingleton(Context context){
        mTracks = new ArrayList<Track>();
        this.mAppContext = context;
        masterVolume = 0.8;                 //volume of the master fader/whole application
        bpm = R.integer.default_bpm;                           //tempo in beats per minute

        for(int i = 0; i<8;i++){
            Track t = new Track("track " + i, 0.8, 0.5);
            mTracks.add(t);
        }

        //sets attributes so as to better recognize the audio samples
        AudioAttributes sampleAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();


        //Streams are the number of sounds that can be played simultaneously
        samplePoolNew = new SoundPool.Builder().setMaxStreams(25)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        bmb_k_id = samplePoolNew.load(mAppContext, R.raw.ac_k, 1);
        phn_clp_id = samplePoolNew.load(mAppContext, R.raw.phn_clp,1);
        dry_ohh_cra_id = samplePoolNew.load(mAppContext, R.raw.dry_ohh_cra,1);

        sequencer = new Sequencer(mAppContext, bpm);        //Creates the sequencer thread
    }


    public static TrackSingleton get(Context c) {
        if (sTrackSingleton == null) {
            sTrackSingleton = new TrackSingleton(c.getApplicationContext());
        }

        return sTrackSingleton;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
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

    //getter and setter for volume of the master track (final output)
    public double getMasterVolume() {
        return masterVolume;
    }
    public void setMasterVolume(double masterVolume) {
        this.masterVolume = masterVolume;
    }

    //play sample back
    public void playSound(int id){
        Log.d(TAG, "isMusted = " + mTracks.get(id).isMuted());
        if(!mTracks.get(id).isMuted()){
            samplePoolNew.play(id+1, (float) 1,
                    (float) 1, 1, 0, 1);
        }
    }

    //getter for (tempo) sequencer
    public static Sequencer getSequencer() {
        return sequencer;
    }
//    public static void setSequencer(Sequencer sequencer) {
//        TrackSingleton.sequencer = sequencer;
//    }
}
