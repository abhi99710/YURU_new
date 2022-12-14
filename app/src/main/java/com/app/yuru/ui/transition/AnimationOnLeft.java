package com.app.yuru.ui.transition;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;

public class AnimationOnLeft extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_on_left);




        VideoView rocketVideo = findViewById(R.id.rocketVideo1);
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

        rocketVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framwQts, new SleepEnhancer2()).commit();
            }
        });
    }
}