package com.app.yuru.ui.transition;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TtsAdapter extends RecyclerView.Adapter<TtsAdapter.Myholder> {
     final Context context;
     final List<String> idParent;
    final List<String> fileName;
    final List<String> gender;
    final List<String> languages;
    final List<String> duration;
    final List<String> thumb;
    final List<String> fileURL;
    ClickPosition clickPosition;
    ClickInterface clickInterface;

    public TtsAdapter(Context context, List<String> idParent, List<String> fileName, List<String> gender, List<String> languages,
                      List<String> duration, List<String> thumb, List<String> fileURL, ClickPosition clickPosition, ClickInterface clickInterface) {
        this.context = context;
        this.idParent = idParent;
        this.fileName = fileName;
        this.gender = gender;
        this.languages = languages;
        this.duration = duration;
        this.thumb = thumb;
        this.fileURL = fileURL;
        this.clickPosition = clickPosition;
        this.clickInterface = clickInterface;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_transition_to_sleep, parent, false);

        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        holder.tts_videoview.setOnClickListener(v -> {
            clickPosition.clickPos(position);
            clickInterface.urlGet(fileURL.get(position), "");
//            Intent intent = new Intent(context, VideoActivity.class);
//            intent.putExtra(Constants.VIDEO_LINK, fileURL.get(position)/*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*/);
//            context.startActivity(intent);
        });

//        Picasso.get().load("https://i.pinimg.com/originals/e9/37/ec/e937ece4a014308c3e3685ff2dc4f751.jpg")
//                .fit().centerCrop().noFade().into(holder.tts_videoview);
        Picasso.get().load(thumb.get(position))
                .fit().centerCrop().noFade().into(holder.tts_videoview);


    }

    @Override
    public int getItemCount() {
        return idParent.size();
    }

    public static class Myholder extends RecyclerView.ViewHolder {
        final ImageView tts_videoview;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            tts_videoview = itemView.findViewById(R.id.tts_videoview);

        }
    }
}
