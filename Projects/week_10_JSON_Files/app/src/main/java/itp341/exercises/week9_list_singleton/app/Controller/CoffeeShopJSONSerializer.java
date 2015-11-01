package itp341.exercises.week9_list_singleton.app.Controller;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import itp341.exercises.week9_list_singleton.app.model.CoffeeShop;

/**
 * Created by jerrywebb on 10/27/15.
 */
public class CoffeeShopJSONSerializer{

    //member variables
    private Context mContext;
    private String mFilename;

    public CoffeeShopJSONSerializer(Context c, String f){
       this.mContext = c;
       this.mFilename = f;
    }

    //loadCoffeeShops
    public ArrayList<CoffeeShop> loadCoffeeShops() throws IOException, JSONException {
        ArrayList<CoffeeShop> coffeeShops = new ArrayList<CoffeeShop>();
        BufferedReader reader = null;
        try {
            // open and read the file into a StringBuilder
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of coffeeShops from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                coffeeShops.add(new CoffeeShop(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return coffeeShops;
    }

    //saveCoffeeShops
    public void saveCoffeeShops(ArrayList<CoffeeShop> coffeeShops) throws JSONException, IOException {
        //build JSON array
        JSONArray array = new JSONArray();

        for (CoffeeShop cs: coffeeShops) {
            array.put(cs.toJSON());
        }

        //write file to disk
        Writer writer = null;

        try {
            //open file and connect to data stream
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);

            //now writer is connected to the file in the android file system
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }

        finally {
            if(writer != null){
                writer.close();
            }
        }
    }
}
