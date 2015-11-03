package itp341.webb.jerry.assignment8.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import itp341.webb.jerry.assignment8.Model.Stock;
import itp341.webb.jerry.assignment8.R;

/**
 * Created by jerrywebb on 11/3/15.
 */
public class StockAdapter extends ArrayAdapter<Stock> {
    public StockAdapter(Context context, ArrayList<Stock> stock){
        super(context, 0, stock);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stock stock = getItem(position);

        //TODO need to create the custom adapter view
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_stock , parent, false);
        }


        return convertView;


    }
}
