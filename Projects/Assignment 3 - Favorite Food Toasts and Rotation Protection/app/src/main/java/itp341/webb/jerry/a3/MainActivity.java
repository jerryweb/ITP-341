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
    public static final String[] ClicksArray = new String[6];

    private int[] BtnClicks = {0,0,0,0,0,0};

    private void populateStringArray()
    {
        ClicksArray[0] = "itp341.webb.jerry.a3.btn1clicks";
        ClicksArray[1] = "itp341.webb.jerry.a3.btn2clicks";
        ClicksArray[2] = "itp341.webb.jerry.a3.btn3clicks";
        ClicksArray[3] = "itp341.webb.jerry.a3.btn4clicks";
        ClicksArray[4] = "itp341.webb.jerry.a3.btn5clicks";
        ClicksArray[5] = "itp341.webb.jerry.a3.btn6clicks";
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onCreate(outState);
        for (int i = 0; i < 6;i++){
            outState.putInt(ClicksArray[i], BtnClicks[i]);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateStringArray();
        if(savedInstanceState != null) {    //activity has existed before
            for (int i = 0; i < 6;i++){
                BtnClicks[i] = savedInstanceState.getInt(ClicksArray[i],0);
            }
        }
        else{
            for (int i = 0; i < 6;i++){
                BtnClicks[i] = 0;
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
                    BtnClicks[0]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_american)
                            + BtnClicks[0] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton2:
                    BtnClicks[1]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_chinese)
                                    + BtnClicks[1] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton3:
                    BtnClicks[2]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_indian)
                                    + BtnClicks[2] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton4:
                    BtnClicks[3]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_italian)
                                    + BtnClicks[3] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton5:
                    BtnClicks[4]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_middle_eastern)
                                    + BtnClicks[4] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;

                case R.id.imageButton6:
                    BtnClicks[5]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.toast_portuguese)
                                    + BtnClicks[5] + getResources().getString(R.string.toast_end),
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

}
