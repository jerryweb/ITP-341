package itp341.webb.jerry.greenbeatmachine.Controller;

import android.content.Context;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/11/15.
 * This class is used as a seperate thread to play the audio sequence back.
 * The thread will do nothing when the sequence is not playing. During playback, the
 * thread will sleep for a set time at a constant interval to set the tempo of the
 * beat.
 */
public class Sequencer {
    public static final String TAG = "itp341.webb.jerry.finalProject.tag";
    private Context mAppContext;
    private volatile boolean playing;
    private volatile int bpm;
    private boolean midiSteps[][]; //These correspond to the midi step checkboxes in the sequencer
    private final Object lock = new Object();


    public Sequencer(Context c, int BPM){
        this.bpm = BPM;
        playing = false;
        mAppContext = c;
        midiSteps = new boolean[1][16];

//        for (int i = 0; i<8; i++){
            for (int j = 0; j<16; j++){
                midiSteps[0][j] = false;
            }
//        }
    }

    public void playBeat(){
        while (playing) {
            synchronized (lock) {
                for (int i = 0; i < 16; i++) {

                    //This checks whether the steps are checked; if true, then it will tell the track
                    // to play the corresponding sound
                    if (midiSteps[0][i]) {
                        TrackSingleton.get(mAppContext).playSound(0);
                    }
//                if(midiSteps[1][i]){
//                    TrackSingleton.get(mAppContext).playSound(1);
//                }
//                if(midiSteps[2][i]){
//                    TrackSingleton.get(mAppContext).playSound(2);
//                }

                    if (playing == false) {    //if
                        break;
                    }
                    try {
//                        Thread.sleep(60000 / (bpm * 4)); //this sets the tempo
                        lock.wait(60000 / (bpm * 4)); //this sets the tempo
                        if (i == 15) i = -1;

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        }
    }

    public void startPlayback(){
        synchronized (lock) {
            lock.notify();
            this.playing = true;
            playBeat();
        }
    }
    public void stopPlayback(){

            this.playing = false;
        }

    public boolean[][] getMidiSteps() {
        return midiSteps;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setMidiSteps(boolean[][] midiSteps) {
        synchronized (lock) {
            lock.notify();
            this.midiSteps = midiSteps;
        }

    }



//    public boolean getMidiStep(int iIndex, int jIndex){
//        return midiSteps[iIndex][jIndex];
//    }
//
//    public void setMidiStep(int iIndex)

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }


}