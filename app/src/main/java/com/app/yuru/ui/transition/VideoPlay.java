package com.app.yuru.ui.transition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;

public class VideoPlay extends AppCompatActivity {

    int x = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);


        String url = getIntent().getStringExtra("videoLink");

        VideoView videoPlay_videoview = findViewById(R.id.videoPlay_videoview);

        playVideo(videoPlay_videoview, url);


    }
    public void playVideo(VideoView videoView,String path) {

        MediaController ctlr = new MediaController(this);
        ctlr.setMediaPlayer(videoView);
        videoView.setMediaController(ctlr);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.lop);
//        Uri uri = Uri.parse(url);
        videoView.setMediaController(ctlr);

        videoView.setVideoURI(uri);
        videoView.start();

        new Handler().postDelayed(() -> {


            startActivity(new Intent(VideoPlay.this, TransitionActivity.class));


        }, 30000);

    }

    public void stopVideo(VideoView videoView) {
        if (videoView.isPlaying()) {
            videoView.stopPlayback();
        }
    }
}