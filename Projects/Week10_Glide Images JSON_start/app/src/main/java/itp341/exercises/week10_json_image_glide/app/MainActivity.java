package itp341.exercises.week10_json_image_glide.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;

import itp341.exercises.week10_json_image_glide.R;
import itp341.exercises.week10_json_image_glide.app.model.WebPhoto;
import itp341.exercises.week10_json_image_glide.app.model.WebPhotoSingleton;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    ImageView imageView;
    Button buttonNext;
    TextView textCaption;
    int index = 0;

    private ArrayList<WebPhoto> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        buttonNext = (Button) findViewById(R.id.buttonNext);
        textCaption = (TextView) findViewById(R.id.labelCaption);

        photos = WebPhotoSingleton.get(this).getWebPhotos();
        Log.d(TAG, photos.toString());

        //TODO Click listener
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = photos.get(index).getUrl();
                String name = photos.get(index).getName();

                //with: the context that we are in
                Glide.with(getApplicationContext())
                        .load(url)
                        .placeholder(R.drawable.placeholder)
                        .crossFade()
                        .into(imageView);

                index = (index + 1)% photos.size();
                textCaption.setText(name);
            }
        });

    }

}










