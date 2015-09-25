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

    public static final String[] values = new String[5];

    Double[] calculatedValues = new Double[5];

    private void populateStringArray()
    {
        values[0] = "itp341.webb.jerry.a4.billAmount";
        values[1] = "itp341.webb.jerry.a4.tipAmount";
        values[2] = "itp341.webb.jerry.a4.totalAmount";
        values[3] = "itp341.webb.jerry.a4.perPersonAmount";
        values[4] = "itp341.webb.jerry.a4.spinnerPerPersonSelection";
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
    String Payers;


    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onCreate(outState);
        for (int i = 0; i < 5;i++){
            outState.putDouble(values[i],calculatedValues[i]);
        }
        outState.putInt(Payers,numOfPayers);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateStringArray();
        if(savedInstanceState != null) {    //activity has existed before
            for (int i = 0; i < 5;i++){
                calculatedValues[i] = savedInstanceState.getDouble(values[i], 0);

            }
             billAmount = savedInstanceState.getDouble(values[0], 0);
             tipAmount = savedInstanceState.getDouble(values[1], 0);
             totalAmount = savedInstanceState.getDouble(values[2], 0);
             perPersonAmount = savedInstanceState.getDouble(values[3], 0);
            int had = savedInstanceState.getInt(Payers);
            Log.d(TAG, "heellll: " + had);
             spinnerSplitBill.setSelection(1);

        }
        else{
            for (int i = 0; i < 5;i++){
                calculatedValues[i] = Double.valueOf(0);
            }
        }
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


        //Allows for instant feedback for the user when they select a different option from the
        // spinner
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

        //Converts Doubles to currency values
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        //textTipCalculation.setText("$" + Double.toString(tipAmount));
        textTipCalculation.setText(formatter.format(tipAmount));
        textTotalCalculation.setText(formatter.format(totalAmount));
        textPerPersonAmount.setText(formatter.format(perPersonAmount));

        calculatedValues[0] = billAmount;
        calculatedValues[1] = tipAmount;
        calculatedValues[2] = totalAmount;
        calculatedValues[3] = perPersonAmount;
    }

}
