package com.example.mytube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.OnClickAction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class VideoPlalyerActivity extends AppCompatActivity {

    static Handler handler = new Handler();
    boolean isPlaying = true;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_plalyer);

        // This Activity is called only from LisMusicActivity as I add more features I should edit this
        Intent intent = getIntent();
        String MusicToPlay = intent.getStringExtra("Name");

        // initiate videoviev and media controller
        videoView = findViewById(R.id.vidview);
        final MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        // Hide mediaController and other controls I added after few sec
        HideCntrols();

        // Explanation not needed :D
        videoView.setVideoPath("sdcard/DCIM/SharedFolder/" + MusicToPlay);
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
        videoView.setOnTouchListener(new OnSwipeTouchListener(VideoPlalyerActivity.this) {
            public void onSwipeTop() {
                //   Toast.makeText(context, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                RelativeLayout listLayout = findViewById(R.id.listlayout);
                listLayout.setVisibility(View.VISIBLE);


            }

            public void onSwipeLeft() {

            }

            public void onSwipeBottom() {

            }

            public void onNoSwipe() {
                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                VideoClick();
                mediaController.show(5000);
            }

        });

        // Extra Controls that I want in videoview
        initiateMyVideoControls();


        // remove prev handler calls then call to Hide playpausebutton after 5 sec
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                HideCntrols();
            }
        }, 5 * 1000);

        ListView lv = findViewById(R.id.inPalyListView);
        RelativeLayout l = findViewById(R.id.listlayout);
        GenerateListViewAdapter(lv, videoView, l);

    }




    /*  Keep this for debug puprpurses
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context,resIDs.length+":"+fields.length ,Toast.LENGTH_LONG);
        toast.show();
    */



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

    void NextClick() {   // Next music code to be added
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

    void PrevClick() {  // prev music code to be added
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


    void PlayPauseClick() {  // It looks good atm
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

    }  // This is done


    void HideCntrols() {
        LinearLayout cntrlsLayout = findViewById(R.id.cntrols);
        cntrlsLayout.setVisibility(View.INVISIBLE);
    }  // No need to mess with this either


    void GenerateListViewAdapter(final ListView lv, final VideoView v, final RelativeLayout relativeLayout) {

        // Send this in MainAcitivity make static so we dont have to call same thing a milion times
        ArrayList<Music> music = new ArrayList<Music>();
        final String path = "sdcard/DCIM/SharedFolder";
        File f = new File(path);
        File[] filearray = f.listFiles();
        ArrayList<Music> Muzikat = new ArrayList<>();

        for (File file : filearray) {
            Music m = new Music(file.getName());
            Muzikat.add(m);
        }

        MusicAdapter musicAdapter = new MusicAdapter(this, Muzikat);
        lv.setAdapter(musicAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String musicName = ((TextView) view.findViewById(R.id.name)).getText().toString();

                v.setVideoPath("sdcard/DCIM/SharedFolder/" + musicName);
                relativeLayout.setVisibility(View.GONE);
            }
        });


        final Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.setVisibility(View.GONE);

            }
        });
    }


    void initiateMyVideoControls() {
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
    }  // PlayPause Next and Prev Initialisations only  dont mess till you need to add more buttons


}