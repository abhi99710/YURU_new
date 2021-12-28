package com.app.yuru.ui.transition;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;

public class SleepEnhancerNew extends Fragment {

    private ImageView right_new_sleep1, left_new_sleep1;
    private VideoView sleep_video1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sleep_enhancer_new, container, false);

        sleep_video1 = view.findViewById(R.id.sleep_video1);

        //Creating MediaController
        MediaController mediaController= new MediaController(getContext());
        mediaController.setAnchorView(sleep_video1);

        //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/R.raw/" + R.raw.moonset);
        //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
        sleep_video1.setVideoURI(uri);
        sleep_video1.requestFocus();
        sleep_video1.start();

        right_new_sleep1 = view.findViewById(R.id.right_new_sleep1);
        left_new_sleep1  = view.findViewById(R.id.left_new_sleep1);

        left_new_sleep1.setOnClickListener(v->
        {
            Intent intent = new Intent(getContext(), AnimationOnLeft.class);
            startActivity(intent);
        });

        right_new_sleep1.setOnClickListener(v->
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framwQts, new SleepEnhancer2New()).commit();
        });

        return view;
    }
}