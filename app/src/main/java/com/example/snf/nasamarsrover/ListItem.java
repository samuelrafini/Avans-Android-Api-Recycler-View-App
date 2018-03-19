package com.example.snf.nasamarsrover;

import android.util.Log;

/**
 * Created by SNF on 13-3-2018.
 */

public class ListItem {
    private static final String TAG = "ListItem";

    private int id;
    private String imageURL;
    private String cameraName;

    public ListItem(int id, String imageURL, String cameraName) {
        this.id = id;
        this.imageURL = imageURL;
        this.cameraName = cameraName;
    }

    public int getId() {
        Log.d(TAG, "getTitle is called: ");

        return id;
    }
    public String getImageURL() {
        Log.d(TAG, "getimageURL is called: ");

        return imageURL;
    }

    public String getCameraName(){
        Log.d(TAG, "getCameraName is called: ");

        return cameraName;}
}
