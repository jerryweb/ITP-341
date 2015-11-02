package itp341.webb.jerry.assignment7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import itp341.webb.jerry.assignment7.model.Note;
import itp341.webb.jerry.assignment7.model.NoteSingleton;

/**
 * Created by jerrywebb on 10/27/15.
 */
public class NoteEditActivity extends Activity {
    public static final String EXTRA_NOTE_POSITION = "itp341.webb.jerry.assignment7.notePosition";
    private static final String TAG = NoteEditActivity.class
            .getSimpleName();

    int position;

    Button buttonSave;
    Button buttonDelete;
    EditText editTitle;
    EditText editContent;

    ButtonListener buttonListener;
    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_edit_activity);

        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTitle = (EditText) findViewById(R.id.editTitle);
        editContent = (EditText) findViewById(R.id.editContent);

        buttonListener = new ButtonListener();

        buttonSave.setOnClickListener(buttonListener);
        buttonDelete.setOnClickListener(buttonListener);

        Intent i = getIntent();
        position = i.getIntExtra(EXTRA_NOTE_POSITION,-1);

        if (position != -1){
            Note n = NoteSingleton.get(this).getNote(position);
            loadData(n);
        }
        else {
            buttonDelete.setEnabled(false);
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        NoteSingleton.get(this).save();
//    }

    private void loadData(Note note){
        editTitle.setText(note.getTitle());
        editContent.setText(note.getContent());
    }

    private void saveAndClose(){

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        String formattedDate = df.format(calendar.getTime());

        Log.d(TAG, "saving and closing");

        Note nt = new Note();
        nt.setTitle(editTitle.getText().toString());
        nt.setContent(editContent.getText().toString());
        nt.setDateFormat(formattedDate);
        if(position != -1){ //existing entry
            NoteSingleton.get(this).updateNote(position, nt);
        }
        else {
            NoteSingleton.get(this).addNote(nt);
        }
        setResult(RESULT_OK);
        finish();
    }

    private void deleteAndClose(){
        Log.d(TAG, "deleteAndClose");
        NoteSingleton.get(this).removeNote(position);
        setResult(RESULT_OK);
        finish();
    }

    private class ButtonListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.buttonSave:
                    saveAndClose();
                    break;
                case R.id.buttonDelete:
                    deleteAndClose();
                    break;
            }
        }
    }
}
