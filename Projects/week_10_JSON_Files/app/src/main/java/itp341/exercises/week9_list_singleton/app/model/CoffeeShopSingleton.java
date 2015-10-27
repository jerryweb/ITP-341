package itp341.exercises.week9_list_singleton.app.model;

import android.content.Context;

import java.util.ArrayList;

//TODO complete
//NOT THREAD SAFE YET
public class CoffeeShopSingleton {
    ArrayList<CoffeeShop> mCoffeeShops;

    private static CoffeeShopSingleton sCoffeeShopSingleton;
    private Context mAppContext;

    private CoffeeShopSingleton(Context c){
        this.mAppContext = c;
        mCoffeeShops = new ArrayList<>();

        //Dummy code to load array-- remove later
        for(int i = 0; i < 6; i++){
            CoffeeShop cs = new CoffeeShop();
            cs.setName("Coffee Shop " + i);
            mCoffeeShops.add(cs);
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
        mCoffeeShops.set(position, cs);
    }

    public void removeCoffeeShop(int position){
        if(position >= 0 && position < mCoffeeShops.size()){
            mCoffeeShops.remove(position);
        }
    }
}


