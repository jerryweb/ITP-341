package itp341.webb.jerry.greenbeatmachine;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity {
    public static final String TAG = "itp341.webb.jerry.finalProject.tag";
    private MediaPlayer mp = new MediaPlayer();
    private SoundPool samplePool;

    Button btn_pad_0;
    Button btn_pad_1;
    Button btn_pad_2;
    Button btn_pad_3;
    Button btn_pad_4;
    Button btn_pad_5;
    Button btn_pad_6;
    Button btn_pad_7;

    ListView soundList;
    ButtonListener buttonListener;


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

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_pad_0:
                    break;
                case R.id.btn_pad_1:
                    break;
                case R.id.btn_pad_2:
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
