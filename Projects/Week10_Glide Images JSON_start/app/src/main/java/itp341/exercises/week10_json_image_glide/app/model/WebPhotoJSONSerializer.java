package itp341.exercises.week10_json_image_glide.app.model;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by R on 10/26/2015.
 */


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

public class WebPhotoJSONSerializer {


    private Context mContext;
    private String mFilename;

    public WebPhotoJSONSerializer(Context c, String f) {
        mContext = c;
        mFilename = f;
    }

    public ArrayList<WebPhoto> loadWebPhotos() throws IOException, JSONException {
        ArrayList<WebPhoto> webPhotos = new ArrayList<WebPhoto>();
        BufferedReader reader = null;

        try {
            //TODO Load asset
//            InputStream in = mContext.openFileInput(mFilename);

            //load from assets
            InputStream in;
            AssetManager manager = mContext.getAssets();
            in = manager.open(mFilename);

            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            // build the array of webPhotos from JSONObjects
            for (int i = 0; i < array.length(); i++) {
                webPhotos.add(new WebPhoto(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
            // we will ignore this one, since it happens when we start fresh
        } finally {
            if (reader != null)
                reader.close();
        }
        return webPhotos;
    }

    public void saveWebPhotos(ArrayList<WebPhoto> webPhotos) throws JSONException, IOException {
        // build an array in JSON
        JSONArray array = new JSONArray();
        for (WebPhoto w : webPhotos)
            array.put(w.toJSON());

        // write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
