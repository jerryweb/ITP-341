package itp341.webb.jerry.assignment8;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import itp341.webb.jerry.assignment8.Controller.StockAdapter;
import itp341.webb.jerry.assignment8.Model.Stock;
import itp341.webb.jerry.assignment8.Model.StockSingleton;

public class StockManagerActivity extends Activity {

    ImageButton imageBtnAdd;
    ListView listStock;
    ArrayList<Stock> stocks;
    StockAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_manager);

        imageBtnAdd = (ImageButton) findViewById(R.id.imageBtnAdd);
        listStock = (ListView) findViewById(R.id.listStocks);

        stocks = StockSingleton.get(this).getmStocks();
        adapter = new StockAdapter(this, stocks);
        listStock.setAdapter(adapter);

        imageBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        listStock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
