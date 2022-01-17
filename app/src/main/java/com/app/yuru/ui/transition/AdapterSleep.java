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
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterSleep extends RecyclerView.Adapter<AdapterSleep.MyHolder> {
    final Context context;
    final List<String> ids;
    final List<String> traint;
    final List<String> thumb;
    final List<String> duration;
    final List<String> url;

    public AdapterSleep(Context context, List<String> ids,  List<String> traint, List<String> thumb, List<String> duration, List<String> url) {
        this.context = context;
        this.ids = ids;
        this.traint = traint;
        this.thumb = thumb;
        this.duration = duration;
        this.url = url;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclersleep, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {


        Picasso.get().load("https://i.pinimg.com/originals/e9/37/ec/e937ece4a014308c3e3685ff2dc4f751.jpg").fit().into(holder.sleep_videoview);

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

    public static class MyHolder extends RecyclerView.ViewHolder {
        final ImageView sleep_videoview;
        public MyHolder(@NonNull View itemView) {
            super(itemView);

            sleep_videoview = itemView.findViewById(R.id.sleep_videoview);

        }
    }
}
