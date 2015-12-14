package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import itp341.webb.jerry.greenbeatmachine.model.Track;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;


public class MainActivity extends Activity {
    public static final String TAG = "itp341.finalProject.tag";
    private MediaPlayer mp = new MediaPlayer();


    double masterVolume;
//    Button btn_to_midi_sequencer;
    EditText editTextBPM;
    ListView soundList;

    SeekBar seekBarMasterVolume;
    ArrayList<Track> tracks;
    private RecyclerView beatPadLayout;
    private PadAdapter padAdapter;
    Switch playingIndicator;

    Cursor c; //CursorWrapper
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        beatPadLayout = (RecyclerView) findViewById(R.id.beatPadLayout);
        padAdapter = new PadAdapter(getApplicationContext(),getData());
        beatPadLayout.setAdapter(padAdapter);
        beatPadLayout.setLayoutManager(new GridLayoutManager(this, 4));

//        btn_to_midi_sequencer = (Button) findViewById(R.id.buttonToMidiSequencer);


        editTextBPM = (EditText) findViewById(R.id.editTextBPM);
        playingIndicator = (Switch) findViewById(R.id.playingIndicator);
        soundList = (ListView) findViewById(R.id.soundsList);

//        btn_to_midi_sequencer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(), MidiSequencerActivity.class);
////                TrackSingleton.get(getApplicationContext()).setMasterVolume(masterVolume);
//                startActivityForResult(i, 0);
//            }
//        });

        addSoundsForTest();
        updateView();

        masterVolume =  TrackSingleton.get(this).getMasterVolume();
        seekBarMasterVolume =  (SeekBar) findViewById(R.id.seekbarMasterVolume);
        seekBarMasterVolume.setProgress(80);
        seekBarMasterVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TrackSingleton.get(getApplicationContext())
                        .setMasterVolume(Double.parseDouble(String.valueOf(progress)) / 10);

//                Log.d(TAG,"" + masterVolume);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        editTextBPM.setText(String.valueOf(TrackSingleton.get(getApplicationContext()).getBpm()));
        editTextBPM.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                TrackSingleton.get(getApplicationContext()).setBpm(Integer.parseInt(editTextBPM.getText().toString()));

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
    public void addSoundsForTest(){
        padAdapter.notifyDataSetChanged();
//        bmb_k_id = samplePool.load(this, R.raw.ac_k, 1);
//        phn_clp_id = samplePool.load(this, R.raw.phn_clp,1);
//        dry_ohh_cra_id = samplePool.load(this, R.raw.dry_ohh_cra,1);
//
//
//        Sound so = new Sound("bmb_k",bmb_k_id,"kick");
//        SoundsSingleton.get(this).addSound(so);
//
//        Track t0 = new Track("track 1", 0.8, 0.5);
//        t0.setTrackSample(so);
//        TrackSingleton.get(this).updateTrack(0, t0);
//
//        Sound s1 = new Sound("phn_clp", phn_clp_id, "clap");
//        SoundsSingleton.get(this).addSound(s1);
//
//        Track t1 = new Track("track 2", 0.8, 0.5);
//        t1.setTrackSample(s1);
//        TrackSingleton.get(this).updateTrack(1, t1);
//
//        Sound s2 = new Sound("dry_ohh_cra", dry_ohh_cra_id, "crash");
//        SoundsSingleton.get(this).addSound(s2);
//
//        Track t2 = new Track("track 3", 0.8, 0.5);
//        t2.setTrackSample(s2);
//        TrackSingleton.get(this).updateTrack(2, t2);


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
            TrackSingleton.get(getApplicationContext()).togglePlay();
            playingIndicator.setChecked(true);
        }

        if(id == R.id.action_pause_track){
            TrackSingleton.get(getApplicationContext()).togglePlay();
            playingIndicator.setChecked(false);
        }

        if(id == R.id.action_go_to_midi_sequencer){
            Intent i = new Intent(getApplicationContext(), MidiSequencerActivity.class);
            startActivityForResult(i, 0);
        }

        if(id == R.id.action_go_to_mixer){
            Intent i = new Intent(getApplicationContext(), MixerActivity.class);
            startActivityForResult(i, 0);
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

        seekBarMasterVolume.setProgress((int) (TrackSingleton.get(getApplicationContext()).getMasterVolume()*10));
        playingIndicator.setChecked(TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            padAdapter.notifyDataSetChanged();
        }

        seekBarMasterVolume.setProgress((int) (TrackSingleton.get(getApplicationContext()).getMasterVolume()*10));
        playingIndicator.setChecked(TrackSingleton.get(getApplicationContext()).getSequencer().isPlaying());
    }

    //    private class ButtonListener implements View.OnClickListener{
////        float outputVolume = (float)masterVolume;
//
//        @Override
//        public void onClick(View v) {
//            Log.d(TAG,"outputVolume = "+ masterVolume);
//
//            switch (v.getId()){
//                case R.id.btn_pad_0:
//                    playSample(0); // starts at 1
//                    trigger(0);
//                    break;
//                case R.id.btn_pad_1:
//                    playSample(1);
//                    trigger(1);
//
//                    break;
//                case R.id.btn_pad_2:
//                    playSample(2);
//                    trigger(2);
//
//                    break;
//                case R.id.btn_pad_3:
//                    playSample(3);
//                    break;
//                case R.id.btn_pad_4:
//                    playSample(4);
//                    break;
//                case R.id.btn_pad_5:
//                    playSample(5);
//                    break;
//                case R.id.btn_pad_6:
//                    playSample(6);
//                    break;
//                case R.id.btn_pad_7:
//                    playSample(7);
//                    break;
//            }
//        }
//    }
}
