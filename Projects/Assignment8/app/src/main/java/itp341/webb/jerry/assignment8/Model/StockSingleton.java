package itp341.webb.jerry.assignment8.Model;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import itp341.webb.jerry.assignment8.Controller.StockJSONSerializer;
import itp341.webb.jerry.assignment8.StockManagerActivity;

/**
 * Created by jerrywebb on 11/3/15.
 */
public class StockSingleton {
    private static final String TAG = StockManagerActivity.class
            .getSimpleName();
    private static final String FILENAME = "stocks.json";
    private static StockSingleton stockSingleton;
    private StockJSONSerializer mSerializer;
    ArrayList<Stock> mStocks;
    private Context mAppContext;

    private StockSingleton(Context c){
        this.mAppContext = c;
        mSerializer = new StockJSONSerializer(c, FILENAME);
        mStocks = new ArrayList<Stock>();
//        for(int i =0; i < 1;i++){
//            Stock s = new Stock(i,i+30,30+i,"black","Apple");
//            mStocks.add(s);
//        }
        try {
            mStocks = mSerializer.loadStocks();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static StockSingleton get(Context c){
        if(stockSingleton == null){
            stockSingleton = new StockSingleton(c.getApplicationContext());
        }
        return  stockSingleton;
    }


    //Data retrieval methods
    public ArrayList<Stock> getmStocks(){
        return mStocks;
    }

    public Stock getStock(int position){
            return mStocks.get(position);
    }

    public void addStock(Stock s){
        mStocks.add(s);
    }

    public void removeStock(int position) {
        if (position >= 0 && position < mStocks.size()) {
            mStocks.remove(position);
        }
    }

    public void buyStock(int position){
        if(position >= 0 && position < mStocks.size()) {
           int stock = mStocks.get(position).getStock();
            mStocks.get(position).setStock(stock + 1);
            Log.d(TAG, "stock value: " + mStocks.get(position).getStock());
        }

    }

    public void updateStock(int position, Stock s){
        if(position >= 0 && position < mStocks.size()) {
            mStocks.set(position,s);
        }
    }

    public void sellStock(int position){
        if(position >= 0 && position < mStocks.size()){
          int stock =  mStocks.get(position).getStock();
            if(stock > 0) {
                mStocks.get(position).setStock(stock - 1);
            }
        }
    }

    public void sellAllStocks(int position){
        if(position >= 0 && position < mStocks.size()){
           mStocks.get(position).setStock(0);
        }
    }

    //TODO JSON -- saving
    public boolean saveStock(){
        try {
            mSerializer.saveStocks(mStocks);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
