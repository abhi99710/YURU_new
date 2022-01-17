package com.app.yuru.ui.transition;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.app.yuru.R;

public class AnimLeft extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anim_left, container, false);

        VideoView videoLeftAnim = view.findViewById(R.id.videoLeftAnim);
        Button skipLeft = view.findViewById(R.id.skipLeft);

        new Handler().postDelayed(() -> requireActivity().getSupportFragmentManager().beginTransaction().add(R.id.framwQts, new SleepEnhancer2()).commit(),16000);

//        videoLoginAnim = findViewById(R.id.videoLoginAnim);
        //Creating MediaController
        MediaController mediaController= new MediaController(getContext());
        mediaController.setAnchorView(videoLeftAnim);

        //specify the location of media file
//        Uri uri= Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/media/1.mp4");

        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/R.raw/" + R.raw.rocket);
        //Setting MediaController and URI, then starting the videoView
//        videoLoginAnim.setMediaController(mediaController);
        videoLeftAnim.setVideoURI(uri);
        videoLeftAnim.requestFocus();
        videoLeftAnim.start();

        return view;
    }
}