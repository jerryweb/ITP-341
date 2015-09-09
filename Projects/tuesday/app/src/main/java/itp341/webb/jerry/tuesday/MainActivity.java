package itp341.webb.jerry.tuesday;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends Activity {
    //create my Logging tag
    private static final String TAG = MainActivity.class.getSimpleName();
    //TAG now storing "MainActivity" as a string

    Button btnMessage;
    ButtonListener btnListener;
    Button btnClickMe;


    public static final String MYSTERY = "itp341.webb.jerry.week3day1.mystery";
    private int mystery;

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onCreate(outState);
        outState.putInt(MYSTERY, mystery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "In onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mystery = 4;

        Log.d(TAG, "In onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "In onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "In onDestroy");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "In onStart");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {    //activity has existed before
            mystery = savedInstanceState.getInt(MYSTERY,0);
        }
        else{
            mystery = 0;
        }

        Log.d(TAG, "I am in onCreate! The value of mystery = " + mystery);
//        Log.d("Foo", "msg ");

        //find a reference to a button
        //findViewById will connect the button on the screen to the button message... This is very
        // processor intensive
        btnMessage = (Button) findViewById(R.id.btnGreeting);
        btnClickMe = (Button) findViewById(R.id.btn2);

        //create a listener
        btnListener = new ButtonListener();

        //connect listener object with button
        btnMessage.setOnClickListener(btnListener);

        //create listener for click me button
        btnClickMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = getResources().getString(R.string.label_toast_message);
                msg = msg.toUpperCase();
                //disadvantage to this is that if two diff buttons that want to use this listener, then it will not work
                //because it doesn't have a name

                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, R.string.label_toast_message, Toast.LENGTH_SHORT).show();
            }
        });
    }
    //interface is like a skeleton for the listeners

    //create named inner class to respond to button clicks
    public class  ButtonListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Android is fun", Toast.LENGTH_SHORT).show();
        }
    }

}
