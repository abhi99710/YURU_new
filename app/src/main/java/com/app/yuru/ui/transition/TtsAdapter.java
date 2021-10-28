package com.app.yuru.ui.transition;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
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
    Activity activity;

    public TtsAdapter(Context context, List<String> idParent, List<String> title, List<String> idChild, List<String> transition_id,
                      List<String> medium, List<String> language_slug, List<String> filename, List<String> duration, Activity activity) {
        this.context = context;
        this.idParent = idParent;
        this.title = title;
        this.idChild = idChild;
        this.transition_id = transition_id;
        this.medium = medium;
        this.language_slug = language_slug;
        this.filename = filename;
        this.duration = duration;
        this.activity = activity;
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
            intent.putExtra(Constants.VIDEO_LINK, filename.get(position)/*"http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4"*/);
            context.startActivity(intent);
        });

        holder.save_ttsvids.setOnClickListener(v -> {
//            context.startActivity(new Intent(activity, SleepEnhancer.class));
            Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show();

//            FragmentManager fragmentManager = activity.getFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager
//                    .beginTransaction();
//            fragmentTransaction.replace(R.id.framwQts, new SleepEnhancer());
//            fragmentTransaction.commit();


        });

        holder.title_tv.setText(title.get(position));
    }

    @Override
    public int getItemCount() {
        return idParent.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        final ImageView tts_videoview;
        final TextView save_ttsvids, title_tv;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            tts_videoview = itemView.findViewById(R.id.tts_videoview);
            save_ttsvids = itemView.findViewById(R.id.save_ttsvids);
            title_tv = itemView.findViewById(R.id.title_tv);
        }
    }
}
