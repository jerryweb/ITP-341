package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
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

import itp341.webb.jerry.greenbeatmachine.model.Sound;
import itp341.webb.jerry.greenbeatmachine.database.SoundDbSchema.TABLE_SOUNDS;
import itp341.webb.jerry.greenbeatmachine.model.SoundsSingleton;


public class MainActivity extends Activity {
    public static final String TAG = "itp341.webb.jerry.finalProject.tag";
    private MediaPlayer mp = new MediaPlayer();

    //used to play sounds simultaneously... best used for sound files < 1MB
    private SoundPool samplePool;

    int bmb_k_id;
    int phn_clp_id;
    int dry_ohh_cra_id;
    double masterVolume;

    Button btn_pad_0;
    Button btn_pad_1;
    Button btn_pad_2;
    Button btn_pad_3;
    Button btn_pad_4;
    Button btn_pad_5;
    Button btn_pad_6;
    Button btn_pad_7;

    ListView soundList;

    SeekBar seekBarMasterVolume;
    ButtonListener buttonListener;


    Cursor c; //CursorWrapper
    SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_pad_0 = (Button) findViewById(R.id.btn_pad_0);
        btn_pad_1 = (Button) findViewById(R.id.btn_pad_1);
        btn_pad_2 = (Button) findViewById(R.id.btn_pad_2);
        btn_pad_3 = (Button) findViewById(R.id.btn_pad_3);
        btn_pad_4 = (Button) findViewById(R.id.btn_pad_4);
        btn_pad_5 = (Button) findViewById(R.id.btn_pad_5);
        btn_pad_6 = (Button) findViewById(R.id.btn_pad_6);
        btn_pad_7 = (Button) findViewById(R.id.btn_pad_7);

        soundList = (ListView) findViewById(R.id.soundsList);
        buttonListener = new ButtonListener();

        btn_pad_0.setOnClickListener(buttonListener);
        btn_pad_1.setOnClickListener(buttonListener);
        btn_pad_2.setOnClickListener(buttonListener);
        btn_pad_3.setOnClickListener(buttonListener);
        btn_pad_4.setOnClickListener(buttonListener);
        btn_pad_5.setOnClickListener(buttonListener);
        btn_pad_6.setOnClickListener(buttonListener);
        btn_pad_7.setOnClickListener(buttonListener);

        loadData();


        //sets attributes so as to better recognize the audio samples
        AudioAttributes sampleAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build();


        //Streams are the number of sounds that can be played simultaneously
        samplePool = new SoundPool.Builder().setMaxStreams(25)
                .setAudioAttributes(sampleAttributes)
                .build();//(20, AudioManager.STREAM_MUSIC,0); //int maxStreams, int streamType,int srcQuality

        bmb_k_id = samplePool.load(this, R.raw.ac_k, 1);
        phn_clp_id = samplePool.load(this, R.raw.phn_clp,1);
        dry_ohh_cra_id = samplePool.load(this, R.raw.dry_ohh_cra,1);

        Sound so = new Sound();
        so.setName("bmb_k_id");
        so.setType("kick");
        SoundsSingleton.get(this).addSound(so);

        masterVolume =  0.8;
        seekBarMasterVolume =  (SeekBar) findViewById(R.id.seekbarMasterVolume);

        seekBarMasterVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                masterVolume = Double.parseDouble(String.valueOf(progress))/10;
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

    public void playSample( int sampleId){
        samplePool.play(sampleId,(float) masterVolume, (float) masterVolume,1,0,1);
    }

    private void loadData() {
        //What columns are we pulling from the table and displaying on the screen
        String[] from = {TABLE_SOUNDS.KEY_NAME};
        //Array of android ids
        int[] to = {android.R.id.text1};

        //get cursor
        c = SoundsSingleton.get(this).getSounds();
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                c, from, to, 0);    //the last argument is the flag (we can just put 0 for the most part

//        soundList.setAdapter(cursorAdapter);

    }
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
    public playSample(int ){

    }
    private class ButtonListener implements View.OnClickListener{
//        float outputVolume = (float)masterVolume;

        @Override
        public void onClick(View v) {
            Log.d(TAG,"outputVolume = "+ masterVolume);

            switch (v.getId()){
                case R.id.btn_pad_0:
                    playSample(bmb_k_id);
                    //int soundID, float leftVolume,
                    // float rightVolume,int priority,int loop,float rate
                    break;
                case R.id.btn_pad_1:
//                    playSample(phn_clp_id);
//                    samplePool.play(phn_clp_id, (float)masterVolume,(float) masterVolume, 1, 0, 1); //int soundID, float leftVolume,
                    break;
                case R.id.btn_pad_2:
//                    playSample(dry_ohh_cra_id);

//                    samplePool.play(dry_ohh_cra_id, (float)masterVolume, (float)masterVolume, 1, 0, 1); //int soundID, float leftVolume,
                    break;
                case R.id.btn_pad_3:
                    break;
                case R.id.btn_pad_4:
                    break;
                case R.id.btn_pad_5:
                    break;
                case R.id.btn_pad_6:
                    break;
                case R.id.btn_pad_7:
                    break;
            }
        }
    }
}
