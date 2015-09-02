package itp341.webb.jerry.tuesday;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button btnMessage;
    ButtonListener btnListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find a reference to a button
        btnMessage = (Button) findViewById(R.id.btnGreeting);

        //create a listener
        btnListener = new ButtonListener();

        //connect listener object with button
        btnMessage.setOnClickListener(btnListener);
    }

    //create named inner class to respond to button clicks
    public class  ButtonListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "Android is fun", Toast.LENGTH_SHORT).show();
        }
    }

}
