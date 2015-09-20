package itp341.webb.jerry.a3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {

    ImageButton picBtn1;
    ImageButton picBtn2;
    ImageButton picBtn3;
    ImageButton picBtn4;
    ImageButton picBtn5;
    ImageButton picBtn6;

    ButtonListener picBtnListener;

    //These 2 arrays contain the value for the number of times the button was clicked, and the message
    // for the toast after each button click
    public static final String[] numClicksArray = new String[3];

    private int[] numBtnClicks = {0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picBtn1 = (ImageButton) findViewById(R.id.imageButton1);
        picBtn2 = (ImageButton) findViewById(R.id.imageButton2);
        picBtn3 = (ImageButton) findViewById(R.id.imageButton3);
        picBtn4 = (ImageButton) findViewById(R.id.imageButton4);
        picBtn5 = (ImageButton) findViewById(R.id.imageButton5);
        picBtn6 = (ImageButton) findViewById(R.id.imageButton6);

        //create listener object
        picBtnListener = new ButtonListener();

        //connect button and listener object
        picBtn1.setOnClickListener(picBtnListener);
        picBtn2.setOnClickListener(picBtnListener);
        picBtn3.setOnClickListener(picBtnListener);
        picBtn4.setOnClickListener(picBtnListener);
        picBtn5.setOnClickListener(picBtnListener);
        picBtn6.setOnClickListener(picBtnListener);

    }

    public class  ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageButton1:
                    numBtnClicks[0]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_american)
                            + numBtnClicks[0] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
