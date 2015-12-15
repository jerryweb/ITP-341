package itp341.webb.jerry.greenbeatmachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import itp341.webb.jerry.greenbeatmachine.model.Track;
import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 */
public class PadAdapter extends RecyclerView.Adapter <PadAdapter.PadViewHolder>{
public static final String TAG = "itp341.finalProject.tag";

    private LayoutInflater inflater;
    List<PadLayoutInformation> data = Collections.emptyList();
    ArrayList<PadViewHolder> holders;
    private Context mAppContext;
    private PadViewHolder holder;
    private int lastPadClicked;


    public PadAdapter(Context context, List<PadLayoutInformation> data){
        mAppContext = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        lastPadClicked = 0;
        holders = new ArrayList<>();
    }

    @Override
    public PadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_custom_pad, parent, false);
        holder = new PadViewHolder(view);
        if (holders.size() == 8) {
            holders = new ArrayList<PadViewHolder>();
        }
        holders.add(holder);


        return holders.get(holders.size()-1);
    }

    @Override
    public void onBindViewHolder(PadViewHolder holder, int position) {
        ArrayList<Track> tracks = TrackSingleton.get(mAppContext).getmTracks();
        holder.btn_pad_item.setText(tracks.get(position).getName());
        holder.textView3.setText(Integer.toString(position)); //this holds the
        // individual ids for each of the pads

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //This will avoid having to find the items in the view every time
    class PadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btn_pad_item;
        TextView textView3;

        public PadViewHolder(View itemView) {
            super(itemView);

            textView3 = (TextView) itemView.findViewById(R.id.textView3);

            btn_pad_item = (Button) itemView.findViewById(R.id.btn_pad_item);
            btn_pad_item.setOnClickListener(this);
//            Log.d(TAG, "button id: " + btn_pad_item.getId());
        }

        // onClick Listener for view

        @Override
        public void onClick(View v) {
            TrackSingleton.get(mAppContext)
                    .playSound(Integer.parseInt(textView3.getText().toString()));
            TrackSingleton.get(mAppContext)
                    .callTriggerBeat(Integer.parseInt(textView3.getText().toString()));
            lastPadClicked = Integer.parseInt(textView3.getText().toString());
        }
    }

    public void setPadSound(int id, String name){
        holders.get(lastPadClicked).btn_pad_item.setText(name);
        TrackSingleton.get(mAppContext).setSamplesToTracks();

    }

    public int getLastPadClicked() {
        return lastPadClicked;
    }
}
