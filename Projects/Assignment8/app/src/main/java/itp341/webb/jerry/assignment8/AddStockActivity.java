package itp341.webb.jerry.assignment8;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import itp341.webb.jerry.assignment8.Model.Stock;
import itp341.webb.jerry.assignment8.Model.StockSingleton;

/**
 * Created by jerrywebb on 11/3/15.
 */
public class AddStockActivity extends Activity {
    private static final String TAG = StockManagerActivity.class
            .getSimpleName();

    Button buttonSave;
    EditText editProductName;
    EditText editBrand;
    EditText editPrice;
    EditText editColor;
    EditText editStock;
    Stock s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_stock);
        s  = new Stock();
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editBrand = (EditText) findViewById(R.id.editBrand);
        editColor = (EditText) findViewById(R.id.editColor);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editProductName = (EditText) findViewById(R.id.editProductName);
        editStock = (EditText)findViewById(R.id.editStock);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStock();
            }
        });
            //changed from new TextView.OnEditActionListener()
        editBrand.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                s.setBrand(editBrand.getText().toString());
                return false;
            }
        });

        editProductName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                s.setProductName(editProductName.getText().toString());
                return false;
            }
        });

        editColor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                s.setColor(editColor.getText().toString());
                return false;
            }
        });

        editPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Int2String(editPrice);
                return false;
            }
        });

        editStock.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Int2String(editStock);
                return false;
            }
        });

    }

    private void Int2String(EditText et){
        int myNum = 0;

        try {
            myNum = Integer.parseInt(et.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        if(et == editPrice){
            s.setPrice(myNum);

        }
        if(et == editStock){
            s.setStock(myNum);
        }

    }

    private void saveStock(){
        Log.d(TAG, "in saveStock");

        Int2String(editPrice);
        Int2String(editStock);
        s.setBrand(editBrand.getText().toString());
        s.setColor(editColor.getText().toString());
        s.setProductName(editProductName.getText().toString());
        s.setID(StockSingleton.get(this).getmStocks().size());
        StockSingleton.get(this).addStock(s);

        setResult(RESULT_OK);
        finish();
    }
}
