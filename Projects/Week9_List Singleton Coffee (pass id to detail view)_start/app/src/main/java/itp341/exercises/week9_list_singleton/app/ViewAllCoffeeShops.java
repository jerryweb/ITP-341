package itp341.exercises.week9_list_singleton.app;

import itp341.exercises.week9_list_singleton.R;
import itp341.exercises.week9_list_singleton.app.model.CoffeeShop;
import itp341.exercises.week9_list_singleton.app.model.CoffeeShopSingleton;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ViewAllCoffeeShops extends Activity {

    private static final String TAG = ViewAllCoffeeShops.class.getSimpleName();

    Button buttonAdd;
    ListView listView;

    //create the arraylist and adapter
    //connect the arraylist and adapter
    ArrayList <CoffeeShop> coffeeShops;
    ArrayAdapter<CoffeeShop> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_coffee_shops);
        Log.d(TAG, "onCreate");

        //find views
        buttonAdd = (Button) findViewById(R.id.button_add);
        listView = (ListView) findViewById(R.id.listView);


        //TODO access coffee shop list and load it in the list
        coffeeShops = CoffeeShopSingleton.get(this).getmCoffeeShops();
        adapter = new ArrayAdapter<CoffeeShop>(this, android.R.layout.simple_list_item_1, coffeeShops);
        listView.setAdapter(adapter);

        //TODO What happens when user clicks add?
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),AddEditCoffeeShopListing.class);
                startActivityForResult(i, 0);
            }
        });

        //TODO create listview item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), AddEditCoffeeShopListing.class);

                i.putExtra(AddEditCoffeeShopListing.EXTRA_COFFEE_SHOP_POSITION,
                        position);
                startActivityForResult(i, 0);
            }
        });

    }


    protected void onPause() {
        // Called after onStart() as Activity comes to foreground.
        Log.d(TAG, "onResume");
        super.onPause();

    }

    //TODO finish onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode: " + requestCode);

        if(resultCode ==  Activity.RESULT_OK) {
            adapter.notifyDataSetChanged();
        }

    }
}
