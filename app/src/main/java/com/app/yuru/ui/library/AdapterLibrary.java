package com.app.yuru.ui.library;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.yuru.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterLibrary extends RecyclerView.Adapter<AdapterLibrary.Myholder> {

    private Context context;
    private List<ModelLibrary> list;

    public AdapterLibrary(Context context, List<ModelLibrary> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdapterLibrary.Myholder(LayoutInflater.from(context).inflate(R.layout.libraries, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {

        Picasso.get().load(list.get(position).getAuthorImg()).fit().noFade().centerCrop()
                .into(holder.circleImageView);
        Picasso.get().load(list.get(position).getThumb()).fit().noFade().centerCrop()
                .into(holder.imageViewVideo);

        holder.duration.setText(list.get(position).getDuration());

        holder.videoname.setText(list.get(position).getNameVideo());

        holder.Description.setText(list.get(position).getDescription());

        holder.playBtn.setOnClickListener(v->{
            Intent intent = new Intent(context, LibraryPlayVideos.class);
            intent.putExtra("link", list.get(position).getVideoLink());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {
        ImageView imageViewVideo, circleImageView, playBtn;
        TextView duration, videoname, Description;
        public Myholder(@NonNull View itemView) {
            super(itemView);

            imageViewVideo = itemView.findViewById(R.id.imageViewVideo);
            circleImageView = itemView.findViewById(R.id.circleImageView);
            playBtn = itemView.findViewById(R.id.playBtn);
            duration = itemView.findViewById(R.id.duration);
            videoname = itemView.findViewById(R.id.videoname);
            Description = itemView.findViewById(R.id.Description);

        }
    }
}
