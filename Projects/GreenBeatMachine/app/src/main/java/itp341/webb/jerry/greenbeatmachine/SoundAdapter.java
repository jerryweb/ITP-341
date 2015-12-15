package itp341.webb.jerry.greenbeatmachine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import itp341.webb.jerry.greenbeatmachine.model.Sound;

/**
 * Created by jerrywebb on 12/14/15.
 */
public class SoundAdapter extends ArrayAdapter<Sound> {

    public SoundAdapter(Context context, ArrayList<Sound> sounds) {
        super(context,0, sounds);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sound sound =  getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sound, parent, false);
        }

        // Lookup view for data population
        TextView textViewSoundName = (TextView) convertView.findViewById(R.id.textViewSoundName);
        TextView textViewSoundType = (TextView) convertView.findViewById(R.id.textViewSoundType);
        // Populate the data into the template view using the data object
        textViewSoundName.setText(sound.getName());
        textViewSoundType.setText(sound.getType());
        // Return the completed view to render on screen
        return convertView;
    }

//    public void setListAdapter(ArrayList<String>){
//
//    }
}

