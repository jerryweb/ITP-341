package itp341.webb.jerry.assignment8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
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

        ImageView imageLogo = (ImageView) convertView.findViewById(R.id.imageLogo);

        // Lookup view for data population
        switch (stock.getBrand()){
            case "Apple":
                imageLogo.setImageResource(R.drawable.apple);
                break;
            case "apple":
                imageLogo.setImageResource(R.drawable.apple);
                break;
            case "Logo":
                imageLogo.setImageResource(R.drawable.logo);
                break;
            case "logo":
                imageLogo.setImageResource(R.drawable.logo);
                break;
            case "Microsoft":
                imageLogo.setImageResource(R.drawable.microsoft);
                break;
            case "microsoft":
                imageLogo.setImageResource(R.drawable.microsoft);
                break;
            case "Samsung":
                imageLogo.setImageResource(R.drawable.samsung);
                break;
            case "samsung":
                imageLogo.setImageResource(R.drawable.samsung);
                break;
        }
        TextView textProductName = (TextView) convertView.findViewById(R.id.textProductName);
        TextView textStock =  (TextView) convertView.findViewById(R.id.textStock);

        textStock.setText(Integer.toString(stock.getStock()));
//        textStock.setText(formatter.format(stock.getPrice()));
        textProductName.setText(stock.getBrand());

        return convertView;


    }
}
