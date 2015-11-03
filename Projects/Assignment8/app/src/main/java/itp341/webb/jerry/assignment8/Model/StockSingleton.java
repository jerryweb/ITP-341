package itp341.webb.jerry.assignment8.Model;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import itp341.webb.jerry.assignment8.Controller.StockJSONSerializer;

/**
 * Created by jerrywebb on 11/3/15.
 */
public class StockSingleton {

    private static final String FILENAME = "stocks.json";
    private static StockSingleton stockSingleton;
    private StockJSONSerializer mSerializer;
    ArrayList<Stock> mStocks;
    private Context mAppContext;

    private StockSingleton(Context c){
        this.mAppContext = c;
        mSerializer = new StockJSONSerializer(c, FILENAME);

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

    public void updateStock(int position, Stock s){
        if(position >= 0 && position < mStocks.size()) {
            mStocks.set(position, s);
        }

    }

    public void removeStock(int position){
        if(position >= 0 && position < mStocks.size()){
            mStocks.remove(position);
        }
    }

    //TODO JSON -- saving
    public boolean saveCoffeeShops(){
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
