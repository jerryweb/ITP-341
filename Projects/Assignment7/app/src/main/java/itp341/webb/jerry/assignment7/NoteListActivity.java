package itp341.webb.jerry.assignment7;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import itp341.webb.jerry.assignment7.model.Note;
import itp341.webb.jerry.assignment7.model.NoteSingleton;

public class NoteListActivity extends Activity {
    public static final String TAG = "itp341.webb.jerry.assignment7.tag";

    ArrayList <Note> notes;
    NoteAdapter noteAdapter;
    Button buttonAdd;
    ListView listNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_list_activity);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        listNotes = (ListView) findViewById(R.id.listNotes);

        // Construct the data source
        notes = NoteSingleton.get(this).getmNotes();
        noteAdapter = new NoteAdapter(this, notes);
        listNotes.setAdapter(noteAdapter);

        //This will switch to the edit activity for saving the notes
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "moving to EditActivity");
                Intent i = new Intent(getApplicationContext(),NoteEditActivity.class);
                startActivityForResult(i, 0);
            }
        });

        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "selected item");

                Intent i = new Intent(getApplicationContext(), NoteEditActivity.class);
                i.putExtra(NoteEditActivity.EXTRA_NOTE_POSITION,
                        position);
                startActivityForResult(i, 0);
            }
        });

        listNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Long click listener activated");
//                openContextMenu();
                return false;
            }
        });

    }

//    protected void onPause(){
//        // Called after onStart() as Activity comes to foreground.
//        Log.d(TAG, "onResume");
//        super.onPause();
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            noteAdapter.notifyDataSetChanged();
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
