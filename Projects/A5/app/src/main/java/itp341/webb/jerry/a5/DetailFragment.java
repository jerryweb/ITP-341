package itp341.webb.jerry.a5;

import android.app.Fragment;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by jerrywebb on 10/4/15.
 */
public class DetailFragment extends Fragment {

    String[] contactInfo;

    TextView textNameDisplay;
    TextView textEmailDisplay;
    TextView textNumberDisplay;
    TextView textAddressDisplay;
    TextView textNotesDisplay;

    EditText editNameField;
    EditText editEmailField;
    EditText editPhoneField;
    EditText editAddressField;
    EditText editNotesField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.details_fragment, container, false);

        textNameDisplay = (TextView) view.findViewById(R.id.editNameField);
        textEmailDisplay = (TextView) view.findViewById(R.id.textEmailDisplay);

        //editNameField = (EditText) view.findViewById(R.id.editNameField);
        editEmailField = (EditText) view.findViewById(R.id.editTextEmailField);
        editPhoneField = (EditText) view.findViewById(R.id.editTextPhoneField);
        editAddressField = (EditText) view.findViewById(R.id.editTextAddressField);
//        editNotesField = (EditText) view.findViewById(R.id.editNotesField);



        SpannableString nameUnderlined = new SpannableString("Name");
//        SpannableString emailUnderlined = new SpannableString(getResources().getString(R.string.email));
//        SpannableString addressUnderlined = new SpannableString(getResources().getString(R.string.address));
//        SpannableString numberUnderlined = new SpannableString(getResources().getString(R.string.phone));
//        SpannableString notesUnderlined = new SpannableString(getResources().getString(R.string.phone));

        nameUnderlined.setSpan(new UnderlineSpan(), 0, nameUnderlined.length(),0);

        textNameDisplay.setText(nameUnderlined);


        return  view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        contactInfo = bundle.getStringArray(MainActivity.EXTRA_CONTACT_INFO_ARRAY);

        textNameDisplay.setText(contactInfo[0]);
        editEmailField.setText(contactInfo[1]);
        editPhoneField.setText(contactInfo[2]);
        editAddressField.setText(contactInfo[3]);
    }
}
