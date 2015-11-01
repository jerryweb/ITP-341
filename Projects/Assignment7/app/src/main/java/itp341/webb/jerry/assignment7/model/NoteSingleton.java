package itp341.webb.jerry.assignment7.model;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by jerrywebb on 10/28/15.
 */
public class NoteSingleton {

    ArrayList<Note> mNotes;
    private static NoteSingleton sNoteSingleton;
    private Context mAppContext;
     Calendar calendar = Calendar.getInstance();
    int[] date = new int[3];


    private NoteSingleton(Context c){
        mNotes = new ArrayList<Note>();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(calendar.getTime());

        date[0] = calendar.get(Calendar.MONTH);
        date[1] = calendar.get(Calendar.DAY_OF_MONTH);
        date[2] = calendar.get(Calendar.YEAR);

        this.mAppContext = c;

        for(int i = 0; i<3;i++){
            Note n = new Note("Blank Note "+i, "  ", date);
            n.setDateFormat(formattedDate);
            mNotes.add(n);
        }
    }

    public static NoteSingleton get(Context c){
        if(sNoteSingleton == null)   {
            sNoteSingleton = new NoteSingleton(c.getApplicationContext());
        }
        return sNoteSingleton;
    }

    //Data retrieval methods
    public ArrayList<Note> getmNotes(){
        return mNotes;
    }

    public Note getNote(int position){
        return mNotes.get(position);
    }

    public void addNote(Note n){
        mNotes.add(n);
    }

    public void updateNote(int position, Note n){
        if(position >= 0 && position < mNotes.size()){
            mNotes.set(position, n);
        }
    }

    public void removeNote(int position){
        if(position >= 0 && position < mNotes.size()){
            mNotes.remove(position);
        }
    }
}
