package itp341.webb.jerry.assignment8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import itp341.webb.jerry.assignment8.Model.Stock;
import itp341.webb.jerry.assignment8.Model.StockSingleton;

/**
 * Created by jerrywebb on 11/8/15.
 */
public class BuySellStock extends Activity {
    private static final String TAG = BuySellStock.class.getSimpleName();

    Button buttonBuyStock;
    Button buttonDeleteFromRecord;
    Button buttonSellAll;
    Button buttonSellStock;

    TextView textProductName;
    TextView textPrice;
    TextView textBrand;
    TextView textColor;
    TextView textStock;

    ButtonListener buttonListener;
    int position;   //position of clicked stock
    int id;     //id of the stock

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = 0;
        Log.d(TAG, "in BuySellStock");
        setContentView(R.layout.activity_buy_sell_stock);

        buttonBuyStock = (Button) findViewById(R.id.buttonBuyStock);
        buttonDeleteFromRecord = (Button) findViewById(R.id.buttonDeleteFromRecord);
        buttonSellAll = (Button) findViewById(R.id.buttonSellAll);
        buttonSellStock = (Button) findViewById(R.id.buttonSellStock);

        textBrand = (TextView) findViewById(R.id.textBrand);
        textColor = (TextView) findViewById(R.id.textColor);
        textPrice = (TextView) findViewById(R.id.textPrice);
        textProductName = (TextView) findViewById(R.id.textProductName);
        textStock = (TextView) findViewById(R.id.textStock);

        buttonListener = new ButtonListener();

        buttonSellAll.setOnClickListener(buttonListener);
        buttonSellStock.setOnClickListener(buttonListener);
        buttonBuyStock.setOnClickListener(buttonListener);
        buttonDeleteFromRecord.setOnClickListener(buttonListener);

        Intent i = getIntent();
        position = i.getIntExtra(StockManagerActivity.EXTRA_STOCK_POSITION, 0);
        Log.d(TAG,"the position is: " + position);

//        if((position < StockSingleton.get(this).getmStocks().size() )|| (position >=0)){
            Stock st = StockSingleton.get(this).getStock(position);
            if(st != null){
                loadData(st);
            }

    }

    private void loadData(Stock s){
        textProductName.setText(s.getProductName());
        textBrand.setText(s.getBrand());
        textStock.setText(Integer.toString(s.getStock()));

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        textPrice.setText(formatter.format(s.getPrice()));
        textColor.setText(s.getColor());
        id = s.getID();

        if(s.getStock() == 0){
            buttonSellStock.setEnabled(false);
            buttonSellAll.setEnabled(false);
        }
        else{
            buttonSellAll.setEnabled(true);
            buttonSellStock.setEnabled(true);
        }
    }

    private void saveAndClose(){
        Log.d(TAG, "saveAndClose");

        Stock s = new Stock();
        int myNum = 0;
        s.setBrand(textBrand.getText().toString());
        s.setColor(textColor.getText().toString());

        try {
            myNum = Integer.parseInt(textPrice.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        s.setPrice(myNum);

        s.setProductName(textProductName.getText().toString());
        try {
            myNum = Integer.parseInt(textStock.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        s.setStock(myNum);
        s.setID(id);

        StockSingleton.get(this).updateStock(position, s);

        setResult(RESULT_OK);
        finish();
    }

    private void sellAllStocks(int position){
        StockSingleton.get(this).sellAllStocks(position);
        loadData(StockSingleton.get(this).getStock(position));
        Toast.makeText(getApplicationContext(),"Sold all!", Toast.LENGTH_SHORT).show();
    }

    private void sellStock(int position){
        StockSingleton.get(this).sellStock(position);
        loadData(StockSingleton.get(this).getStock(position));
    }

    private void buyStock(int position){
        StockSingleton.get(this).buyStock(position);
        loadData(StockSingleton.get(this).getStock(position));
    }

    private void deleteStock(int position){
        StockSingleton.get(this).removeStock(position);
        setResult(RESULT_OK);
        finish();
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonBuyStock:
                    buyStock(position);
                    break;
                case R.id.buttonDeleteFromRecord:
                    deleteStock(position);
                    break;
                case R.id.buttonSellAll:
                   sellAllStocks(position);
                    break;
                case R.id.buttonSellStock:
                    sellStock(position);
                    break;
            }
        }
    }
}
