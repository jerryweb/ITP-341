package itp341.webb.jerry.assignment7;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
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
    private ActionMode mActionMode;
    int positionForDelete = 0;

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

        //This allows access to an Action Context Menu that allows for direct deletion of notes from
        //the list activity
        listNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Long click listener activated");
                if(mActionMode != null){
                    return false;
                }
                positionForDelete = position;
                // Start the CAB using the ActionMode.Callback defined above
                mActionMode = startActionMode(mActionModeCallback);
                view.setSelected(true);
                return true;
            }
        });

    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater menuInflater = mode.getMenuInflater();
            menuInflater.inflate(R.menu.context_menu, menu);
            return true;
        }
        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch(item.getItemId()){
                case R.id.contextMenu:
                    NoteSingleton.get(getApplicationContext()).removeNote(positionForDelete);
                    noteAdapter.notifyDataSetChanged();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;

            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };


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
