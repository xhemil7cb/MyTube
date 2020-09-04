package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


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


        Intent myIntent = new Intent(MainActivity.this, ListMusicsActivity.class);
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