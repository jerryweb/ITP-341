package itp341.webb.jerry.assignment7;

import java.util.ArrayList;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note note =  getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_note, parent, false);
        }

        // Lookup view for data population
        TextView noteName = (TextView) convertView.findViewById(R.id.noteName);
        TextView noteDate = (TextView) convertView.findViewById(R.id.noteDate);

        // Populate the data into the template view using the data object
        noteName.setText(note.getTitle());
        //year, month ,day

        noteDate.setText(note.getDateFormat());
        // Return the completed view to render on screen
        return convertView;
    }
}
