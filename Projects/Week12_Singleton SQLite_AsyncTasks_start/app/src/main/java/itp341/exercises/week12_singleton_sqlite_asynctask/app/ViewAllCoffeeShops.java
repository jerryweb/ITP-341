package itp341.exercises.week12_singleton_sqlite_asynctask.app;

import itp341.exercises.week12_singleton_sqlite_asynctask.R;
import itp341.exercises.week12_singleton_sqlite_asynctask.app.model.CoffeeShopSingleton;
import itp341.exercises.week12_singleton_sqlite_asynctask.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;


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


    Cursor c;
    SimpleCursorAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_all_coffee_shops);
        Log.d(TAG, "onCreate");

        //find views
        buttonAdd = (Button) findViewById(R.id.button_add);
        listView = (ListView) findViewById(R.id.listView);


        loadData();


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        AddEditCoffeeShopListing.class);
                startActivityForResult(i, 0);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Log.d(TAG, "onListItemClick");
                Toast.makeText(getApplicationContext(),
                        "Id: " + id + ", position: " + position, Toast.LENGTH_SHORT)
                        .show();

                Intent i = new Intent(getApplicationContext(),
                        AddEditCoffeeShopListing.class);
                i.putExtra(AddEditCoffeeShopListing.EXTRA_COFFEE_SHOP_ID, id);

                startActivityForResult(i, 0);

            }
        });

    }

    //TODO AsT - modify
    private void loadData() {

         String[] from = new String[] {TABLE_COFFEE_SHOPS.KEY_NAME};
        int[] to = new int[] {android.R.id.text1};


        c = CoffeeShopSingleton.get(this).getCoffeeShops();
        adapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                c,
                from,
                to,
                0);

        listView.setAdapter(adapter);
    }

    protected void onPause() {
        // Called after onStart() as Activity comes to foreground.
        Log.d(TAG, "onResume");
        super.onPause();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: resultCode: " + resultCode);

        if (resultCode == Activity.RESULT_OK) { // this means user saved
            Log.d(TAG, "onActivityResult - NotifyDAtaset changed");
            loadData();
            adapter.notifyDataSetChanged();
        }
        //else means they pressed back and didn't save

    }

}
