package itp341.webb.jerry.assignment7;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import itp341.webb.jerry.assignment7.model.Note;

/**
 * Created by jerrywebb on 10/31/15.
 */
public class NoteAdapter extends ArrayAdapter<Note> {
    public NoteAdapter(Context context, ArrayList<Note> note) {
        super(context, 0, note);
    }

    int[] date;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note =  getItem(position);
        date = note.getDate();

//        date[0] = calendar.get(Calendar.MONTH);
//        date[1] = calendar.get(Calendar.DAY_OF_MONTH);
//        date[2] = calendar.get(Calendar.YEAR);
        //date = note.getDate();

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
        }

        // Lookup view for data population
        TextView noteName = (TextView) convertView.findViewById(R.id.noteName);
        TextView noteDate = (TextView) convertView.findViewById(R.id.noteDate);

        // Populate the data into the template view using the data object
        noteName.setText(note.getTitle());
        //year, month ,day
//        calendar.set(date[2],date[0],date[1]);

        noteDate.setText(note.getDateFormat());
        // Return the completed view to render on screen
        return convertView;
    }
}
