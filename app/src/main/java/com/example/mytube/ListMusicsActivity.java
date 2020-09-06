package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.media.ThumbnailUtils;

import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ListMusicsActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 100;
    Field[] fields;
    private ListView lv;
    private Context context;
    String path = "sdcard/DCIM/SharedFolder";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        // Read and Write permissions
        checkPermission();


        // Add just music names from a specific folder to an arrylist
        File f = new File(path);
        File[] filearray = f.listFiles();
        ArrayList<Music> Muzikat = new ArrayList<>();
        for (File file : filearray) {
            Music m = new Music(file.getName());
            Muzikat.add(m);
        }

        // Fill the music adapter with the music names and form arraylist Muzilat
        // Note to self: Music thumbnails will be generated in adapter
        MusicAdapter musicAdapter = new MusicAdapter(this, Muzikat);
        lv = findViewById(R.id.listView);
        lv.setAdapter(musicAdapter);


        // When ListView item Music is clicked do this
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String musicName = ((TextView) view.findViewById(R.id.name)).getText().toString();
                ImageView iv = ((ImageView) view.findViewById(R.id.playingIndicator));
                iv.setVisibility(View.VISIBLE);

                Intent intended = new Intent(ListMusicsActivity.this, VideoPlalyerActivity.class);
                intended.putExtra("Name", musicName);
                ListMusicsActivity.this.startActivity(intended);

            }
        });


    }

    // Read and Write premissions
    private void checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            }
        }
    }


}