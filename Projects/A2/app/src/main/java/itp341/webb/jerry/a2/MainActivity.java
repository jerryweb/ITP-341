package itp341.webb.jerry.a2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity {
    //create my Logging tag
//    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String[] numClicksArray = new String[3];

    Button mBtn1;
    Button mBtn2;
    Button mBtn3;
    ButtonListener mBtnListener;

    private int[] numBtnClicks = {0,0,0};

    private void populateStringArray()
    {
        numClicksArray[0] = "itp341.webb.jerry.a2.btn1clicks";
        numClicksArray[1] = "itp341.webb.jerry.a2.btn2clicks";
        numClicksArray[2] = "itp341.webb.jerry.a2.btn3clicks";
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onCreate(outState);
        for (int i = 0; i < 3;i++){
            outState.putInt(numClicksArray[i], numBtnClicks[i]);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {    //activity has existed before
            for (int i = 0; i < 3;i++){
                numBtnClicks[i] = savedInstanceState.getInt(numClicksArray[i],0);
            }
        }
        else{
            for (int i = 0; i < 3;i++){
            numBtnClicks[i] = 0;
            }
        }

        mBtn1 = (Button) findViewById(R.id.btn);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn3 = (Button) findViewById(R.id.btn3);

        //create a listener
        mBtnListener = new ButtonListener();

        //connect listener object with button
        mBtn1.setOnClickListener(mBtnListener);
        mBtn2.setOnClickListener(mBtnListener);
        mBtn3.setOnClickListener(mBtnListener);
    }

    //create named inner class to respond to button clicks
    public class  ButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn:
                    numBtnClicks[0]++;
                    Toast.makeText(MainActivity.this,  getResources().getString(R.string.Toast_Answer_1)
                            + numBtnClicks[0], Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn2:
                    numBtnClicks[1]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Answer_2)
                            + numBtnClicks[1], Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn3:
                    numBtnClicks[2]++;
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.Toast_Answer_3)
                            + numBtnClicks[2], Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
