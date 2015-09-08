package itp341.webb.jerry.tuesday;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btnMessage;
    ButtonListener btnListener;
    Button btnClickMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
