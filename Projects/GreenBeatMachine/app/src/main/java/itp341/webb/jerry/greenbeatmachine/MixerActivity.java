package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.content.Intent;
import android.media.audiofx.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 */
public class MixerActivity extends Activity {
    public static final String TAG = "itp341.finalProject.tag";


    Equalizer eq = null;
    BassBoost bb = null;

    int min_level = 0;
    int max_level = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixer);

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

        return super.onOptionsItemSelected(item);
    }


}
