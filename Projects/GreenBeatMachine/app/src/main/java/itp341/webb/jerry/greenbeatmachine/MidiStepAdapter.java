package itp341.webb.jerry.greenbeatmachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 */
public class MidiStepAdapter extends RecyclerView.Adapter <MidiStepAdapter.MidiViewHolder> {
    public static final String TAG = "itp341.finalProject.tag";

    private LayoutInflater inflater;
    List<MidiStepInformation> data = Collections.emptyList();
    private Context mAppContext;

    public MidiStepAdapter(Context context, List<MidiStepInformation> data){
        mAppContext = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MidiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_custom_midi_step, parent, false);

        MidiViewHolder holder = new MidiViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MidiViewHolder holder, int position) {
        MidiStepInformation current = data.get(position);
        holder.checkBox_midi_step.setChecked(current.isChecked);
        Log.d(TAG, "position for step: " + position/16);
        holder.dataSave.setText(Integer.toString(position/16));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MidiViewHolder extends  RecyclerView.ViewHolder implements CheckBox.OnCheckedChangeListener{
        CheckBox checkBox_midi_step;
        TextView dataSave;

        public MidiViewHolder(View itemView) {
            super(itemView);
            checkBox_midi_step = (CheckBox) itemView.findViewById(R.id.checkBoxMidiStep);
            dataSave = (TextView) itemView.findViewById(R.id.dataSave);


        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }
}
