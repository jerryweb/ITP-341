package itp341.webb.jerry.assignment8.Controller;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import itp341.webb.jerry.assignment8.Model.Stock;

/**
 * Created by jerrywebb on 11/3/15.
 */
public class StockJSONSerializer {
    public static final String TAG = "itp341.webb.jerry.assignment8.tag";
    public static final String JSON_NAME = "itp341.webb.jerry.assignment8.name";
    private Context mContext;
    private String mFilename;

    public StockJSONSerializer(Context c, String f){
        this.mContext = c;
        this.mFilename = f;
        Log.d(TAG, "The file name is: " + f);

    }

    //load stocks
    public ArrayList<Stock> loadStocks() throws IOException, JSONException{
        ArrayList<Stock> stocks = new ArrayList<Stock>();
        BufferedReader reader = null;
        InputStream in = null;

        try {
            // open and read the file into a StringBuilder

            File inputFile = new File(mContext.getFilesDir() + "/" + mFilename);


            if(inputFile.isFile() && inputFile.exists()){
                in = mContext.openFileInput(mFilename);
            }
            else {
                in =  mContext.getAssets().open(mFilename);
            }

            reader = new BufferedReader(new InputStreamReader(in));

            StringBuilder jsonString = new StringBuilder();
            String line = null;


            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            String jsonText = jsonString.toString();
            JSONObject jObject = new JSONObject(jsonText);
            JSONObject sObj =  jObject.getJSONObject("stock");
            JSONArray array = sObj.names();
            // parse the JSON using JSONTokener
//            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of stocks from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                String productName = array.getString(i);
                JSONObject object = sObj.getJSONObject(productName);
                stocks.add(new Stock(object, productName));
                Log.d(TAG, "mFilename content brand: " + stocks.get(i).getBrand());
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
            e.printStackTrace();
        } finally {
            if (reader != null){
                reader.close();

            }
            if(in != null){
                in.close();
            }
        }

        return stocks;
    }

    //save stocks
    public void  saveStocks(ArrayList<Stock> stocks) throws JSONException, IOException {
        //build JSON array
        JSONArray array = new JSONArray();

        for (Stock s: stocks) {
            array.put(s.toJSON());
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
