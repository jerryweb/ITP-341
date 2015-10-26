package itp341.webb.jerry.assigment6;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import itp341.webb.jerry.assigment6.model.CoffeeOrder;

/**
 * Created by jerrywebb on 10/22/15.
 */
public class ViewOrderActivity extends Activity{
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_COFFEE_ORDER = "itp341.webb.jerry.coffeeOrder";
    public static final String EXTRA_ARRAY_COFFEE_ORDER = "itp341.webb.jerry.arrayCoffeeOrder";
    int listSelection;
    CoffeeOrder cOrder = new CoffeeOrder("New User", R.id.radioButtonSmall, "iced", false, false);

    ArrayAdapter<CoffeeOrder> adapter;
//    ExpandableListAdapter expandableListAdapter;
//    ExpandableListView orderList;
    Button buttonConfirm;
    ListView listViewOrder;
    Button buttonClear;

    String orderForString, msgPart2, msgSize, msgPart4;
    TextView textOrderConfirm;
    TextView textBrew;
    TextView textSize;
    TextView textSpecialInstructions;
    ButtonListener buttonListener;
    ArrayList<CoffeeOrder> arrayCoffeeOrder = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_order_activity);
        Log.d(TAG, "in ViewOrderActivity");

        textOrderConfirm = (TextView) findViewById(R.id.textOrderConfirm);
        textBrew = (TextView) findViewById(R.id.textBrew);
        textSize = (TextView) findViewById(R.id.textSize);
        textSpecialInstructions = (TextView) findViewById(R.id.textSpecialInstructions);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonConfirm = (Button) findViewById(R.id.buttonConfirm);
        listViewOrder = (ListView) findViewById(R.id.listViewOrder);

        listSelection = 0;
        Intent i = getIntent();
        cOrder = (CoffeeOrder) i.getSerializableExtra(EXTRA_COFFEE_ORDER);
        arrayCoffeeOrder = (ArrayList<CoffeeOrder>) i.getSerializableExtra(EXTRA_ARRAY_COFFEE_ORDER);



        adapter = new ArrayAdapter<CoffeeOrder>(this, android.R.layout.simple_list_item_1, arrayCoffeeOrder);
        buttonListener = new ButtonListener();
        buttonClear.setOnClickListener(buttonListener);
        buttonConfirm.setOnClickListener(buttonListener);
        listViewOrder.setAdapter(adapter);


        listViewOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cOrder = arrayCoffeeOrder.get(position);
                populateView();
            }
        });

        populateView();
    }

    private void populateView(){


        orderForString = "Order for " + cOrder.getName();
        msgPart2 = cOrder.getBrew();

        //This displays the size value as a string
        msgSize = "(";
        switch (cOrder.getSize()) {
            case R.id.radioButtonSmall:
                msgSize += "small";
                break;
            case R.id.radioButtonMedium:
                msgSize += "medium";
                break;
            case R.id.radioButtonLarge:
                msgSize += "large";
                break;
        }
        msgSize += ")";

        msgPart4 = "with ";
        if(cOrder.isMilkOrCream()){
            msgPart4 += "milk ";
        }
        else {
            msgPart4 += "no milk";
        }
        if(cOrder.isSugar()){
            msgPart4 += " and sugar";
        }
        else {
            msgPart4 += " and no sugar";
        }

        msgPart4 += "\nSpecial Instructions: " + cOrder.getSpecialInstructions();


        textOrderConfirm.setText(orderForString);
        textBrew.setText(msgPart2);
        textSize.setText(msgSize);
        textSpecialInstructions.setText(msgPart4);
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonClear:
                    setResult(RESULT_CANCELED);
                    Log.d(TAG, "result canceled");

                    finish();

                    break;
                case R.id.buttonConfirm:
                    setResult(RESULT_OK);
                    Log.d(TAG, "result confirmed");
                    Toast.makeText(getApplicationContext(), "Thanks for your order!", Toast.LENGTH_SHORT).show();

                    finish();
                    break;
            }
        }
    }
}
