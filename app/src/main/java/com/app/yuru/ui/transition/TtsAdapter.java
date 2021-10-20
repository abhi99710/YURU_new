package com.app.yuru.ui.transition;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;

import java.util.List;

public class TtsAdapter extends RecyclerView.Adapter<TtsAdapter.Myholder> {
     Context context;
     List<String> idParent;
    List<String> title;
    List<String> idChild;
    List<String> transition_id;
    List<String> medium;
    List<String> language_slug;
    List<String> filename;
    List<String> duration;

    public TtsAdapter(Context context, List<String> idParent, List<String> title, List<String> idChild, List<String> transition_id,
                      List<String> medium, List<String> language_slug, List<String> filename, List<String> duration) {
        this.context = context;
        this.idParent = idParent;
        this.title = title;
        this.idChild = idChild;
        this.transition_id = transition_id;
        this.medium = medium;
        this.language_slug = language_slug;
        this.filename = filename;
        this.duration = duration;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_transition_to_sleep, parent, false);

        return new TtsAdapter.Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        holder.tts_videoview.setOnClickListener(v -> {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra(Constants.VIDEO_LINK, "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return idParent.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        final ImageView tts_videoview;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            tts_videoview = itemView.findViewById(R.id.tts_videoview);
        }
    }
}
