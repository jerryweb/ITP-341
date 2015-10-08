package itp341.webb.jerry.a5;

import android.app.Fragment;
import android.os.Bundle;
import android.text.InputType;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by jerrywebb on 10/4/15.
 */
public class DetailFragment extends Fragment {
    MainActivity mainActivity;
    private static final String TAG = MainActivity.class.getSimpleName();
    int id =0;
    String[] contactInfo;


    EditText editNameField;
    EditText editEmailField;
    EditText editPhoneField;
    EditText editAddressField;
    EditText editNotesField;


    ImageView contactImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.details_fragment, container, false);

        if(getArguments() != null) {
            mainActivity = (MainActivity) getActivity();



            editNameField = (EditText) view.findViewById(R.id.editNameField);
            editEmailField = (EditText) view.findViewById(R.id.editTextEmailField);
            editPhoneField = (EditText) view.findViewById(R.id.editTextPhoneField);
            editAddressField = (EditText) view.findViewById(R.id.editTextAddressField);
            editNotesField = (EditText) view.findViewById(R.id.editTextNotes);


            contactImage = (ImageView) view.findViewById(R.id.imageView);

            Bundle bun = getArguments();
            contactInfo = bun.getStringArray(MainActivity.EXTRA_CONTACT_INFO_ARRAY);


            editNameField.setText(contactInfo[0]);
            editEmailField.setText(contactInfo[1]);
            editPhoneField.setText(contactInfo[2]);
            editAddressField.setText(contactInfo[3]);

            switch (bun.getInt(MainActivity.EXTRA_CONTACT_PIC_ID)) {
                case R.id.btn_contact_1:
                    contactImage.setImageResource(R.drawable.contact_1_image);
                    id =0;
                    break;

                case R.id.btn_contact_2:
                    contactImage.setImageResource(R.drawable.contact_2_image);
                    id = 1;
                    break;

                case R.id.btn_contact_3:
                    contactImage.setImageResource(R.drawable.contact_3_image);
                    id =2;
                    break;
            }
        }
        return  view;
    }

    public void allowEditing(boolean allow){

        if(allow) {
            editNameField.setInputType(InputType.TYPE_CLASS_TEXT);
            editEmailField.setInputType(InputType.TYPE_CLASS_TEXT);
            editPhoneField.setInputType(InputType.TYPE_CLASS_TEXT);
            editAddressField.setInputType(InputType.TYPE_CLASS_TEXT);
            editNotesField.setInputType(InputType.TYPE_CLASS_TEXT);



        }
        else{
            editNameField.setInputType(InputType.TYPE_NULL);
            editEmailField.setInputType(InputType.TYPE_NULL);
            editPhoneField.setInputType(InputType.TYPE_NULL);
            editAddressField.setInputType(InputType.TYPE_NULL);
            editNotesField.setInputType(InputType.TYPE_NULL);

            editNameField.setKeyListener(null);
            editEmailField.setKeyListener(null);
            editPhoneField.setKeyListener(null);
            editAddressField.setKeyListener(null);
            editNotesField.setKeyListener(null);

            mainActivity.contact[id][0] = editNameField.getText().toString();
            mainActivity.contact[id][1] = editEmailField.getText().toString();
            mainActivity.contact[id][2] = editPhoneField.getText().toString();
            mainActivity.contact[id][3] = editAddressField.getText().toString();
            mainActivity.contact[id][4] = editNotesField.getText().toString();
        }

    }

 



}
