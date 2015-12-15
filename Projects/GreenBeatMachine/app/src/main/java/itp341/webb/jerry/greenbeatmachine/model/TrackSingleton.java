package itp341.webb.jerry.greenbeatmachine.model;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioAttributes;
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
 */
public class TrackSingleton {
    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<Track> mTracks;
    private static TrackSingleton sTrackSingleton;
    private Context mAppContext;
    private double masterVolume;
    private int bpm;
    boolean loaded;

    int bmb_k_id;
    int ac_k_id;
    int ben_k_id;
    int phn_clp_id;
    int phn_snr2_id;
    int dry_ohh_cra_id;
    int smt_hat_id;
    int vb1_shk_id;
    int vb6_shk_id;
    int spk_hat_id;
    int wsh_clp1_id;
    int fm_ohh_cra_id;
    int perfect_808_id;
    int lex_rim_id;
    int [] soundResourceIds;


    SoundPool metronomeSamplePool;
    SoundPool [] trackSamplePool;

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
        trackSamplePool = new SoundPool[8];
        soundResourceIds = new int[32];

        for(int i = 0; i<8;i++){
            Track t = new Track("Track " + (i+1), 80.0, 50.0);
            mTracks.add(t);
        }

        //sets attributes so as to better recognize the audio samples
        AudioAttributes sampleAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();

        metronomeSamplePool = new SoundPool.Builder().setMaxStreams(1)
                .setAudioAttributes(sampleAttributes)
                .build(); //int maxStreams, int streamType,int srcQuality

        //Streams are the number of sounds that can be played simultaneously

        for(int i = 0; i<8; i++){
            trackSamplePool[i] = new SoundPool.Builder().setMaxStreams(5)
                    .setAudioAttributes(sampleAttributes)
                    .build(); //int maxStreams, int streamType,int srcQuality
        }


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

    public void setSamplesToTracks(){

        int[] dummySoundId = new int[12];//place holder for assigned id by sound pool
        for(int i =0;i<12;i++){
            dummySoundId[i] =0;
        }

        soundResourceIds[0] = R.raw.ac_k;
        Sound sTemp = new Sound("ac_k",dummySoundId[0],"kick",0);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        soundResourceIds[1] = R.raw.bmb_k;
        Sound s2 = new Sound("bmb_k",dummySoundId[1],"kick",1);
        SoundsSingleton.get(mAppContext).addSound(s2);

        soundResourceIds[2] = R.raw.ben_k;
        Sound s3 = new Sound("ben_k",dummySoundId[2],"kick",2);
        SoundsSingleton.get(mAppContext).addSound(s3);

        soundResourceIds[3] = R.raw.phn_clp;
        Sound s4 = new Sound("phn_clp",dummySoundId[3],"clap",3);
        SoundsSingleton.get(mAppContext).addSound(s4);

        soundResourceIds[4] = R.raw.phn_snr2;
        Sound s5 = new Sound("phn_snr2",dummySoundId[4],"snare",4);
        SoundsSingleton.get(mAppContext).addSound(s5);

        soundResourceIds[5] = R.raw.wsh_clp1;
        Sound s6 = new Sound("wsh_clp1",dummySoundId[5],"clap",5);
        SoundsSingleton.get(mAppContext).addSound(s6);

        soundResourceIds[6] = R.raw.dry_ohh_cra;
        sTemp = new Sound("dry_ohh_cra",dummySoundId[6],"crash",6);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        soundResourceIds[7] = R.raw.fm_ohh_cra;
        sTemp = new Sound("fm_ohh_cra",dummySoundId[7],"crash",7);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        soundResourceIds[8] = R.raw.smt_hat;
        sTemp = new Sound("smt_hat",dummySoundId[8],"hat",8);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        soundResourceIds[9] = R.raw.spk_hat;
        sTemp = new Sound("spk_hat",dummySoundId[9],"hat",9);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        soundResourceIds[10] = R.raw.vb1_shk;
        sTemp = new Sound("vb1_shk",dummySoundId[10],"shaker",10);
        SoundsSingleton.get(mAppContext).addSound(sTemp);

        soundResourceIds[11] = R.raw.vb6_shk;
        sTemp = new Sound("vb6_shk",dummySoundId[11],"shaker",11);
        SoundsSingleton.get(mAppContext).addSound(sTemp);




        //These load the sound files into the sound pools
//        ac_k_id = trackSamplePool[0].load(mAppContext, soundResourceIds[0], 1);
//        bmb_k_id = trackSamplePool[0].load(mAppContext, R.raw.bmb_k, 1);
//        phn_clp_id = trackSamplePool[1].load(mAppContext, R.raw.phn_clp, 1);
//        dry_ohh_cra_id = trackSamplePool[2].load(mAppContext, R.raw.dry_ohh_cra, 1);
//        vb6_shk_id = trackSamplePool[3].load(mAppContext, R.raw.vb6_shk, 1);
////        perfect_808_id = track5SamplePool.load(mAppContext, R.raw.perfect_808, 1);
//        spk_hat_id = trackSamplePool[4].load(mAppContext, R.raw.spk_hat, 1);
//        smt_hat_id = trackSamplePool[5].load(mAppContext, R.raw.smt_hat, 1);
//        wsh_clp1_id = trackSamplePool[6].load(mAppContext, R.raw.wsh_clp1, 1);
//        fm_ohh_cra_id = trackSamplePool[7].load(mAppContext, R.raw.fm_ohh_cra, 1);
//        lex_rim_id = metronomeSamplePool.load(mAppContext, R.raw.lex_rim, 1);

    }

    public void loadSample(int id, String name){

        Sound s = new Sound();
        Cursor c = SoundsSingleton.get(mAppContext).getSound(name);
        if (c != null) { // this means we are editing old record

            if (c.moveToFirst()) {
                s.setName(c.getString(TABLE_SOUNDS.COLUMN_NAME));
                s.setType(c.getString(TABLE_SOUNDS.COLUMN_TYPE));
                s.setSoundId(trackSamplePool[id].load(mAppContext,
                        soundResourceIds[c.getInt(TABLE_SOUNDS.COLUMN_RESOURCE_ARRAY_POSTION)], 1));
                s.setResourceArrayPosition(c.getInt(TABLE_SOUNDS.COLUMN_RESOURCE_ARRAY_POSTION));

                mTracks.get(id).setTrackSample(s);
//                SoundsSingleton.get(mAppContext).updateSound(s.getName(), s);
            }

            }

    }
    //play sample back
    public void playSound(int id){

                if(!mTracks.get(id).isMuted()){
            /*This calculates the pan and volume of track 1 Left channel*/
            double leftChannelOutput = (this.masterVolume/100)*
                    (mTracks.get(id).getTrackVolume()/100)*(1 - mTracks.get(id).getTrackPan()/100);
            /*This calculates the pan and volume of track 1 Right channel*/
            double rightChannelOutput = (this.masterVolume/100)*
                    (mTracks.get(id).getTrackVolume()/100)*(mTracks.get(id).getTrackPan()/100);

            switch (id){
                case 0:
                    //int soundID, float leftVolume, float rightVolume, int priority, int loop, float rate)

                    trackSamplePool[0].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 1:
                    trackSamplePool[1].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 2:
//                    mTracks.get(0).setTrackSample(s1);

                    trackSamplePool[2].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 3:
//                    mTracks.get(0).setTrackSample(so);

                    trackSamplePool[3].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 4:
                    trackSamplePool[4].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 5:
                    trackSamplePool[5].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 6:
                    trackSamplePool[6].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;
                case 7:
                    trackSamplePool[7].play(1, (float) leftChannelOutput,
                            (float) rightChannelOutput, 1, 0, 1);
                    break;

            }
        }
    }

    public void playMetronome(){
            metronomeSamplePool.play(1,(float) masterVolume/100,
                    (float) masterVolume/100, 1, 0, 1);
    }

    //allows other classes to access the sequencer class
    public static Sequencer getSequencer() {
        return sequencer;
    }

}
