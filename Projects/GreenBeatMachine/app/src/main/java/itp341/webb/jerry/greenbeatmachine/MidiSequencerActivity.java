package itp341.webb.jerry.greenbeatmachine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;


import itp341.webb.jerry.greenbeatmachine.model.Track;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;
/**
 * Created by jerrywebb on 12/10/15.
 */
public class MidiSequencerActivity extends Activity{
    public static final String TAG = "itp341.finalProject.tag";
    Activity MainActivity;
    Button btn_toMainActivity;
    Button btn_play;
    TextView [] textViewTracks;
    CheckBox [] checkBoxTrackMutes;
    CheckBox [][] checkBoxMidiSteps;
    ArrayList<Track> tracks;
    Thread sequencerThread;         //This is the thread for the midi sequencer (playback)
    private boolean midiSteps[][]; //These correspond to the midi step checkboxes in the sequencer
    int bpm;
    Handler handler;                //This is used to access the sequencer thread when it's locked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midi_sequencer);

        handler = new Handler();
        bpm = 120;
        midiSteps = new boolean[8][16];
        tracks = TrackSingleton.get(this).getmTracks();
        MainActivity = new MainActivity();
        btn_toMainActivity = (Button) findViewById(R.id.button2);
        btn_play = (Button) findViewById(R.id.play);



        textViewTracks = new TextView[8];


        textViewTracks[0] = (TextView) findViewById(R.id.textViewTrack1);
        textViewTracks[1] = (TextView) findViewById(R.id.textViewTrack2);
        textViewTracks[2] = (TextView) findViewById(R.id.textViewTrack3);
        textViewTracks[3] = (TextView) findViewById(R.id.textViewTrack4);
        textViewTracks[4] = (TextView) findViewById(R.id.textViewTrack5);
        textViewTracks[5] = (TextView) findViewById(R.id.textViewTrack6);
        textViewTracks[6] = (TextView) findViewById(R.id.textViewTrack7);
        textViewTracks[7] = (TextView) findViewById(R.id.textViewTrack8);

        checkBoxTrackMutes = new CheckBox[8];

        checkBoxTrackMutes[0] = (CheckBox) findViewById(R.id.checkBoxMute1);
        checkBoxTrackMutes[1] = (CheckBox) findViewById(R.id.checkBoxMute2);
        checkBoxTrackMutes[2] = (CheckBox) findViewById(R.id.checkBoxMute3);
        checkBoxTrackMutes[3] = (CheckBox) findViewById(R.id.checkBoxMute4);
        checkBoxTrackMutes[4] = (CheckBox) findViewById(R.id.checkBoxMute5);
        checkBoxTrackMutes[5] = (CheckBox) findViewById(R.id.checkBoxMute6);
        checkBoxTrackMutes[6] = (CheckBox) findViewById(R.id.checkBoxMute7);
        checkBoxTrackMutes[7] = (CheckBox) findViewById(R.id.checkBoxMute8);

        checkBoxMidiSteps = new CheckBox[1][16];
        initializeMidiSteps();

        btn_toMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        connectMuteCheckBoxListeners();
//        for(int i = 0; i<8; i++){
//            checkBoxTrackMutes[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    tracks = TrackSingleton.get(getApplicationContext()).getmTracks();
//                tracks.get(i).setIsMuted(isChecked);
//                TrackSingleton.get(getApplicationContext()).updateTrack(i,tracks.get(i));
//                }
//            });
//        }

        btn_play.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.d(TAG, "isPlaying = " + !TrackSingleton.get(getApplicationContext())
                        .getSequencer().isPlaying());

                if (!TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying()) {
                    for (int j = 0; j < 16; j++) {
                        midiSteps[0][j] = checkBoxMidiSteps[0][j].isChecked();
                    }

                    TrackSingleton.get(getApplicationContext()).getSequencer().setMidiSteps(midiSteps);
                    sequencerThread = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            TrackSingleton.get(getApplicationContext()).getSequencer().startPlayback();
                            handler.post(new Runnable() {

                                @Override
                                public void run() {

                                }
                            });
                        }
                    });
                    sequencerThread.start();
                } else {
                    TrackSingleton.get(getApplicationContext()).getSequencer().stopPlayback();
                }
            }
        });

        updateTrackName();

        for (int i = 0; i <16; i++){
            checkBoxMidiSteps[0][i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    for (int i = 0; i<8; i++){
                    for (int j = 0; j < 16; j++) {
                        midiSteps[0][j] = checkBoxMidiSteps[0][j].isChecked();
                    }
//                    }
                    TrackSingleton.get(getApplicationContext()).getSequencer().setMidiSteps(midiSteps);
                }
            });

        }

    }


    public void initializeMidiSteps(){
        //this is the set of all the midi steps in the grid
        checkBoxMidiSteps[0][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_1);
        checkBoxMidiSteps[0][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_2);
        checkBoxMidiSteps[0][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_3);
        checkBoxMidiSteps[0][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_4);
        checkBoxMidiSteps[0][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_5);
        checkBoxMidiSteps[0][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_6);
        checkBoxMidiSteps[0][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_7);
        checkBoxMidiSteps[0][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_8);
        checkBoxMidiSteps[0][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_9);
        checkBoxMidiSteps[0][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_10);
        checkBoxMidiSteps[0][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_11);
        checkBoxMidiSteps[0][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_12);
        checkBoxMidiSteps[0][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_13);
        checkBoxMidiSteps[0][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_14);
        checkBoxMidiSteps[0][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_15);
        checkBoxMidiSteps[0][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_1_16);

    }

    public void connectMuteCheckBoxListeners(){

        checkBoxTrackMutes[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            int i = 0;
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                tracks.get(i).setIsMuted(isChecked);
//                TrackSingleton.get(getApplicationContext()).updateTrack(i,tracks.get(i));
                TrackSingleton.get(getApplicationContext()).getTrack(0).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(1).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(2).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(3).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(4).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[5].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(5).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[6].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(6).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[7].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(7).setIsMuted(isChecked);
            }
        });
    }

//    public void updateTracks(){
//        TrackSingleton.
//    }

    public void updateTrackName(){
        tracks = TrackSingleton.get(this).getmTracks();

        for(int i = 0; i<8; i++){
            textViewTracks[i].setText(tracks.get(i).getName());
        }
    }

}
