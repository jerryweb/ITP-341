package itp341.webb.jerry.assigment6;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import itp341.webb.jerry.assigment6.model.CoffeeOrder;

public class MainActivity extends Activity {
    public static final String PREFERENCE_FILENAME = "itp341.exercises.week7.filename";
    public static final String PREFERENCE_NAME = "itp341.webb.jerry.name";
    public static final String PREFERENCE_SIZE = "itp341.webb.jerry.size";
    public static final String PREFERENCE_BREW = "itp341.webb.jerry.brew";
    public static final String PREFERENCE_SUGAR = "itp341.webb.jerry.sugar";
    public static final String PREFERENCE_MILK = "itp341.webb.jerry.milk";
    public static final String PREFERENCE_SPECIAL_INSTRUCTIONS = "itp341.webb.jerry.specialInstructions";
    public static final String PREFERENCE_RATING = "itp341.webb.jerry.rating";
    private static final String TAG = MainActivity.class.getSimpleName();

    String[] brewChoices;
    Button buttonClear;
    Button buttonLoad;
    Button buttonOrder;
    Button buttonSave;
    CheckBox checkMilk;
    EditText editSpecialInstruction;
    EditText editName;
    RadioGroup radioGroupSize;
    Spinner spinnerBrew;
    Switch switchSugar;

    SharedPreferences prefs;

    ButtonListener buttonListener = new ButtonListener();

    CoffeeOrder order = new CoffeeOrder("New User", R.id.radioButtonSmall, "iced", false, false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        brewChoices = getResources().getStringArray(R.array.brew_array);

        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonLoad = (Button) findViewById(R.id.buttonLoadFav);
        buttonOrder = (Button) findViewById(R.id.buttonOrder);
        buttonSave = (Button) findViewById(R.id.buttonSaveFav);
        checkMilk = (CheckBox) findViewById(R.id.checkBoxMilk);
        editSpecialInstruction = (EditText) findViewById(R.id.editTextSpecialInstruction);
        editName = (EditText) findViewById(R.id.editName);
        radioGroupSize = (RadioGroup) findViewById(R.id.radioGroupSize);
        spinnerBrew = (Spinner) findViewById(R.id.spinnerBrewChoices);
        switchSugar = (Switch) findViewById(R.id.switchSugar);

        buttonClear.setOnClickListener(buttonListener);
        buttonSave.setOnClickListener(buttonListener);
        buttonLoad.setOnClickListener(buttonListener);
        buttonOrder.setOnClickListener(buttonListener);

        spinnerBrew.setSelection(0);


        radioGroupSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                order.setSize(checkedId);
//                switch (checkedId) {
//                    case R.id.radioButtonSmall:
//                        order.setSize("small");
//                        break;
//                    case R.id.radioButtonMedium:
//                        order.setSize("medium");
//                        break;
//                    case R.id.radioButtonLarge:
//                        order.setSize("large");
//                        break;
//                }
            }
        });

        spinnerBrew.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                order.setBrew(brewChoices[position]);
                order.setBrewPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        checkMilk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "checkMilk onCheckedChanged has been triggered");
                order.setMilkOrCream(isChecked);
            }
        });

        switchSugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                order.setSugar(isChecked);
            }
        });

        editSpecialInstruction.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                Log.d(TAG, "inside onEditorAction for editSpecialInstruction");
                order.setSpecialInstructions(editSpecialInstruction.getText().toString());
                return false;
            }
        });

        editName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.d(TAG, "inside onEditorAction for editName");
                order.setName(editName.getText().toString());
                return false;
            }
        });

    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonSaveFav:
                    prefs = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                    SharedPreferences.Editor preEditor = prefs.edit();

                    preEditor.putString(PREFERENCE_NAME, order.getName());
                    preEditor.putString(PREFERENCE_BREW, order.getBrew());
                    preEditor.putInt(PREFERENCE_SIZE, order.getSize());
                    preEditor.putString(PREFERENCE_SPECIAL_INSTRUCTIONS, order.getSpecialInstructions());
                    preEditor.putBoolean(PREFERENCE_MILK, order.isMilkOrCream());
                    preEditor.putBoolean(PREFERENCE_SUGAR, order.isSugar());
                    preEditor.putInt(PREFERENCE_RATING, order.getRating());
                    preEditor.commit();

                    Toast.makeText(getApplicationContext(),"Saved user preferences!",Toast.LENGTH_SHORT).show();
                    break;

                case R.id.buttonLoadFav:
                    prefs = getSharedPreferences(PREFERENCE_FILENAME, MODE_PRIVATE);
                    order.setName(prefs.getString(PREFERENCE_NAME, "New Customer"));
                    order.setBrew(prefs.getString(PREFERENCE_BREW, "Iced"));
                    order.setSize(prefs.getInt(PREFERENCE_SIZE, R.id.radioButtonSmall));
                    order.setSpecialInstructions(prefs.getString(PREFERENCE_SPECIAL_INSTRUCTIONS, ""));
                    order.setMilkOrCream(prefs.getBoolean(PREFERENCE_MILK, false));
                    order.setSugar(prefs.getBoolean(PREFERENCE_SUGAR, false));
                    order.setRating(prefs.getInt(PREFERENCE_RATING, 0));

                    refreshView();
                    Toast.makeText(getApplicationContext(),"Loaded user preferences!",Toast.LENGTH_SHORT).show();

                    break;

                case R.id.buttonClear:
                    order.setName("New Customer");
                    order.setBrew("Iced");
                    order.setSize(R.id.radioButtonSmall);
                    order.setSpecialInstructions("");
                    order.setMilkOrCream(false);
                    order.setSugar(false);
                    order.setRating(0);

                    refreshView();
                    break;
                case R.id.buttonOrder:
                    Intent i = new Intent(getApplicationContext(), ViewOrderActivity.class);
                    i.putExtra(ViewOrderActivity.EXTRA_COFFEE_ORDER, order);
                    startActivityForResult(i,0);
                    break;
            }
        }
    }

    public void refreshView(){
        editSpecialInstruction.setText(order.getSpecialInstructions());
        spinnerBrew.setSelection(order.getBrewPosition());
        switchSugar.setChecked(order.isSugar());
        checkMilk.setChecked(order.isMilkOrCream());
        radioGroupSize.check(order.getSize());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
