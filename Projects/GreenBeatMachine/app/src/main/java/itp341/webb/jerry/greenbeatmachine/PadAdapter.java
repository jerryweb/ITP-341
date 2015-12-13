package itp341.webb.jerry.greenbeatmachine;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Collections;
import java.util.List;

import itp341.webb.jerry.greenbeatmachine.model.TrackSingleton;

/**
 * Created by jerrywebb on 12/12/15.
 */
public class PadAdapter extends RecyclerView.Adapter <PadAdapter.PadViewHolder>{
    private Context mAppContext;
public static final String TAG = "itp341.finalProject.tag";

    private LayoutInflater inflater;
    List<PadLayoutInformation> data = Collections.emptyList();
    int pos;

    public PadAdapter(Context context, List<PadLayoutInformation> data){
        mAppContext = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
        pos = 0;
    }

    @Override
    public PadViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = inflater.inflate(R.layout.item_custom_pad, parent, false);

        PadViewHolder holder = new PadViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(PadViewHolder holder, int position) {
        pos = position;
        PadLayoutInformation current = data.get(position);
        holder.btn_pad_item.setText(current.trackName);
        data.get(position).padId = position;

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //This will avoid having to find the items in the view everytime
    class PadViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button btn_pad_item;

        public PadViewHolder(View itemView) {
            super(itemView);

            btn_pad_item = (Button) itemView.findViewById(R.id.btn_pad_item);
            btn_pad_item.setOnClickListener(this);
        }

        // onClick Listener for view

        @Override
        public void onClick(View v) {
//            TrackSingleton.get(mAppContext).playSound(1);
        }
    }
}
