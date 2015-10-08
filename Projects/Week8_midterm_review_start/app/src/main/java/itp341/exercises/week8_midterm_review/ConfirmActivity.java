package itp341.exercises.week8_midterm_review;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConfirmActivity extends Activity {
    public static final String TAG = MainActivity.class.getSimpleName();
    //TODO create Extra Keys

    Button buttonConfirm;
    Button buttonCancel;
    TextView textTicketQuantity;
    TextView textDisplayTickets;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        //need to create the corresponding view in the XML layout
        buttonConfirm = (Button)findViewById(R.id.buttonConfirm);
        buttonCancel = (Button)findViewById(R.id.buttonCancel);
        textTicketQuantity = (TextView)findViewById(R.id.textTicketQuantity);
        textDisplayTickets = (TextView)findViewById(R.id.textDisplayTickets);


        // TODO read intent number of tickets
        Intent i = getIntent();
        textTicketQuantity.setText(Integer.toString(i.getIntExtra(MainActivity.EXTRA_NUMBER_OF_TICKETS, 0)));

        // TODO create button confirm listener--what does it do?
        buttonConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
            }
        });

        //TODO create button cancel listener--what does it do?
        buttonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
            }
        });
    }

}
