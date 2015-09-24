package itp341.webb.jerry.a4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    // TAG
    private static final String TAG = MainActivity.class.getSimpleName();

    //widget variables
    EditText editName;
    SeekBar seekBarPercentage;
    Spinner splitBill;
    TextView textTipPercentage;

    //instance variables
    double billAmount = 0;
    double tipAmount = 0;
    double totalAmount = 0;
    int intTipPercentage = 0;
    int numOfPayers = 1;
    String tipPercentage = "";
    String tipTotal = "";
    String stringTotal = "";
    String perPersonSplit = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creation of the Listener Variables for
        editName = (EditText) findViewById(R.id.editText);
        seekBarPercentage = (SeekBar) findViewById(R.id.seekBarPercentage);
        splitBill = (Spinner) findViewById(R.id.splitBill);
        textTipPercentage = (TextView) findViewById(R.id.testView3);

        //text
        editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                //name = editName.getText().toString();

                //Log.d(TAG, "inside onEditorAction. Name is " + name);
                return false;
            }
        });

        seekBarPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                intTipPercentage =  seekBarPercentage.getProgress();
                textTipPercentage.setText(Integer.toString(intTipPercentage) + "%");

                calculateAndDisplay();
                Log.d(TAG, "in onProgressChanged for seekBar. Percentage is: "
                        + seekBarPercentage.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void calculateAndDisplay(){

        tipAmount =  (billAmount/numOfPayers)/seekBarPercentage.getProgress();
        Log.d(TAG, "in calculate. The tipAmount is: " + tipAmount);

        totalAmount = billAmount/numOfPayers + tipAmount;
        Log.d(TAG, "in calculate. The totalAmount is: " + totalAmount);

        
    }

}
