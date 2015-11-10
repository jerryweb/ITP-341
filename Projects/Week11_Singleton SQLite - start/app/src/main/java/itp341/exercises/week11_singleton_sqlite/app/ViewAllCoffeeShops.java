package itp341.exercises.week11_singleton_sqlite.app;

import itp341.exercises.week11_singleton_sqlite.R;
import itp341.exercises.week11_singleton_sqlite.app.database.CoffeeShopDbHelper;
import itp341.exercises.week11_singleton_sqlite.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;
import itp341.exercises.week11_singleton_sqlite.app.model.CoffeeShopSingleton;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewAllCoffeeShops extends Activity {

    private static final String TAG = ViewAllCoffeeShops.class.getSimpleName();

    Button buttonAdd;
    ListView listView;

    //TODO DB - add members
    Cursor c; //CursorWrapper
    SimpleCursorAdapter cursorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_coffee_shops);
        Log.d(TAG, "onCreate");

        //find views
        buttonAdd = (Button) findViewById(R.id.button_add);
        listView = (ListView)findViewById(R.id.listView);

        loadData();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        AddEditCoffeeShopListing.class);
                startActivityForResult(i, 0);
            }
        });

        //TODO  DB - modify
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "onListItemClick");
                Toast.makeText(getApplicationContext(),
                        "Id: " + id + ", position: " + position, Toast.LENGTH_SHORT)
                        .show();

                Intent i = new Intent(getApplicationContext(), AddEditCoffeeShopListing.class);
                i.putExtra(AddEditCoffeeShopListing.EXTRA_COFFEE_SHOP_ID, id);  //we need to use id because position doesn't matter
                startActivityForResult(i, 0);
            }
        });

    }

    //TODO DB - modify
    private void loadData() {
        //What columns are we pulling from the table and displaying on the screen
        String[] from = {TABLE_COFFEE_SHOPS.KEY_NAME};
        //Array of android ids
        int[] to = {android.R.id.text1};

        //get cursor
        c = CoffeeShopSingleton.get(this).getCoffeeShops();
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                c, from, to, 0);    //the last argument is the flag (we can just put 0 for the most part

        listView.setAdapter(cursorAdapter);

    }
    protected void onPause() {
        // Called after onStart() as Activity comes to foreground.
        Log.d(TAG, "onPause");
        super.onPause();

    }

    //TODO finish onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: resultCode: " + resultCode);

        if (resultCode == Activity.RESULT_OK) { // this means user saved
            Log.d(TAG, "onActivityResult - NotifyDataset changed");
            loadData();
            cursorAdapter.notifyDataSetChanged();

        }
        //else means they pressed back and didn't save

    }
}
