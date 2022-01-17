package com.app.yuru.ui.transition;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterMain extends BaseAdapter {

    final Context context;
    final List<String> id;
    final List<String> fileName;
    final List<String> traits;
    final List<String> duration;
    final List<String> thumb;
    final List<String> fileURL;

    public AdapterMain(Context context, List<String> id, List<String> fileName,
                       List<String> traits, List<String> duration, List<String> thumb, List<String> fileURL) {
        this.context = context;
        this.id = id;
        this.fileName = fileName;
        this.traits = traits;
        this.duration = duration;
        this.thumb = thumb;
        this.fileURL = fileURL;
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


        cardsub.setOnClickListener(v -> {

                    Intent intent = new Intent(context, VideoActivity.class);
                    intent.putExtra(Constants.VIDEO_LINK, fileURL.get(position));
                    context.startActivity(intent);

                }

        );

        Picasso.get().load(thumb.get(position)).fit().noFade().centerCrop().into(videoView);


        return convertView;
    }

}
