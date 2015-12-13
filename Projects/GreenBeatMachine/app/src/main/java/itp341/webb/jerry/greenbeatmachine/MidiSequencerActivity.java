package itp341.webb.jerry.greenbeatmachine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


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
//    private RecyclerView midiStepLayout;
//    private MidiStepAdapter midiStepAdapter;
    private boolean midiSteps[][]; //These correspond to the midi step checkboxes in the sequencer
    int bpm;
    Handler handler;                //This is used to access the sequencer thread when it's locked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midi_sequencer);

//        midiStepLayout = (RecyclerView) findViewById(R.id.midiSequencerLayout);
//        midiStepAdapter = new MidiStepAdapter(getApplicationContext(),getData());
//        midiStepLayout.setAdapter(midiStepAdapter);
//        midiStepLayout.setLayoutManager(new GridLayoutManager(this, 16));

        handler = new Handler();
        bpm = 80;
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

        checkBoxMidiSteps = new CheckBox[8][16];
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
//                    for (int i = 0; i<3;i++) {
//                        for (int j = 0; j < 16; j++) {
//                            midiSteps[i][j] = checkBoxMidiSteps[i][j].isChecked();
//                        }
//                    }
                    addStepsToSequncerController();

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

        for (int i = 0; i<8;i++) {
            for (int j = 0; j < 16; j++) {
                checkBoxMidiSteps[i][j].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        addStepsToSequncerController();
//                        for (int i = 0; i<2;i++) {
//                            for (int j = 0; j < 16; j++) {
//                                midiSteps[i][j] = checkBoxMidiSteps[i][j].isChecked();
//                            }
//                        }
                        TrackSingleton.get(getApplicationContext()).getSequencer().setMidiSteps(midiSteps);
                    }
                });

            }
        }

    }

    public void addStepsToSequncerController(){
        for (int i = 0; i<8;i++) {
            for (int j = 0; j < 16; j++) {
                midiSteps[i][j] = checkBoxMidiSteps[i][j].isChecked();
            }
        }
    }

    public void initializeMidiSteps(){
        //this connects the 2D array of all the midi steps to the checkBoxes in the View
        //first row for track 1
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

        //second row for track 2
        checkBoxMidiSteps[1][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_1);
        checkBoxMidiSteps[1][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_2);
        checkBoxMidiSteps[1][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_3);
        checkBoxMidiSteps[1][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_4);
        checkBoxMidiSteps[1][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_5);
        checkBoxMidiSteps[1][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_6);
        checkBoxMidiSteps[1][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_7);
        checkBoxMidiSteps[1][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_8);
        checkBoxMidiSteps[1][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_9);
        checkBoxMidiSteps[1][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_10);
        checkBoxMidiSteps[1][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_11);
        checkBoxMidiSteps[1][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_12);
        checkBoxMidiSteps[1][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_13);
        checkBoxMidiSteps[1][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_14);
        checkBoxMidiSteps[1][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_15);
        checkBoxMidiSteps[1][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_2_16);

        //second row for track 3
        checkBoxMidiSteps[2][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_1);
        checkBoxMidiSteps[2][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_2);
        checkBoxMidiSteps[2][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_3);
        checkBoxMidiSteps[2][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_4);
        checkBoxMidiSteps[2][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_5);
        checkBoxMidiSteps[2][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_6);
        checkBoxMidiSteps[2][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_7);
        checkBoxMidiSteps[2][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_8);
        checkBoxMidiSteps[2][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_9);
        checkBoxMidiSteps[2][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_10);
        checkBoxMidiSteps[2][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_11);
        checkBoxMidiSteps[2][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_12);
        checkBoxMidiSteps[2][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_13);
        checkBoxMidiSteps[2][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_14);
        checkBoxMidiSteps[2][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_15);
        checkBoxMidiSteps[2][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_3_16);

        //second row for track 4
        checkBoxMidiSteps[3][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_1);
        checkBoxMidiSteps[3][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_2);
        checkBoxMidiSteps[3][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_3);
        checkBoxMidiSteps[3][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_4);
        checkBoxMidiSteps[3][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_5);
        checkBoxMidiSteps[3][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_6);
        checkBoxMidiSteps[3][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_7);
        checkBoxMidiSteps[3][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_8);
        checkBoxMidiSteps[3][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_9);
        checkBoxMidiSteps[3][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_10);
        checkBoxMidiSteps[3][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_11);
        checkBoxMidiSteps[3][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_12);
        checkBoxMidiSteps[3][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_13);
        checkBoxMidiSteps[3][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_14);
        checkBoxMidiSteps[3][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_15);
        checkBoxMidiSteps[3][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_4_16);

        //second row for track 5
        checkBoxMidiSteps[4][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_1);
        checkBoxMidiSteps[4][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_2);
        checkBoxMidiSteps[4][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_3);
        checkBoxMidiSteps[4][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_4);
        checkBoxMidiSteps[4][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_5);
        checkBoxMidiSteps[4][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_6);
        checkBoxMidiSteps[4][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_7);
        checkBoxMidiSteps[4][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_8);
        checkBoxMidiSteps[4][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_9);
        checkBoxMidiSteps[4][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_10);
        checkBoxMidiSteps[4][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_11);
        checkBoxMidiSteps[4][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_12);
        checkBoxMidiSteps[4][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_13);
        checkBoxMidiSteps[4][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_14);
        checkBoxMidiSteps[4][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_15);
        checkBoxMidiSteps[4][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_5_16);

        //second row for track 6
        checkBoxMidiSteps[5][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_1);
        checkBoxMidiSteps[5][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_2);
        checkBoxMidiSteps[5][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_3);
        checkBoxMidiSteps[5][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_4);
        checkBoxMidiSteps[5][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_5);
        checkBoxMidiSteps[5][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_6);
        checkBoxMidiSteps[5][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_7);
        checkBoxMidiSteps[5][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_8);
        checkBoxMidiSteps[5][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_9);
        checkBoxMidiSteps[5][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_10);
        checkBoxMidiSteps[5][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_11);
        checkBoxMidiSteps[5][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_12);
        checkBoxMidiSteps[5][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_13);
        checkBoxMidiSteps[5][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_14);
        checkBoxMidiSteps[5][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_15);
        checkBoxMidiSteps[5][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_6_16);

        //second row for track 7
        checkBoxMidiSteps[6][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_1);
        checkBoxMidiSteps[6][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_2);
        checkBoxMidiSteps[6][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_3);
        checkBoxMidiSteps[6][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_4);
        checkBoxMidiSteps[6][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_5);
        checkBoxMidiSteps[6][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_6);
        checkBoxMidiSteps[6][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_7);
        checkBoxMidiSteps[6][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_8);
        checkBoxMidiSteps[6][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_9);
        checkBoxMidiSteps[6][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_10);
        checkBoxMidiSteps[6][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_11);
        checkBoxMidiSteps[6][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_12);
        checkBoxMidiSteps[6][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_13);
        checkBoxMidiSteps[6][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_14);
        checkBoxMidiSteps[6][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_15);
        checkBoxMidiSteps[6][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_7_16);

        //second row for track 8
        checkBoxMidiSteps[7][0] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_1);
        checkBoxMidiSteps[7][1] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_2);
        checkBoxMidiSteps[7][2] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_3);
        checkBoxMidiSteps[7][3] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_4);
        checkBoxMidiSteps[7][4] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_5);
        checkBoxMidiSteps[7][5] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_6);
        checkBoxMidiSteps[7][6] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_7);
        checkBoxMidiSteps[7][7] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_8);
        checkBoxMidiSteps[7][8] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_9);
        checkBoxMidiSteps[7][9] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_10);
        checkBoxMidiSteps[7][10] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_11);
        checkBoxMidiSteps[7][11] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_12);
        checkBoxMidiSteps[7][12] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_13);
        checkBoxMidiSteps[7][13] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_14);
        checkBoxMidiSteps[7][14] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_15);
        checkBoxMidiSteps[7][15] = (CheckBox) findViewById(R.id.checkBoxMidiStep_8_16);
    }

    public static List<MidiStepInformation>getData(){

        List<MidiStepInformation> data = new ArrayList<>();

        for (int i = 0; i<8;i++) {
            for (int j = 0; j < 16; j++) {
                MidiStepInformation current =  new MidiStepInformation();
                current.isChecked = false;
//                        j + 16*i;
                data.add(current);
            }
        }
        return data;
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
