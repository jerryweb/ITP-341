package itp341.exercises.week9_list_singleton.app;

import itp341.exercises.week9_list_singleton.R;
import itp341.exercises.week9_list_singleton.app.model.CoffeeShop;
import itp341.exercises.week9_list_singleton.app.model.CoffeeShopSingleton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class AddEditCoffeeShopListing extends Activity {

    private static final String TAG = AddEditCoffeeShopListing.class
            .getSimpleName();

    //TODO how will we pass data from ViewAllCoffeeShop activity?
    public static final String EXTRA_COFFEE_SHOP_POSITION = "extra_coffee_shop_position";

    EditText editName;
    EditText editAddress;
    EditText editCity;
    Spinner spinnerState;
    EditText editZip;
    EditText editPhone;
    EditText editWebsite;
    Button buttonSaveListing;
    Button buttonDeleteListing;

    int position;   //position of clicked coffee shop

    public static String[] states; // read from arrays.xml for US states
    ArrayAdapter<CharSequence> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_coffee_shop_listing);

        //findViews
        editName = (EditText) findViewById(R.id.edit_name);
        editAddress = (EditText) findViewById(R.id.edit_address);
        editCity = (EditText) findViewById(R.id.edit_city);
        spinnerState = (Spinner) findViewById(R.id.spinner_state); // update
        editZip = (EditText) findViewById(R.id.edit_zip);
        editPhone = (EditText) findViewById(R.id.edit_phone);
        editWebsite = (EditText) findViewById(R.id.edit_website);
        buttonSaveListing = (Button) findViewById(R.id.button_save_listing);
        buttonDeleteListing = (Button) findViewById(R.id.button_delete_listing);

        //load spinner values for States in the coffee shop address
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.states,
                android.R.layout.simple_spinner_item);
        spinnerState.setAdapter(spinnerAdapter);

        // format phone edit text
        editPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

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

        //TODO check if this is an existing or new listing
        Intent i = getIntent();
        position = i.getIntExtra(EXTRA_COFFEE_SHOP_POSITION, -1); //-1 is the default because we
        // don't want to access the array, and we can use it to indicate a new listing to add

        if(position != -1){     //existing coffee shop listing
            CoffeeShop cs = CoffeeShopSingleton.get(this).getCoffeeShop(position);
            if(cs != null){
                loadData(cs);
            }
        }

        else {  //this must be a new entry
            buttonDeleteListing.setEnabled(false);

        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

    }

    //TODO load data from existing coffee shop object
    private void loadData(CoffeeShop cs) {
        editName.setText(cs.getName());
        editAddress.setText(cs.getAddress());
        editCity.setText(cs.getCity());
        editZip.setText(cs.getZip());
        editPhone.setText(cs.getPhone());
        editWebsite.setText(cs.getWebsite());

//        int spinnerPos = spinnerAdapter.getPosition(cs.getState())
        spinnerState.setSelection(spinnerAdapter.getPosition(cs.getState()));
    }

    //TODO Listing should be saved (updated if existing, or added if new)
    private void saveAndClose() {
        Log.d(TAG, "saveAndClose");

        CoffeeShop cs = new CoffeeShop();
        cs.setName(editName.getText().toString());
        cs.setAddress(editAddress.getText().toString());
        cs.setCity(editCity.getText().toString());
        cs.setZip(editZip.getText().toString());
        cs.setPhone(editPhone.getText().toString());
        cs.setWebsite(editWebsite.getText().toString());

        TextView tv = (TextView) spinnerState.getSelectedView();
        cs.setState(tv.getText().toString());

        if(tv != null){
            cs.setState(tv.getText().toString());
        }

        if(position != -1){ //existing entry
            CoffeeShopSingleton.get(this).updateCoffeeShop(position, cs);
        }

        else {
            CoffeeShopSingleton.get(this).addCoffeeShop(cs);
        }

        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        CoffeeShopSingleton.get(this).saveCoffeeShops();
    }

    //TODO Listing should be deleted (only it was an existing entry, not if it was new))
    private void deleteAndClose() {
        Log.d(TAG, "deleteAndClose");
        CoffeeShopSingleton.get(this).removeCoffeeShop(position);
        setResult(RESULT_OK);
        finish();
    }

}
