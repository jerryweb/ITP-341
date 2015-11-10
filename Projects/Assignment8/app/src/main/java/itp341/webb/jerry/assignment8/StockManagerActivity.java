package itp341.webb.jerry.assignment8;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import itp341.webb.jerry.assignment8.Model.Stock;
import itp341.webb.jerry.assignment8.Model.StockSingleton;

public class StockManagerActivity extends Activity {
    public static final String EXTRA_STOCK_POSITION = "itp341.webb.jerry.assignment.position";

    public static final String TAG = "itp341.webb.jerry.assignment7.tag";
    Button buttonAdd;
    ListView listStock;
    ArrayList<Stock> stocks;
    StockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_manager);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listStock = (ListView) findViewById(R.id.listStocks);

        stocks = StockSingleton.get(this).getmStocks();
        adapter = new StockAdapter(this, stocks);
        listStock.setAdapter(adapter);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),AddStockActivity.class);
                startActivityForResult(i,0);
            }
        });


        listStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), BuySellStock.class);
                i.putExtra(EXTRA_STOCK_POSITION, position);
                startActivityForResult(i, 0);
                Log.d(TAG, "starting buySellStock activity");

//                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stock_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
