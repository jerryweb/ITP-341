package itp341.webb.jerry.a5;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Array;

/**
 * Created by jerrywebb on 10/2/15.
 */
public class MasterFragment extends Fragment {
    MainActivity mainActivity;

    Button btnContact1;
    Button btnContact2;
    Button btnContact3;

    ButtonListener btnCListener = new ButtonListener();
    String [] contacts;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.master_fragment, container, false);

        return  view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mainActivity = (MainActivity)getActivity();

        contacts = getResources().getStringArray(R.array.name_array);
        btnContact1 = (Button) mainActivity.findViewById(R.id.btn_contact_1);
        btnContact2 = (Button) mainActivity.findViewById(R.id.btn_contact_2);
        btnContact3 = (Button) mainActivity.findViewById(R.id.btn_contact_3);

        btnContact1.setOnClickListener(btnCListener);
        btnContact2.setOnClickListener(btnCListener);
        btnContact3.setOnClickListener(btnCListener);
        getArguments();
    }



    @Override
    public void onStart() {
        super.onStart();



    }

    public class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mainActivity.onContactSelected(v.getId());

        }
    }
}
