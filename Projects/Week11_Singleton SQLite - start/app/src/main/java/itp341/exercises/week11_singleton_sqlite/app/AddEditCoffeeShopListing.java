package itp341.exercises.week11_singleton_sqlite.app;

import itp341.exercises.week11_singleton_sqlite.R;
import itp341.exercises.week11_singleton_sqlite.app.database.CoffeeShopDbSchema.TABLE_COFFEE_SHOPS;
import itp341.exercises.week11_singleton_sqlite.app.model.CoffeeShop;
import itp341.exercises.week11_singleton_sqlite.app.model.CoffeeShopSingleton;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddEditCoffeeShopListing extends Activity {

    private static final String TAG = AddEditCoffeeShopListing.class
            .getSimpleName();

   //TODO DB - key for ID
    public static final String EXTRA_COFFEE_SHOP_ID = "extra_coffee_shop_id";

    EditText editName;
    EditText editRating;

    Button buttonSaveListing;
    Button buttonDeleteListing;


    //TODO DB - modify
    private long id = -1; //never will have a record with a negative value, so this tells us that it hasn't been set


    //TODO DB - modify
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
        //TODO DB  - modify
        Intent i = getIntent();
        id = i.getLongExtra(EXTRA_COFFEE_SHOP_ID, -1);

        if (id != -1){ //existing record and we need to pull data from DB to populate Views
            Cursor c = CoffeeShopSingleton.get(this).getCoffeeShop(id);
            if(c != null){
                if(c.moveToFirst()){    //Are there valid rows in the query
                    String name = c.getString(TABLE_COFFEE_SHOPS.COLUMN_NAME);
                    String rating = c.getString(TABLE_COFFEE_SHOPS.COLUMN_RATING);

                    editName.setText(name);
                    editRating.setText(rating);
                }
            }
        }
        else { //this is a new record
            buttonDeleteListing.setEnabled(false);
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

        CoffeeShop cs = new CoffeeShop();
        cs.setName(editName.getText().toString());
        cs.setRating(Double.parseDouble(editRating.getText().toString()));

        if(id != -1) { //this indicates this is an existing record
            CoffeeShopSingleton.get(this).updateCoffeeShop(id, cs);
        }
        else {
            CoffeeShopSingleton.get(this).addCoffeeShop(cs);
        }
        setResult(Activity.RESULT_OK);
        finish();

    }


    //TODO DB - delete record
    // Listing should be deleted (only it was an existing entry, not if it was new))
    private void deleteAndClose() {
        Log.d(TAG, "onClick");

        CoffeeShopSingleton.get(this).removeCoffeeShop(id);
        setResult(Activity.RESULT_OK);
        finish();
    }

}
