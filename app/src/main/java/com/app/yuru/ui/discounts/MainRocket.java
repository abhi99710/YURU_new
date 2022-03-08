package com.app.yuru.ui.discounts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;
import com.app.yuru.ui.coupons.Journals;
import com.app.yuru.ui.transition.TransitionActivity;

public class MainRocket extends AppCompatActivity {
    private ImageView rockettop, rochetFog;
    private ImageView rocketmiddle;
    private ImageView rocketbottom;
    private VideoView rocketVideo;
    private VideoView vids_rocketBlast;
    private ConstraintLayout clRocketBlast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rocket);

        rocketmiddle = findViewById(R.id.rocketmiddle);
        rockettop = findViewById(R.id.rockettop);
        rocketbottom = findViewById(R.id.rocketbottom);
        rocketVideo = findViewById(R.id.vidMainRocket);
        rochetFog = findViewById(R.id.rochetFog);
        vids_rocketBlast = findViewById(R.id.vids_rocketBlast);
        clRocketBlast = findViewById(R.id.clRocketBlast);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String frstRckt = getIntent().getStringExtra("first_rocket");  // from wakeup save click

        if(frstRckt.equalsIgnoreCase("journal")){  //from add journal save click
            TranslateAnimation animation = new TranslateAnimation(010f, 0f, 50f, 30f);
            animation.setDuration(2000);
            animation.setFillAfter(true);
            rockettop.setAnimation(animation);

            rockettop.animate().x(0f).y(-20f).translationYBy(-20f).setDuration(1000);

        }else if(frstRckt.equalsIgnoreCase("cal")){  // from calenderV click
//            rockettop.setAnimation(animation);
//            rocketmiddle.setAnimation(animation);
//            rocketbottom.setAnimation(animation);
//            rochetFog.setAnimation(animation);

            /*rochetFog.setVisibility(View.INVISIBLE);
            rocketbottom.setVisibility(View.INVISIBLE);
            rocketmiddle.setVisibility(View.INVISIBLE);
            rockettop.setVisibility(View.INVISIBLE);
            rocketVideo.setVisibility(View.INVISIBLE);*/

            clRocketBlast.setVisibility(View.INVISIBLE);

            // play video

            vids_rocketBlast.setVisibility(View.VISIBLE);
            //Creating MediaController
            MediaController mediaController= new MediaController(this);
            mediaController.setAnchorView(rocketVideo);

            //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.rocketblast);
            //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
            vids_rocketBlast.setVideoURI(uri);
            vids_rocketBlast.requestFocus();
            vids_rocketBlast.start();

            new Handler().postDelayed(() -> {
                startActivity(new Intent(MainRocket.this, TransitionActivity.class));

            },16000);
        }





        rockettop.setOnClickListener(v->{
            TranslateAnimation animation = new TranslateAnimation(010f, 0f, 50f, -30f);
            animation.setDuration(5000);
            animation.setFillAfter(true);
            rockettop.setAnimation(animation);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(MainRocket.this, Journals.class));
            },5000);
        });

        rocketmiddle.setOnClickListener(v->{
            rocketVideo.setVisibility(View.VISIBLE);

            //Creating MediaController
            MediaController mediaController= new MediaController(this);
            mediaController.setAnchorView(rocketVideo);

            //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

            Uri uri = Uri.parse("android.resource://" + getPackageName() + "/R.raw/" + R.raw.rocketmid);
            //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
            rocketVideo.setVideoURI(uri);
            rocketVideo.requestFocus();
            rocketVideo.start();

            TranslateAnimation animation1 = new TranslateAnimation(010f, 0f, 50f, 30f);
            animation1.setDuration(2000);
            animation1.setFillAfter(true);
            rocketmiddle.setAnimation(animation1);

            new Handler().postDelayed(() -> {
                rocketVideo.setVisibility(View.INVISIBLE);
            },10000);

        });

        rocketbottom.setOnClickListener(v->{
            TranslateAnimation animation2 = new TranslateAnimation(010f, 0f, 50f, 30f);
            animation2.setDuration(2000);
            animation2.setFillAfter(true);
            rocketbottom.setAnimation(animation2);
            rochetFog.setAnimation(animation2);

            new Handler().postDelayed(() -> {
                startActivity(new Intent(MainRocket.this, Survey.class));
            },2000);

        });
    }
}