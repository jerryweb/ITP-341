package itp341.webb.jerry.greenbeatmachine.model;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Handler;
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
    int ac_k_id;
    int ben_k_id;
    int phn_clp_id;
    int dry_ohh_cra_id;
    int smt_hat_id;
    int vb1_shk_id;
    int vb6_shk_id;
    int spk_hat_id;
    int wsh_clp1_id;
    int fm_ohh_cra_id;

    SoundPool track1SamplePool;   //used to play sounds simultaneously... best used for sound files < 1MB
    SoundPool track2SamplePool;
    SoundPool track3SamplePool;
    SoundPool track4SamplePool;
    SoundPool track5SamplePool;
    SoundPool track6SamplePool;
    SoundPool track7SamplePool;
    SoundPool track8SamplePool;

    Handler handler;                //This is used to access the sequencer thread when it's locked
    public static Sequencer sequencer;
    Thread sequencerThread;         //This is the thread for the midi sequencer (playback)

    private TrackSingleton(Context context){
        handler = new Handler();
        mTracks = new ArrayList<Track>();
        this.mAppContext = context;
        masterVolume = 0.8;                 //volume of the master fader/whole application
        bpm = 80;                           //tempo in beats per minute

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
        track1SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track2SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track3SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track4SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track5SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track6SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track7SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        track8SamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        bmb_k_id = track1SamplePool.load(mAppContext, R.raw.ac_k, 1);
        phn_clp_id = track2SamplePool.load(mAppContext, R.raw.phn_clp, 1);
        dry_ohh_cra_id = track3SamplePool.load(mAppContext, R.raw.dry_ohh_cra, 1);
        vb6_shk_id = track4SamplePool.load(mAppContext, R.raw.vb6_shk, 1);
        spk_hat_id = track5SamplePool.load(mAppContext, R.raw.spk_hat, 1);
        smt_hat_id = track6SamplePool.load(mAppContext, R.raw.smt_hat, 1);
        wsh_clp1_id = track7SamplePool.load(mAppContext, R.raw.wsh_clp1, 1);
        fm_ohh_cra_id = track8SamplePool.load(mAppContext, R.raw.fm_ohh_cra, 1);


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


    //This will toggle either play or pause depending on what the sequencer thread is doing
    public void togglePlay(){
        if (!TrackSingleton.get(mAppContext).getSequencer().isPlaying()) {

            sequencerThread = new Thread(new Runnable() {

                @Override
                public void run() {
                    TrackSingleton.get(mAppContext).getSequencer().startPlayback();
                    handler.post(new Runnable() {

                        @Override
                        public void run() {

                        }
                    });
                }
            });
            sequencerThread.start();
        }
        else {
            sequencer.stopPlayback();
        }
    }

    //getter and setter for volume of the master track (final output)
    public double getMasterVolume() {
        return masterVolume;
    }
    public void setMasterVolume(double masterVolume) {
        this.masterVolume = masterVolume;
    }

    public void callTriggerBeat(int id){
        if(sequencer.isPlaying()) {
            sequencer.beatTrigger(id);
        }
    }

    //play sample back
    public void playSound(int id){
        Log.d(TAG, "isMusted = " + mTracks.get(id).isMuted());
        if(!mTracks.get(id).isMuted()){
            switch (id){
                case 0:
                    track1SamplePool.play(1, (float) masterVolume,
                        (float) masterVolume, 1, 0, 1);
                    break;
                case 1:
                    track2SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
                case 2:
                    track3SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
                case 3:
                    track4SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
                case 4:
                    track5SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
                case 5:
                    track6SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
                case 6:
                    track7SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
                case 7:
                    track8SamplePool.play(1,(float) masterVolume,
                            (float) masterVolume, 1, 0, 1);
                    break;
            }
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
