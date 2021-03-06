package com.example.mytube;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MusicAdapter extends ArrayAdapter<Music> {
    public MusicAdapter(Context context, ArrayList<Music> musics) {
        super(context, 0, musics);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Music music = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_row, parent, false);
        }


        // Set the music name and generate a thumbnail for the given music
        ImageView imgv = (ImageView) convertView.findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);

        name.setText(music.name);
        String pathtofile = "/mnt/sdcard/MyTubeFiles/" + music.name;

        Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(pathtofile, MediaStore.Images.Thumbnails.MICRO_KIND);
        imgv.setImageBitmap(thumbnail);


        // Return the completed view to render on screen
        return convertView;
    }






}
