package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.model.Track;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;
import itp341.webb.jerry.greenbeatmachine.MainActivity;
/**
 * Created by jerrywebb on 12/10/15.
 */
public class MidiSequencerActivity extends Activity{
    public static final String TAG = "itp341.webb.jerry.finalProject.tag";
    Activity MainActivity;
    Button btn_toMainActivity;
    TextView [] textViewTracks;
    CheckBox [] checkBoxTrackMutes;
    CheckBox [][] checkBoxMidiSteps;
    ArrayList<Track> tracks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midi_sequencer);
        tracks = TrackSingleton.get(this).getmTracks();
        MainActivity = new MainActivity();
        btn_toMainActivity = (Button) findViewById(R.id.button2);

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

        updateTrackName();

        for (int i = 0; i <16; i++){
            checkBoxMidiSteps[0][i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ;
                }
            });

        }

//        checkBoxMidiSteps[0][0].setOnCheckedChangeListener();
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

//        for(int i = 0; i <16;i++){
//           String step =  "checkBoxMidiStep_1_" + i;
//        }
    }

    public void updateTrackName(){
        tracks = TrackSingleton.get(this).getmTracks();

        for(int i = 0; i<8; i++){
            textViewTracks[i].setText(tracks.get(i).getName());
        }
    }
}
