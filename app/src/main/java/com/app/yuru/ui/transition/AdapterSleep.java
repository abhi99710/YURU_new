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
    List<String> ids;
    List<String> language_slug;
    List<String> gender;
    List<String> traint;
    List<String> sleep_id;
    List<String> duration;
    List<String> url;

    public AdapterSleep(Context context, List<String> ids, List<String> language_slug, List<String>
            gender, List<String> traint, List<String> sleep_id, List<String> duration, List<String> url) {
        this.context = context;
        this.ids = ids;
        this.language_slug = language_slug;
        this.gender = gender;
        this.traint = traint;
        this.sleep_id = sleep_id;
        this.duration = duration;
        this.url = url;
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
            intent.putExtra(Constants.VIDEO_LINK, url.get(position)/*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*/);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        ImageView sleep_videoview;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sleep_videoview = itemView.findViewById(R.id.sleep_videoview);

        }
    }
}
