package itp341.exercises.week8_midterm_review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InterruptedIOException;
import java.text.NumberFormat;

public class MainActivity extends Activity {
	public static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_NUMBER_OF_TICKETS ="itp341.webb.jerry.midtermReview.numOfTickets";
	Spinner spinnerNumTickets;
	Button buttonOrder;
	Button buttonProceed;
	TextView textCost;

	ButtonListener btnListen;

	int numTickets = 0;
    double cost = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//need to create the corresponding view in the XML layout
		spinnerNumTickets = (Spinner) findViewById(R.id.spinnerNumTickets);
		buttonOrder = (Button) findViewById(R.id.buttonOrder);
		buttonProceed = (Button) findViewById(R.id.buttonProceedToCheckout);
		textCost = (TextView) findViewById(R.id.textCost);

        btnListen = new ButtonListener();
        //TODO implement spinner listener to update total cost

		spinnerNumTickets.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				numTickets = spinnerNumTickets.getSelectedItemPosition() + 1;
                NumberFormat formatter = NumberFormat.getCurrencyInstance();
                cost = numTickets*9.99;
                textCost.setText(formatter.format(cost));
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		//TODO implement proceed button listener to launch ConfirmActivity and pass any info
		buttonProceed.setOnClickListener(btnListen);

        //TODO implment order button listener to place order (i.e. display a toast)
		buttonOrder.setOnClickListener(btnListen);
	}

	public class  ButtonListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()){
				case R.id.buttonOrder:
                    Toast.makeText(getApplicationContext(),"Congrats!",Toast.LENGTH_SHORT).show();
					break;

				case R.id.buttonProceedToCheckout:
                    Log.d(TAG,"in case 2");
                    Intent i = new Intent(getApplicationContext(),ConfirmActivity.class);
                    i.putExtra(EXTRA_NUMBER_OF_TICKETS,numTickets);
                    startActivityForResult(i,0);
					break;
			}
		}
	}

		//TODO complete OnActivityResult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                buttonProceed.setVisibility(View.GONE);
                buttonOrder.setVisibility(View.VISIBLE);
            }
        }
	}

}
