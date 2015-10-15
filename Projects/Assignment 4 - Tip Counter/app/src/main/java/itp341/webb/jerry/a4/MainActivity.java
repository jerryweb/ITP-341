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

import java.text.NumberFormat;

public class MainActivity extends Activity {

    // TAG
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final String[] doubleValues = new String[4];
    public static final String[] integerValues = new String[2];

    private Double[] dCalculatedValues = {0.0,0.0,0.0,0.0};
    private Integer[] intCalculatedValues = {0,0};

    private void populateStringArray()
    {
        doubleValues[0] = "itp341.webb.jerry.a4.billAmount";
        doubleValues[1] = "itp341.webb.jerry.a4.tipAmount";
        doubleValues[2] = "itp341.webb.jerry.a4.totalAmount";
        doubleValues[3] = "itp341.webb.jerry.a4.perPersonAmount";

        integerValues[0] = "itp341.webb.jerry.a4.spinnerSelection";
        integerValues[1] = "itp341.webb.jerry.a4.value";
    }

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
    static int previousNumOfPayers = 1;


    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onCreate(outState);
        for (int i = 0; i < 4;i++){
            outState.putDouble(doubleValues[i], dCalculatedValues[i]);
        }
        for (int i = 0; i <2; i++){
            outState.putInt(integerValues[i],intCalculatedValues[i]);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateStringArray();

        //Creation of the Listener Variables
        editBillAmount = (EditText) findViewById(R.id.editText);
        seekBarPercentage = (SeekBar) findViewById(R.id.seekBarPercentage);
        spinnerSplitBill = (Spinner) findViewById(R.id.spinnerSplitBill);
        textTipPercentage = (TextView) findViewById(R.id.textPercentageDisplay);
        textTipCalculation = (TextView) findViewById(R.id.textTipCalculation);
        textTotalCalculation = (TextView) findViewById(R.id.textTotalCalculation);
        textPerPersonAmount = (TextView) findViewById(R.id.textPerPersonAmount);
        layoutPerPersonAmount = (LinearLayout) findViewById(R.id.layoutPerPersonAmount);

        if(savedInstanceState != null) {    //activity has existed before
            for (int i = 0; i < 4;i++){
                dCalculatedValues[i] = savedInstanceState.getDouble(doubleValues[i], 0);

            }
            billAmount = savedInstanceState.getDouble(doubleValues[0], 0);
            tipAmount = savedInstanceState.getDouble(doubleValues[1], 0);
            totalAmount = savedInstanceState.getDouble(doubleValues[2], 0);
            perPersonAmount = savedInstanceState.getDouble(doubleValues[3], 0);
            previousNumOfPayers = savedInstanceState.getInt(integerValues[0],0);
            spinnerSplitBill.setSelection(previousNumOfPayers -1);
            editBillAmount.setText((String.valueOf( billAmount)));
            Log.d(TAG, "in savedInstanceState != null, numOfPayers is: " + numOfPayers);
        }
        else{
            for (int i = 0; i < 4;i++){
                dCalculatedValues[i] = Double.valueOf(0);
            }
        }

        //text
        editBillAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                billAmount = Double.parseDouble(editBillAmount.getText().toString().trim());
                calculateAndDisplay();
                Log.d(TAG, "inside onEditorAction. Name is " + billAmount);
                return false;
            }
        });


        //Allows for instant feedback for the user when they select a different option from the
        // spinner
        spinnerSplitBill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numOfPayers = spinnerSplitBill.getSelectedItemPosition() + 1;
                calculateAndDisplay();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                calculateAndDisplay();
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



    }

    public void calculateAndDisplay(){
        //get the number of payers from the spinner
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

        //Converts Doubles to currency doubleValues
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        textTipCalculation.setText(formatter.format(tipAmount));
        textTotalCalculation.setText(formatter.format(totalAmount));
        textPerPersonAmount.setText(formatter.format(perPersonAmount));

        dCalculatedValues[0] = billAmount;
        dCalculatedValues[1] = tipAmount;
        dCalculatedValues[2] = totalAmount;
        dCalculatedValues[3] = perPersonAmount;

        intCalculatedValues[0] = numOfPayers;
    }

}
