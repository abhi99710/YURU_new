package com.app.yuru.ui.transition;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterMain extends BaseAdapter {

    final Context context;
    final List<String> id;
    final List<String> fileName;
    final List<String> traits;
    final List<String> duration;
    final List<String> thumb;
    final List<String> fileURL;
    ClickInterface clickInterface;

    public AdapterMain(Context context, List<String> id, List<String> fileName,
                       List<String> traits, List<String> duration, List<String> thumb, List<String> fileURL, ClickInterface clickInterface) {
        this.context = context;
        this.id = id;
        this.fileName = fileName;
        this.traits = traits;
        this.duration = duration;
        this.thumb = thumb;
        this.fileURL = fileURL;
        this.clickInterface = clickInterface;
    }

    @Override
    public int getCount() {
        return id.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridsubjects, parent, false);
        }


        ConstraintLayout cardsub = convertView.findViewById(R.id.cardsub);
        ImageView videoView = convertView.findViewById(R.id.gridIMageView);


//        cardsub.setOnClickListener(v -> {
//
//                    Intent intent = new Intent(context, VideoActivity.class);
//                    intent.putExtra(Constants.VIDEO_LINK, fileURL.get(position));
//                    context.startActivity(intent);
////
////            apiListVideos(id.get(position));
////                    idCLickDialog();
////
//                }
//
//        );

        Picasso.get().load("https://media.giphy.com/media/WoWpouO164dBS/giphy.gif").fit().noFade().centerCrop().into(videoView);


        return convertView;
    }





}
