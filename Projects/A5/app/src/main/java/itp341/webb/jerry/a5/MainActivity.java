package itp341.webb.jerry.a5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_CONTACT_INFO_ARRAY = "itp341.webb.jerry.a5.info";
    public static final String EXTRA_CONTACT_PIC_ID = "itp341.webb.jerry.a5.picID";

    int numUsers = 3;
    int buttonLastClicked =0;
    String contact[][];

    FragmentManager manager = getFragmentManager();
    FragmentTransaction fragmentTransaction = manager.beginTransaction();
    MasterFragment fragment = new MasterFragment();

    ButtonListener btnCListener = new ButtonListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contact = new String[numUsers][5];
        contact[0] =  getResources().getStringArray(R.array.contact_1);
        contact[1] =  getResources().getStringArray(R.array.contact_2);
        contact[2] =  getResources().getStringArray(R.array.contact_3);
        DetailFragment detailFrag = (DetailFragment)getFragmentManager().findFragmentById(R.id.detail_fragment);

        if(detailFrag == null){

            if(savedInstanceState == null){
                fragmentTransaction.add(R.id.fragment_container, fragment, "MainFragment");
                fragmentTransaction.commit();
            }
        }
    }

    public void onContactSelected(int ID){

        Bundle bundle = new Bundle();
        buttonLastClicked = ID;

        Log.d(TAG,"The id of the button pushed is: " + ID);
        switch (ID){
            case R.id.btn_contact_1:
                bundle.putStringArray(EXTRA_CONTACT_INFO_ARRAY, contact[0]);
                bundle.putInt(EXTRA_CONTACT_PIC_ID, ID);
                Log.d(TAG,"in contact 1 switch case");
                break;

            case R.id.btn_contact_2:
                bundle.putStringArray(EXTRA_CONTACT_INFO_ARRAY, contact[1]);
                bundle.putInt(EXTRA_CONTACT_PIC_ID, ID);
                Log.d(TAG, "in contact 2 switch case");
                break;

            case R.id.btn_contact_3:
                bundle.putStringArray(EXTRA_CONTACT_INFO_ARRAY, contact[2]);
                bundle.putInt(EXTRA_CONTACT_PIC_ID, ID);

                Log.d(TAG, "in contact 3 switch case");
                break;
        }

        DetailFragment dFrag = new DetailFragment();
        dFrag.setArguments(bundle);
        DetailFragment detailFrag = (DetailFragment)getFragmentManager().findFragmentById(R.id.detail_fragment);

        if(detailFrag != null){
            fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.large_container_D,dFrag,"DetailFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }
        else{

            fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, dFrag, "DetailFragment");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            onContactSelected(v.getId());

        }
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
        DetailFragment DF = (DetailFragment) getFragmentManager().findFragmentByTag("DetailFragment");
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_edit){
            Toast.makeText(this,"You can edit", Toast.LENGTH_SHORT).show();
            DF.allowEditing(true);
        }
        if (id == R.id.action_save){
            Toast.makeText(this, "You saved the contact", Toast.LENGTH_SHORT).show();
            DF.allowEditing(false);

        }

        return super.onOptionsItemSelected(item);
    }
}
