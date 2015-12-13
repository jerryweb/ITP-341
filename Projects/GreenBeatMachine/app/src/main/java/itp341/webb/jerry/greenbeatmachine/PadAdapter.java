package itp341.webb.jerry.greenbeatmachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 */
public class PadAdapter extends RecyclerView.Adapter <PadAdapter.PadViewHolder>{
public static final String TAG = "itp341.finalProject.tag";

    private LayoutInflater inflater;
    List<PadLayoutInformation> data = Collections.emptyList();

    private Context mAppContext;

    public PadAdapter(Context context, List<PadLayoutInformation> data){
        mAppContext = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public PadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.item_custom_pad, parent, false);

        PadViewHolder holder = new PadViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(PadViewHolder holder, int position) {
        PadLayoutInformation current = data.get(position);
        holder.btn_pad_item.setText(current.trackName);
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

        }
    }
}