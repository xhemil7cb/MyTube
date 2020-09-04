package com.example.mytube;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

public class Music {
    public String name;
    public int resID;


    public Music(String musicName, int resourceID) {
        name = musicName;
        resID = resourceID;
    }


}
