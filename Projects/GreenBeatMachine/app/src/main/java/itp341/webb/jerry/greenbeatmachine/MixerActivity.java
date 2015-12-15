package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.util.Log;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 * This holds all of the volume and panning controls for each of the tracks and the master track,
 * to allow for better mix-downs of the beat. The custom class VerticalSlider (used for volume
 * faders) is included here.
 * Mute can also be triggered here.
 */
public class MixerActivity extends Activity {
    public static final String TAG = "itp341.finalProject.tag";

    //widgets for the 8 tracks
    CheckBox [] checkBoxTrackMutes;
    SeekBar [] seekBarTrackPans;
    VerticalSlider [] verticalSlidersTrackVolume;
    TextView [] textViewTrackVolumeFaderLevel;
    TextView [] textViewTrackLabel;
    
    //Master channel Section
    VerticalSlider seekbarMasterVolume;
    TextView textViewMasterVolume;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixer);


        checkBoxTrackMutes = new CheckBox[8];
        seekBarTrackPans = new SeekBar[8];
        verticalSlidersTrackVolume = new VerticalSlider[8];
        textViewTrackVolumeFaderLevel = new TextView[8];
        textViewTrackLabel = new TextView[8];

        initializeChannels();

        
    }

    public void initializeChannels(){
        //Master channel Section
        textViewMasterVolume = (TextView) findViewById(R.id.textViewMasterVolume);
        seekbarMasterVolume = (VerticalSlider) findViewById(R.id.seekbarMasterVolume);
        seekbarMasterVolume.setProgress((int)
                TrackSingleton.get(getApplicationContext()).getMasterVolume());
        textViewMasterVolume.setText(String.valueOf(TrackSingleton.get(getApplicationContext()).getMasterVolume()));


        //Track 1 channel Section
        verticalSlidersTrackVolume[0] = (VerticalSlider) findViewById(R.id.seekBarTrack1Volume);
        seekBarTrackPans[0] = (SeekBar) findViewById(R.id.seekBarTrack1Pan);
        textViewTrackVolumeFaderLevel[0] = (TextView) findViewById(R.id.textViewTrack1VolPercentage);
        checkBoxTrackMutes[0] = (CheckBox) findViewById(R.id.checkBoxMute1);
        seekBarTrackPans[0].setProgress((int) TrackSingleton.get(getApplicationContext()).getTrack(0).getTrackPan());
        verticalSlidersTrackVolume[0].setProgress((int) TrackSingleton.get(getApplicationContext()).getTrack(0).getTrackVolume());
        textViewTrackVolumeFaderLevel[0].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(0).getTrackVolume()));
        textViewTrackLabel[0] = (TextView) findViewById(R.id.textViewTrack1);
        textViewTrackLabel[0].setText(TrackSingleton.get(getApplicationContext()).getTrack(0).getName());



        //Track 2 channel Section
        verticalSlidersTrackVolume[1] = (VerticalSlider) findViewById(R.id.seekBarTrack2Volume);
        seekBarTrackPans[1] = (SeekBar) findViewById(R.id.seekBarTrack2Pan);
        textViewTrackVolumeFaderLevel[1] = (TextView) findViewById(R.id.textViewTrack2VolPercentage);
        checkBoxTrackMutes[1] = (CheckBox) findViewById(R.id.checkBoxMute2);
        seekBarTrackPans[1].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(1).getTrackPan());
        verticalSlidersTrackVolume[1].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(1).getTrackVolume());
        textViewTrackVolumeFaderLevel[1].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(1).getTrackVolume()));
        textViewTrackLabel[1] = (TextView) findViewById(R.id.textViewTrack2);
        textViewTrackLabel[1].setText(TrackSingleton.get(getApplicationContext()).getTrack(1).getName());



        //Track 3 channel Section
        verticalSlidersTrackVolume[2] = (VerticalSlider) findViewById(R.id.seekBarTrack3Volume);
        seekBarTrackPans[2] = (SeekBar) findViewById(R.id.seekBarTrack3Pan);
        textViewTrackVolumeFaderLevel[2] = (TextView) findViewById(R.id.textViewTrack3VolPercentage);
        checkBoxTrackMutes[2] = (CheckBox) findViewById(R.id.checkBoxMute3);
        seekBarTrackPans[2].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(2).getTrackPan());
        verticalSlidersTrackVolume[2].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(2).getTrackVolume());
        textViewTrackVolumeFaderLevel[2].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(2).getTrackVolume()));
        textViewTrackLabel[2] = (TextView) findViewById(R.id.textViewTrack3);
        textViewTrackLabel[2].setText(TrackSingleton.get(getApplicationContext()).getTrack(2).getName());


        //Track 4 channel Section
        verticalSlidersTrackVolume[3] = (VerticalSlider) findViewById(R.id.seekBarTrack4Volume);
        seekBarTrackPans[3] = (SeekBar) findViewById(R.id.seekBarTrack4Pan);
        textViewTrackVolumeFaderLevel[3] = (TextView) findViewById(R.id.textViewTrack4VolPercentage);
        checkBoxTrackMutes[3] = (CheckBox) findViewById(R.id.checkBoxMute4);
        seekBarTrackPans[3].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(3).getTrackPan());
        verticalSlidersTrackVolume[3].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(3).getTrackVolume());
        textViewTrackVolumeFaderLevel[3].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(3).getTrackVolume()));
        textViewTrackLabel[3] = (TextView) findViewById(R.id.textViewTrack4);
        textViewTrackLabel[3].setText(TrackSingleton.get(getApplicationContext()).getTrack(3).getName());


        //Track 5 channel Section
        verticalSlidersTrackVolume[4] = (VerticalSlider) findViewById(R.id.seekBarTrack5Volume);
        seekBarTrackPans[4] = (SeekBar) findViewById(R.id.seekBarTrack5Pan);
        textViewTrackVolumeFaderLevel[4] = (TextView) findViewById(R.id.textViewTrack5VolPercentage);
        checkBoxTrackMutes[4] = (CheckBox) findViewById(R.id.checkBoxMute5);
        seekBarTrackPans[4].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(4).getTrackPan());
        verticalSlidersTrackVolume[4].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(4).getTrackVolume());
        textViewTrackVolumeFaderLevel[4].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(4).getTrackVolume()));
        textViewTrackLabel[4] = (TextView) findViewById(R.id.textViewTrack5);
        textViewTrackLabel[4].setText(TrackSingleton.get(getApplicationContext()).getTrack(4).getName());


        //Track 6 channel Section
        verticalSlidersTrackVolume[5] = (VerticalSlider) findViewById(R.id.seekBarTrack6Volume);
        seekBarTrackPans[5] = (SeekBar) findViewById(R.id.seekBarTrack6Pan);
        textViewTrackVolumeFaderLevel[5] = (TextView) findViewById(R.id.textViewTrack6VolPercentage);
        checkBoxTrackMutes[5] = (CheckBox) findViewById(R.id.checkBoxMute6);
        seekBarTrackPans[5].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(5).getTrackPan());
        verticalSlidersTrackVolume[5].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(5).getTrackVolume());
         textViewTrackVolumeFaderLevel[5].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                 .getTrack(5).getTrackVolume()));
        textViewTrackLabel[5] = (TextView) findViewById(R.id.textViewTrack6);
        textViewTrackLabel[5].setText(TrackSingleton.get(getApplicationContext()).getTrack(0).getName());

        //Track 7 channel Section
        verticalSlidersTrackVolume[6] = (VerticalSlider) findViewById(R.id.seekBarTrack7Volume);
        seekBarTrackPans[6] = (SeekBar) findViewById(R.id.seekBarTrack7Pan);
        textViewTrackVolumeFaderLevel[6] = (TextView) findViewById(R.id.textViewTrack7VolPercentage);
        checkBoxTrackMutes[6] = (CheckBox) findViewById(R.id.checkBoxMute7);
        seekBarTrackPans[6].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(6).getTrackPan());
        verticalSlidersTrackVolume[6].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(6).getTrackVolume());
        textViewTrackVolumeFaderLevel[6].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                .getTrack(6).getTrackVolume()));
        textViewTrackLabel[6] = (TextView) findViewById(R.id.textViewTrack7);
        textViewTrackLabel[6].setText(TrackSingleton.get(getApplicationContext()).getTrack(6).getName());

        //Track 8 channel Section
        verticalSlidersTrackVolume[7] = (VerticalSlider) findViewById(R.id.seekBarTrack8Volume);
        seekBarTrackPans[7] = (SeekBar) findViewById(R.id.seekBarTrack8Pan);
        textViewTrackVolumeFaderLevel[7] = (TextView) findViewById(R.id.textViewTrack8VolPercentage);
        checkBoxTrackMutes[7] = (CheckBox) findViewById(R.id.checkBoxMute8);
        seekBarTrackPans[7].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(7).getTrackPan());
        verticalSlidersTrackVolume[7].setProgress((int)
                TrackSingleton.get(getApplicationContext()).getTrack(7).getTrackVolume());
         textViewTrackVolumeFaderLevel[7].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                 .getTrack(7).getTrackVolume()));
        textViewTrackLabel[7] = (TextView) findViewById(R.id.textViewTrack8);
        textViewTrackLabel[7].setText(TrackSingleton.get(getApplicationContext()).getTrack(7).getName());

        connectMuteCheckBoxListeners();
        connectSeekBars();
    }

    //Connects all the sliders and faders
    public void connectSeekBars(){

        seekbarMasterVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TrackSingleton.get(getApplicationContext())
                        .setMasterVolume(progress);

                textViewMasterVolume.setText(String.valueOf(TrackSingleton.get(getApplicationContext()).getMasterVolume()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //This connects the volume faders and pan seekbars for each of the 8 tracks
        for(int i = 0; i<8; i++){
            final int finali = i;
            verticalSlidersTrackVolume[finali].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    TrackSingleton.get(getApplicationContext())
                            .getTrack(finali).setTrackVolume(progress);

                    textViewTrackVolumeFaderLevel[finali].setText(String.valueOf(TrackSingleton.get(getApplicationContext())
                            .getTrack(finali).getTrackVolume()));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            seekBarTrackPans[finali].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                    TrackSingleton.get(getApplicationContext()).getTrack(finali).setTrackPan(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

        }

    }


    //this connects the mute checkboxes
    public void connectMuteCheckBoxListeners(){

        checkBoxTrackMutes[0].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(0).setIsMuted(isChecked);
            }
        });
        checkBoxTrackMutes[0].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(0).isMuted());

        checkBoxTrackMutes[1].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(1).setIsMuted(isChecked);
            }
        });
        checkBoxTrackMutes[1].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(1).isMuted());

        checkBoxTrackMutes[2].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(2).setIsMuted(isChecked);
            }
        });
        checkBoxTrackMutes[2].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(2).isMuted());

        checkBoxTrackMutes[3].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TrackSingleton.get(getApplicationContext()).getTrack(3).setIsMuted(isChecked);
            }
        });
        checkBoxTrackMutes[3].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(3).isMuted());

       checkBoxTrackMutes[4].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               TrackSingleton.get(getApplicationContext()).getTrack(4).setIsMuted(isChecked);
           }
       });
       checkBoxTrackMutes[4].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(4).isMuted());

       checkBoxTrackMutes[5].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               TrackSingleton.get(getApplicationContext()).getTrack(5).setIsMuted(isChecked);
           }
       });
        checkBoxTrackMutes[5].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(5).isMuted());


       checkBoxTrackMutes[6].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               TrackSingleton.get(getApplicationContext()).getTrack(6).setIsMuted(isChecked);
           }
       });
       checkBoxTrackMutes[6].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(6).isMuted());

       checkBoxTrackMutes[7].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               TrackSingleton.get(getApplicationContext()).getTrack(7).setIsMuted(isChecked);
           }
       });
        checkBoxTrackMutes[7].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(7).isMuted());

    }

    //called to refresh the ui if the user switches between the 3 activities after they are created
    @Override
    public void onResume() {
        super.onResume();

        seekbarMasterVolume.setProgress((int) TrackSingleton.get(getApplicationContext()).getMasterVolume());
        Log.d(TAG, "mixer called onResume");

        for(int i = 0; i<8; i++) {
            final int finali = i;
            checkBoxTrackMutes[finali].setChecked(TrackSingleton.get(getApplicationContext()).getTrack(finali).isMuted());
        }
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
            if(!TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying()) {
                TrackSingleton.get(getApplicationContext()).togglePlay();
            }
        }

        if(id == R.id.action_pause_track){
            if(TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying()) {
                TrackSingleton.get(getApplicationContext()).togglePlay();
            }
        }

        if (id == R.id.action_to_beat_pad){
            setResult(RESULT_OK);
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

        if(id == R.id.action_go_to_midi_sequencer){
            Intent i = new Intent(getApplicationContext(), MidiSequencerActivity.class);
            startActivityForResult(i, 2);
            finish();
        }

        if(id == R.id.action_toggle_metronome){
            TrackSingleton.get(getApplicationContext()).getSequencer().toggleMetronome();
        }

        return super.onOptionsItemSelected(item);
    }


}
