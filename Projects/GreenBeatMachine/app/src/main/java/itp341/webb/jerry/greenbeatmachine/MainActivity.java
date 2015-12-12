package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.model.Sound;
import itp341.webb.jerry.greenbeatmachine.model.SoundsSingleton;
import itp341.webb.jerry.greenbeatmachine.model.Track;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;


public class MainActivity extends Activity {
    public static final String TAG = "itp341.finalProject.tag";
    private MediaPlayer mp = new MediaPlayer();

    //used to play sounds simultaneously... best used for sound files < 1MB
//    private SoundPool samplePool;

    double masterVolume;
    Button padArray[];
    Button btn_to_midi_sequencer;

    ListView soundList;

    SeekBar seekBarMasterVolume;
    ButtonListener buttonListener;
    ArrayList<Track> tracks;

    Cursor c; //CursorWrapper
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        padArray = new Button[8];
        btn_to_midi_sequencer = (Button) findViewById(R.id.buttonToMidiSequencer);


        padArray[0] = (Button) findViewById(R.id.btn_pad_0);
        padArray[1] = (Button) findViewById(R.id.btn_pad_1);
        padArray[2] = (Button) findViewById(R.id.btn_pad_2);
        padArray[3] = (Button) findViewById(R.id.btn_pad_3);
        padArray[4] = (Button) findViewById(R.id.btn_pad_4);
        padArray[5] = (Button) findViewById(R.id.btn_pad_5);
        padArray[6] = (Button) findViewById(R.id.btn_pad_6);
        padArray[7] = (Button) findViewById(R.id.btn_pad_7);

        soundList = (ListView) findViewById(R.id.soundsList);
        buttonListener = new ButtonListener();

        for (int i =0; i<8;i++){
            padArray[i].setOnClickListener(buttonListener);
        }

        btn_to_midi_sequencer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MidiSequencerActivity.class);
                TrackSingleton.get(getApplicationContext()).setMasterVolume(masterVolume);
                startActivity(i);
            }
        });

        //sets attributes so as to better recognize the audio samples
//        AudioAttributes sampleAttributes = new AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                .setUsage(AudioAttributes.USAGE_MEDIA)
//                .build();
//
//
//        //Streams are the number of sounds that can be played simultaneously
//        samplePool = new SoundPool.Builder().setMaxStreams(25)
//                .setAudioAttributes(sampleAttributes)
//                .build();//(20, AudioManager.STREAM_MUSIC,0); //int maxStreams, int streamType,int srcQuality

        addSoundsForTest();
        updateView();


        masterVolume =  TrackSingleton.get(this).getMasterVolume();
        seekBarMasterVolume =  (SeekBar) findViewById(R.id.seekbarMasterVolume);

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

    }

    public void addSoundsForTest(){
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



//       String[] f = {TABLE_SOUNDS.KEY_ID};
//
////        from = f;
//
//        int[] to = {android.R.id.text1};

    }

//    public void playSample( int sampleId){
//        samplePool.play(sampleId,(float) masterVolume, (float) masterVolume,1,0,1);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void updateView(){
        tracks = TrackSingleton.get(this).getmTracks();
        masterVolume = TrackSingleton.get(this).getMasterVolume();

        for(int i = 0; i <8; i++) {
            padArray[i].setText(tracks.get(i).getName());
        }
    }

    public void playSample(int id){
        TrackSingleton.get(this).playSound(id);
//        samplePool.play(tracks.get(id).getCurrentSampleId(),
//                (float) masterVolume, (float) masterVolume, 1, 0,1);


//        int[] f = {TABLE_SOUNDS.COLUMN_ID};
////        int[] to = {android.R.id.text1};
//
////        Log.d(TAG, "long id is: " + to[0]);
//        c = SoundsSingleton.get(this).getSound(id);
//
//        if(c != null) {
//            if (c.moveToFirst()) {
//                Log.d(TAG, "id is: " + c.getInt(TABLE_SOUNDS.COLUMN_SOUNDID) +1 + ", and wav id is: " + phn_clp_id);
//                Toast.makeText(getApplicationContext(), "soundId is: " + c.getInt(TABLE_SOUNDS.COLUMN_SOUNDID), Toast.LENGTH_SHORT).show();
//                samplePool.play(c.getInt(TABLE_SOUNDS.COLUMN_SOUNDID)+1,
//                        (float) masterVolume, (float) masterVolume, 1, 0,1);
//            }
//        }
    }


    private class ButtonListener implements View.OnClickListener{
//        float outputVolume = (float)masterVolume;

        @Override
        public void onClick(View v) {
            Log.d(TAG,"outputVolume = "+ masterVolume);

            switch (v.getId()){
                case R.id.btn_pad_0:
                    playSample(0); // starts at 1
                    //Will trigger track 0
                    //int soundID, float leftVolume,
                    // float rightVolume,int priority,int loop,float rate
                    break;
                case R.id.btn_pad_1:
                    playSample(1);
//                    samplePool.play(phn_clp_id, (float)masterVolume,(float) masterVolume, 1, 0, 1); //int soundID, float leftVolume,
                    break;
                case R.id.btn_pad_2:
                    playSample(2);

//                    samplePool.play(dry_ohh_cra_id, (float)masterVolume, (float)masterVolume, 1, 0, 1); //int soundID, float leftVolume,
                    break;
                case R.id.btn_pad_3:
                    playSample(3);
                    break;
                case R.id.btn_pad_4:
                    playSample(4);
                    break;
                case R.id.btn_pad_5:
                    playSample(5);
                    break;
                case R.id.btn_pad_6:
                    playSample(6);
                    break;
                case R.id.btn_pad_7:
                    playSample(7);
                    break;
            }
        }
    }
}
