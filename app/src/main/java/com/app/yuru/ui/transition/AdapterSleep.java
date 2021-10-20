package com.app.yuru.ui.transition;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;
import com.app.yuru.corescheduler.player.video.ui.VideoActivity;
import com.app.yuru.corescheduler.utils.Constants;

import java.util.List;

public class AdapterSleep extends RecyclerView.Adapter<AdapterSleep.MyHolder> {
    Context context;
    List<String> idParent;
    List<String> language_slug;
    List<String> gender;
    List<String> traint;
    List<String> idChild;
    List<String> duration;
    List<String> filename;

    public AdapterSleep(Context context, List<String> idParent, List<String> language_slug, List<String> gender, List<String> traint,
                        List<String> idChild, List<String> duration, List<String> filename) {
        this.context = context;
        this.idParent = idParent;
        this.language_slug = language_slug;
        this.gender = gender;
        this.traint = traint;
        this.idChild = idChild;
        this.duration = duration;
        this.filename = filename;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclersleep, parent, false);
        return new AdapterSleep.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.sleep_videoview.setOnClickListener(v->
        {
            Intent intent = new Intent(context, VideoActivity.class);
            intent.putExtra(Constants.VIDEO_LINK, /*filename.get(position)*/"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return idParent.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView sleep_videoview;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sleep_videoview = itemView.findViewById(R.id.sleep_videoview);

        }
    }
}
