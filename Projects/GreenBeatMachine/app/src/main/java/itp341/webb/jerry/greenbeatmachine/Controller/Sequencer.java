package itp341.webb.jerry.greenbeatmachine.Controller;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.SoundPool;

import itp341.webb.jerry.greenbeatmachine.R;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/11/15.
 * This class is used as a seperate thread to play the audio sequence back.
 * The thread will do nothing when the sequence is not playing. During playback, the
 * thread will lock using synchronization (similar to an OS lock) for a set time at a
 * constant interval to set the tempo of the beat. Whenever the user updates the
 * sequencer UI, the boolean array of step values will update here using the notify
 * method. Same with the play/stop button.
 */
public class Sequencer {
    public static final String TAG = "itp341.webb.jerry.finalProject.tag";
    private Context mAppContext;
    private volatile boolean playing;
    private volatile int bpm;
    private boolean midiSteps[][]; //These correspond to the midi step checkboxes in the sequencer
    private boolean metronome;
    private final Object lock = new Object();
    private int step;



    public Sequencer(Context c, int BPM){
        this.bpm = BPM;
        step = 0;
        playing = false;
        metronome = false;
        mAppContext = c;
        midiSteps = new boolean[8][16];

        for (int i = 0; i<2; i++){
            for (int j = 0; j<16; j++){
                this.midiSteps[i][j] = false;
            }
        }

    }

    public void playBeat(){
        while (playing) {
            synchronized (lock) {
                for (int i = 0; i < 16; i++) {
                    step = i;
                    //This checks whether the steps are checked; if true, then it will tell the track
                    // to play the corresponding sound
//                    Log.d(TAG,"kick: " + i + " "+ midiSteps[0][i] + "   clap: " + midiSteps[1][i]);

                    if(this.metronome){
                        TrackSingleton.get(mAppContext).playMetronome();
                    }

                    if(midiSteps[0][i]) {
                        TrackSingleton.get(mAppContext).playSound(0);
                    }
                    if(midiSteps[1][i]){
                        TrackSingleton.get(mAppContext).playSound(1);
                    }
                    if(midiSteps[2][i]){
                        TrackSingleton.get(mAppContext).playSound(2);
                    }
                    if(midiSteps[3][i]) {
                        TrackSingleton.get(mAppContext).playSound(3);
                    }
                    if(midiSteps[4][i]){
                        TrackSingleton.get(mAppContext).playSound(4);
                    }
                    if(midiSteps[5][i]){
                        TrackSingleton.get(mAppContext).playSound(5);
                    }
                    if(midiSteps[6][i]){
                        TrackSingleton.get(mAppContext).playSound(6);
                    }
                    if(midiSteps[7][i]){
                        TrackSingleton.get(mAppContext).playSound(7);
                    }


                    if (playing == false) {     //if the user stopped playback, it will leave
                                                // the loop
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
        synchronized (lock) {
            lock.notify();

            this.playing = false;
        }
    }
    public void beatTrigger(int id) {
        synchronized (lock) {
            lock.notify();
            this.midiSteps[id][step] = true;
        }
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

    public void toggleMetronome() {
        synchronized (lock) {
            lock.notify();
            this.metronome = !metronome;
        }

    }
}
