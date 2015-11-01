package itp341.exercises.week9_list_singleton.app.model;

import android.content.Context;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import itp341.exercises.week9_list_singleton.app.Controller.CoffeeShopJSONSerializer;

//TODO complete
//NOT THREAD SAFE YET
public class CoffeeShopSingleton {

    private static final String FILENAME = "coffee_shops.json";

    private CoffeeShopJSONSerializer mSerializer;

    ArrayList<CoffeeShop> mCoffeeShops;

    private static CoffeeShopSingleton sCoffeeShopSingleton;
    private Context mAppContext;

    private CoffeeShopSingleton(Context c){
        this.mAppContext = c;

        mSerializer = new CoffeeShopJSONSerializer(c, FILENAME);


        try {
            mCoffeeShops = mSerializer.loadCoffeeShops();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static CoffeeShopSingleton get(Context c){
        if(sCoffeeShopSingleton == null){
            sCoffeeShopSingleton = new CoffeeShopSingleton(c.getApplicationContext());
        }
        return  sCoffeeShopSingleton;
    }

    //Data retrieval methods
    public ArrayList<CoffeeShop> getmCoffeeShops(){
        return mCoffeeShops;
    }

    public CoffeeShop getCoffeeShop(int position){
        return mCoffeeShops.get(position);
    }

    public void addCoffeeShop(CoffeeShop cs){
        mCoffeeShops.add(cs);
    }

    public void updateCoffeeShop(int position, CoffeeShop cs){
        if(position >= 0 && position < mCoffeeShops.size()) {
            mCoffeeShops.set(position, cs);
        }
    }

    public void removeCoffeeShop(int position){
        if(position >= 0 && position < mCoffeeShops.size()){
            mCoffeeShops.remove(position);
        }
    }

    //TODO JSON -- saving
    public boolean saveCoffeeShops(){
        try {
            mSerializer.saveCoffeeShops(mCoffeeShops);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}


