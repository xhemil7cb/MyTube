package com.example.mytube;


import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class NetworkingService extends Service {
    public NetworkingService() {
    }
    String url = "http://192.168.1.231/MyTube/index.php";
    String uri = "http://192.168.1.231/MyTube/";



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CompareWithServer(getMusicsInDevice());

        return START_STICKY;
    }

    String [] getMusicsInDevice (){

        String path = "/mnt/sdcard/MyTubeFiles/";
        Log.e("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();
        Log.e("Files", "Size: "+ files.length);
        String [] DeviceMusics = new String[files.length];
        for (int i = 0; i < files.length; i++)
        {
            DeviceMusics[i] = files[i].getName();
        }


        return  DeviceMusics;
    }

    void Download(String MusicName){
        DownloadManager.Request request=new
                DownloadManager.Request(Uri.parse(uri+MusicName));
        request.setTitle(MusicName);
        request.setDescription("your file is downloading ...");
        request.allowScanningByMediaScanner();

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        Uri dst_uri = Uri.parse("file:///mnt/sdcard/MyTubeFiles/"+MusicName);

        request.setDestinationUri(dst_uri);

        DownloadManager manager =(DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }


    public void CompareWithServer(final String[] CurrentlyInDevice){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            final JsonObjectRequest jo = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("MyFiles");
                        for (int i=0;i<jsonArray.length();i++){
                            String o = jsonArray.getString(i);
                            Boolean download = true;
                            for (int j=0;j<CurrentlyInDevice.length;j++){
                                if(o.equals(CurrentlyInDevice[j])){
                                    Log.e("Alarm","Duplicat");
                                    download = false;
                                }
                            }
                            if(download){
                                Download(o);
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(jo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

