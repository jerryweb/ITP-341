package itp341.webb.jerry.greenbeatmachine.model;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.Controller.Sequencer;
import itp341.webb.jerry.greenbeatmachine.MainActivity;
import itp341.webb.jerry.greenbeatmachine.R;
import itp341.webb.jerry.greenbeatmachine.database.SoundDbSchema.TABLE_SOUNDS;

/**
 * Created by jerrywebb on 11/28/15.
 * This singleton holds the information for the 8 tracks and the set of sounds used during playback.
 * The sequencer controller is also located here as it is used for tempo playback.
 * Sound samples must not exceed 1 MB in size in order to properly play from sound pool.
 */
public class TrackSingleton {
    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<Track> mTracks;
    private static TrackSingleton sTrackSingleton;
    private Context mAppContext;
    private double masterVolume;
    private int bpm;
    boolean loaded;
    int [] soundResourceIds;
    int metronomeSound;


    SoundPool metronomeSamplePool;
    SoundPool [] trackSamplePool;
    SoundPool soundPool;
    Handler handler;                //This is used to access the sequencer thread when it's locked
    public static Sequencer sequencer;
    Thread sequencerThread;         //This is the thread for the midi sequencer (playback)

    private TrackSingleton(Context context){
        handler = new Handler();
        mTracks = new ArrayList<Track>();
        this.mAppContext = context;
        masterVolume = 80;                 //volume of the master fader/whole application
        bpm = 95;                           //tempo in beats per minute
        loaded = false;
        soundResourceIds = new int[32];
        metronomeSound = 1;

        for(int i = 0; i<8;i++){
            Track t = new Track("Track " + (i+1), 80.0, 50.0);
            mTracks.add(t);
        }


        //sets attributes so as to better recognize the audio samples
        AudioAttributes sampleAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();

        metronomeSamplePool = new SoundPool.Builder().setMaxStreams(5)
                .setAudioAttributes(sampleAttributes)
                .build();

        //int maxStreams, int streamType,int srcQuality
        soundPool = new SoundPool.Builder().setMaxStreams(25)
        .setAudioAttributes(sampleAttributes)
        .build();
        //Streams are the number of sounds that can be played simultaneously

        setSamplesToTracks();


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
        sequencer.setBpm(bpm);
    }

    public ArrayList<Track> getmTracks(){
        return mTracks;
    }

    public Track getTrack(int position){
        return mTracks.get(position);
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

    //This pre-loads all of the sound samples and stores them into the database
    //each sound needs a context, resource id, and a playback priority when loaded into the
    // sound pool. soundResourceIds holds the resource ids for the sounds
    public void setSamplesToTracks(){

        int[] dummySoundId = new int[12];//place holder for assigned id by sound pool
        //Array of sample resource ids
        soundResourceIds[0] = R.raw.ac_k;
        soundResourceIds[1] = R.raw.bmb_k;
        soundResourceIds[2] = R.raw.ben_k;
        soundResourceIds[3] = R.raw.phn_clp;
        soundResourceIds[4] = R.raw.phn_snr2;
        soundResourceIds[5] = R.raw.wsh_clp1;
        soundResourceIds[6] = R.raw.dry_ohh_cra;
        soundResourceIds[7] = R.raw.fm_ohh_cra;
        soundResourceIds[8] = R.raw.smt_hat;
        soundResourceIds[9] = R.raw.spk_hat;
        soundResourceIds[10] = R.raw.vb1_shk;
        soundResourceIds[11] = R.raw.vb6_shk;


        for (int i = 0; i< 12; i++){
            dummySoundId[i] = soundPool.load(mAppContext, soundResourceIds[i],1);
        }

        //These are the sound objects that hold th sample information
        Sound sTemp = new Sound("ac_k",dummySoundId[0],"kick",0);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        Sound s2 = new Sound("bmb_k",dummySoundId[1],"kick",1);
        SoundsSingleton.get(mAppContext).addSound(s2);

        Sound s3 = new Sound("ben_k",dummySoundId[2],"kick",2);
        SoundsSingleton.get(mAppContext).addSound(s3);

        Sound s4 = new Sound("phn_clp",dummySoundId[3],"clap",3);
        SoundsSingleton.get(mAppContext).addSound(s4);

        Sound s5 = new Sound("phn_snr2",dummySoundId[4],"snare",4);
        SoundsSingleton.get(mAppContext).addSound(s5);

        Sound s6 = new Sound("wsh_clp1",dummySoundId[5],"clap",5);
        SoundsSingleton.get(mAppContext).addSound(s6);

        sTemp = new Sound("dry_ohh_cra",dummySoundId[6],"crash",6);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        sTemp = new Sound("fm_ohh_cra",dummySoundId[7],"crash",7);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        sTemp = new Sound("smt_hat",dummySoundId[8],"hat",8);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        sTemp = new Sound("spk_hat",dummySoundId[9],"hat",9);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        sTemp = new Sound("vb1_shk",dummySoundId[10],"shaker",10);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        sTemp = new Sound("vb6_shk",dummySoundId[11],"shaker",11);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

       metronomeSound =  metronomeSamplePool.load(mAppContext, R.raw.lex_rim,1);
    }

    //This loads the sounds from the database to the corresponding tracks and pads
    public void loadSample(int id, String name){

        Sound s = new Sound();
        Cursor c = SoundsSingleton.get(mAppContext).getSound(name);
        if (c != null) { // this means we are editing old record

            if (c.moveToFirst()) {
                s.setName(c.getString(TABLE_SOUNDS.COLUMN_NAME));
                s.setType(c.getString(TABLE_SOUNDS.COLUMN_TYPE));
                s.setSoundId(c.getInt(TABLE_SOUNDS.COLUMN_SOUNDID));
                s.setResourceArrayPosition(c.getInt(TABLE_SOUNDS.COLUMN_RESOURCE_ARRAY_POSTION));

                mTracks.get(id).setTrackSample(s);
            }

            }

    }
    //play corresponding sample back by finding the triggered track number and getting  its sample
    // id
    public void playSound(int id){

                if(!mTracks.get(id).isMuted()){
                    if(mTracks.get(id).getCurrentSampleId() != -1) {
                        int soundId = mTracks.get(id).getCurrentSampleId();
            /*This calculates the pan and volume of track 1 Left channel*/
                        double leftChannelOutput = (this.masterVolume / 100) *
                                (mTracks.get(id).getTrackVolume() / 100) * (1 - mTracks.get(id).getTrackPan() / 100);
            /*This calculates the pan and volume of track 1 Right channel*/
                        double rightChannelOutput = (this.masterVolume / 100) *
                                (mTracks.get(id).getTrackVolume() / 100) * (mTracks.get(id).getTrackPan() / 100);

                        switch (id) {
                            case 0:
                                //int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 1:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 2:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 3:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 4:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 5:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 6:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;
                            case 7:
                                soundPool.play(soundId, (float) leftChannelOutput,
                                        (float) rightChannelOutput, 1, 0, 1);
                                break;

                        }
                    }

        }
    }

    public void playMetronome(){
            metronomeSamplePool.play(metronomeSound,(float) masterVolume/100,
                    (float) masterVolume/100, 1, 0, 1);
    }

    //allows other classes to access the sequencer class
    public static Sequencer getSequencer() {
        return sequencer;
    }

}
