package itp341.exercises.week12_singleton_sqlite_asynctask.app;

import itp341.exercises.week12_singleton_sqlite_asynctask.R;
import itp341.exercises.week12_singleton_sqlite_asynctask.app.model.CoffeeShop;
import itp341.exercises.week12_singleton_sqlite_asynctask.app.model.CoffeeShopSingleton;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import itp341.exercises.week12_singleton_sqlite_asynctask.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;

public class AddEditCoffeeShopListing extends Activity {

    private static final String TAG = AddEditCoffeeShopListing.class
            .getSimpleName();


    public static final String EXTRA_COFFEE_SHOP_ID = "extra_coffee_shop_id";

    EditText editName;
    EditText editRating;

    Button buttonSaveListing;
    Button buttonDeleteListing;


    long id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_coffee_shop_listing);

        //findViews
        editName = (EditText) findViewById(R.id.edit_name);
        editRating = (EditText) findViewById(R.id.edit_rating);

        buttonSaveListing = (Button) findViewById(R.id.button_save_listing);
        buttonDeleteListing = (Button) findViewById(R.id.button_delete_listing);

        //button listeners
        buttonDeleteListing.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteAndClose();

            }
        });
        buttonSaveListing.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                saveAndClose();

            }
        });

        //check if this is an existing or new listing
        Intent i = getIntent();

        id = i.getLongExtra(EXTRA_COFFEE_SHOP_ID, -1);

//TODO AsT
        if (id != -1) { //this means we are editing an existing listing
            Cursor c = CoffeeShopSingleton.get(this).getCoffeeShop(id);
            if (c != null) { // this means we are editing old record

                if (c.moveToFirst()) {    // otherwise something weird happened but necesssary for android
                    editName.setText(c.getString(TABLE_COFFEE_SHOPS.COLUMN_NAME));
                    editRating.setText(c.getString(TABLE_COFFEE_SHOPS.COLUMN_RATING));


                }
            }

        }else {//else means this should be a new (blank) entry
            buttonDeleteListing.setEnabled(false); //shouldn't be able to "delete" new entry
        }


    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause -- saving coffeeShops");

    }


    // Listing should be saved (updated if existing, or added if new)
    private void saveAndClose() {
        Log.d(TAG, "saveAndClose");
        CoffeeShop coffeeShop = new CoffeeShop();


        coffeeShop.setName(editName.getText().toString());
        coffeeShop.setRating(Double.parseDouble(editRating.getText().toString()));

//TODO AsT
        if (id != -1) {    //this was an existing list we should update
            Log.d(TAG, "Save and Close (update existing): id = " + id);
            CoffeeShopSingleton.get(this).updateCoffeeShop(id, coffeeShop);
        } else {  //this is a new list we should add
            Log.d(TAG, "Save and Close (add new): id = " + id);
            CoffeeShopSingleton.get(this).addCoffeeShop(coffeeShop);

        }

        setResult(RESULT_OK);
        finish();

    }


    // Listing should be deleted (only it was an existing entry, not if it was new))
    private void deleteAndClose() {
        Log.d(TAG, "onClick");

        if (id != -1) {    //this was an existing list we should update
            CoffeeShopSingleton.get(this).removeCoffeeShop(id);
        }
        setResult(RESULT_OK);
        finish();

    }




}
