package com.example.mytube;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;

import android.widget.Toast;
import android.widget.VideoView;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


         /* //   Wroks like a charm *_*
        Intent myIntent = new Intent(MainActivity.this, VideoPlalyerActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivity(myIntent);
*/


        Intent myIntent = new Intent(MainActivity.this, SomeActivity.class);
        MainActivity.this.startActivity(myIntent);



/*
           VideoView videoView = findViewById(R.id.vidview);
           Uri uri = Uri.parse(Environment.getExternalStorageDirectory()+"/Videos/blabla.mp4");
          // videoView.setVideoPath("/sdcard/Videos/blabla.mp4");



        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context,uri+"" ,Toast.LENGTH_LONG);
        toast.show();


           videoView.setVideoURI(uri);
           videoView.requestFocus();
           videoView.start();

*/



    }


}