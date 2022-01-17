package com.app.yuru.ui.discounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;

public class RocketTake extends AppCompatActivity {

    //    private Button skipLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_take);

//        skipLogin = findViewById(R.id.skipLogin);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, CalenderV.class);
//            intent.putExtra("nameLogin", nameLogin);
            startActivity(intent);
        },6000);

        VideoView rocketVideo = findViewById(R.id.rocketVideo);
        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(rocketVideo);

        //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.rocket);
        //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
        rocketVideo.setVideoURI(uri);
        rocketVideo.requestFocus();
        rocketVideo.start();

    }
}