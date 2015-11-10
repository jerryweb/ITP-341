package acad276.parke.rob.mvc_example;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView textName;
    TextView textAge;
    TextView textColor;
    EditText editName;
    Spinner spinnerColor;
    EditText editAge;
    Button buttonAdd;
    ListView listview;
    ImageView imageView;
    LinearLayout layout;

    ArrayList<Shiba> dogs;
    ArrayAdapter<Shiba> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textName = (TextView) findViewById(R.id.textName);
        textAge = (TextView) findViewById(R.id.textAge);
        textColor = (TextView) findViewById(R.id.textColor);
        editName = (EditText) findViewById(R.id.editName);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        editAge = (EditText) findViewById(R.id.editAge);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listview = (ListView) findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.imageView);
        layout = (LinearLayout) findViewById(R.id.outerLayout);


        dogs = new ArrayList<>();
        adapter = new ArrayAdapter<Shiba>(this, android.R.layout.simple_list_item_1, dogs);
        listview.setAdapter(adapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shiba s = new Shiba();
                s.setName(editName.getText().toString());

                String tempInt = editAge.getText().toString();
                if (tempInt.equals(""))
                    s.setAge(0);
                else
                    s.setAge(Integer.parseInt(tempInt));


                s.setColor(spinnerColor.getSelectedItem().toString());

                dogs.add(s);
                adapter.notifyDataSetChanged();
                Log.d("Shiba", s.toString());
                Log.d("Shiba array", dogs.toString());
            }
        });

        //TODO create listview listener (ie what happens when you click the list)
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                



            }
        });


    }
    //TODO - Palette - change colors
    /*
    Common available Paletter colors
        Vibrant
        Vibrant Dark
        Vibrant Light
        Muted
        Muted Dark
        Muted Light
    */

//TODO - Palette Task

}

