package itp341.webb.jerry.assigment6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import itp341.webb.jerry.assigment6.model.CoffeeOrder;

/**
 * Created by jerrywebb on 10/22/15.
 */
public class ViewOrderActivity extends Activity{
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_COFFEE_ORDER = "itp341.webb.jerry.coffeeOrder";

    CoffeeOrder cOrder = new CoffeeOrder("New User", R.id.radioButtonSmall, "iced", false, false);

    TextView textOrderConfirm;
    Button buttonConfirm;
    Button buttonClear;
    String msg;

    ButtonListener buttonListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_activity);
        Log.d(TAG, "in ViewOrderActivity");

        textOrderConfirm = (TextView) findViewById(R.id.textOrderConfirm);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);

        buttonListener = new ButtonListener();

        Intent i = getIntent();
        cOrder = (CoffeeOrder) i.getSerializableExtra(EXTRA_COFFEE_ORDER);
        msg = "Order for " + cOrder.getName();
        msg += "\n";

        textOrderConfirm.setText(msg);
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonClear:
                    break;
                case R.id.buttonConfirm:
                    break;
            }
        }
    }
}
