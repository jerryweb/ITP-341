package itp341.webb.jerry.a5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {//implements MasterFragment.onMasterFragmentListener {

    public static final String EXTRA_CONTACT_INFO_ARRAY = "itp341.webb.jerry.a5";
    public static final String EXTRA_CONTACT_PIC = "itp341.webb.jerry.a5";
    int numUsers = 3;
    FragmentManager manager = getFragmentManager();
    FragmentTransaction fragmentTransaction = manager.beginTransaction();
    MasterFragment fragment = new MasterFragment();
    DetailFragment dFrag = new DetailFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if(savedInstanceState == null){
           // fragment.setArguments();
            fragmentTransaction.add(R.id.fragment_container, fragment, "MasterFragment");
            fragmentTransaction.commit();

        }
    }

    public void onContactSelected(int ID){
        Bundle bundle = new Bundle();

        String[][] contactsInfo = new String[numUsers][];
            contactsInfo[0] =  getResources().getStringArray(R.array.contact_1);
            contactsInfo[1] =  getResources().getStringArray(R.array.contact_2);
            contactsInfo[2] =  getResources().getStringArray(R.array.contact_3);

        int picID = 0;

        switch (ID){
            case R.id.btn_contact_1:
                bundle.putStringArray(EXTRA_CONTACT_INFO_ARRAY, contactsInfo[0]);
                break;
            case R.id.btn_contact_2:
                bundle.putStringArray(EXTRA_CONTACT_INFO_ARRAY, contactsInfo[1]);
                break;
            case R.id.btn_contact_3:
                bundle.putStringArray(EXTRA_CONTACT_INFO_ARRAY, contactsInfo[2]);
                break;
        }
//        FragmentManager manager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        DetailFragment detailFrag =
                (DetailFragment) getFragmentManager().findFragmentById(R.layout.details_fragment);
        if(detailFrag != null){
            //This is in the large screen layout

        }
        else{

            dFrag.setArguments(bundle);
            fragmentTransaction = manager.beginTransaction();
            //fragmentTransaction.hide(fragment);
            fragmentTransaction.addToBackStack(null);

            fragmentTransaction.replace(R.id.fragment_container, dFrag, "DetailFragment");
            fragmentTransaction.commit();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
