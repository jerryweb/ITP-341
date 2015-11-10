package acad276.parke.rob.mvc_example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity_no_palette extends AppCompatActivity {
    EditText editName;
    Spinner spinnerColor;
    EditText editAge;
    Button buttonAdd;
    ListView listview;
    ImageView imageView;

    ArrayList<Shiba> dogs;
    ArrayAdapter<Shiba> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = (EditText) findViewById(R.id.editName);
        spinnerColor = (Spinner) findViewById(R.id.spinnerColor);
        editAge = (EditText) findViewById(R.id.editAge);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listview = (ListView) findViewById(R.id.listView);
        imageView = (ImageView) findViewById(R.id.imageView);

        //TODO edit here
        dogs = new ArrayList<>();
        adapter = new ArrayAdapter<Shiba>(this, android.R.layout.simple_list_item_1, dogs);
        listview.setAdapter(adapter);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Shiba s = new Shiba();
                s.setName(editName.getText().toString());
                s.setAge(Integer.parseInt(editAge.getText().toString()));


                s.setColor(spinnerColor.getSelectedItem().toString());

                dogs.add(s);
                adapter.notifyDataSetChanged();
                Log.d("Shiba", s.toString());
                Log.d("Shiba array", dogs.toString());
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Shiba s = dogs.get(i);
                editName.setText(s.getName());
                editAge.setText(String.valueOf(s.getAge()));
                Log.d("Shiba List", s.toString());

                if (s.getColor().equalsIgnoreCase("black")) {
                    imageView.setImageResource(R.drawable.shiba_black);
                }
                else if (s.getColor().equalsIgnoreCase("sesame")) {
                    imageView.setImageResource(R.drawable.shiba_sesame);
                }
                else {
                    imageView.setImageResource(R.drawable.shiba_white);
                }

            }
        });


    }
}
