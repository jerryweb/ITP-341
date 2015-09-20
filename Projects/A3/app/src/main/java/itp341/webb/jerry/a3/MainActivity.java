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
    public static final String[] numClicksArray = new String[6];

    private int[] numBtnClicks = {0,0,0,0,0,0};

    private void populateStringArray()
    {
        numClicksArray[0] = "itp341.webb.jerry.a3.btn1clicks";
        numClicksArray[1] = "itp341.webb.jerry.a3.btn2clicks";
        numClicksArray[2] = "itp341.webb.jerry.a3.btn3clicks";
        numClicksArray[3] = "itp341.webb.jerry.a3.btn4clicks";
        numClicksArray[4] = "itp341.webb.jerry.a3.btn5clicks";
        numClicksArray[5] = "itp341.webb.jerry.a3.btn6clicks";
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onCreate(outState);
        for (int i = 0; i < 6;i++){
            outState.putInt(numClicksArray[i], numBtnClicks[i]);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {    //activity has existed before
            for (int i = 0; i < 6;i++){
                numBtnClicks[i] = savedInstanceState.getInt(numClicksArray[i],0);
            }
        }
        else{
            for (int i = 0; i < 6;i++){
                numBtnClicks[i] = 0;
            }
        }
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

                case R.id.imageButton2:
                    numBtnClicks[1]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_chinese)
                                    + numBtnClicks[1] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton3:
                    numBtnClicks[2]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_indian)
                                    + numBtnClicks[2] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton4:
                    numBtnClicks[3]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_italian)
                                    + numBtnClicks[3] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton5:
                    numBtnClicks[4]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_middle_eastern)
                                    + numBtnClicks[4] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton6:
                    numBtnClicks[5]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_portuguese)
                                    + numBtnClicks[5] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
