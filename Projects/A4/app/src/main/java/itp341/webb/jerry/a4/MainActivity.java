package itp341.webb.jerry.a4;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends Activity {

    // TAG
    private static final String TAG = MainActivity.class.getSimpleName();

    //widget variables
    EditText editBillAmount;
    SeekBar seekBarPercentage;
    Spinner spinnerSplitBill;
    TextView textTipPercentage;
    TextView textTipCalculation;
    TextView textTotalCalculation;
    TextView textPerPersonAmount;
    LinearLayout layoutPerPersonAmount;

    //instance variables
    double billAmount = 0.0;
    double tipAmount = 0.0;
    double totalAmount = 0.0;
    double perPersonAmount = 0.0;
    int numOfPayers = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creation of the Listener Variables
        editBillAmount = (EditText) findViewById(R.id.editText);
        seekBarPercentage = (SeekBar) findViewById(R.id.seekBarPercentage);
        spinnerSplitBill = (Spinner) findViewById(R.id.spinnerSplitBill);
        textTipPercentage = (TextView) findViewById(R.id.textPercentageDisplay);
        textTipCalculation = (TextView) findViewById(R.id.textTipCalculation);
        textTotalCalculation = (TextView) findViewById(R.id.textTotalCalculation);
        textPerPersonAmount = (TextView) findViewById(R.id.textPerPersonAmount);
        layoutPerPersonAmount = (LinearLayout) findViewById(R.id.layoutPerPersonAmount);


        //text
        editBillAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                billAmount = Double.parseDouble(editBillAmount.getText().toString().trim());

                Log.d(TAG, "inside onEditorAction. Name is " + billAmount);
                return false;
            }
        });

        //connecting spinner to widget
        //set the seek bar to default to 10% tip
        seekBarPercentage.setProgress(10);
        seekBarPercentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //intTipPercentage =  seekBarPercentage.getProgress();
                textTipPercentage.setText(Integer.toString(seekBarPercentage.getProgress()) + "%");

                calculateAndDisplay();
                Log.d(TAG, "in onProgressChanged for seekBar. Percentage is: "
                        + seekBarPercentage.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                calculateAndDisplay();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                calculateAndDisplay();

            }
        });

        spinnerSplitBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                calculateAndDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                calculateAndDisplay();
            }
        });


    }

    public void calculateAndDisplay(){
        //get the number of payers from the spinner
        numOfPayers = spinnerSplitBill.getSelectedItemPosition() +1;

        //this will toggle the visibility of the per person display depending upon if the bill
        // will be split
        if(spinnerSplitBill.getSelectedItemPosition() > 0){
            layoutPerPersonAmount.setVisibility(View.VISIBLE);
        }
        else {
            layoutPerPersonAmount.setVisibility(View.INVISIBLE);
        }

        Log.d(TAG, "in calculateAndDisplay and the numberOfPayers is: " + numOfPayers);

        //check if the tip amount is 0 percent, so as to avoid dividing by zero
        if(seekBarPercentage.getProgress() == 0){
            tipAmount = 0;
        }
        else {
            tipAmount = (billAmount/numOfPayers) * (seekBarPercentage.getProgress() / 100.0);
        }

        Log.d(TAG, "in calculate. The tipAmount is: " + tipAmount);

        totalAmount = billAmount + (tipAmount*numOfPayers);
        perPersonAmount = billAmount/numOfPayers + tipAmount;
        Log.d(TAG, "in calculate. The totalAmount is: " + totalAmount);
        textTipCalculation.setText("$" + Double.toString(tipAmount));
        textTotalCalculation.setText("$" + Double.toString(totalAmount));
        textPerPersonAmount.setText("$" + Double.toString(perPersonAmount));
    }

}
