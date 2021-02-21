package com.example.mytube;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;


public class ListMusicsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        startService(new Intent(this, NetworkingService.class));


        // Note to self: Music thumbnails will be generated in adapter
        MusicAdapter musicAdapter = new MusicAdapter(this, GetMuzikatArrayList());
        ListView lv = findViewById(R.id.listView);
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



    public ArrayList<Music> GetMuzikatArrayList() {
        // Add just music names from a specific folder to an arrylist
        File f = new File("/mnt/sdcard/MyTubeFiles/");
        File[] filearray = f.listFiles();
        ArrayList<Music> Muzikat = new ArrayList<>();
        for (File file : filearray) {
            Music m = new Music(file.getName());
            Muzikat.add(m);
        }

        return Muzikat;
    }
}
