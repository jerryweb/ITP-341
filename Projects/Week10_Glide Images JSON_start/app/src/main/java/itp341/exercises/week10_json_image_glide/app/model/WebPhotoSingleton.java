package itp341.exercises.week10_json_image_glide.app.model;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class WebPhotoSingleton {
    private static final String TAG = WebPhotoSingleton.class.getSimpleName();

    private static final String FILENAME = "web_photos.json";

    private WebPhotoJSONSerializer mSerializer;

    private ArrayList<WebPhoto> mWebPhotos;

    private static WebPhotoSingleton sWebPhotos;
    private Context mAppContext;

    private WebPhotoSingleton(Context appContext) {
        mAppContext = appContext;

        mSerializer = new WebPhotoJSONSerializer(mAppContext, FILENAME);

        try {
            Log.d(TAG, "Loaded JSON");
            mWebPhotos = mSerializer.loadWebPhotos();
        } catch (Exception e) {
            mWebPhotos = new ArrayList<WebPhoto>();
            Log.e(TAG, "Error loading webphoto JSON: ", e);
        }

    }

    public static WebPhotoSingleton get(Context c) {
        if (sWebPhotos == null) {
            sWebPhotos = new WebPhotoSingleton(c.getApplicationContext());
        }
        return sWebPhotos;
    }


    public ArrayList<WebPhoto> getWebPhotos() {
        return mWebPhotos;
    }


}
