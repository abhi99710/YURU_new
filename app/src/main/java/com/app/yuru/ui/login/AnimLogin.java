package com.app.yuru.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;
import com.app.yuru.ui.getStarted.GetStartedActivity;

public class AnimLogin extends AppCompatActivity {

    private VideoView videoLoginAnim;
    private Button skipLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_login);

        skipLogin = findViewById(R.id.skipLogin);

        String nameLogin = getIntent().getStringExtra("nameLogin");

        skipLogin.setOnClickListener(v->{
            Intent intent = new Intent(this, GetStartedActivity.class);
            intent.putExtra("nameLogin", nameLogin);
            startActivity(intent);
        });


        new Handler().postDelayed(() -> {
             Intent intent = new Intent(this, GetStartedActivity.class);
             intent.putExtra("nameLogin", nameLogin);
             startActivity(intent);
        },16000);

        videoLoginAnim = findViewById(R.id.videoLoginAnim);
        //Creating MediaController
        MediaController mediaController= new MediaController(this);
        mediaController.setAnchorView(videoLoginAnim);

        //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.rocket);
        //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
        videoLoginAnim.setVideoURI(uri);
        videoLoginAnim.requestFocus();
        videoLoginAnim.start();

    }
}