package com.example.mytube;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

    static Handler handler = new Handler();
    boolean isPlaying = true;
    Field[] fields;
    int[] resIDs;
    int playingNow = 0;
    boolean loop = false;   // the logic is implemented just missing UI elemnt


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HideCntrols();

        // initiate videoviev and media controller
        final VideoView videoView = findViewById(R.id.vidview);
        final MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        // Fill array with video id's from raw
        GetRawResources();
        int resID = resIDs[playingNow];

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resID);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        videoView.setMediaController(mediaController);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            final Context context = getApplicationContext();

            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(context, "DONE !", Toast.LENGTH_SHORT).show();
                NextClick();
                videoView.start();
            }
        });


        final Context context = getApplicationContext();

        videoView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(context, "top", Toast.LENGTH_SHORT).show();
                mediaController.show(5000);
            }

            public void onSwipeRight() {
                Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
                mediaController.show(5000);
            }

            public void onSwipeLeft() {

                Intent intent = new Intent(MainActivity.this, SomeActivity.class);
                startActivity(intent);

                Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
                mediaController.show(5000);
            }

            public void onSwipeBottom() {
                Toast.makeText(context, "bottom", Toast.LENGTH_SHORT).show();
                mediaController.show(5000);
            }

            public void onNoSwipe() {
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                VideoClick();
                mediaController.show(5000);
            }

        });


        // Initiate my own media controller buttons
        ImageButton playpauseimgbutton = findViewById(R.id.playpauseimagebuton);
        playpauseimgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayPauseClick();
            }
        });

        ImageButton nextimgbutton = findViewById(R.id.right);
        nextimgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NextClick();
            }
        });

        ImageButton previmgbutton = findViewById(R.id.left);
        previmgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrevClick();
            }
        });


        // remove prev handler calls then call to Hide playpausebutton after 3sec
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideCntrols();
            }
        }, 5 * 1000);


    }

    void GetRawResources() {

        fields = R.raw.class.getFields();
        resIDs = new int[fields.length];


        for (int i = 0; i < fields.length; i++) {
            try {
                resIDs[i] = (fields[i].getInt(fields[i]));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


    /*   for debug puprpurses
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context,resIDs.length+":"+fields.length ,Toast.LENGTH_LONG);
        toast.show();
    */
    }


    void VideoClick() {

        LinearLayout cntrlsLayout = findViewById(R.id.cntrols);
        cntrlsLayout.setVisibility(View.VISIBLE);

        // remove prev calls
        handler.removeCallbacksAndMessages(null);
        //final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideCntrols();
            }
        }, 5 * 1000);

    }

    void NextClick() {

        if (playingNow < resIDs.length - 1) {
            VideoView videoView = findViewById(R.id.vidview);
            playingNow++;
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resIDs[playingNow]);
            videoView.setVideoURI(uri);
        } else if (loop) {
            VideoView videoView = findViewById(R.id.vidview);
            playingNow = 0;
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resIDs[playingNow]);
            videoView.setVideoURI(uri);
        }


        // remove prev calls
        handler.removeCallbacksAndMessages(null);
        //final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideCntrols();
            }
        }, 5 * 1000);
    }

    void PrevClick() {

        if (playingNow > 0) {
            VideoView videoView = findViewById(R.id.vidview);
            playingNow--;
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resIDs[playingNow]);
            videoView.setVideoURI(uri);
        } else if (loop) {
            VideoView videoView = findViewById(R.id.vidview);
            playingNow = resIDs.length - 1;  // set playingnow to the last element
            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resIDs[playingNow]);
            videoView.setVideoURI(uri);
        }


        // remove prev calls
        handler.removeCallbacksAndMessages(null);
        //final Handler handler =new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideCntrols();
            }
        }, 5 * 1000);

    }


    void PlayPauseClick() {
        VideoView videoView = findViewById(R.id.vidview);
        ImageButton imageButton = findViewById(R.id.playpauseimagebuton);

        if (isPlaying) {
            videoView.pause();
            isPlaying = false;
            imageButton.setImageResource(R.drawable.play);
        } else {
            videoView.start();
            isPlaying = true;
            imageButton.setImageResource(R.drawable.pause);
        }

        // remove prev handler calls then call to Hide playpausebutton after few sec
        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideCntrols();
            }
        }, 5 * 1000);

    }


    void HideCntrols() {

        LinearLayout cntrlsLayout = findViewById(R.id.cntrols);
        cntrlsLayout.setVisibility(View.INVISIBLE);

    }


}