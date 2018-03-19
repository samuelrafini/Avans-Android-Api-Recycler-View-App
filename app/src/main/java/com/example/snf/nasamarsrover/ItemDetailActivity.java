package com.example.snf.nasamarsrover;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by SNF on 14-3-2018.
 */

public class ItemDetailActivity extends AppCompatActivity{
    private static final String TAG = "ItemDetailActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "On create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Log.d(TAG,"onCreate: Started.");

        intentGetter();
    }

    private void intentGetter(){
        Log.d(TAG, "Get Intent");

        String imageURL = getIntent().getStringExtra("imageURL");
        String cameraName = getIntent().getStringExtra("cameraName");

        setImageAndName(imageURL,cameraName);
    }

    private void setImageAndName(String imageUrl, String cameraName){
        Log.d(TAG, "Set image url and camera name");

        TextView camera = findViewById(R.id.cameraName);
        camera.setText(cameraName);

        ImageView image = findViewById(R.id.imgDetail);
        Picasso.with(this).load(imageUrl).into(image);
    }
}
