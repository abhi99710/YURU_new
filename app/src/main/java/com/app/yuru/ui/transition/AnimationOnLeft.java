package com.app.yuru.ui.transition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;
import com.app.yuru.ui.discounts.CalenderV;

public class AnimationOnLeft extends AppCompatActivity {

    private VideoView rocketVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_on_left);


        new Handler().postDelayed(() -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.framwQts, new SleepEnhancerNew()).commit();
        },6000);

        rocketVideo = findViewById(R.id.rocketVideo1);
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