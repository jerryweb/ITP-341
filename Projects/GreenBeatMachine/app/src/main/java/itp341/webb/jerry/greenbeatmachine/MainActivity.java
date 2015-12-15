package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import itp341.webb.jerry.greenbeatmachine.model.Sound;
import itp341.webb.jerry.greenbeatmachine.model.SoundsSingleton;
import itp341.webb.jerry.greenbeatmachine.model.Track;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;


public class MainActivity extends Activity {
    public static final String TAG = "itp341.finalProject.tag";


    double masterVolume;
    EditText editTextBPM;
    ListView soundList;             //List that shows
    SoundAdapter soundAdapter;

    SeekBar seekBarMasterVolume;
    ArrayList<Track> tracks;
    ArrayList<Sound> soundBank;
    private RecyclerView beatPadLayout;
    private PadAdapter padAdapter;
    Switch playingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tracks = TrackSingleton.get(this).getmTracks();
        soundBank = SoundsSingleton.get(getApplicationContext()).getSoundList();


        beatPadLayout = (RecyclerView) findViewById(R.id.beatPadLayout);
        padAdapter = new PadAdapter(getApplicationContext(),getData());
        beatPadLayout.setAdapter(padAdapter);
        beatPadLayout.setLayoutManager(new GridLayoutManager(this, 4));

        editTextBPM = (EditText) findViewById(R.id.editTextBPM);
        playingIndicator = (Switch) findViewById(R.id.playingIndicator);
        soundList = (ListView) findViewById(R.id.soundsList);

        soundAdapter = new SoundAdapter(getApplicationContext(),soundBank);
        soundList.setAdapter(soundAdapter);

        //This si the
        soundList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getChoiceText;
                padAdapter.setPadSound(position, soundBank.get(position).getName());
                getChoiceText = (TextView)view.findViewById(R.id.textViewSoundName);


                soundAdapter.notifyDataSetChanged();
                soundList.deferNotifyDataSetChanged();


                TrackSingleton.get(getApplicationContext())
                        .loadSample(padAdapter.getLastPadClicked()
                                , getChoiceText.getText().toString());
                return false;
            }
        });


        updateView();

        masterVolume =  TrackSingleton.get(this).getMasterVolume();

        //Allows the user to control the master track volume output from main screen
        seekBarMasterVolume =  (SeekBar) findViewById(R.id.seekbarMasterVolume);
        seekBarMasterVolume.setProgress((int) TrackSingleton.get(getApplicationContext()).getMasterVolume());
        seekBarMasterVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TrackSingleton.get(getApplicationContext())
                        .setMasterVolume(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editTextBPM.setText(String.valueOf(TrackSingleton.get(getApplicationContext()).getBpm()));

        //The user must enter an integer beats per minute value between 50 and 140
        //This will set the tempo according to the users input
        editTextBPM.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(50 <= Integer.parseInt(editTextBPM.getText().toString())
                        && Integer.parseInt(editTextBPM.getText().toString()) <= 140) {
                    TrackSingleton.get(getApplicationContext())
                            .setBpm(Integer.parseInt(editTextBPM.getText().toString()));
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Pleae enter a valid Integer BPM", Toast.LENGTH_SHORT).show();
                }

                return false;
            }
        });

    }



    public static List<PadLayoutInformation> getData(){

        List<PadLayoutInformation> data = new ArrayList<>();
        int[] buttonIds = new int[8];
        String[] trackNames = new String[8];
        for (int i = 0; i<8; i++) {

            buttonIds[i] = i;
            trackNames[i] = "track " +i ;
        }
        for (int i = 0; i<buttonIds.length; i++) {

            PadLayoutInformation current = new PadLayoutInformation();
            current.padId = buttonIds[i];
            current.trackName = trackNames[i];
            data.add(current);
        }


        return data;

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

        if (id == R.id.action_save){

        }

        if (id == R.id.action_play_track){

            if(!TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying()) {
                TrackSingleton.get(getApplicationContext()).togglePlay();
                playingIndicator.setChecked(true);
            }

        }

        if(id == R.id.action_pause_track){
            if(TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying()) {
                TrackSingleton.get(getApplicationContext()).togglePlay();
                playingIndicator.setChecked(false);
            }
        }

        if(id == R.id.action_go_to_midi_sequencer){
            Intent i = new Intent(getApplicationContext(), MidiSequencerActivity.class);
            startActivityForResult(i, 0);
        }

        if(id == R.id.action_go_to_mixer){
            Intent i = new Intent(getApplicationContext(), MixerActivity.class);
            startActivityForResult(i, 1);
        }

        if(id == R.id.action_toggle_metronome){
            TrackSingleton.get(getApplicationContext()).getSequencer().toggleMetronome();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateView(){
        tracks = TrackSingleton.get(this).getmTracks();
        masterVolume = TrackSingleton.get(this).getMasterVolume();

    }

    @Override
    public void onResume() {
        super.onResume();
//        padAdapter.notifyDataSetChanged();
        soundAdapter.notifyDataSetChanged();
        seekBarMasterVolume.setProgress((int) TrackSingleton.get(getApplicationContext()).getMasterVolume());
        playingIndicator.setChecked(TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying());
    }
//
//    @Override
//    public void onPause(){
//        super.onPause();
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "Worked : " + requestCode);
//            padAdapter.notifyDataSetChanged();

        }
        soundAdapter.notifyDataSetChanged();
        seekBarMasterVolume.setProgress((int) TrackSingleton.get(getApplicationContext()).getMasterVolume());
        playingIndicator.setChecked(TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying());
    }

}
