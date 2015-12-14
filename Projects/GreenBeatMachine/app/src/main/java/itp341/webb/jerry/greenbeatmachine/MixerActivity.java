package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 */
public class MixerActivity extends Activity {
    public static final String TAG = "itp341.finalProject.tag";

    //widgets for the 8 tracks
    CheckBox [] checkBoxTrackMutes;
    SeekBar [] seekBarTrackPans;
    VerticalSlider [] verticalSlidersTrackVolume;
    TextView [] textViewTrackVolumeFaderLevel;
    
    //Master channel Section
    VerticalSlider seekbarMasterVolume;
    TextView textViewMasterVolume;
    ProgressBar progressBarMasterVolumeLeft;
    ProgressBar progressBarMasterVolumeRight;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixer);


        checkBoxTrackMutes = new CheckBox[8];
        seekBarTrackPans = new SeekBar[8];
        verticalSlidersTrackVolume = new VerticalSlider[8];
        textViewTrackVolumeFaderLevel = new TextView[8];
        
        initializeChannels();

        
    }

    public void initializeChannels(){
        //Master channel Section
        textViewMasterVolume = (TextView) findViewById(R.id.textViewMasterVolume);
        progressBarMasterVolumeLeft = (ProgressBar) findViewById(R.id.progressBarMasterVolLeft);
        progressBarMasterVolumeRight = (ProgressBar) findViewById(R.id.progressBarMasterVolRight);
        seekbarMasterVolume = (VerticalSlider) findViewById(R.id.seekbarMasterVolume);
        seekbarMasterVolume.setProgress((int)
                TrackSingleton.get(getApplicationContext()).getMasterVolume());


        //Track 1 channel Section
        verticalSlidersTrackVolume[0] = (VerticalSlider) findViewById(R.id.seekBarTrack1Volume);
        seekBarTrackPans[0] = (SeekBar) findViewById(R.id.seekBarTrack1Pan);
        textViewTrackVolumeFaderLevel[0] = (TextView) findViewById(R.id.textViewTrack1VolPercentage);
        checkBoxTrackMutes[0] = (CheckBox) findViewById(R.id.checkBoxMute1);
        seekBarTrackPans[0].setProgress((int) TrackSingleton.get(getApplicationContext()).getTrack(0).getTrackPan());
        verticalSlidersTrackVolume[0].setProgress((int) TrackSingleton.get(getApplicationContext()).getTrack(0).getTrackVolume());
        textViewTrackVolumeFaderLevel[0].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(0).getTrackVolume()));



        //Track 2 channel Section
        verticalSlidersTrackVolume[1] = (VerticalSlider) findViewById(R.id.seekBarTrack2Volume);
        seekBarTrackPans[1] = (SeekBar) findViewById(R.id.seekBarTrack2Pan);
        textViewTrackVolumeFaderLevel[1] = (TextView) findViewById(R.id.textViewTrack2VolPercentage);
        checkBoxTrackMutes[1] = (CheckBox) findViewById(R.id.checkBoxMute2);
        seekBarTrackPans[1].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(1).getTrackPan());

        verticalSlidersTrackVolume[1].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(1).getTrackVolume());



        //Track 3 channel Section
        verticalSlidersTrackVolume[2] = (VerticalSlider) findViewById(R.id.seekBarTrack3Volume);
        seekBarTrackPans[2] = (SeekBar) findViewById(R.id.seekBarTrack3Pan);
        textViewTrackVolumeFaderLevel[2] = (TextView) findViewById(R.id.textViewTrack3VolPercentage);
        checkBoxTrackMutes[2] = (CheckBox) findViewById(R.id.checkBoxMute3);
        seekBarTrackPans[2].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(2).getTrackPan());
        verticalSlidersTrackVolume[2].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(2).getTrackVolume());


        connectMuteCheckBoxListeners();
        connectSeekBars();
    }
    public void connectSeekBars(){

        seekbarMasterVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TrackSingleton.get(getApplicationContext())
                        .setMasterVolume(progress);

                textViewMasterVolume.setText(String.valueOf(TrackSingleton.get(getApplicationContext()).getMasterVolume()));
                progressBarMasterVolumeLeft.setProgress((int) TrackSingleton
                        .get(getApplicationContext()).getMasterVolume());
                progressBarMasterVolumeRight.setProgress((int) TrackSingleton
                        .get(getApplicationContext()).getMasterVolume());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Channel section for track 1
        verticalSlidersTrackVolume[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TrackSingleton.get(getApplicationContext())
                        .getTrack(0).setTrackVolume(progress);

                textViewTrackVolumeFaderLevel[0].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                        .getTrack(0).getTrackVolume()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarTrackPans[0].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TrackSingleton.get(getApplicationContext()).getTrack(0).setTrackPan(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Channel section for track 2
        verticalSlidersTrackVolume[1].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TrackSingleton.get(getApplicationContext())
                        .getTrack(1).setTrackVolume(progress);

                textViewTrackVolumeFaderLevel[1].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                        .getTrack(1).getTrackVolume()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarTrackPans[1].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TrackSingleton.get(getApplicationContext()).getTrack(1).setTrackPan(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //Channel section for track 2
        verticalSlidersTrackVolume[2].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TrackSingleton.get(getApplicationContext())
                        .getTrack(2).setTrackVolume(progress);//Double.parseDouble(String.valueOf(progress)) / 10);

                textViewTrackVolumeFaderLevel[2].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                        .getTrack(2).getTrackVolume()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarTrackPans[2].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                TrackSingleton.get(getApplicationContext()).getTrack(2).setTrackPan(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    //this connects the mute 
    public void connectMuteCheckBoxListeners(){

        checkBoxTrackMutes[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(0).setIsMuted(isChecked);
            }
        });

        checkBoxTrackMutes[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(1).setIsMuted(isChecked);
            }
        });
//
        checkBoxTrackMutes[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(2).setIsMuted(isChecked);
            }
        });
//
//        checkBoxTrackMutes[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                TrackSingleton.get(getApplicationContext()).getTrack(3).setIsMuted(isChecked);
//            }
//        });
//
//        checkBoxTrackMutes[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                TrackSingleton.get(getApplicationContext()).getTrack(4).setIsMuted(isChecked);
//            }
//        });
//
//        checkBoxTrackMutes[5].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                TrackSingleton.get(getApplicationContext()).getTrack(5).setIsMuted(isChecked);
//            }
//        });
//
//        checkBoxTrackMutes[6].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                TrackSingleton.get(getApplicationContext()).getTrack(6).setIsMuted(isChecked);
//            }
//        });
//
//        checkBoxTrackMutes[7].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                TrackSingleton.get(getApplicationContext()).getTrack(7).setIsMuted(isChecked);
//            }
//        });
    }

    //called to refresh the ui if the user switches between the 3 activities after they are created
    @Override
    public void onResume() {
        super.onResume();

        seekbarMasterVolume.setProgress((int) TrackSingleton.get(getApplicationContext()).getMasterVolume());

        //Updates channel 1 UI
//        verticalsetProgress((int) (TrackSingleton.get(getApplicationContext()).getTrack(0).getTrackVolume() * 100));
//        seekBarTrackPans[0].setProgress((int)
//                (TrackSingleton.get(getApplicationContext()).getTrack(0).getTrackPan() * 10));
//        checkBoxTrackMutes[0].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(0).isMuted());


        //Updates Channel 2 UI
//



//        checkBoxTrackMutes[3].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(3).isMuted());
//        checkBoxTrackMutes[4].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(4).isMuted());
//        checkBoxTrackMutes[5].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(5).isMuted());
//        checkBoxTrackMutes[6].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(6).isMuted());
//        checkBoxTrackMutes[7].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(7).isMuted());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.play_menu, menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);
//        getMenuInflater().inflate(R.menu.play_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_play_track){
            TrackSingleton.get(getApplicationContext()).getSequencer().startPlayback();
        }

        if(id == R.id.action_pause_track){
            TrackSingleton.get(getApplicationContext()).getSequencer().startPlayback();
        }

        if (id == R.id.action_to_beat_pad){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(i, 2);
        }

        if(id == R.id.action_go_to_midi_sequencer){
            Intent i = new Intent(getApplicationContext(), MidiSequencerActivity.class);
            startActivityForResult(i, 2);
        }

        if(id == R.id.action_toggle_metronome){
            TrackSingleton.get(getApplicationContext()).getSequencer().toggleMetronome();
        }

        return super.onOptionsItemSelected(item);
    }


}
